(ns board.dao.render
  (:require  [board.dao.helper :as helper]))

(declare render-operator)
(declare render-expression)

(defn- function-symbol? [s]
  (re-matches #"\w+" (name s)))

(def NONE (helper/->Sql "" nil))


(defn render-limit [s]
  (if-let [l (:limit s)]
    ['LIMIT l]
    NONE))

(defn render-field [[alias nm]]
  (if (= alias nm)
    nm  ; просто имя столбца
    [(render-expression nm) 'AS alias]))

(defn render-fields
  [{:keys [fields]}]
  (if (or (nil? fields) (= fields :*))
    '*
    (interpose (symbol ",") (map render-field fields))))

(defn render-table
  [[alias table]]
  (if (= alias table)
    table
    [table 'AS alias]))

(defn render-join-type
  [jt]
  (get
    {nil (symbol ",")
     :cross '[CROSS JOIN],
     :left '[LEFT OUTER JOIN],
     :right '[RIGHT OUTER JOIN],
     :inner '[INNER JOIN],
     :full '[FULL JOIN],
     jt jt}))

(defn render-from
  [{:keys [tables joins]}]
  (if (not (empty? joins))
    ['FROM
     (let [[a jn] (first joins)
           t (tables a)]
       (assert (nil? jn))
       (render-table [a t]))
     (for [[a jn c] (rest joins)
           :let [t (tables a)]]
       [(render-join-type jn)
        (render-table [a t])
        (if c ['ON (render-expression c)] NONE)])]

    NONE))

(defn render-operator
  [op & args]
  (let [ra (map render-expression args)
        lb (symbol "(")
        rb (symbol ")")]
    (if (function-symbol? op)
      [op lb (interpose (symbol ",") ra) rb]
      [lb (interpose op (map render-expression args)) rb])))

(defn render-expression
  [etree]
  (if (and (sequential? etree) (symbol? (first etree)))
    (apply render-operator etree)
    etree))

(defn render-where
  [{:keys [where]}]
  (if where
    ['WHERE (render-expression where)]
    NONE))

(defn render-select
  [select]
  ['SELECT
   (mapv
     #(% select)
     [render-fields
      render-from
      render-where
      render-limit])])


