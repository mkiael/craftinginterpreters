(ns clojlox.lox
  (:require [clojure.string :as str]))

(defrecord Token [tokentype])

(defn consume-until [input until-ch]
  (let [ch (first input)]
    (if (= ch until-ch)
      input
      (consume-until (rest input) until-ch))))

(defn scan-tokens [input]
  (loop [input input
         tokens []]
    (if (empty? input)
      tokens
      (let [ch (first input)]
        (cond
          (= ch \() (recur (rest input) (conj tokens (->Token :left-paren)))
          (= ch \)) (recur (rest input) (conj tokens (->Token :right-paren)))
          (= ch \{) (recur (rest input) (conj tokens (->Token :left-brace)))
          (= ch \}) (recur (rest input) (conj tokens (->Token :right-brace)))
          (= ch \,) (recur (rest input) (conj tokens (->Token :comma)))
          (= ch \.) (recur (rest input) (conj tokens (->Token :dot)))
          (= ch \-) (recur (rest input) (conj tokens (->Token :minus)))
          (= ch \+) (recur (rest input) (conj tokens (->Token :plus)))
          (= ch \;) (recur (rest input) (conj tokens (->Token :semicolon)))
          (= ch \*) (recur (rest input) (conj tokens (->Token :star)))
          (= ch \!) (if (= (first (rest input)) \=)
                      (recur (drop 2 input) (conj tokens (->Token :bang-equal)))
                      (recur (rest input) (conj tokens (->Token :bang))))
          (= ch \=) (if (= (first (rest input)) \=)
                      (recur (drop 2 input) (conj tokens (->Token :equal-equal)))
                      (recur (rest input) (conj tokens (->Token :equal))))
          (= ch \<) (if (= (first (rest input)) \=)
                      (recur (drop 2 input) (conj tokens (->Token :less-equal)))
                      (recur (rest input) (conj tokens (->Token :less))))
          (= ch \>) (if (= (first (rest input)) \=)
                      (recur (drop 2 input) (conj tokens (->Token :greater-equal)))
                      (recur (rest input) (conj tokens (->Token :greater))))
          (= ch \/) (if (= (first (rest input)) \/)
                      (recur (rest (consume-until input \newline)) tokens)
                      (recur (rest input) (conj tokens (->Token :slash))))
          :else (recur (rest input) tokens))))))

(defn run [input]
  (println (scan-tokens input)))

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