(ns motific-web.models.words
  (:require [motific.word-list :as wl]
            [motific.anagram :as anagrams]
            [noir.validation :as vali]
            [motific-web.output :as output]))

(def word-list (wl/generate "./resources/eowl.txt"))
(def random-defaults {:quantity 25 :max-length nil :min-length nil :exact-length nil})

(defn valid-permutable?
  "Check if a given `word` is valid to use with permute."
  ([word]
    (vali/rule (vali/min-length? word 2)
               [:word "Must be at least two letters long to permutate"])
    (vali/rule (vali/max-length? word 8)
               [:word "Can't be any longer than eight letters"])
    (not (vali/errors? :word))))

(defn valid-anagramable?
  "Check if a given `word` is valid to use with anagram."
  ([word]
    (vali/rule (vali/min-length? word 2)
               [:word "Must be at least two letters long to search."])
    (vali/rule (vali/max-length? word 10)
               [:word "There are no words in the dictionary longer than ten letters."])
    (not (vali/errors? :word))))

(defn anagram
  "Find anagrams of `word` in `word-list`"
  ([word]
    (if (valid-anagramable? word)
      (output/to-words (anagrams/find-in-list word-list word) :need_sort true)
      (vali/on-error :word output/to-errors))))

(defn random
  "Returns random words from `word-list` given parameters `opts`"
  ([opts]
    (output/to-words (apply (partial wl/random word-list) (interleave (keys opts) (vals opts))))))

(defn permutate
  "Returns all possible permutations of `word`"
  ([word]
    (if (valid-permutable? word)
      (output/to-words (anagrams/permutate word) :need_sort true)
      (vali/on-error :word output/to-errors))))

