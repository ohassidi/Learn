(ns greeting.welcome)

(ns greeting.welcome
  (:require [greeting.person-names :as pnames])) ;; NOTE: `myproject.welcome` requires `myproject.person-names`

(defn greet
  [first-name last-name]
  (str "Hello, " (pnames/familiar-name first-name last-name)))
