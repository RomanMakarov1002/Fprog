(ns board.dao.protocol.postprotocol)

(defprotocol post-rep-protocol
	(create [this params])
	(read [this username])
	(all [this])
	(update [this id params])
	(delete [this id]))
