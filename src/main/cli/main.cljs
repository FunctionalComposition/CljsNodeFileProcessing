(ns cli.main
  (:require [yargs :as y]
            [cljs-node-io.core :as io]))

(defn handle-input [s]
  (println s))

(defn register-stdin-handlers
  "register node process stdin callback handlers"
  []
  (let [stdin (atom "")]
    (.setEncoding js/process.stdin "utf8")
    (.on js/process.stdin "data"
         (fn [data]
           (swap! stdin #(str % data))))
    (.on js/process.stdin "end"
         (fn []
           (swap! stdin (fn [s]
                          (subs s 0 (count s))))
           (handle-input @stdin)))))

(defn main!
  "main entry point into app"
  [& args]
  (let [arg-map ;; uses the yargs cli arguments package process the command line into arg-map
        (-> yargs (.options (clj->js
                             {:file {:alias :f
                                     :describe "File to process (expects utf8 encoding)"
                                     :requiresArg true
                                     :string true}}))
            (.example "$0 -f package.json" "Using file input argument")
            (.example "cat package.json | $0" "Using standard input (no file argument)")
            (.alias "help" "h")
            (.alias "version" "v")
            (.parse (clj->js (sequence args)))
            (js->clj :keywordize-keys true))]

    (if (contains? arg-map :file)
      (handle-input (io/slurp (:file arg-map) :encoding "utf8"))
      (register-stdin-handlers))))
