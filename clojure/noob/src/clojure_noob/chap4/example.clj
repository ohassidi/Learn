(ns clojure-noob.chap4.example)

;apply ;==============================================================================

;return the max number of all arguments
(max 0 1 2)
; => 2

;need to get individuals and no collection
(max [0 1 2])
; => [0 1 2]

;explodes the collection and pass it to the max function
(apply max [0 1 2])
; => 2


;partial ;==============================================================================

(def add10 (partial + 10))
(add10 3)
; => 13
(add10 5)
; => 15

(def add-missing-elements
  (partial conj ["water" "earth" "air"]))

(add-missing-elements "unobtainium" "adamantium")
; => ["water" "earth" "air" "unobtainium" "adamantium"]

;==============================================================================

;complement

(defn my-complement
  [fun]
  (fn [& args]
    (not (apply fun args))))

(def my-pos? (complement neg?))
(my-pos? 1)
; => true

(my-pos? -1)
; => false