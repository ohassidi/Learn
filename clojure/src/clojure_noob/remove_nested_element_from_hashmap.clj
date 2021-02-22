(ns clojure-noob.remove-nested-element-from-hashmap)

(def d1 {:k1 "k1" :k2 "k2" :k3 "k3" :k4 "k4"})
(def d2 {:k1 "k1" :k2 "k2" :k5 "k5" :k4 "k4"})
(def explicit {:ks1 [:k1]
               :ks2 [:k3 :k4]
               :ks3 [:k1 :k4]})


(defn filter-it
  "filter out keys that are not in the explicit map"
  [data exp]

  ; get all keys that I want in the map
  (flatten (map #(val %) exp))
  (map () data))