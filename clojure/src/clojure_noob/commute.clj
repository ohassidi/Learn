(ns clojure-noob.commute)


; commute safe usage
; Here’s an example of a safe use.
; The sleep-print-update function returns the updated state but also sleeps the specified number of milliseconds
; so we can force transaction overlap. It prints the state that it’s attempting to update so we can gain insight
; into what’s going on:
(defn sleep-print-update
  [sleep-time thread-name update-fn]
  (fn [state]
    (Thread/sleep sleep-time)
    (println (str thread-name ": " state ", before update"))
    (update-fn state)))
    ;(println (str thread-name ": " state ", after update"))))


(def counter (ref 0))

(future
  (dosync
    (commute counter (sleep-print-update 100 "Thread A" inc))))

(future
  (dosync
    (commute counter (sleep-print-update 150 "Thread B" inc))))