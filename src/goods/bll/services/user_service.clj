(ns goods.bll.services.user-service
  (:require [goods.bll.protocols.user-protocol :as user-protocol]
            [goods.dal.models.user-model :as user-model]
            ))

(deftype user-service [user-model]

  user-protocol/user-service-protocol

  (sign-in [this login password]
    (.sign-in user-model login password))

  (get-user-by-login [this login]
    (def user (.get-user-by-login user-model login))
    (println user)
    user)

  )

