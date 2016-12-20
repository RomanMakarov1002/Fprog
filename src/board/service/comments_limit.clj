
;
;(ns board.service.comments-limit
;  (:require [board.service.logger :as logger]))
;
;(def comments-count (atom 0))
;(def comments-limit 10)
;
;(defn adding-comment []
;  (swap! comments-count inc)
;  (if (== (@comments-count comments-limit))
;    (false))
;    (true))
;
;(def count-of-registration (atom 0))
;(def count-to-log 5)
;
;(defn registration-of-user []
;  (swap! count-of-registration inc)
;  (if (== (mod @count-of-registration count-to-log) 0)
;    (log/logger-pattern (str "Count of registration") (str " = " @count-of-registration))))
