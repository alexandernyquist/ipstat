(defproject ipstat "0.1.0-SNAPSHOT"
  :description "A simple JSON API around iplocation.net"
  :url "http://github.com/alexandernyquist/ipstat"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
					[clj-http "0.9.1"]
					[ring/ring-core "1.3.2"]
					[ring/ring-jetty-adapter "1.3.2"]
					[cheshire "5.5.0"]]
  :main ^:skip-aot ipstat.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
