(ns goods.bll.protocols.posts-protocol)

(defprotocol posts-service-protocol
  (add-post [this new_post]"Add post for posts")
  (get-all-posts [this] "Select all posts from db")
  (get-post-by-id [this id] "Select post by id")
  (get-posts-count[this take_count skip_count] "Select posts by take and skip")
  (search-post-by-title [this title_query] "Select posts by title-name")
  (update-post [this updated_post] "Update post"))
