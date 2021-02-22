(ns sandbox.rop)

; ROP stands for Railway Oriented Programming
; And we want to test it how to use for better error handling in clojure
; instead of exception oriented, that will give more clear flow of the code and better performance
; as we know that exception is some penalty

(defmacro =>>
  "Threading macro, will execute a given functions one by one.
  If one of a given functions will return a fail,
  it will skip the rest of the functions and will return a fail cause."
  [val & fns]
  (let [fns (for [f fns] `(apply-or-error ~f))]
    `(->> [~val nil]
          ~@fns)))

(defn apply-or-error [f [val err]]
  (if (nil? err)
    (f val)
    [val err]))


(defn f2 [x]
  (prn "f2: " x)
  ["clear" nil])

(defn f1 [x y]
  (prn "f1: " x y)
  [(conj x "hi") nil])

(let [f2 f2
      f1 f1

      [res err] (=>> ["there"]
                     f2
                     #(f1 % 33)
                     f2)]
  (prn "res: " res)
  (prn "err: " err))