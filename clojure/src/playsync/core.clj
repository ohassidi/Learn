(ns playsync.core
  (:require [clojure.core.async
             :as a
             :refer [
                     >! >!! ;put (! parking, !! blocking)
                     <! <!!;take (! parking, !! blocking)
                     go ;create a new process
                     chan ;create a new channel
                     buffer ;created a channel that can receive more than input
                     close! thread
                     alts! alts!! ;lets you use the result of the first successful channel operation among a collection of operations
                     timeout]]))

(def echo-chan (chan)) ;creating channel
(go (println (<! echo-chan))) ;creating process and run inside separated thread
(>!! echo-chan "ketchup")
; => true
; => ketchup

; (>!! (chan) "mustard") ; this will block the REPL since you have created channel and put data in,
; but no one listen to it


(def echo-buffer (chan 2)) ;created 2 spaces buffered channel
(go (println (<! echo-buffer)))
(>!! echo-buffer "ketchup1")
; => true
(>!! echo-buffer "ketchup2")
; => true

; This blocks because the channel buffer is full
;(>!! echo-buffer "ketchup")


;===================================

(def hi-chan (chan))
(doseq [n (range 1000)]
  (go (>! hi-chan (str "hi " n)))) ;we've created 1k processes that created 1k channels and wait for someone to take

;thread ===================================

(thread (println (<!! echo-chan)))
(>!! echo-chan "mustard")
; => true
; => mustard

(let [t (thread "chili")]
  (<!! t))
; => "chili",
; thread return a value not like future that return reference.
; the thread return a channel ready to read from
; In this case, the process doesn’t wait for any events; instead, it stops immediately.
; Its return value is "chili", which gets put on the channel that’s bound to t.
; We take from t, returning "chili".

; alts!! ===================================
(defn upload
  [headshot c]
  (go (Thread/sleep (rand 100))
      (>! c headshot)))

(let [c1 (chan)
      c2 (chan)
      c3 (chan)]
  (upload "serious.jpg" c1)
  (upload "fun.jpg" c2)
  (upload "sassy.jpg" c3)
  (let [[headshot channel]
        (alts!! [c1 c2 c3])]
    (println "Sending headshot notification for" headshot)))
; => Sending headshot notification for sassy.jpg

(let [c1 (chan)]
  (upload "serious.jpg" c1)
  (let [[headshot channel] (alts!! [c1 (timeout 20)])]
    (if headshot
      (println "Sending headshot notification for" headshot)
      (println "Timed out!"))))
; => Timed out!