# CLJS Node app template for text file processing

A template app that can be used as a base for a ClojureScript node file processing app with the following features:
 
 - text file input to be utf8 encoded
 - file argument option (uses node yargs lib, and cljs-node-io lib)
 - standard input used if no file argument supplied
 - echos the file input to standard out (you implement your features by changing the "handle-input" function)

## Project environment config notes

The project was built in the following environment:

 - node manged with [nvm](https://github.com/nvm-sh/nvm)
 - node version 12 LTS via nvm command: nvm install lts/erbium && nvm alias default lts/erbium && nvm use default

Project creation with:

    npx create-cljs-project cljs-node-file-processing

Then basic app components added from: https://github.com/minimal-xyz/minimal-shadow-cljs-nodejs

Then a number of other references were used to construct the file processing components.

## Building

Command line instructions (see REPL development for Spacemacs below for better live dev if using Emacs/Spacemacs):

First for installing node dependencies:

    npm install

Then for installing shadow-cljs dependencies and compiling live-dev/dev/release: 

    npx shadow-cljs watch app
    npx shadow-cljs compile app
    npx shadow-cljs release app

Making a binary executable after the 'release' build from above (primarily for containerisation i.e. minimal docker container, with linux environment executable (see the pkg node documentation for other environments):

    npx pkg --targets node12-linux-x64 --output target/main target/main.js

NOTE: The above native binary construction is for minimising container size and not necessarily for speed improvements.

## Running

From the compile/release build:

    node target/main.js --help

From the binary executable build:

    ./target/main --help

## REPL development Spacemacs

Starting a repl in Spacemacs key combos (replaces the need for the shadow-cljs watch app build command from above):

    ,'
    cider-jack-in-cljs
    shadow
    :app
    y

The 'y' is to answer the interactive prompt for opening repl control panel in the browser (the above will start a file watch build). 

Note: you can use "node-repl" instead of the ":app" target (this will not start a file watch build).

