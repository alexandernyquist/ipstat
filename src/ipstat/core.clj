(ns ipstat.core
  (:gen-class)
  (:require [clj-http.client :as http]))

(defn mnth
	"Returns a specific element from a jagged array"
	[coll a b]
	(nth (nth coll a) b))

(defn iplookup
  "Executes a RPC against iplocation.net and returns a hashmap with IP info"
  [ip]
  (def response (http/get (str "https://www.iplocation.net/?query=" ip)))
  (def parts (re-seq #"<td(\swidth='80')?>([^<]+)</td>" (:body response)))
  (hash-map :ip (mnth parts 0 2)
            :region (mnth parts 1 2)
            :city (mnth parts 2 2)
            :isp (mnth parts 3 2)))

(defn -main
  "Entry point, baby."
  [& args]
  (def ipinfo (iplookup "8.8.4.4"))
  (print ipinfo))