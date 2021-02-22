(ns four-clojure.elementary)


(= true true)
(= (- 10 (* 2 3)) 4)
(= "HELLO WORLD" (.toUpperCase "hello world"))

; lists
(= (list :a :b :c) '(:a :b :c))
(= '(1 2 3 4) (conj '(2 3 4) 1))
(= '(1 2 3 4) (conj '(3 4) 2 1))

; vectors
(= [:a :b :c] (list :a :b :c) (vec '(:a :b :c)) (vector :a :b :c))
(= [1 2 3 4] (conj [1 2 3] 4))
(= [1 2 3 4] (conj [1 2] 3 4))

; sets
(= #{:a :b :c :d} (set '(:a :a :b :c :c :c :c :d :d)))
(= #{:a :b :c :d} (clojure.set/union #{:a :b :c} #{:b :c :d}))
(= #{1 2 3 4} (conj #{1 4 3} 2))

; maps
(= 20 ((hash-map :a 10, :b 20, :c 30) :b))
(= 20 (:b {:a 10, :b 20, :c 30}))
(= {:a 1, :b 2, :c 3} (conj {:a 1} {:b 2} [:c 3]))

; sequences
(= 3 (first '(3 2 1)))
(= 3 (second [2 3 4]))
(= 3 (last (list 1 2 3)))
(= '(20 30 40) (rest [10 20 30 40]))

; functions
(= 8 ((fn add-five [x] (+ x 5)) 3))
(= 8 ((fn [x] (+ x 5)) 3))
(= 8 (#(+ % 5) 3))
(= 8 ((partial + 5) 3))

(= ((partial * 2) 2) 4)
(= (#(* % 2) 3) 6)
(= ((fn [x] (* x 2)) 11) 22)

; Hello World
(= (#(str "Hello, " % "!") "Dave") "Hello, Dave!")

;Sequences: map
(= '(6 7 8) (map #(+ % 5) '(1 2 3))) ; a map takes a function and sequence and apply the function on each sequence entry

;Sequences: filter
(= '(6 7) (filter #(> % 5) '(3 4 5 6 7))) ;takes a predicate function and sequence, return all entries match the filter


;; Reduce takes a 2 argument function and an optional starting value.
;; It then applies the function to the first 2 items in the sequence
;; (or the starting value and the first element of the sequence).
;; In the next iteration the function will be called on the previous return value and the
;; next item from the sequence, thus reducing the entire collection to one value. Don't worry,
;; it's not as complicated as it sounds.
;(= 15 (reduce __ [1 2 3 4 5]))
;(=  0 (reduce __ []))
;(=  6 (reduce __ 1 [2 3]))

#(+ %1 %2)

(comment (= 15 (reduce #(+ %1 %2) [1 2 3 4 5])))
; same as
(comment (= 15 (reduce + [1 2 3 4 5])))


;; A recursive function is a function which calls itself. This is one of the fundamental techniques used in functional programming.
(= '(5 4 3 2 1) ((fn foo [x] (when (> x 0) (conj (foo (dec x)) x))) 5))



;; Clojure lets you give local names to values using the special let-form.
(= 7 (let [x 5] (+ 2 x)))
(= 7 (let [x 3, y 10] (- y x)))
(= 7 (let [x 21] (let [y 3] (/ x y))))

;; When retrieving values from a map, you can specify default values in case the key is not found:
; (= 2 (:foo {:bar 0, :baz 1} 2))
;However, what if you want the map itself to contain the default values?
; Write a function which takes a default value and a sequence of keys and constructs a map.
;(= (__ 0 [:a :b :c]) {:a 0 :b 0 :c 0})
;(= (__ "x" [1 2 3]) {1 "x" 2 "x" 3 "x"})
;(= (__ [:a :b] [:foo :bar]) {:foo [:a :b] :bar [:a :b]})

#(loop [value %1 keys %2 output {}]
   (if (empty? keys)
     output
     (do
       (prn "first key -" (first keys) "value: " value "output -" output)
       (recur value (rest keys) (into output {(first keys) value})))))