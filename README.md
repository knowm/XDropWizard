# XDropWizard

A starting-point Webservice using DropWizard and containing Xeiam's normal dev stack including projects such as Yank, Sundial (a Quartz fork), Flot, Markdown, Redis, HSQLDB, ZeroMQ, XChart, etc.

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
    
## Sundial

Integrating [Sundial](https://github.com/timmolter/Sundial) into a DropWizard instance requires minimal setup, and once it's all configured and running, 
the scheduling and automatic running of jobs is straight forward and stable. For those not familiar with Sundial, it is a simplified fork of [Quartz](http://quartz-scheduler.org/) 
developed by Xeiam. A lot of the bloat and confusion of configuring Quartz was removed in creating Sundial and a convenient wrapper around jobs was added to enable 
more modular job building and organization. Sundial creates a threadpool on application startup and uses it for background jobs.

There are two different types of jobs:

* Jobs which need to run at a specific time, via a cron-like expression (trigger defined in jobs.xml)
* Manually triggered jobs via a POST to a task

### jobs.xml

Put a file called jobs.xml on your classpath. See jobs.xml in `src/main/resources` to see two jobs. the `SampleJob3` job has an associated trigger as well 
as a key-value pair, which the job has access to. 

        <job>
            <name>SampleJob3</name>
            <job-class>com.xeiam.xdropwizard.jobs.SampleJob3</job-class>
            <job-data-map>
                <entry>
                    <key>MyParam</key>
                    <value>42</value>
                </entry>
            </job-data-map>
        </job>
        <trigger>
            <cron>
                <name>SampleJob3-Trigger</name>
                <group>CRON</group>
                <job-name>SampleJob3</job-name>
                <cron-expression>0/45 * * * * ?</cron-expression>
            </cron>
        </trigger>

        <job>
            <name>MyJob</name>
            <job-class>com.xeiam.xdropwizard.jobs.MyJob</job-class>
        </job>

### MyJob.java

This extremely simple example job demonstrates how easy it is to get a basic job coded. Whenever it's run, it just logs a message, but it could do anything you want.

    public class MyJob extends Job {
    
      private final Logger logger = LoggerFactory.getLogger(MyJob.class);
    
      @Override
      public void doRun() throws JobInterruptException {
    
        logger.info("MyJob says hello!");
      }
    }
    
### SampleJob3.java

This job is slightly more complicated and it demonstrates two nice features of Sundial. First it logs the value for myParam which it gets from jobs.xml. 
Second it uses a `JobAction` and passes it a parameter via the `JobContext`. Using `JobAction`s is a good way to reuse common job actions across many different 
jobs, mixing and matching if desired. This keeps your jobs organized.

    public class SampleJob3 extends Job {
    
      private final Logger logger = LoggerFactory.getLogger(SampleJob3.class);
    
      @Override
      public void doRun() throws JobInterruptException {
    
        JobContext context = getJobContext();
    
        String valueAsString = context.get("MyParam");
        logger.info("valueAsString = " + valueAsString);
    
        Integer valueAsInt = Integer.valueOf(valueAsString);
        logger.info("valueAsInt = " + valueAsInt);
    
        context.put("MyValue", new Integer(123));
    
        new SampleJobAction().run();
    
      }
    }

### SampleJob3Task.java

Here, we see how to hook a job into DropWizard's environment as a task for asynchronously starting it via a POST. 

    public class SampleJob3Task extends Task {
    
      /**
       * Constructor
       */
      public SampleJob3Task() {
    
        super("samplejob3");
      }
    
      @Override
      public void execute(ImmutableMultimap<String, String> arg0, PrintWriter arg1) throws Exception {
    
        SundialJobScheduler.startJob("SampleJob3");
    
      }
    }

### SundialManager.java

`SundialManager.java` is the class responsible for starting the scheduler and it is hooked into DropWizard in the `Service` class by 
including the following line of code:
    
    SundialManager sm = new SundialManager(configuration.getSundialProperties()); 
    environment.manage(sm);
    
In your `*.yml` DropWizard configuration file, you can easily set some helpful parameters to customize Sundial as DropWizard starts up, right from the config file:

    sundial:
 
        thread-pool-size: 5
        shutdown-on-unload: true
        wait-on-shutdown: false
        start-delay-seconds: 0
        start-scheduler-on-load: true
        global-lock-on-load: false
 
### Sundial Asynchronous Control via HTTP

By defining some tasks and hooking them into DropWizard you can asynchronously trigger your jobs and/or put a global lock and unlock on the Sundial scheduler.

    curl -X POST http://localhost:9091/tasks/locksundialscheduler
    curl -X POST http://localhost:9091/tasks/unlocksundialscheduler
    curl -X POST http://localhost:9091/tasks/myjob
    curl -X POST http://localhost:9091/tasks/samplejob3
    
    