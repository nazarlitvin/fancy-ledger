(ns guell.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [guell.events :as events]
   [guell.views :as views]
   [guell.routes :as routes]
   [guell.config :as config]
   ))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn init []
  (routes/app-routes)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
