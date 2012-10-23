(ns motific-web.sanitize
  (:require [motific-web.models.words :as words]))

(def ^{:private true} int-rgx #"^\d+$")
(def ^{:private true} api-translations {:min :min-length :max :max-length :exact :exact-length})

(defn intify-or-default
  "Given a default (defv) and desired value (setv),
    it will attempt to coerce the `setv` to an integer.
    If it fails, it will defer to the default value."
  ([defv setv]
    (if (string? setv)
      (if (re-matches int-rgx setv) (Integer/parseInt setv) defv)
      setv)))

(defn translate-params
  "Translate a map from the API keys [min, max, exact] to
    what motific expects: [min-length, max-length, exact-length]"
  ([hsh]
    (into {}
      (for [[k v] hsh] [(or (k api-translations) k) v]))))

(defn clean-params
  "Will clean / intify a `hsh` of parameters for use with the 'random' action."
  ([hsh]
    (merge-with intify-or-default words/random-defaults (translate-params hsh))))
