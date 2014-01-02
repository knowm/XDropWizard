# XDropWizard

A starting-point Webservice using DropWizard and containing Xeiam's normal dev stack including projects such as Yank, Sundial (a Quartz fork), Flot, Markdown, 
Redis, HSQLDB, ZeroMQ, XChart, JUnit, etc.

## Requirements

* Java 1.6
* Maven

## Banner Generator 

http://www.webestools.com/ascii-text-generator-ascii-art-code-online-txt2ascii-text2ascii-maker-free-text-to-ascii-converter.html

## Terminal

    cd ~/path/to/project/XDropWizard

## Build

    mvn clean package
    
#### maven-license-plugin

    mvn license:check
    mvn license:format
    mvn license:remove

## Run

    $ java -jar target/xdropwizard-1.0.0-SNAPSHOT.jar server xdropwizard.yml 
    
## Test Basics

    http://localhost:9090/hello-world
    http://localhost:9091/
    http://localhost:9091/healthcheck
    
## Run Tasks

    curl -X POST http://localhost:9091/tasks/gc
    
## Sundial

Sundial is a lightweight Java job scheduling framework.

Integrating [Sundial](https://github.com/timmolter/Sundial) into a DropWizard instance requires minimal setup, and once it's all configured and running, 
the scheduling and automatic running of jobs is straight forward and stable. For those not familiar with Sundial, it is a simplified fork of [Quartz](http://quartz-scheduler.org/) 
developed by Xeiam. A lot of the bloat and confusion of configuring Quartz was removed in creating Sundial and a convenient wrapper around jobs was added to enable 
more modular job building and organization. Sundial creates a threadpool on application startup and uses it for background jobs.

There are two different types of jobs:

* Jobs which need to run at a specific time, via a cron-like expression (trigger defined in jobs.xml)
* Manually triggered jobs via a POST to a task

### jobs.xml

Put a file called `jobs.xml` on your classpath. See `jobs.xml` in `src/main/resources` to see two jobs. the `SampleJob3` job has an associated trigger as well 
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
    
## Yank
 
Yank is a very easy-to-use yet flexible Java persistence layer for JDBC-compatible databases build on top of 
[org.apache.DBUtils](http://commons.apache.org/dbutils/). Usage is very simple: define DB connectivity properties, create a DAO and POJO class, 
and execute queries.

Integrating Yank into DropWizard requires just a minimum of setup.

### DB.properties

The `DB.properties` file should be a familiar sight for people used to working with JDBC-compatible databases such as MySQL, HSQLDB, Oracle, and Postgres. 
Put a file called `DB.properties` on your classpath. See `DB.properties` in `src/main/resources`. In this file, you define the properties needed to connect to your 
database such as the JDBC driver class name, the user and password. Yank will load this file at startup and handle connecting to the database. 

### SQL.properties

Put a file called `SQL.properties` on your classpath. See `SQL.properties` in `src/main/resources`. The `SQL.properties` file is a place to centrally store your 
SQL statements. There are a few advantages to this. First, all your statements are found at a single place so you can see tham all at once. Secondly, if you want 
to switch your underlying database you'll need to rewrite all your SQL statements. If you have a `SQL.properties` file, you can just create a second one for the new 
database and easily make the transition. Of course, you can write all your SQL statements in the Java DAO classes directly as well.

### Book.java

Yank requires that you have a single POJO for each table in your database. The POJO's fields should match the column names and data types of the matching table. 
Add the getter and setters as well. 

    public class Book {
    
      private String title;
      private String author;
      private double price;
    
      /** Pro-tip: In Eclipse, generate all getters and setters after defining class fields: Right-click --> Source --> Generate Getters and Setters... */
    
      public String getTitle() {
        return title;
      }
    
      public void setTitle(String title) {
        this.title = title;
      }
    
      public String getAuthor() {
        return author;
      }
    
      public void setAuthor(String author) {
        this.author = author;
      }
    
      public double getPrice() {
        return price;
      }
    
      public void setPrice(double price) {
        this.price = price;
      }
    
    }

### BooksDAO.java

It is not required by Yank, but it really helps to organize your persistence layer code to have one DAO class for each table. The DAO class is just a collection 
of public static methods that each interact with Yank's `DBProxy` class. Note that in some of the following methods, the SQL statements are written directly as a 
String, while others come from the `SQL.properties` file on the classpath. The presence of the word `key` in the `DBProxy` method indicates that the SQL 
statement is being fetched from the `SQL.properties`.


    public class BooksDAO {
    
      public static int createBooksTable() {
    
        String sqlKey = "BOOKS_CREATE_TABLE";
        return DBProxy.executeSQLKey("myconnectionpoolname", sqlKey, null);
      }
    
      public static int insertBook(Book book) {
    
        Object[] params = new Object[] { book.getTitle(), book.getAuthor(), book.getPrice() };
        String SQL = "INSERT INTO BOOKS  (TITLE, AUTHOR, PRICE) VALUES (?, ?, ?)";
        return DBProxy.executeSQL("myconnectionpoolname", SQL, params);
      }
      
      public static List<Book> selectAllBooks() {
    
        String SQL = "SELECT * FROM BOOKS";
        return DBProxy.queryObjectListSQL("myconnectionpoolname", SQL, Book.class, null);
      }
       
      public static Book selectRandomBook() {
    
        String sqlKey = "BOOKS_SELECT_RANDOM_BOOK";
        return DBProxy.querySingleObjectSQLKey("myconnectionpoolname", sqlKey, Book.class, null);
      }
    
    }

### YankBookResource.java

In order to access objects from the database and return them as JSON, you need a resource class for it. It makes most sense to create a resource class for 
each table in your database. Don't forget to add this resource in `Service` class!

    @Path("book")
    @Produces(MediaType.APPLICATION_JSON)
    public class YankBookResource {
    
      @GET
      @Path("random")
      public Book getRandomBook() {
    
        return BooksDAO.selectRandomBook();
      }
    
      @GET
      @Path("all")
      public List<Book> getAllBooks() {
    
        return BooksDAO.selectAllBooks();
      }
    }

### YankManager.java

`YankManager.java` is the class responsible for setting up `Yank` and it is hooked into DropWizard in the `Service` class by 
including the following line of code:
    
    YankManager ym = new YankManager(configuration.getYankConfiguration()); // A DropWizard Managed Object
    environment.manage(ym); // Assign the management of the object to the Service
    environment.addResource(new YankBookResource());
    
In your `*.yml` DropWizard configuration file, you can easily define the database and SQL statement files that Yank uses:

    yank:

        dbPropsFileName: DB.properties
        sqlPropsFileName: SQL.properties
        
### Yank Database Access

Finally, once DropWizard is running, you can access the JSON objects via the following URLS:

    http://localhost:9090/book/random
    http://localhost:9090/book/all
    


    
