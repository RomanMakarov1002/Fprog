(ns goods.bll.protocols.user-protocol)

(defprotocol user-service-protocol
  (sign-in [this login password])
  (get-user-by-login [this login]))