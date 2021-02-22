(ns sandbox.intersect
  (:require [clojure.set :as cset]
            [clojure.string :as s]
            [jsonista.core :as j]))


(defn find-and-substring
  []
  (let [rt ["one_1" "two_2" "four_4" "five_5"]
        conf ["one_" "two_" "three_"]]
    ;expected ["1" "2" "four_4" "five_5"]]
    (loop [c conf rr rt]
      (prn "c:" c)
      (prn "rr:" rr)
      (prn)
      (if (not= 0 (count c))
        (do
          (recur
            (next c)
            (mapv (fn [x] (if (.contains x (first c)) (subs x (count (first c))) x)) rr)))
        (prn "final " (distinct rr))))))


(defn test-it-v1 []
  (let [incoming-topics ["one_1" "some_1" "three_3" "another_1"]
        body "[{\"media_source\":\"liftoff_int\",\"1\": 16},
            {\"media_source\":\"tappx_int\",\"3\": 11367}]"
        b (j/read-value body j/keyword-keys-object-mapper)
        rename-keys-fn (fn [k1 b]
                         (if (not (nil? (s/index-of k1 "_")))
                           (let [k (keyword k1)
                                 kk (keyword (subs k1 (inc (s/index-of k1 "_"))))]
                             (mapv #(cset/rename-keys % {kk k}) b))))]

    (mapv #(rename-keys-fn % b) incoming-topics)))


;(defn test-it-v2 []
;  (let [incoming-topics ["one_1" "some1" "three_3" "another1"]
;        body "[{\"media_source\":\"liftoff_int\",\"1\": 16},
;            {\"media_source\":\"tappx_int\",\"3\": 11367}]"
;        b (j/read-value body j/keyword-keys-object-mapper)
;        rename-keys-fn (fn [m topics]
;                         (if (not (nil? (s/index-of k1 "_")))
;                           (let [k (keyword k1)
;                                 kk (keyword (subs k1 (inc (s/index-of k1 "_"))))]
;                             (mapv #(cset/rename-keys % {kk k}) b))))]
;
;    (mapv #(rename-keys-fn % incoming-topics) b)))