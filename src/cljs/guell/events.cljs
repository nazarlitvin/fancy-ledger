(ns guell.events
  (:require
   [re-frame.core :as re-frame]
   [re-frame-readfile-fx.core]
   [guell.db :as db]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
 :visit-page
 (fn [db [_ page]]
   (assoc db :page page)))

(re-frame/reg-event-fx
 :file-selected
 (fn [_ [_ file]]
   {:readfile {
               :files [file]
               :on-success [:file-parse-success]
               :on-error [:file-parse-failure]}}))

(re-frame/reg-event-fx
 :file-parse-success
 (fn [_ [_ response]]
   (.log js/console response)))

(re-frame/reg-event-fx
 :file-parse-failure
 (fn [_ [_ error]]
   (.log js/console "error" error)))
