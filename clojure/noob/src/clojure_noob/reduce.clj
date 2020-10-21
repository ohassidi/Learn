(ns clojure-noob.reduce)

;; sum with reduce
(reduce + [1 2 3 4]) ; => 10
(reduce - [1 2 3 4]) ;

;This is like telling Clojure to do this:
(+ (+ (+ 1 2) 3) 4)

(reduce + 15 [1 2 3 4]) ; give an initial value

(defn my-reduce
  ([f initial coll]
   (loop [result initial
          remaining coll]
     (if (empty? remaining)
       result
       (recur (f result (first remaining)) (rest remaining)))))
  ([f [head & tail]]
   (my-reduce f head tail)))


;==============================================================================

(reduce (fn [new-map [key val]]
          (assoc new-map key (inc val)))
        {}
        {:max 30 :min 10})
; => {:max 31, :min 11}