(ns clojure-noob.defstate-start-stop
  (:require [mount.core :refer [defstate]]))

(defn do-something []
  (prn "something")
  "start")

(defn shutdown []
  (prn "shutting down")
  "stop")


(defstate st
          :start (do-something)
          :stop (shutdown))

