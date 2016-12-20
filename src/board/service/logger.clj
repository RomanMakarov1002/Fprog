(ns board.service.logger
  (:require [clojure.java.io :as io]
            )
  (:import (java.util Date)))


(defn now [] (.. (Date.) (toString)))

(def log-agent (agent (clojure.java.io/writer "D:\\log.txt" :append true)))

(defn write-out [writer message]
  (.write writer message)
  writer)

(defn flush-and-return [writer]
  (.flush writer)
  writer)

(defn log-message [logger message]
  (send logger write-out message))

;(defn close [writer]
;  (send writer #(.close %)))
;
;(defn close-log [user-role]
;  (send log-agent close))

(defn flush [writer]
  (send writer flush-and-return)
  writer)


(defn log-comment [username date-time post-id comment-content]
  (log-message log-agent (str "User: " username "  Created at: " date-time "  On post: " post-id "  Following comment " comment-content))
  (flush log-agent))