(ns goods.core
  (:use compojure.core)
  (:require [goods.dal.db :as db]
            [goods.views.view :as view]
            [goods.dal.dao.user-dao :as user-dao]
            [goods.bll.services.user-service :as user-service-d]
            [goods.dal.dao.post-dao :as post-dao]
            [goods.bll.services.posts-service :as posts-service-d]


            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [ring.util.response :as response]))

;Users
(def user-dao-core (user-dao/->user-dao db/db-map))
(def user-service-core (user-service-d/->user-service user-dao-core))

;;Posts
(def posts-dao-core (post-dao/->post-dao db/db-map))
(def posts-service-core (posts-service-d/->post-service posts-dao-core))
;; ---------------------------- USER ACTIONS ----------------------------


(defn get-home-page
  []
  (do
    (response/redirect "/")
    (view/render-home-page (.get-all-posts posts-service-core ))))
;; --------------------------------- ROUTES ------------------------------
(defroutes app-routes
           (GET "/" [] (get-home-page))
           (GET "/home" [:as request] (view/render-home-page (:session request)))
           (GET "/auth" [] (view/render-signin-page))
           (GET "/signup" [] (view/render-signup-page))



           (route/not-found "Page not found")
           (route/resources "/"))

(def engine
  (-> (handler/site app-routes)
      (middleware/wrap-json-body {:keywords? true})
      ))



