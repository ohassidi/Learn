(ns clojure-noob.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hi i'm new here!"))


(if (= 1 1)
  (println "well that is true")
  (println "This would be printed in case of false"))

;; to execute more than one expression use 'do'
(if (= 1 1)
  (do
    (println "well that is true")
    (println "Also do that")))


;; use 'when' when you want to evaluate more that expression
(when (= 2 2)
  (println "first action is done")
  (println "second action is done"))

(let [q 2
      x 1
      is-google? true
      is-fb? true
      is-android-platform? true
      is-deeplink? false
      is-preinstalled? false
      is-sdk? true
      is-in-blacklist? false]
  (when (and
          (not (is-google?))
          (not (is-fb?))
          (is-android-platform?)
          (not (is-deeplink?))
          (not (is-preinstalled?))
          (is-sdk?)
          (is-in-blacklist?))
    ("not cool")))

(if nil 2 "ff") ;; as if ask (if false then ...)
(if "hi" 4 "something else") ;; as if we ask (if true then...)
(nil? 2) ;; check if 2 is nil

; or return the first true expression or last value so...
(or false nil :large_I_mean_venti :why_cant_I_just_say_large) ; => :large_I_mean_venti

; and return the first false expression or if none then last false expression
(and :free_wifi :hot_coffee); => :hot_coffee
(and :feelin_super_cool nil false); => nil


(get {:a 0 :b 1} :c); => nil, since there is no c in the map
(get {:a 0 :b 1} :c "unicorns?"); => "unicorns?" giving the return value a default
(get-in {:a 0 :b {:c "ho hum"}} [:b :c]) ; => "ho hum", search with in a map
({:a 0 :b {:c "ho hum"}} :b) ; => search value using keys, can't be use inside inner maps


; vectors
[1 2 3]
(vector 1 2 3) ; same as above∏
(def w [1 2 3])
; user get to get an element in the vector in position x
(get w 1)

; lists
'(1 2 3)
(list 1 2 "three") ; same as above
; use ntn to get an element in the vector in position x, less performance then the get in vector

; sets
#{1 2}
(hash-set 1 1 2)
; contains? ask for an element and return true if exist otherwise false, or you can use a keyword

(contains? #{1 3 5} 5)

; hash map
(hash-map :a 1 :b 2)
{:a 1 :b 2}
(second {"key1" 1 "key2" 2}) ;return the second element

; functions
(map inc [1 1]) ; map takes the first parameter (the method) and execute it on each element of the vector

; define a function
(defn sayhello "say hello to..." [towhom] (str "hello " towhom))
(sayhello "world")


;; functions overloading, look that the second eval will occur when giving the function one argument
; and this will call the first eval with the default value of the second arg as "karate"
; (x-chop "hello")
;=> "I karate chop hello! Take that!"
;(x-chop "eee" "ttt")
;=> "I ttt chop eee! Take that!"
(defn x-chop
  "Describe the kind of chop you're inflicting on someone"
  ([name chop-type]
   (str "I " chop-type " chop " name "! Take that!"))
  ([name]
   (x-chop name "karate")))

(defn receive-treasure-location
  [{:keys [lat lng] :as treasure-location}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng))

  ;; One would assume that this would put in new coordinates for your ship
  (println treasure-location))

; define anonymous function
#(* % 8)
; call it
(#(* % 8) 4)

(#(identity %&) 1 "blarg" :yip)
(#(second %) {"key1" 1 "key2" 2 "11" 3}) ;return the second entry
(second {"key1" 1 "key2" 2}) ;return the second entry (same as above)

(map #(second %) {1 2 3 4}) ;return a list of values
(map #(first %) {1 2 3 4}) ;return a list of keys


(def d (+ 1 3))
(+ 1 d)



(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  (println inc-by)
  #(+ % inc-by))
(def inc3 (inc-maker 3))
(inc3 7)

;=========================================
(defn foo
  [n]
  (cond (> n 40) (+ n 20)
        (> n 20) (- n 20)
        :else 0))

(def tt 5)
tt
(doto tt prn)

(prn 5)
(comment pr 3)


;; functional thoughts

(+ 1 2)
; => 3 will always return the same so, referential transparent

(defn wisdom
  [words]
  (str words ", Daniel-san"))

(wisdom "Always bathe on Fridays")
; => "Always bathe on Fridays, Daniel-san" also as above since it relies on immutable value (", Daniel San")

(defn year-end-evaluation
  []
  (if (> (rand) 0.5)
    "You get a raise!"
    "Better luck next year!"))
; here the result will always be different so not pure function here!

;; The following function, analyze-file, is not referentially transparent, but the function analysis is
(defn analysis
  [text]
  (str "Character count: " (count text)))

(defn analyze-file
  [filename]
  (analysis (slurp filename)))

;;
(defn sum
  ([vals]
   (sum vals 0))
  ([vals accumulating-total]
   (if (empty? vals)
     accumulating-total
     (recur (rest vals) (+ (first vals) accumulating-total)))))

;; function composition. Is when calling function and the result passed to another function
(require '[clojure.string :as s])
(defn clean
  [text]
  (s/replace (s/trim text) #"lol" "LOL"))

(clean "My boa constrictor is so sassy lol!  ")
; => "My boa constrictor is so sassy LOL!"


;; composition of several functions
((comp inc *) 2 3) ;first the * then inc


;; comp implementation for two functions
(defn two-comp
  [f g]
  (fn [& args]
    (f (apply g args))))

((two-comp inc *) 2 3)


(defn endless-comp
  [& funcs]
  (fn [& args]
    (loop [f funcs a args]
      (if (empty? f)
        (let [[d] a] d)
        (recur (drop-last f) (list (apply (last f) a)))))))

((endless-comp dec inc *) 2 3 5)

(defn comp3
  [f g h]
  (fn [& args]
    (f (apply g (list (apply h args))))))

((comp3 inc dec +) 2 3 1)


;; Memoization
(defn sleepy-identity
  "Returns the given value after 1 second"
  [x]
  (Thread/sleep 1000)
  x)
(sleepy-identity "Mr. Fantastico")
; => "Mr. Fantastico" after 1 second

(sleepy-identity "Mr. Fantastico")
; => "Mr. Fantastico" after 1 second

; After memoizing
(def memo-sleepy-identity (memoize sleepy-identity))
(memo-sleepy-identity "Mr. Fantastico")
; => "Mr. Fantastico" after 1 second

(memo-sleepy-identity "Mr. Fantastico")
; => "Mr. Fantastico" immediately

;======================================================================
;; Concurrency
;======================================================================

; Atoms - sync ===================================
(def foo (atom {:blah "hey"}))
; get the foo value
(prn @foo) ;or
(deref foo)
;changing the atom foo value
(swap! foo (fn [in] 3)) ;pay attention to the fact it has to be inside a function
(swap! foo inc) ;inc foo value

(map inc (range 5))
(pmap (fn [_] (swap! foo inc)) (range 5))

; Agents - async ===================================
(def my-agent
  (agent
    {:name "oded"
     :favortites []}))
; like atoms -
(deref my-agent) ;or
@my-agent
; build a function that take the current agent and append a favorite to it
; the function has sleep in order to see the some concurrency
(defn slow-append-fav
  [val new-fav]
  (Thread/sleep 2000)
  (assoc val ;adding to val the new val
    :favortites
    (conj (:favortites val) new-fav))) ;join the new fav to the old one
; now lets call it
(do
  (send my-agent slow-append-fav "food") ;the food will be passed to new-fav
  (send my-agent slow-append-fav "music")
  (prn @my-agent) ;this will print empty since it has sleep and also each action happens one at a time for a specific agent
  (Thread/sleep 2300) ;so we added a sleep
  (prn @my-agent) ;and print again, now we should see the first value
  (Thread/sleep 2300) ;so we added a sleep
  (prn @my-agent)) ;and print again and now also the second
   ;since this values are shared, the values are kept for the next runs :)

;Error handling in agents
(def error-agent (agent 3)) ;define new agent with value 3
(defn modify-with-error ;function should get new value to agent and if not 42 throw and exception
  [current new]
  (if (= 42 new)
    (throw (Exception. "Not 42!")) ;"Exception." like writing "new Exception()" in java
    new))
; now call it
(send error-agent modify-with-error 42)
; in order to see the errors use the followed
(agent-errors error-agent)
; if you'll deref error-agent it still preserve the 422 value
@error-agent
; we clean the agent errors by
(clear-agent-errors error-agent)
; and then call it again
(send error-agent modify-with-error 12) ;now the value will be  12

; Refs - ===================================
; Allow for sync changes to shared state
; Can only be changed inside a transaction (STM Software Transactional Memory)
; Automatic retries
; For example if you want to exchange money between two bank accounts and you use atom. Since there is no
; transaction something may go wrong
(def foo (ref
           {:first "Oded"
            :last "Hassidi"}))

;updating the ref with assoc
(assoc @foo
  :children 4)
; look at the value
@foo ;remain as created
; now if we want to change the shared state of foo
(dosync ;inside a transactional scope
  (commute foo assoc
           :children 4))
@foo ;now foo is with the new value
(dosync ;inside a transactional scope
  (alter foo assoc ;same as commute be occur immediate, refer to docs for more info between the two
           :habits "surfing"))
@foo;now foo is with the new value

; Macros ======================================================================
(defmacro with-new-thread [& body]
  ;this actually says that calling the macro will start the what's in the body, in a new thread where the item in the
  ;list is not evaluated but the body so in fact the body is replaced by the given arguments in the macro
  `(.start (Thread. (fn [] ~@body))))

(macroexpand-1 '(with-new-thread (prn @foo))) ; and we get here (with-new-thread (prn (clojure.core/deref foo)))

; now how to use it...
; concurrent writing with-in a transaction
(def r (ref 0))

(with-new-thread
  (dosync
    (println "tx1 initial:" @r)
    (alter r inc)
    (println "tx1 final:" @r)
    (Thread/sleep 5000)
    (println "tx1 done")))

(with-new-thread
  (dosync
    (println "tx2 initial:" @r)
    (Thread/sleep 1000)
    (alter r inc)
    (println "tx2 final:" @r)
    (println "tx2 done")))

; Look at the running result when running both sort of the same time.
;tx1 initial: 0
;tx1 final: 1, tx1 entering a sleep
;tx2 initial: 0, so till the tx1 is not done tx2 is restarting since clojure knows that it holds r
;tx2 initial: 0
;tx2 initial: 0
;tx2 initial: 0
;tx2 initial: 0
;tx1 done, now that tx1 is done tx2 initial is 1 and starting.
;tx2 initial: 1
;tx2 final: 2
;tx2 done

; concurrent reads with-in a transaction,
(dosync (ref-set r 0)) ; set r to be 0 w/o a function that will retry like in alter/commute

(with-new-thread
  (dotimes [_ 10]
    (Thread/sleep 1000)
    (dosync (alter r inc))
    (println "updated ref to" @r)))

(with-new-thread
  (println "r outside is:" @r) ;here r will be as the value as read once r is updated or 0
  (dosync
    (dotimes [i 7]
      (println "iter is:" i)
      (println "r is:" @r) ; r will remain is first read the entire tx time
      (Thread/sleep 1000)))
  (println "r outside1 is:" @r)) ;this the final r value

; using multi refs to coordinate changes
; although we update r1 nd r2 in different times, from out side the transaction we see that they are the same!
(def r1 (ref 0))
(def r2 (ref 0))
(with-new-thread
  (dotimes [_ 10]
    (dosync
      (alter r1 inc)
      (Thread/sleep 500)
      (alter r2 inc))
    (println "updated r1/2 to:" @r1)))
(with-new-thread
  (dotimes [_ 15]
    (println "r1=" @r1 "r2=" @r2)
    (Thread/sleep 500)))

;; Results
;; r1= 0 r2= 0
;updated r1/2 to: 1
;r1= 1 r2= 1
;updated r1/2 to: 2
;r1= 2 r2= 2
;updated r1/2 to: 3
;r1= 3 r2= 3
;updated r1/2 to: 4
;r1= 4 r2= 4
;updated r1/2 to: 5
;r1= 5 r2= 5
;updated r1/2 to: 6
;r1= 6 r2= 6
;updated r1/2 to: 7
;r1= 7 r2= 7
;r1= 7 r2= 7
;updated r1/2 to: 8
;r1= 8 r2= 8
;updated r1/2 to: 9
;updated r1/2 to: 10r1=
;9 r2= 9
;r1= 10 r2= 10
;r1= 10 r2= 10
;r1= 10 r2= 10
;r1= 10 r2= 10

; Validators ===================================
(def ^:dynamic thingy 17)
(set-validator! (var thingy) #(not= %1 16))

(binding [thingy 20] (println thingy))
(binding [thingy 16] (println thingy))

;; Clear it
(set-validator! (var thingy) nil)

; Watchers ===================================
(defn my-watcher [key r old new]
  (println key r old new))

(def foo (atom 3))

(add-watch foo :my-key my-watcher)

(swap! foo inc) ; each time will try to change foo the watcher function (my-watcher) will be invoke

(remove-watch foo :my-key)

; Futures ===================================
(def my-future
  (future
    (Thread/sleep 5000)
    (println "Doing something")
    (Thread/sleep 3000)
    17))

@my-future ;what will happen is that future will execute in the background and will block until done

; Promises ===================================


(println "Done")