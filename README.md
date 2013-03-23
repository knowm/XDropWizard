# XDropWizard

A starting-point Webservice using DropWizard and containing some Xeiam OSS projects such as Yank, Sundial, and XChart

## Requirements

* Java 1.7
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
    
## Test XDropWizard

    coming soon...
    

## Profiling

    $ jvisualvm