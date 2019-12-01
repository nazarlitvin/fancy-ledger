(ns guell.parser
  (:require [instaparse.core :as insta :refer-macros [defparser]]))

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

(defn parse-journal
  "Parse a ledger journal file."
  [journal]
  (map (transaction-parser transaction)))


;; All backslash characters in your strings and regexes \ should be escaped and turned into \\.
;; So putting this grammar in a separate resource file has an additional advantage.

;; (* Transaction *)

;; transaction = header <newline> entries+
;; entries = (entry-or-note <newline>)+
;; <entry-or-note> = <ws> entry | <ws> note | <ws>

;; (* Header *)

;; header = date [<ws> state] <ws> optional-code-then-payee [note]
;; <optional-code-then-payee> = code <ws> payee | !code payee
;; (* TODO: handle onother date formats*)
;; date = #"\d{4}-\d{2}-\d{2}"
;; state = #"[*!]"
;; code = <"("> #"[^);\r\n]+" <")">
;; (* TODO: starts not with "!" or "*" but may contain them*)
;; payee = #"[^!*;\r\n]+"
;; note = <";"> #"[^\r\n]*"

;; (* Entry *)

;; entry = account [<ws> amount] [<ws> value] [<ws> note] [<ws>]
;; account = #"[^ ;\t\r\n]+"
;; value = total-cost | unit-cost
;; total-cost = "@@" <ws> amount
;; unit-cost = "@" <ws> amount
;; amount = commodity <ws?> quantity | quantity <ws?> commodity | quantity
;; quantity = #"-?[\d,.]+"
;; commodity = quoted | unquoted
;; <quoted> = <"\""> #"[^\r\n\\\"]+" <"\"">
;; <unquoted> = #"[^-0123456789., @;\\r\\n\\\"]+"

;; (* Whitespace *)
;; ws = #"[ \t]+"

;; (* Newline *)
;; newline = "\n" / "\r\n" / "\r"
