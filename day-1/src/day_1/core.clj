(ns day-1.core
  (:gen-class)
  (:require [clojure.string :as str]))

(defn get-depth [measurements]
  (->> measurements
       (partition 2 1)
       (filter #(> (second %) (first %)))
       (count)))

(defn get-depth-from-sums [measurements]
  (->> measurements
       (partition 3 1)
       (map #(reduce + %))
       (get-depth)))

(defn -main
  [& args]
  (let [measurements (map #(Integer/parseInt %) (str/split-lines (slurp "src/day_1/input.txt")))]
    (println (str "part 1: " (get-depth measurements)))
    (println (str "part 2: " (get-depth-from-sums measurements)))))
