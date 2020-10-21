(ns clojure-noob.map)

(defn titleize
  [topic]
  (str topic " for the Brave and True"))

(map titleize ["Hamsters" "Ragnarok"]) ;works on vector
; => ("Hamsters for the Brave and True" "Ragnarok for the Brave and True")

(map titleize '("Empathy" "Decorating")) ;works on lists
; => ("Empathy for the Brave and True" "Decorating for the Brave and True")

(map titleize #{"Elbows" "Soap Carving"}) ;works on sets
; => ("Elbows for the Brave and True" "Soap Carving for the Brave and True")

(map #(titleize (second %)) {:uncomfortable-thing "Winking"}) ;works on hash map, takes second element from the map ("Winking") and pass it to the titleize function
; => ("Winking for the Brave and True")
; map is working on the give hashmap creating a list, the anonymous function (second %) gets the hashmap as parameter and
; return the 'Winking' and pass it through to the 'titleize' function


(map println ["1" "2" "3"] ["2" "3" "4"] ["one" "two"] ["yellow"])
;=> 1 2 one yellow

;==============================================================================

(def human-consumption   [8.1 7.3 6.6 5.0])
(def critter-consumption [0.0 0.2 0.3 1.1])
(defn unify-diet-data
  [human critter]
  {:human human
   :critter critter})

(map unify-diet-data human-consumption critter-consumption)
; => ({:human 8.1, :critter 0.0}
; {:human 7.3, :critter 0.2}
; {:human 6.6, :critter 0.3}
; {:human 5.0, :critter 1.1})
;
;==============================================================================

; In this example, the stats function iterates over a vector of functions, applying each function to numbers.

(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))

(stats [3 4 10])
; => (17 3 17/3)

(stats [80 1 44 13 6])
; => (144 5 144/5)

;==============================================================================

(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spider-Man" :real "Peter Parker"}
   {:alias "Santa" :real "Your mom"}
   {:alias "Easter Bunny" :real "Your dad"}])

(map :real identities)
; => ("Bruce Wayne" "Peter Parker" "Your mom" "Your dad")

;==============================================================================

(map identity {:sunlight-reaction "Glitter!" :daylight-reaction "Slitter!"})
; => ([:sunlight-reaction "Glitter!"][:daylight-reaction "Slitter!"])
(into {} (map identity {:sunlight-reaction "Glitter!"}))
; => {:sunlight-reaction "Glitter!"}∏

