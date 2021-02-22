(ns clojure-noob.groupby-and-sum)


(def ms [{:a 1 :b "b"} {:a 2 :b "c"} {:a 3 :b "b"} {:a 4 :b "c"} {:a 3 :b "d"}])
(def mss [{:a 1 :b "b" :c "a" :d "hi"}
          {:a 2 :b "b" :c "a" :d "hi"}
          {:a 2 :b "c" :c "a" :d "hello"}
          {:a 3 :b "b" :c "e" :d "bye"}
          {:a 4 :b "b" :c "e" :d "bye"}
          {:a 4 :b "c" :c "c" :d "adios"}
          {:a 4 :b "c" :c "c" :d "adios1"}
          {:a 3 :b "d" :c "e" :d "shalom"}])


;nir
(defn work [data]
  (let [grouping (group-by :b data)]
    (for [[k v] grouping
          :let [sum (reduce + 0 (map :a v))]]
      {:a sum :b k})))

;ben
(defn fold-by1
  ([kf op coll]
   (persistent!
     (reduce
        (fn [ret x]
          (let [k (kf x)]
            (assoc! ret k (op (get ret k) x))))
        (transient {}) coll))))


(defn dimfold
  [group-dim op-dim op ms]
  (vals
    (fold-by1
       group-dim
       (fn [m1 m2]
         (if m1
           (update m1 op-dim op (op-dim m2)))
         m2)
       ms)))

;lior
(defn group-by-and-sum-all-other-keys [map-list group-key]
  (for [r (group-by group-key map-list)]
    (assoc (apply merge-with + (map #(dissoc % group-key) (val r)))
      group-key (key r))))


(defn group-by-and-sum-specific-key [map-list group-key sum-key]
  (for [[k v] (group-by group-key map-list)]
    {group-key k
      sum-key   (reduce + 0 (map sum-key v))}))


;mine
(defn group-and-sum [maps-coll groupings-key-list sum-key]
  (let [grouped (vals (group-by #(select-keys % groupings-key-list) maps-coll))
        ai-vals (map (fn [coll] (reduce + (map #(sum-key %) coll))) grouped)
        merged (map #(reduce merge %) grouped)]
    (loop [aa merged
           ll ai-vals
           res '()]
      (if (not-empty aa)
        (let [aaa (first aa)
              lll (first ll)]
          (recur (next aa) (next ll) (conj res (assoc aaa sum-key lll))))
        res))))

(defn group-by-and-sum-specific-key2 [map-list group-key sum-key]
  (for [[k v] (group-by group-key map-list)]
    (let [res {group-key k
               sum-key   (reduce + 0 (map sum-key v))}]
      (merge res (dissoc (reduce merge (seq v)) group-key sum-key)))))


(defn group-by-and-sum-specific-key1 [map-list group-keys sum-key]
  (for [[k v] (group-by #(select-keys % group-keys) map-list)]
    (merge {sum-key   (reduce + 0 (map sum-key v))} k)))

(defn group-by-requested-dimensions [map-list group-keys sum-key]
  (for [[k v] (group-by #(select-keys % group-keys) map-list)]
    (merge {sum-key   (reduce + 0 (map sum-key v))} k)))


;ben
(defn fold-by
  ([kf op coll]
   (persistent!
    (reduce
     (fn [ret x]
       (let [k (kf x)
             v (get ret k)]
         (assoc! ret k (if (some? v)
                         (op v x)
                         (op (op) x)))))
     (transient {})
     coll))))
(defn- map-combiner
  [m]
  (fn
    ([]
     (reduce-kv
      (fn [acc k f]
        (assoc acc k (f)))
      {}
      m))
    ([rec]
     (reduce-kv
      (fn [acc k f]
        (let [v (get rec k)]
          (assoc acc k (f v))))
      {}
      m))
    ([m1 m2]
     (reduce-kv
      (fn [acc k f]
        (let [v1 (get m1 k)
              v2 (get m2 k)]
          (assoc acc k (f v1 v2))))
      {}
      m))))
(defn foldmaps
  [idx ops ms]
  (vals (fold-by idx (map-combiner ops) ms)))

(defn- right
  ([] nil)
  ([_ b] b))

(foldmaps (juxt :b :c)
          {:a +
           :b right
           :c right}
          mss)




;=========

(defn check-for-error
  "Gets a list of http reponses and check if one of them is not 200,
   in case that there is one or more that are not 200, return the first one..."
  [& ds-response]
  (loop [res ds-response]
    (if (not (empty? res))
      (do
        (let [frst (first res)]
          (if (or (nil? frst)
                  (empty? frst)
                  (= 200 (:status frst)))
            (recur (rest res))
            frst)))
      nil)))
