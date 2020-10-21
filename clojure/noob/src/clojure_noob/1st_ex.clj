(ns clojure-noob.1st-ex)

;using str function
(str "hi" " all")

;using vector
(vector "hello " "world")
["hello" 2 "world"]

;list
'("hello" 2 "world")
(list 4 3 "hi")

;hash-map
{:key "value" :name "oded"}
(hash-map :key "value" :city "Kefar Sava")

;hash-set
(hash-set 3 4)
#{5 6}


; fucntion to add 100 to given param
(defn add_100 [x] (+ x 100))
(add_100 3)

;; Write a function, dec-maker, that works exactly like the function inc-maker except with subtraction:
;(def dec9 (dec-maker 9))
;(dec9 10)
; => 1
(defn dec-maker
  "Create a custom decrementor"
  [dec-by]
  (println dec-by)
  #(- % dec-by))

(def dec3 (dec-maker 3))

(dec3 7)



;==============================================================================

(def vampire-database
  {0 {:makes-blood-puns? false, :has-pulse? true  :name "McFishwich"}
   1 {:makes-blood-puns? false, :has-pulse? true  :name "McMackson"}
   2 {:makes-blood-puns? true,  :has-pulse? false :name "Damon Salvatore"}
   3 {:makes-blood-puns? true,  :has-pulse? true  :name "Mickey Mouse"}})

(defn vampire-related-details
  [social-security-number]
  (Thread/sleep 1000)
  (get vampire-database social-security-number))

(defn vampire?
  [record]
  (and (:makes-blood-puns? record)
       (not (:has-pulse? record))
       record))

(defn identify-vampire
  [social-security-numbers]
  (first (filter vampire?
                 (map vampire-related-details social-security-numbers))))

(time (vampire-related-details 0))
(time (def mapped-details (map vampire-related-details (range 0 1000000))))
(time (first mapped-details))
(time (identify-vampire (range 0 1000000)))

;==============================================================================

(defn even-numbers
  ([] (even-numbers 0))
  ([n] (cons n (lazy-seq (even-numbers (+ n 2))))))

(take 10 (even-numbers))
; => (0 2 4 6 8 10 12 14 16 18)∏∏

(cons 0 '(2 4 6))
; => (0 2 4 6)