(ns board.dao.protocol.categoryprotocol)


(defprotocol category-rep-protocol
  (create [this params])
  (delete [this id]))