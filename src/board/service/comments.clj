(ns board.service.comments
  (:use compojure.core)
  (:require [board.dao.comment :as comment]
            [board.dao.user :as user]))

(def commentdao (comment/->comment-rep))
(def userdao (user/->user-rep))

(def now
  (str (java.sql.Timestamp. (System/currentTimeMillis))))

(defn is-valid-comment [str]
  (let [value (java.util.regex.Pattern/compile "[A-Za-z0-9\\s\\.]{1,150}")]
    (let [res (.matcher value str)]
      (.matches res))))

(defn me [session]
  (get session :user_id))

(defn create [{{:keys [post_id content] :as comment} :params session :session}]
  (if (is-valid-comment content)
    (.create commentdao
             (merge comment {:created_at now :user_id (me session)}))
    nil)
  )

(defn read [id]
  (.read commentdao id))

(defn all [post_id]
  (sort-by :created_at #(compare %2 %1)
           (.all commentdao post_id))
  )
;;((println (simple_comment :user_id)))
(defn detailed_comments [post_id]
  (for [simple_comment (all post_id)]  (assoc simple_comment :user (.read-by-id userdao (simple_comment :user_id )))))

(defn delete [{{:keys [id] :as id} :params}]
  (let [{post_id :post_id} (.read commentdao id)]
    (.delete commentdao id)))