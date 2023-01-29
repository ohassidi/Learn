(ns assured-allies.service
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [jsonista.core :as json]
            [assured-allies.trie :as trie])
  (:gen-class))

(def dict
  "Keeps the dictionary"
  (atom {}))
(def srvr
  "Keeps the server instance in case we will want gradual shutdown,
   Currently mainly for dev in order not to restart the repl each time"
  (atom {}))

(def max-number-of-request-to-record
  "Stats are always for the last 10000 records"
  10000)
(def last-requests-time
  "Keeps the last request times"
  (atom {:time [] :num-of-req 0}))

(def stats
  "Keeps the stats records"
  (atom {:average_request_handle_time_ms 0
         :request_handled_count          0
         :word_count                     0}))

(defn calc-avg
  "Calculating the avg from all last requests time"
  []
  (/ (reduce + (:time @last-requests-time)) (* (:num-of-req @last-requests-time) 1.0)))

(defn update-request-time-records
  "Updating the stats and last-requests-time on each call to dictionary"
  [time]
  ;; in case we are over the max of request for avg we fifo the time recorded
  (if (> (:num-of-req @last-requests-time) max-number-of-request-to-record)
    (swap! last-requests-time assoc :time (rest (:time @last-requests-time))
           :num-of-req (dec (:num-of-req @last-requests-time))))

  ;; record the current time took for the request
  (swap! last-requests-time assoc :time (conj (:time @last-requests-time) time)
         :num-of-req (inc (:num-of-req @last-requests-time)))

  ;; update stats
  (swap! stats assoc :average_request_handle_time_ms (calc-avg)
         :request_handled_count (inc (:request_handled_count @stats))))

(defn welcome []
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "Welcome to my Assured Allies test app"})

(defmacro time-execution
  "since 'time' function only prints the time certain function, we need a way to return it with
  the desired function result; so the macro return a tuple of the time the function result"
  [& body]
  `(let [s# (new java.io.StringWriter)]
     (binding [*out* s#]
       (hash-map :return (time ~@body)
                 :time (.replaceAll (str s#) "[^0-9\\.]" "")))))

(defn dictionary
  "Will retrieve all possible matches of a prefix in a dictionary, /dictionary/prefix=<PREFIX>"
  [req]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (let [{:keys [return time]}
                  (time-execution
                    (json/write-value-as-string (trie/prefix-matches @dict (:prefix (:params req)))))]
              (update-request-time-records (Double/parseDouble time))
              ;; return the result of the dictionary
              return)})

(defn statistics
  "Return a json object of the statistics of the service instance.
   1. averageRequestHandleTimeMs - The average time in ms it took the service to handle the requests.
   2. requestHandledCount - How many requests did the service handled.
   3. wordCount - How many words are in the dictionary"
  [_]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (json/write-value-as-string @stats)})

(defn reset-dictionary [content]
  (let [j (json/read-value content)
        count (count j)]
    (swap! stats assoc :word_count count)
    (reset! dict (trie/build-trie j))
    count))

(defn update-dictionary
  "Reseting the dictionary to the given json body or file"
  [req]
  {:status  200
   :headers {"Content-Type" "text/plain"}
   :body    (str "# of words in dictionary: " (reset-dictionary (:body req)))})

(defroutes app-routes
           (GET "/" [] welcome)
           (GET "/dictionary" [] dictionary)
           (GET "/statistics" [] statistics)
           (POST "/update_dictionary" [] update-dictionary)
           (route/not-found "Error, page not found!"))
(defn -main
  "This is our main entry point"
  [& _]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))
    (reset! srvr (server/run-server (wrap-defaults #'app-routes api-defaults) {:port port}))))


