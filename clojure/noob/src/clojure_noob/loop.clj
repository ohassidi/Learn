(ns clojure-noob.loop)

(loop [iteration 0] ; start with iteration as o
  (println (str "Iteration " iteration)) ; print the current iteration
  (if (> iteration 3) ; ask whether the iteration value is more than 3
    (println "Goodbye!") ; in case answer is yes print goodbye and exit
    (recur (inc iteration)))) ; in case answer is no, re-execute the loop with the iteration value incremented by 1


; the same as
(defn recursive-printer
  ([] ; first option if one calls the function without any param
   (recursive-printer 0))
  ([iteration] ; second option (overloading) if one calls the function with one parameter
   (println iteration)
   (if (> iteration 3)
     (println "Goodbye!")
     (recursive-printer (inc iteration))))) ; call the function with iteration incremented

(recursive-printer)