(ns motific-web.server
  (:require [noir.server :as server]
            [motific-web.config :as config]
            [dieter.core :as dieter]))

(server/load-views "src/motific_web/views/")

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (server/add-middleware dieter/asset-pipeline config/dieter)
    (server/start port {:mode mode
                        :ns 'motific-web})))

