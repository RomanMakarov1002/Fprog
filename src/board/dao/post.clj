(ns board.dao.post  
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.java.jdbc.sql :as sql]
            [board.dao.protocol.postprotocol :as postprotocol]
            [board.dao.connector :as connector]))

(defrecord post-rep [] postprotocol/post-rep-protocol
	
	(create [this params]
  		(jdbc/insert! connector/db :post params))
	
	(read [this id]
  		(first (jdbc/query connector/db
    	(sql/select * :post (sql/where {:id id})))))

	(all [this]
  		(jdbc/query connector/db (sql/select * :post)))

	(update [this id params]
  		(jdbc/update! connector/db :post params (sql/where {:id id})))

	(delete [this id]
  		(jdbc/delete! connector/db :post (sql/where {:id id}))))
