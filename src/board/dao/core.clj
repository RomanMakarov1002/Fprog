(ns board.dao.core
  (:use
    [clojure.template])

  (:require [clojure.java.jdbc :as jdbc]
            [board.dao.helper :as helper]
            [board.dao.render :as render]) (:import (board.dao.helper SqlLike)))


(defn fetch-all [db relation]
  (let [request (helper/to-sql-params relation)]
    (jdbc/query db request :result-set-fn vec)))

(defn fetch-one [db relation]
  (first (fetch-all db relation)))

;---- WHERE(EXPRESSION) -------

(defn where* [query expr]
  (assoc query :where (helper/conj-expression (:where query) expr)))

(defn- canonize-operator-symbol
  [op]
  (get '{not= <>, == =} op op))

(defn prepare-expression [e]
  (if (seq? e)
    `(vector
       (quote ~(canonize-operator-symbol (first e)))
       ~@(map prepare-expression (rest e)))
    e))

(defmacro where
  [q body]
  `(where* ~q ~(prepare-expression body)))

;----- JOINS ------

(defn join* [{:keys [tables joins] :as q} type alias table on]
  (let [a (or alias table)]
    (assoc q
      :tables (assoc tables a table)
      :joins (conj (or joins []) [a type on]))))

(do-template [join-name join-key]

             (defmacro join-name
               ([relation alias table cond]
                `(join* ~relation ~join-key ~alias ~table ~(prepare-expression cond)))
               ([relation table cond]
                `(let [table# ~table]
                   (join* ~relation ~join-key nil table# ~(prepare-expression cond)))))

             join-inner :inner,
             join :inner,
             join-right :right,
             join-left :left,
             join-full :full)

;----- SIMPLE ------

(defn limit [relation v]
  (assoc relation :limit v))

(defn fields [query fd]
  (assoc query :fields fd))

(defn from
  ([q table] (join* q nil table table nil))
  ([q table alias] (join* q nil table alias nil)))

;---- MAIN -------

(defmacro select
  [& body]
  `(-> (map->Select {}) ~@body))


(defrecord Select [fields where order joins tables offet limit]
  SqlLike
  (helper/as-sql [this] (helper/as-sql (render/render-select this))))

;---- FIELD -------

(defn- map-vals
  [f m]
  (into {} (for [[k v] m] [k (f v)])))

(defn- prepare-fields [fs]
  (map-vals prepare-expression fs))

(defn fields* [query fd]
  (assoc query :fields fd))

(defmacro fields [query fd]
  `(fields* ~query ~(prepare-fields fd)))

