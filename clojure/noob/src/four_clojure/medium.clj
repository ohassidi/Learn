(ns four-clojure.medium)

;; Take a set of functions and return a new function that takes a variable number of arguments and returns a
;; sequence containing the result of applying each function left-to-right to the argument list.
;(= [21 6 1] ((__ + max min) 2 3 5 1 6 4))
;(= ["HELLO" 5] ((__ #(.toUpperCase %) count) "hello"))
;(= [2 6 4] ((__ :a :c :b) {:a 2, :b 4, :c 6, :d 8 :e 10}))

((fn [& args] (filter clojure.test/function? args)) + max min)

(defn foo [& funcs] ;the functions
  (fn [& input_vars]
    (loop [f funcs v input_vars r []]
      (if (empty? f)
        r
        (do
          (recur
            (rest f)
            v
            (conj r
                  (cond
                    (string? (first v)) ((first f) (first v))
                    (number? (first v)) (reduce (first f) v)
                    (map? (first v)) ((first f) (first v))))))))))

; another option
(fn [& fs]
  (fn [& args]
    (reduce #(conj %1 (apply %2 args)) [] fs)))


