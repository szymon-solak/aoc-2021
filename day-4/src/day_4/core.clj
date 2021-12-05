(ns day-4.core
  (:gen-class)
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn parse-board [raw-board]
  (->> raw-board
       (#(str/split % #"\n"))
       (map str/trim)
       (map #(str/split % #"\s+"))
       (map #(map read-string %))))

(defn parse-input [raw-input]
  (let [[raw-numbers raw-boards] (str/split raw-input #"\n\n" 2)]
    (vector
     (map read-string (str/split raw-numbers #","))
     (map parse-board (filter #(> (count %) 0) (str/split raw-boards #"\n\n"))))))

(defn transpose [matrix] (apply map list matrix))

(defn is-winning-board [numbers board]
  (or
   (> (count (filter #(= (count (set/intersection (set numbers) (set %))) (count (nth board 0))) board)) 0)
   (> (count (filter #(= (count (set/intersection (set numbers) (set %))) (count board)) (transpose board))) 0)))

(defn get-final-score [final-numbers board]
  (* (reduce + (set/difference (set (flatten board)) (set final-numbers))) (last final-numbers)))

(defn get-winning-numbers-for-board [numbers board]
  (loop [turn 1]
    (let [numbers-in-turn (take turn numbers)]
      (cond
        (is-winning-board numbers-in-turn board) numbers-in-turn
        (= turn (count numbers)) ()
        :else (recur (inc turn))))))

(defn get-boards-in-winning-order [numbers boards]
  (->> boards
       (map-indexed #(vector %1 (get-winning-numbers-for-board numbers %2)))
       (sort-by #(count (second %)))))

(defn -main
  [& args]
  (let [[numbers boards] (parse-input (slurp "src/day_4/input.txt"))
        sorted-boards (get-boards-in-winning-order numbers boards)
        [first-winning-board-index first-winning-board-numbers] (first sorted-boards)
        [last-winning-board-index last-winning-board-numbers] (last sorted-boards)]
    (println (str "part 1: " (get-final-score first-winning-board-numbers (nth boards first-winning-board-index))))
    (println (str "part 2: "(get-final-score last-winning-board-numbers (nth boards last-winning-board-index))))))
