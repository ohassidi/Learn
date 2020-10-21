(ns clojure-noob.hashmap-manipulation
  (:require [cheshire.core :as json]))



(def jc "{\"android_ios_onelink_install_cuid\":{\"all_sites\":true,\"site_ids\":[]},\"fraud_bad_model\":{\"all_sites\":true,\"site_ids\":[\"148871\",\n      \"151529\",\n      \"126365_\"]},\"ms-B\":{\"all_sites\":true,\"site_ids\":[]}}")
(def jc-clj (json/parse-string jc keyword))
(def ks (keys jc-clj))
(def res (assoc {} (first ks) (get-in jc-clj [(first ks) :site_ids])))


(def r (loop [k (keys jc-clj)
              m {}]
         (if (empty? k)
           m
           (recur (rest k)
                  (assoc m (first k) (get-in jc-clj [(first k) :site_ids]))))))




;(def accumulated-str "{\"requests\":79,\"branch\":{\"passed\":80},\"master\":{\"passed\":79}}")
(def accumulated-str "{\"requests\":79,\"branch\":{\"passed\":80},\"master\":{\"passed\":79}, \"messages\":[\"hi\"]}")
(def accumulated (json/parse-string accumulated-str keyword))

(defn add-val-2-map [acc]
  (let [m (merge {:messages []} acc)]
    (update m :messages
            (fn [m] (conj m "hello")))))



(add-val-2-map accumulated)


