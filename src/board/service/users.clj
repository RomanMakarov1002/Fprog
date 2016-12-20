(ns board.service.users
  (:require [noir.util.crypt :as crypt] 
            [board.dao.user :as user]))

(def userdao (user/->user-rep))


(defn encrypt [{password :password :as user}]
  (assoc user :password (crypt/encrypt password)))

(defn admin? [session]
  (get session :admin))

(defn is-correct-email? [email]
  (let [value (java.util.regex.Pattern/compile "([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}")]
    (let [res (.matcher value email)]
      (.matches res))))

(defn is-alphabetical [str]
  (let [value (java.util.regex.Pattern/compile "[A-Za-z]{1,45}")]
    (let [res (.matcher value str)]
      (.matches res))))

(defn is-valid-username [str]
  (let [value (java.util.regex.Pattern/compile "[A-Za-z0-9]{6,45}")]
    (let [res (.matcher value str)]
      (.matches res))))

(defn registration [{{:keys [username password first_name last_name country city email phone] :as user} :params} success error] ;;
  (try
    (if (and ((and (is-alphabetical [last_name]) (is-alphabetical [first_name]))) (is-valid-username [username]))
      (if-not (.read userdao username )
        (if (is-correct-email? email)
          (do ((.create userdao (merge user {:role "user"})))
              (success))
          (error)
          ))
      (error))
    (catch Exception exception
      (error)
      )))

(defn login [{{:keys [username password] :as user} :params session :session} error success]
  (let [{id :id stored-pass :password userrole :role} (.read userdao username)]
    (if (and stored-pass (= password stored-pass))
      (if (= userrole "admin")
        (-> (success) (assoc :session (assoc session :user_id id :admin true :username username)))
        (-> (success) (assoc :session (assoc session :user_id id :username username))))
      (error)))
  )

(defn logout [{session :session} success]
  (->(success) (assoc :session (assoc session :admin nil :user_id nil :username nil))))


