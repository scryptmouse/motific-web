(ns motific-web.output)

(defn- distinct-by-length
  "Sort a collection (of words) first by the length of the word,
    then alphabetical sort."
  ([coll]
    (distinct (sort-by #(vector (count %) %) coll))))

(defn to-words
  "Transform a collection `coll` to be encased in a map under the key 'words'.
  
  Optional parameter `need_sort` to run through distinct-by-length"
  ([coll & {:keys [need_sort] :or {need_sort false}}]
    {:words ((if need_sort distinct-by-length identity) coll)}))

(defn to-errors
  ([errs]
    {:errors errs}))
