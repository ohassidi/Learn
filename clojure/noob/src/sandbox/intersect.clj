(ns sandbox.intersect
  (:require [clojure.set :as cset]
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
        (prn "final "(distinct rr))))))



(defn find-and-addstring
  []
  (let [incoming-topics ["one_1" "some1" "three_3" "another1"]
        used-topics ["1" "some1" "3" "another1"]]
        ;expected ["1" "2" "four_4" "five_5"]]
    (loop [c used-topics rr incoming-topics]
      (prn "c:" c)
      (prn "rr:" rr)
      (prn)
      (if (not= 0 (count c))
        (do
          (recur
            (next c)
            (mapv #(cset/rename-keys % {:installs :blocked_installs}) used-topics)))
        (prn "final " (distinct rr))))))



(let [incoming-topics ["one_1" "some1" "three_3" "another1"]
      body "[{\"media_source\":\"liftoff_int\",\"1\": 16},
            {\"media_source\":\"tappx_int\",\"3\": 11367}]"
      b (j/read-value body j/keyword-keys-object-mapper)
      rename-keys-fn (fn [k b]
                       (let [kk (subs k (inc (s/index-of k "_")))]
                         (prn (keyword kk))
                         (if ((keyword kk) b) (cset/rename-keys b {kk k}))))]
  (prn)
  (mapv #(rename-keys-fn % b) incoming-topics))