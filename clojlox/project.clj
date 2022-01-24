(defproject clojlox "0.1.0-SNAPSHOT"
  :description "Implementation of jlox in Clojure"
  :dependencies [[org.clojure/clojure "1.10.3"]]
  :main ^:skip-aot clojlox.lox
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
