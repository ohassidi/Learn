(ns clojure-noob.core-test
  (:require [clojure.test :refer :all]
            [clojure-noob.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))


(deftest print-test
  (is true? 0))

(deftest test1
  (is (= 1 (inc 0))))

; provide a description for the test
(deftest test2
  (is (= (* 5 5) (+ (* 3 3) (* 4 4)))
      "The square of the hypotenuse is equal to the sum of the squares of the other two sides"))

(deftest math-test
  (testing "basic math"
    (is (odd? 1))
    (is (= 2 (inc 1))))
  (testing "pythagoras"
    (is (= (* 5 5) (+ (* 3 3) (* 4 4)))
        "The square of the hypotenuse is equal to the sum of the squares of the other two sides")))

; not best practice
(deftest multi-check
  (are [x y]
    (= x y)
    2 (+ 1 1)
    4 (* 2 2)))


; check whether exception is thrown
(defn bad [x]
  (throw (ex-info "oh no" {})))

(deftest test-exception
  (is (thrown-with-msg? Exception #"oh no"
                        (bad 42))))

;; test fixtures

(use-fixtures :once
              (fn print-enter-exit [tests]
                (println "before")
                (tests)
                (println "after")))

(use-fixtures :each
              (fn capture-prints [f]
                (with-out-str (f))))


(defn post [url]
  {:body (str "Hello world")})

; this passed since in the above function we have changed the behaviour of the str function
(deftest test-post
  (with-redefs [str (fn [& args]
                      "Goodbye world")]
    (is (= {:body "Goodbye world"}
           (post "http://service.com/greet")))))

; eval expression while running
(defn shazam [a b]
  (/ 1 (+ a b) (doto (+ a (* a b)) (prn "***"))))
(shazam 1 2)

(defn pythag [x y]
  (Math/sqrt (+ (* x x) (* y y))))

(deftest test-pythag
  (is (= (Math/sqrt (* 5 5)) (pythag 3 4))))

; count the number of times str get called
(deftest test-post
  (let [c (atom 0)]
    (with-redefs [str (fn [& args]
                        (swap! c inc) ;increment the atom counter
                        "Goodbye world")]
      (post "http://service.com/greet")
      (post "http://service.com/greet")
      (post "http://service.com/greet")
      (is (= 3 @c)))))

(deftest foo-test
  (is (= 65 (foo 45))))

(deftest identity?
  (is (= nil (identity nil))))

(deftest test-endless-comp
  (is (= 30 ((endless-comp dec inc *) 2 3 5))))