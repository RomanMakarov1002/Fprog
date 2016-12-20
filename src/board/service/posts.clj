(ns board.service.posts
  (:require [board.dao.post :as post]
            [board.dao.category :as category]))

(def postdao (post/->post-rep))
(def categorydao (category/->category-rep))


(def now
  (str (java.sql.Timestamp. (System/currentTimeMillis))))

(defn is-valid-title [str]
  (let [value (java.util.regex.Pattern/compile "[A-Za-z0-9\\s\\.]{1,150}")]
    (let [res (.matcher value str)]
      (.matches res))))


(defn create [{{:keys [title content userId category_id] :as post} :params}]
  (if (is-valid-title title )
    (.create postdao
     (merge post {:created_at now}))
    nil)
  )

(defn read [id]
  (.read postdao id))

(defn get-detailed [id]
  (let [detailed_post (read id)]
    (assoc detailed_post :category (.read-by-id categorydao (detailed_post :category_id))))
  )

(defn all []
  (sort-by :created_at #(compare %2 %1) (.all postdao)))

(defn get-detailed-all []
  (for [detailed_post (all)] (assoc detailed_post :category (.read-by-id categorydao (detailed_post :category_id))))
  )

(defn update [{{:keys [title content] :as post, id :id} :params}]
  (.update postdao id post))

(defn delete [{{:keys [id] :as id} :params}]
  (.delete postdao id))