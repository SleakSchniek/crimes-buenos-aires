(ns crimes-buenos-aires.core
  (:gen-class)
  (:require [clojure.data.json :as json]) 
  (:require [quil.core :as q]
            [quil.middleware :as m]))


(def bar (slurp "desito.json"))
(def crimes (json/read-str bar))


;; manually put in max and min coordinates 
(def min-longitude -58.5311)
(def min-latitude -34.7058)
(def max-longitude -58.4757)
(def max-latitude -34.534)


;;scatterplot coordinates
(defn setup []
  (q/frame-rate 1)
  (q/background 1))

(defn draw []
  (q/background 255)
  (doseq [crime (remove #(or (zero? (get % "longitud"))
                             (zero? (get % "latitud")))crimes)]
    (q/set-pixel (q/map-range (get crime "longitud") min-longitude max-longitude 30 390)    
                 (q/map-range (get crime "latitud")
                              min-latitude max-latitude 20 720)
                 0))
  (q/save-frame "scatterplot_crimes.jpg"))



(q/defsketch crime-pixels
  :title "figure of crime scenes"
  :setup setup
  :draw draw
  :size [1200 770])


