(ns goods.dal.dao.user-dao
  (:require [goods.dal.protocols.user-dal-protocol :as user-protocol]
            [goods.dal.models.user-model :as user-model]
            [clojure.java.jdbc :as jdbc]))

(deftype user-dao [db-map]

  user-protocol/user-db-protocol

  (sign-in
    [this login password]
    (first (jdbc/query db-map
                       ["SELECT *
                        FROM users
                        WHERE login=? AND password=?" login password])))
  ;COUNT(*) as count
  (get-user-by-login
    [this login]
    (first (jdbc/query db-map
                       ["SELECT id, login, first_name, last_name, date_of_birth, county, city,  email, phone_number, role, pass
                        FROM users
                        WHERE login=?" login]
                       :row-fn #(user-model/->user-model
                                  (:id %1)
                                  (:login %1)
                                  (:first_name %1)
                                  (:last_name %1)
                                  (:date_of_birth %1)
                                  (:country %1)
                                  (:city %1)
                                  (:email %1)
                                  (:phone_number %1)
                                  (:role %1)
                                  (:pass %1)))))
  )