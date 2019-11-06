(ns guell.routes
  (:require [secretary.core :as secretary :refer-macros [defroute]]
            [goog.events :as events]
            [re-frame.core :as re-frame])
  (:import [goog History]
           [goog.history EventType]))

(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen EventType.NAVIGATE
                   (fn [event] (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn app-routes []
  (secretary/set-config! :prefix "#")

  (defroute home-path "/" []
    (re-frame/dispatch [:visit-page :home]))

  (defroute about-path "/about" []
    (re-frame/dispatch [:visit-page :about]))

  (defroute profile-path "/profile" []
    (re-frame/dispatch [:visit-page :profile]))

  (hook-browser-navigation!))
