(ns board.service.categories
  (:use compojure.core)
  (:require [board.dao.category :as category]))

(def categorydao (category/->category-rep))

(defn create [{{:keys [category_name] :as category} :params}]
  (.create categorydao category))

(defn delete [{{:keys [id] :as id} :params}]
    (.delete categorydao id))

(defn get-all []
  (.read-all categorydao))

