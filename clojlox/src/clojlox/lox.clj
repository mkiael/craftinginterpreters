(ns clojlox.lox
  (:require [clojure.string :as str]))

(defn run [input]
  (println "Running " input))

(defn run-file [file-path]
  (run (slurp file-path)))

(defn run-prompt []
  (loop []
      (print "> ")
      (flush)
      (let [line (str/trim (read-line))]
        (if (= line "")
          nil
          (do
            (run line)
            (recur))))))

(defn -main
  [& args]
  (if (> (count args) 1)
    (println "Usage: clojlox [script]")
    (if (= (count args) 1)
      (run-file (first args))
      (run-prompt))))