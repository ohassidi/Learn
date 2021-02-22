(ns clojure-noob.take_and_friends)

(take 3 [1 2 3 4 5 6 7 8])
; => (1 2 3)

(drop 3 [1 2 3 4 5 6 7 8])
; => (4 5 6 7 8)

;==============================================================================

(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])

; the anonymous function is a predicate, and the function test each element in the vector
(take-while #(< (:month %) 3) food-journal) ;return all entries that month is < 3 is a true statement
; => ({:month 1 :day 1 :human 5.3 :critter 2.3}
;     {:month 1 :day 2 :human 5.1 :critter 2.0}
;     {:month 2 :day 1 :human 4.9 :critter 2.1}
;     {:month 2 :day 2 :human 5.0 :critter 2.5})

(drop-while #(< (:month %) 3) food-journal) ;return all entries that month is < 3 is a false statement
; => ({:month 3 :day 1 :human 4.2 :critter 3.3}
;     {:month 3 :day 2 :human 4.0 :critter 3.8}
;     {:month 4 :day 1 :human 3.7 :critter 3.9}
;     {:month 4 :day 2 :human 3.7 :critter 3.6})

(take-while #(< (:month %) 4) (drop-while #(< (:month %) 2) food-journal)) ;this will return all months 2,3
; => ({:month 2 :day 1 :human 4.9 :critter 2.1}
;     {:month 2 :day 2 :human 5.0 :critter 2.5}
;     {:month 3 :day 1 :human 4.2 :critter 3.3}
;     {:month 3 :day 2 :human 4.0 :critter 3.8})

;==============================================================================


;Use filter to return all elements of a sequence that test true for a predicate function.
; Here are the journal entries where human consumption is less than five liters:

(filter #(< (:human %) 5) food-journal)
; => ({:month 2 :day 1 :human 4.9 :critter 2.1}
;     {:month 3 :day 1 :human 4.2 :critter 3.3}
;     {:month 3 :day 2 :human 4.0 :critter 3.8}
;     {:month 4 :day 1 :human 3.7 :critter 3.9}
;     {:month 4 :day 2 :human 3.7 :critter 3.6})

; You might be wondering why we didn’t just use filter in the take-while and drop-while examples earlier.
; Indeed, filter would work for that too. Here we’re grabbing the January and February data,
; just like in the take-while example:

(filter #(< (:month %) 3) food-journal)
; => ({:month 1 :day 1 :human 5.3 :critter 2.3}
;     {:month 1 :day 2 :human 5.1 :critter 2.0}
;     {:month 2 :day 1 :human 4.9 :critter 2.1}
;     {:month 2 :day 2 :human 5.0 :critter 2.5})

; This use is perfectly fine, but filter can end up processing all of your data,
; which isn’t always necessary. Because the food journal is already sorted by date,
; we know that take-while will return the data we want without having to examine any of the data we won’t need.
; Therefore, take-while can be more efficient.

;==============================================================================

(some #(> (:critter %) 5) food-journal)
; => nil

(some #(> (:critter %) 3) food-journal)
; => true

(some #(and (> (:critter %) 3) %) food-journal)
; => {:month 3 :day 1 :human 4.2 :critter 3.3}

;==============================================================================

(sort [3 1 2])
; => (1 2 3)

(sort-by count ["aaa" "c" "bb"])
; => ("c" "bb" "aaa")

(concat [1 2] [3 4])
; => (1 2 3 4)


;==============================================================================

(concat (take 8 (repeat "na")) ["Batman!"])
; => ("na" "na" "na" "na" "na" "na" "na" "na" "Batman!")

(take 3 (repeatedly (fn [] (rand-int 10))))
; => (1 4 0)

;==============================================================================

(conj [0] [1])
; => [0 [1]]