(ns board.dao.user
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.java.jdbc.sql :as sql]
            [board.dao.protocol.userprotocol :as userprotocol]
            [board.dao.connector :as connector]))
			
(defrecord user-rep [] userprotocol/user-rep-protocol

	(create [this params]
  		(jdbc/insert! connector/db :user params))

	(read [this username]
  		(first (jdbc/query connector/db
    	(sql/select * :user (sql/where {:username username})))))

	(read-by-id [this id]
		(first (jdbc/query connector/db
											 (sql/select * :user (sql/where {:id id}))))))

