(ns guell.views
  (:require
   [re-frame.core :as re-frame]
   [guell.subs :as subs]
   ))

(defn main-panel []
  (let [page (re-frame/subscribe [::subs/page])]
    [:div
     [:h1 "Hello from " @page]
     [:div [:a {:href "#/"} "go to Home page"]]
     [:div [:a {:href "#/about"} "go to About page"]]
     [:div [:a {:href "#/profile"} "go to Profile page"]]
     [:input {
              :type "file"
              :accept ".dat"
              :on-change (fn [e] (re-frame/dispatch [:file-selected (aget e.target.files "0")]))}]]))
