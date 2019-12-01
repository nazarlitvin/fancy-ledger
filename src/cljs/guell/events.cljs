(ns guell.events
  (:require
   [clojure.string :as str]
   [re-frame.core :as re-frame]
   [re-frame-readfile-fx.core]
   [guell.parser :as parser]
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

(re-frame/reg-event-db
 :file-parse-success
 (fn [db [_ data]]
   (let [records (str/split (first data) #"\n\n")]
     (assoc db :records (parser/parse-journal records)))))

(re-frame/reg-event-db
 :file-parse-failure
 (fn [_ [_ error]]
   (.log js/console "error" error)))
