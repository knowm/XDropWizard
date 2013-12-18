# XDropWizard

A starting-point Webservice using DropWizard and containing Xeiam's normal dev stack including projects such as Yank, Sundial, Flot, Markdown, Redis, HSQLDB, ZeroMQ, XChart, etc.

## Requirements

* Java 1.6
* Maven

## Banner Generator 

http://www.webestools.com/ascii-text-generator-ascii-art-code-online-txt2ascii-text2ascii-maker-free-text-to-ascii-converter.html

## Terminal

    $ cd ~/path/to/project/XDropWizard

## Build

    $ mvn clean package

## Run

    $ java -jar target/xdropwizard-1.0.0-SNAPSHOT.jar server xdropwizard.yml 
    
## Test Basics

    http://localhost:9090/hello-world
    http://localhost:9091/
    http://localhost:9091/healthcheck
    
## Run Tasks

    curl -X POST http://localhost:9091/tasks/gc
    
    curl -X POST http://localhost:9091/tasks/locksundialscheduler
    curl -X POST http://localhost:9091/tasks/unlocksundialscheduler
    curl -X POST http://localhost:9091/tasks/myjob
    curl -X POST http://localhost:9091/tasks/samplejob3
    
## Test XDropWizard

    coming soon...
    

## Profiling

    $ jvisualvm