(ns board.views.view
  (:require [board.service.posts :as posts]
        [board.service.users :as users]
        [board.service.comments :as comments]
        [board.service.categories :as categories]
        [board.layout :as layout]))
  (use 'selmer.parser)

(def users-activity-count (atom 0))

(defn main [{session :session}]
  (swap! users-activity-count inc)
  (layout/render "main.html" {:posts (posts/get-detailed-all) :session session :user-activity @users-activity-count}))

(defn signup [] 
  (layout/render "signup.html"))

(defn login [] 
  (layout/render "login.html"))

(defn add-post []
  (layout/render "add-post.html" {:categories (categories/get-all)}))

(defn add-category []
  (layout/render "add-category.html"))

(defn view-post [{{:keys [id] :as id} :params session :session}]
  (swap! users-activity-count inc)
  (layout/render "post.html" {:comments (comments/detailed_comments id) :post (posts/get-detailed id) :session session})
  )

(defn sql-code []
  (layout/render "dsl-test.html"))

(defn edit-post [{{:keys [id] :as id} :params}]
  (layout/render "edit-post.html" {:post (posts/read id)}))

(defn not-auth [] 
  (layout/render "error.html" (merge {:status 403 :title "You are not authorized."})))