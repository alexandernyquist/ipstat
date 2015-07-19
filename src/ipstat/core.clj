(ns ipstat.core
  (:require [clj-http.client :as http])
  (:gen-class)
  (:use
    ring.middleware.params
    ring.adapter.jetty))

; Utils

(defn mnth
	"Returns a specific element from a jagged array"
	[coll a b]
	(nth (nth coll a) b))

; Iplocation.net

(defn iplookup
  "Executes a RPC against iplocation.net and returns a hashmap with IP info"
  [ip]
  (def response (http/get (str "https://www.iplocation.net/?query=" ip)))
  (def parts (re-seq #"<td(\swidth='80')?>([^<]+)</td>" (:body response)))
  (hash-map :ip (mnth parts 0 2)
            :region (mnth parts 1 2)
            :city (mnth parts 2 2)
            :isp (mnth parts 3 2)))

; Web API handlers

(defn app
  [{params :params client-ip :remote-addr}]
  (def ip (or (params "ip") client-ip))
  (def ipstat (iplookup ip))
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (str ipstat)})

(defn -main
  "Entry point, baby."
  [& args]
  (run-jetty (wrap-params app) {:port 3000}))