(ns clojure-noob.regular-experssions)

(re-find #"^left-" "left-eye"); => "left-"
(re-find #"^left-" "cleft-chin"); => nil
(re-find #"^left-" "wongleblart"); => nil

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(matching-part {:name "left-eye" :size 1}); => {:name "right-eye" :size 1}]
(matching-part {:name "head" :size 3}); => {:name "head" :size 3}]

;regular expression is defined by #"example"