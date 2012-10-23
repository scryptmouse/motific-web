(ns motific-web.views.api
  (:require [noir.response :as resp]
            [motific-web.models.words :as words]
            [motific.anagram :as anagram]
            [motific.word-list :as word-list]
            [motific-web.sanitize :as sani])
  (:use [noir.core :only [defpage render]]))

(defpage "/api/random" {:as params}
  (resp/json (words/random (sani/clean-params params))))

(defpage "/api/permutate" {:keys [word] :or {word ""}}
  (resp/json (words/permutate word)))

(defpage "/api/anagram" {:keys [word] :or {word ""}}
  (resp/json (words/anagram word)))

(defpage "/api/permutate/:word" {:keys [word]}
  (render "/api/permutate" {:word word}))

(defpage "/api/anagram/:word" {:keys [word]}
  (render "/api/anagram" {:word word}))
