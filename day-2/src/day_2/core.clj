(ns day-2.core
  (:gen-class)
  (:require [clojure.string :as str]))

(defn move-submarine [position command]
  (cond
    (= (first command) "forward") (update position :x + (second command))
    (= (first command) "up") (update position :y - (second command))
    (= (first command) "down") (update position :y + (second command))))

(defn move-submarine-correctly [position command]
  (cond
    (= (first command) "forward") (update (update position :y + (* (position :aim) (second command))) :x + (second command))
    (= (first command) "up") (update position :aim - (second command))
    (= (first command) "down") (update position :aim + (second command))))

(defn parse-input [raw-input]
  (->> raw-input
       (str/split-lines)
       (map #(str/split % #" "))
       (map #(vector (first %) (Integer/parseInt (second %))))))

(defn -main
  [& args]
  (let [
        commands (parse-input (slurp "src/day_2/input.txt"))
        part-1-position (reduce move-submarine { :x 0 :y 0 } commands)
        part-2-position (reduce move-submarine-correctly { :x 0 :y 0 :aim 0 } commands)
       ]
    (println (str "part 1: " (* (part-1-position :x) (part-1-position :y))))
    (println (str "part 2: " (* (part-2-position :x) (part-2-position :y))))))
