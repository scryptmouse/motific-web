(defproject motific-web "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :dependencies [[org.clojure/clojure "1.3.0"]
                           [noir "1.2.1"]
                           [motific "0.0.1-SNAPSHOT"]
                           [dieter "0.2.0"]]
            :dev-dependencies [[codox "0.6.1"]]
            :min-lein-version "1.7.1"
            :main motific-web.server)

