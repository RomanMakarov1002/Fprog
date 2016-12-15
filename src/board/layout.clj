(ns board.layout
  (:require 
            [selmer.parser :as parser]
            [selmer.filters :as filters]
            [markdown.core :refer [md-to-html-string]]
            [ring.util.http-response :refer [content-type ok]]))

(declare ^:dynamic *app-context*)
(parser/set-resource-path!  (clojure.java.io/resource "templates"))
(filters/add-filter! :markdown (fn [content] [:safe (md-to-html-string content)]))

(defn render
  [template & [params]]
  (content-type
    (ok
      (parser/render-file
        template
          (assoc params :page template)))
    "text/html; charset=utf-8"))

(defn error-page [error-details]
  {:status  (:status error-details)
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :body    (parser/render-file "error.html" error-details)})