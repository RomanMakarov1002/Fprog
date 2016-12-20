(ns board.service.dsl
  (:require [board.dao.dsldao :as dsldao] ))

(defn split-by-whitespace [s]
  (clojure.string/split s #"\s+"))

(defn code-do [{{:keys [my-code] :as code}:params session :session} success error]
  (dsldao/insert-obj my-code)
  (success))