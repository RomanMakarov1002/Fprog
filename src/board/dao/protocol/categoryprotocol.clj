(ns board.dao.protocol.categoryprotocol)


(defprotocol category-rep-protocol
  (create [this params])
  (delete [this id])
  (read-by-id [this id])
  (update [this id params])
  (read-all [this]))