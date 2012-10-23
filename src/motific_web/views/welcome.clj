(ns motific-web.views.welcome
  (:require [motific-web.views.common :as common]
            [motific-web.models.words :as words]
            [motific.anagram :as anagram]
            [motific.word-list :as word-list])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))

(defpage "/" []
  (common/layout :active "anagram"))

(defpage "/random" []
  (common/layout :active "random"))

(defpage "/permutate" []
  (common/layout :active "permutate"))

(defpage "/anagram" []
  (common/layout :active "anagram"))
