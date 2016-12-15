(ns board.dao.protocol.commentprotocol)

(defprotocol comment-rep-protocol
	(create [this params])
	(read [this username])
	(all [this id])
	(delete [this id]))