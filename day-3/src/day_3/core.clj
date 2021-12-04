(ns day-3.core
  (:gen-class)
  (:require [clojure.string :as str]))

(defn parse-input [report-as-string]
  (->> report-as-string
       (str/split-lines)
       (map #(str/split % #""))
       (map #(map read-string %))))

(defn round [number] (int (+ number 0.5)))

(defn flip-bit [bit] (if (= bit 0) 1 0))

(defn decimal-from-binary-string [binary-string] (read-string (str "2r" binary-string)))

(defn most-common-bits [report]
  (->> report
       (reduce (partial mapv +))
       (map #(/ % (count report)))
       (map round)))

(defn get-row-matching-pattern [report get-bit-pattern]
  (loop [column 0 rows report]
    (let [pattern (get-bit-pattern rows)]
      (if (= (count rows) 1)
        (nth rows 0)
        (recur (inc column) (filter #(= (nth % column) (nth pattern column)) rows))))))

(defn -main
  [& args]
  (let [
        report (parse-input (slurp "src/day_3/input.txt"))
        gamma-rate (most-common-bits report)
        epsilon-rate (map flip-bit gamma-rate)
        oxygen-generator-rating (get-row-matching-pattern report most-common-bits)
        co2-scrubber-rating (get-row-matching-pattern report #(map flip-bit (most-common-bits %)))
       ]
    (println (str "part 1: " (* (decimal-from-binary-string (str/join gamma-rate)) (decimal-from-binary-string (str/join epsilon-rate)))))
    (println (str "part 2: " (* (decimal-from-binary-string (str/join oxygen-generator-rating)) (decimal-from-binary-string (str/join co2-scrubber-rating)))))))
