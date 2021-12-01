(ns day-1.core
  (:gen-class)
  (:require [clojure.string :as str]))

(defn get-depth [measurements]
  (->> measurements
       (map #(Integer/parseInt %))
       (partition 2 1)
       (filter #(> (second %) (first %)))
       (count)))

(defn main
  [& args]
  (println (get-depth (str/split-lines (slurp "src/day_1/input.txt")))))
