(ns guell.parser
  (:require [instaparse.core :as insta]))

(def transaction-parser
  (insta/parser
   "(* Transaction grammar in ebnf notation *)
    transaction =  date ' '? name? '\n' accountLine+
    accountLine = ' '+ account+ (' '+ value)? '\\n'?
    account = #'\\w+'<':'>?
    (* TODO: handle billions and currencies*)
    amount = #'-?([0-9]+,)?[0-9]+.[0-9]{2}' ' '?
    value = amount ' ' commodity?
    commodity = #'\\w+'
    date = #'[0-9]{4}-[0-9]{2}-[0-9]{2}'
    (* TODO: handle a sentence*)
    name =  #'\\w+'"))
