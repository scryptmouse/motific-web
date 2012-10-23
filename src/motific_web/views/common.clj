(ns motific-web.views.common
  (:require [hiccup.core :as hiccup]
            [motific-web.config :as config]
            [clojure.string :as string])
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-css html5]]
        [dieter.core :only [link-to-asset]]))

(defn- active-if
  "Helper for elements that have a default 'active' class"
  [current cmp]
  (if (= current cmp) "active" ""))

(defpartial link-to [& {:keys [target label href classes active] :or {target "" label "" href "#" classes "" active ""}}]
  [:li {:class (str classes (active-if active target))}
   [:a {:data-target target :href href} (if (empty? label) (string/capitalize target) label)]])

(defpartial input-row [& {:keys [field label ph] :or {field "" label "" ph ""}}]
  [:div.row
   [:div.label [:label {:for field} (if (empty? label) (string/capitalize field) label)]]
   [:div.input [:input {:name field :type "text" :placeholder ph}]]])

(defpartial legend [& heading]
  [:legend.row
   [:h2.sixteen.columns heading]])

(defpartial submit [& label]
  [:div.row
   [:div.actions
    [:button {:type "submit"} (if (empty? label) "Submit" label)]]])

(defpartial nav-panes [& {:keys [active]}]
  (let [link (partial link-to :active active)]
    [:nav#option-nav.panes-nav.horizontal.row
     [:ul.sixteen.columns
      (link :target "random" :href "/random")
      (link :target "permutate" :href "/permutate")
      (link :target "anagram" :href "/anagram" :label "Anagrams")]]))

(defpartial random-tpl [& {:keys [active]}]
  [:div#random {:class (str "pane " (active-if active "random"))}
    [:form {:action "/api/random" :method "get"}
     (legend "Random Words")
     [:div.row
      [:div.one-third.column.alpha.omega "&nbsp;" [:br.clear]]
      [:div.two-thirds.column.alpha.omega
       [:div.label [:label {:for "quantity"} "Quantity"]]
       [:div.input [:input {:name "quantity" :type "text" :placeholder "Default is 25 words"}]]]]
     [:div.row.word-lengths
      [:div.one-third.column
       [:ul.panes-nav
        (link-to :target "minmax" :label "Min / Max Lengths" :active "minmax")
        (link-to :target "exact" :label "Exact Length")]]
      [:div.two-thirds.column.panes-content
       [:div#minmax.pane.active
        (input-row :field "min" :label "Minimum")
        (input-row :field "max" :label "Maximum")]
       [:div#exact.pane
        (input-row :field "exact")]]]
     (submit "Randomize!")]])

(defpartial permutate-tpl [& {:keys [active]}]
  [:div#permutate.pane {:class (str "pane " (active-if active "permutate"))}
   [:form {:action "/api/permutate" :method "get"}
    (legend "Word Permutations")
    (input-row :field "word" :ph "Maximum length of 8 letters.")
    (submit "Permutate!")]])

(defpartial anagram-tpl [& {:keys [active]}]
  [:div#anagram.pane {:class (str "pane " (active-if active "anagram"))}
   [:form {:action "/api/anagram" :method "get"}
    (legend "Find Anagrams")
    (input-row :field "word")
    (submit "Find!")]])

(defpartial layout [& {:keys [content active] :or {active "random" content nil}}]
  (html5
    [:head
     [:title "Motific Web"]
     (include-css "/css/skeleton.css")
     (include-css (link-to-asset "css/main.css"))]
    [:body
     [:div#main.container
      (nav-panes :active active)
      [:div#options.panes-content.clearfix
       (random-tpl :active active)
       (permutate-tpl :active active)
       (anagram-tpl :active active)]
      [:div#response
       [:nav#response-nav.horizontal.panes-nav.clearfix
        [:ul
          (link-to :target "list" :active "list")
          (link-to :target "raw")]]
        [:div#total.clearfix [:h4 "Total: " [:data.total "0"]]]
        [:div.panes-content
         [:div#list.pane.active [:ul]]
         [:div#raw.pane [:pre]]]]
      [:footer.main.row
       [:aside.ten.columns.offset-by-three
        [:p "© 2012 "
         [:a {:href "http://alexa.is/" :title "alexa.is" :target "_blank"} "Alexa Grey"]
         ". MIT License. Uses the "
         [:a {:href "http://dreamsteep.com/projects/the-english-open-word-list.html" :title "English Open Words List" :target "_blank"} "EOWL"]
         "."]]]
      content]
     [:script {:src "/js/require.js" :data-main "/js/motific"}]]))
