(ns board.dao.comment
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.java.jdbc.sql :as sql]
            [board.dao.protocol.commentprotocol :as commentprotocol]
            [board.dao.connector :as connector]))

(defrecord comment-rep [] commentprotocol/comment-rep-protocol
	
	(create [this params]
  		(jdbc/insert! connector/db :comment params))
	
	(read [this id]
  		(first (jdbc/query connector/db
    	(sql/select * :comment (sql/where {:id id})))))

	(all [this post_id]
	  	(jdbc/query connector/db
	    (sql/select * :comment (sql/where {:post_id post_id}))))

	(delete [this id]
  		(jdbc/delete! connector/db :comment (sql/where {:id id}))))
