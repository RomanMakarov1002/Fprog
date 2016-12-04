(ns goods.dal.dao.post-dao
  (:require [goods.dal.protocols.post-dal-protocol :as post-protocol]
            [goods.dal.models.post-model :as post-model]
            [clojure.java.jdbc :as jdbc]
            [goods.dal.db :as db]))

(deftype post-dao [db-map]

  post-protocol/post-db-protocol

  (add-post [this new_post]
    (jdbc/execute! db/db-map ["INSERT INTO posts (user_id,
                                        post_title,
                                        post_content)
                   values ((SELECT id FROM users WHERE name=?)
                           ?,
                           ?)"
                              (:user_id new_post)
                              (:post_title new_post)
                              (:post_content new_post)]))


  (get-all-posts [this]
    (into [] (jdbc/query db/db-map ["SELECT id, user_id, post_title, post_content FROM posts"]
                         :row-fn #(post-model/->post_model
                                    (:id %1)
                                    (:user_id %1)
                                    (:post_title %1)
                                    (:post_content %1)))

                ))

  (get-post-by-id [this id]
    (first (jdbc/query db/db-map ["SELECT id, user_id, post_title, post_content FROM posts WHERE id = ?" id]
                         :row-fn #(post-model/->post_model
                                    (:id %1)
                                    (:user_id %1)
                                    (:post_title %1)
                                    (:post_content %1)))

          ))

  (search-post-by-title [this title_query]
    (into [] (jdbc/query db/db-map ["SELECT id, user_id, post_title, post_content FROM posts WHERE post_title Like =?" title_query]
                         :row-fn #(post-model/->post_model
                                    (:id %1)
                                    (:user_id %1)
                                    (:post_title %1)
                                    (:post_content %1)))

          ))

  (update-post [this updated_post]
    (jdbc/execute! db/db-map
                   ["UPDATE posts as t
                JOIN users as u
                  ON u.name=?
                SET  user_id=?,
                    post_title=?,
                    post_content=?
                WHERE t.id=?"
                    (:assignee updated_post)
                    (:post_title updated_post)
                    (:post_content updated_post)
                    (Integer. (re-find #"[0-9]*" (:id updated_post)))]))
  )
