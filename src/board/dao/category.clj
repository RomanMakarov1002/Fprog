(ns board.dao.category
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.java.jdbc.sql :as sql]
            [board.dao.protocol.categoryprotocol :as categoryprotocol]
            [board.dao.connector :as connector]))


(defrecord category-rep [] categoryprotocol/category-rep-protocol

  (create [this params]
    (jdbc/insert! connector/db :category {:category_name (:category_name params)} ))

  (delete [this id]
    (jdbc/delete! connector/db :category (sql/where {:id id})))

  (read-by-id [this id]
    (first (jdbc/query connector/db
      (sql/select * :category (sql/where {:id id})))))

  (read-all [this]
    (jdbc/query connector/db (sql/select * :category)))

  (update [this id params]
    (jdbc/update! connector/db :category params (sql/where {:id id}))))
