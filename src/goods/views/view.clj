(ns goods.views.view
  (:use hiccup.page
        hiccup.element)
  (:require [goods.views.render :as renderer]))

(defn render-home-page
  [posts]
  (renderer/render "home.html" {:posts posts}))

(defn render-signin-page
  []
  (renderer/render "auth.html"))



(defn render-signup-page
  []
  (renderer/render "signup.html"))

