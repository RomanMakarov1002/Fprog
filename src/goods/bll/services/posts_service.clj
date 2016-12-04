(ns goods.bll.services.posts-service
  (:require [goods.bll.protocols.posts-protocol :as posts-protocol]
            [goods.dal.models.post-model :as post-model])
  )


(deftype post-service [post-model]

  posts-protocol/posts-service-protocol

  (add-post [this new_post]  (.add-post post-model))
  (get-all-posts [this] (def response (.get-all-posts post-model))
    (println "\n----------------TASK ITEMS-------------\n" response)
    response)
  (get-post-by-id [this id] "Select post by id")
  (get-posts-count[this take_count skip_count] "Select posts by take and skip")
  (search-post-by-title [this title_query] "Select posts by title-name")
  (update-post [this updated_post] "Update post")

  )