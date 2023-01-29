(ns apiiro.search.core
  (:import (clojure.lang MapEntry)))

;; please implement search engine that has two functions
;; 1. suggest - that gets a string and return top 10 words that start with that string
;; 2. search - that increment a counter of how much this word been search for
;;
;; solution
;; we will have 2 maps:
;; 1. keeps all words with their counter
;; 2. keeps suggestions with the top 10 words as a list

(def store
  "store all search words with their counter, for example 'book, 1'"
  (atom {}))

(def suggests
  "Stores all suggestions, map contains string and list of max 10 string values"
  (atom {}))

(defn create-suggest-combination [word]
  (let [e (atom word)
        l (atom '())]
    (while (not-empty @e)
      (swap! l conj @e)
      (reset! e (apply str (drop-last @e))))
    @l))

(defn sort-by-val [m]
  (into (sorted-map-by
          (fn [key1 key2]
            ;; comparing only by value can result unexpected result since the values are not unique
            (compare [(get m key2) key2]
                     [(get m key1) key1])))
        m))

(defn take-x [x m]
  ;; since inserting into map order is not guarantee we need to sort again
  (sort-by-val
    ;; take the first x keys from the map and build a new map
    (into {}
          (map #(MapEntry. % (get m %))
               (take x (keys m))))))

(defn add-word-to-suggestions-top-10-list
  "Build the list of top 10"
  [combination word rank]
  (take-x 10 (sort-by-val (assoc (get @suggests combination) word rank))))

(defn upsert-suggestion [word rank]
  ;; go over all word combination
  ;; 1. in case combination exists check store if word exists and rank, and push it to map with rank as key
  ;; 2. not exists - add the combination with the word and rank
  (map #(let [w (get @suggests %)]
          (if (nil? w)
            (swap! suggests assoc % (assoc {} word rank))
            (swap! suggests assoc % (add-word-to-suggestions-top-10-list % word rank))))
       (create-suggest-combination word)))

(defn search
  "will increase the counter of each word in the store map
   after that it will create if needed suggest combinations from the given word
   and add it to the suggest map"
  [word]
  (let [word-rank (inc (get @store word 0))]
      (swap! store assoc word word-rank)
      (upsert-suggestion word word-rank)))

(defn suggest
  "Will return all strings that contains the given string as prefix with current word's rank"
  [prefix]
  (get @suggests prefix))

(defn reset []
  (reset! store {})
  (reset! suggests {}))

(defn current []
  {:store @store
   :suggests @suggests})

(comment
  (current)
  (reset))
