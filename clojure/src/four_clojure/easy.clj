(ns four-clojure.easy)

;1. last element. not using last
(defn my-last
  [sq]
  (let [candidate (vec (rest sq))]
    (if (= (count candidate) 1) (get candidate 0) (my-last candidate))))

(= (my-last [1 2 3 4 5]) 5)
(= (my-last ['(1 2) '(3 4) '(5 6)]) '(5 6))
(= (#(get % (- (count %) 1)) [1 2 3 4 5]) 5)

;;2. Write a function which returns the second to last element from a sequence.
;; (= (__ (list 1 2 3 4 5)) 4)
;; (= (__ ["a" "b" "c"]) "b")
;; (= (__ [[1 2] [3 4]]) [1 2])

#(if (list? %)
   (nth % (- (count %) 2))
   (get % (- (count %) 2)))

;;3. Write a function which returns the Nth element from a sequence.
;(= (__ '(4 5 6 7) 2) 6)
;(= (__ [:a :b :c] 0) :a)
;(= (__ [1 2 3 4] 1) 2)
;(= (__ '([1 2] [3 4] [5 6]) 2) [5 6])

#(first (drop %2 %1))

;;4. Write a function which returns the total number of elements in a sequence.
;(= (__ '(1 2 3 3 1)) 5)
;(= (__ "Hello World") 11)
;(= (__ [[1 2] [3 4] [5 6]]) 3)
;(= (__ '(13)) 1)
;(= (__ '(:a :b :c)) 3)

#(loop [c 0 sq %1]
   (if (not (empty? (rest sq)))
     (recur (+ 1 c) (rest sq)) ;yes
     (+ 1 c))) ;no

;;5. Write a function which reverses a sequence.
;(= (__ [1 2 3 4 5]) [5 4 3 2 1])
;(= (__ (sorted-set 5 7 2 7)) '(7 5 2))
;(= (__ [[1 2][3 4][5 6]]) [[5 6][3 4][1 2]])

#(loop [old-sq %1 new-sq []]
   (prn "old:" (drop 1 old-sq) ", new:" new-sq)
   (if (empty? (take 1 old-sq))
     (identity new-sq) ;yes
     (recur (drop 1 old-sq) (concat (take 1 old-sq) new-sq)))) ;no

;;6. Write a function which returns the sum of a sequence of numbers.
;(= (__ [1 2 3]) 6)
;(= (__ (list 0 -2 5 5)) 8)
;(= (__ #{4 2 1}) 7)
;(= (__ '(0 0 -1)) -1)
;(= (__ '(1 10 3)) 14)
;(= (__ '(1 10 3)) 14)

#(loop [sq %1 sum 0]
   (prn "old:" (drop 1 sq) ", sum:" sum)
   (if (empty? (take 1 sq))
     (identity sum) ;yes
     (recur (drop 1 sq) (+ (first sq) sum)))) ;;) ;no

;;7. Write a function which returns only the odd numbers from a sequence.
;(= (__ #{1 2 3 4 5}) '(1 3 5))
;(= (__ [4 2 1 6]) '(1))
;(= (__ [2 2 4 6]) '())
;(= (__ [1 1 1 3]) '(1 1 1 3))

#(loop [old-sq %1 odd-sq []]
   (prn "old:" old-sq ", odd-sq:" odd-sq)
   (if (nil? (first old-sq))
     (identity odd-sq) ;yes
     (recur (rest old-sq) (if (odd? (first old-sq))
                            (concat odd-sq (take 1 old-sq))
                            odd-sq))))


;;8. Write a function which returns the first X fibonacci numbers.
;(= (__ 3) '(1 1 2))
;(= (__ 6) '(1 1 2 3 5 8))
;(= (__ 8) '(1 1 2 3 5 8 13 21))

#(loop [amount %1 start 0 next 1 res []]
   (if (<= amount 0)
     res
     (recur (dec amount) next (+ start next) (into res (list next)))))

;;9. Write a function which returns true if the given sequence is a palindrome.
;Hint: "racecar" does not equal '(\r \a \c \e \c \a \r)
;(false? (__ '(1 2 3 4 5)))
;(true? (__ "racecar"))
;(true? (__ [:foo :bar :foo]))
;(true? (__ '(1 1 3 3 1 1)))
;(false? (__ '(:a :b :c)))

#(loop [input %1]
   (prn input)
   (cond
     (>= (count input) 2) (if (= (first input) (last input))
                            (recur (rest (drop-last 1 input)))
                            false)
     (< (count input) 2) true))

#(= (seq %) (reverse %))

;;10. Write a function which flattens a sequence.
;(= (__ '((1 2) 3 [4 [5 6]])) '(1 2 3 4 5 6))
;(= (__ ["a" ["b"] "c"]) '("a" "b" "c"))
;(= (__ '((((:a))))) '(:a))
;((1 2) 3 [4 [5 6]]) -> (1 2 3 [4 5 6])) -> (1 2 3 4 5 6)

;(loop [col '((1 2) 3 [4 [5 6]])]
;  (if (not (empty? col))
;    (reduce (fn [result input]
;              (if (not (sequential? input))
;                (concat result (recur input))))
;                (concat rest (list input))
;            ()
;            col)))

(comment
  ((fn flatten-seq1
     [input-seq]
     (reduce (fn [acc item]
               (prn item)
               (if (coll? item)
                 (concat acc (flatten-seq1 item))
                 (concat acc (cons item ()))))
             ()
             input-seq))

   '((1 2) 3 [4 [5 6]])))

;; 11. Write a function which takes a string and returns a new string containing only the capital letters.
;(= (__ "HeLlO, WoRlD!") "HLOWRD")
;(empty? (__ "nothing"))
;(= (__ "$#A(*&987Zf") "AZ")

;; 12. Lexical scope and first-class functions are two of the most basic building blocks of a
;; functional language like Clojure. When you combine the two together, you get something very powerful
;; called lexical closures. With these, you can exercise a great deal of control over the lifetime of
;; your local bindings, saving their values for use later, long after the code you're running now has
;; finished.
;It can be hard to follow in the abstract, so let's build a simple closure. Given a positive integer n,
; return a function (f x) which computes xn. Observe that the effect of this is to preserve the value of
; n for use outside the scope in which it is defined.
;(= 256 ((__ 2) 16),
;       ((__ 8) 2))
;(= [1 8 27 64] (map (__ 3) [1 2 3 4]))
;(= [1 2 4 8 16] (map #((__ %) 2) [0 1 2 3 4]))

(fn pp
  [power]
  (fn po [in]
    (apply * (repeat power in))))

(= 256 (((fn pp
           [power]
           (fn po [in]
             (apply * (repeat power in))))
         2) 16))

(defn pp1
  [power]
  (fn po [in]
    (apply * (repeat power in))))

(= 256 ((pp1 2) 16))

;; the main/upper function return a function that applies the power parameter to the current given one



;; 13. Your friend Joe is always whining about Lisps using the prefix notation for math.
;; Show him how you could easily write a function that does math using the infix notation.
;; Is your favorite language that flexible, Joe?
;; Write a function that accepts a variable length mathematical expression consisting of numbers and the
;; operations +, -, *, and /. Assume a simple calculator that does not do precedence and instead
;; just calculates left to right.
;(= 7  (__ 2 + 5))
;(= 42 (__ 38 + 48 - 2 / 2))
;(= 8  (__ 10 / 2 - 1 * 2))
;(= 72 (__ 20 / 2 + 2 + 4 + 8 - 6 - 10 * 9))

(defn build_num_col [args]
  (loop [l args col '()]
    (if (empty? l)
      col
      (if (number? (first l))
        (recur (rest l) (concat col (list (first l))))
        (recur (rest l) col)))))

(defn build_func_col [args]
  (loop [l args col '()]
    (if (empty? l)
      col
      (if (clojure.test/function? (first l))
        (recur (rest l) (concat col (list (first l))))
        (recur (rest l) col)))))

(defn infix [& args]
  (loop [num_col (build_num_col args)
         func_col (conj (build_func_col args) +)
         res 0]
    (if (not (empty? num_col))
      (recur
        (rest num_col)
        (rest func_col)
        ((first func_col) res (first num_col)))
      res)))


(= 7 ((fn foo [& args]
        (loop [num_col (filter number? args)
               func_col (conj (filter (complement number?) args) +)
               res 0]
          (if (not (empty? num_col))
            (recur
              (rest num_col)
              (rest func_col)
              ((first func_col) res (first num_col)))
            res))) 2 + 5))