(ns board.dao.protocol.userprotocol)

(defprotocol user-rep-protocol
	(create [this params])
	(read [this username])
	(read-by-id [this id]))