(ns clojure-noob.atom-vs-defstate
  (:require [mount.core :refer [defstate]]
            [clojure.core.async :as async :refer [go]]
            [criterium.core :as cc]))

(defn start-defstate-cache []
  (let [state (atom "defstate-state")]
    state))

(def atom-state (atom "atom-state"))

(defstate ds :start (start-defstate-cache))


(defn eval-atom-read []
  (let [t (System/nanoTime)]
    @atom-state
    (- (System/nanoTime) t)))

(defn eval-defstate-read []
  (let [t (System/nanoTime)]
    ds
    (- (System/nanoTime) t)))


(defn test-atom-read []
  (let [is-atom-faster (atom 0)]
    (dotimes [_ 100]
      (go
        (let [t1 (eval-atom-read)
              t2 (eval-defstate-read)]
          (if (< t1 t2)
            (swap! is-atom-faster inc)
            (swap! is-atom-faster dec)))))
    (print is-atom-faster)))


