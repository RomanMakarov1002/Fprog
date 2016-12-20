(ns board.dao.dsldao
  (:require
            [clojure.string :as str]
            [clojure.java.jdbc :as jdbc]
            [clojure.java.jdbc.sql :as sql]
            [board.dao.protocol.postprotocol :as postprotocol]
            [board.dao.connector :as connector]
            [board.dao.core :as dbcore]
            [clojure.string :as str]
            ))

(def namsps)


(def current-ns ((meta #'namsps) :ns))


(defn get-objects [objectKey]
  (dbcore/fetch-all connector/db (dbcore/select (dbcore/from objectKey))))

(defn get-object-by-id [objectKey id]
  (let [db-key (keyword (str "id_" (name objectKey)))]
    (str (dbcore/fetch-one connector/db (dbcore/select (dbcore/from objectKey) (dbcore/where (== db-key id)))))))

(defn insert-object [objectKey username password]
  (let [generated (jdbc/insert! connector/db objectKey {:username username :password password})]))


(defn insert-category [objectKey category-name]
  (let [generated (jdbc/insert! connector/db objectKey {:category_name category-name})]))

(defn delete-object [objectKey username]
  (jdbc/delete! connector/db objectKey (sql/where {:username username})))

(defn update-pass [objectKey object]
  (println (:username object))
  (jdbc/update! connector/db objectKey object (sql/where {:username (:username object)})))


(defn insert-obj [query]
  (println query)
  (binding [*ns* current-ns]
    (load-string (str "("query")")))
  )
