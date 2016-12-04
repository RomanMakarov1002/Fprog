(ns goods.dal.models.user-model)

(defrecord user-model [id, login, first_name, last_name, date_of_birth, country, city, email, phone_number, role, pass])
