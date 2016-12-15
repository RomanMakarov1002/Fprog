(ns board.dao.protocol.userprotocol)

(defprotocol user-rep-protocol
	(create [this params])
	(read [this username]))