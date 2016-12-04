(ns goods.dal.protocols.user-dal-protocol)

(defprotocol user-db-protocol
  (sign-in [this login password])
  (get-user-by-login [this login]))