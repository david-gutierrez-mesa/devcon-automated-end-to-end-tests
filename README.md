# Automated end-to-end Tests and other Legendary Creatures
## Description
This git project is the exercise we are following in our workshop 'Automated end-to-end Tests and other Legendary Creatures'.

We are going to implement 4 parts, that correspond with the 4 parts a test should ideally have:
1. Preparation
2. Execution 
3. Assertion 
4. Clean up the environment

## Before the workshop
### Liferay instance to run tests against
Firs step is to prepare an environment to run automation against it. It's going to be our Liferay customization to be tested.

Here we have two possibilities. Download and run our docker image with everything already configured or just import our [Amazing Fragment](src/test/resources/fragments/MyAmazingCollection-V1.0.zip) into an already running liferay instance (Liferay 7.1 or higher). We recommend using our docker container.

#### Docker image
You can to download and install an already configured docker container with a customized instance of Liferay inside.

:warning: **Try to download and run the docker image before the workshop since it may take some time**

In order to do it, just follow:
1. Install docker https://docs.docker.com/get-docker/
2. Increase your Memory in docker to at least 6.00 GB (settings-> Resources -> Advanced -> Memory)
3. In a console just run (please note you can change the port and select another one instead of 9080)
> docker run --name my-local-liferay-test --rm -p 9080:8080 dgutimesa/my-test:7.4.3.10-ga10

#### Import fragment in an already running Liferay instance (7.1 or higher)

1. Import [My Amazing Fragment](src/test/resources/fragments/MyAmazingCollection-V1.0.zip) 
2. Create a **private content page** and call "My Amazing Page". Add the imported fragment. Publish it. 
3. If you are running 7.2 or older, go to [java/utils/APIs/Company.java](src/test/java/utils/APIs/Company.java) file in this automation project, remove comment for line 19 and comment out lines 24 to 29.
4. If you are running 7.3 or newer, login as admin in Liferay portal, go to "Instance Settings > User Authentication" and uncheck "Require strangers to verify their email address?"

:warning: Tested for the following clean docker images; liferay/portal:7.1.3-ga4, liferay/portal:7.2.1-ga2, liferay/portal:7.3.7-ga8 and liferay/portal:7.4.3.10-ga10. Other Liferay versions or not clean instances may not work.

###Liferay Test Automation
Also clone this repo and open it with your favorite IDE (we are going to use IntellJ IDEA to show it)

We recommend you to install some plugin for Java, Gradle and Cucumber.

We also recommend having installed Chrome browser. Otherwise, you will need to pass the browser you want to use when executing the tests.

## How to run automation?
### Run against downloaded docker with Chrome
After install the docker image and clone this repo, in order to run the tests in a console you must just do (please mind if you need to change the port or not):
> ./gradlew :cleanTest :test -Durl=http://localhost:9080/

### Run with default browser (Chrome) against localhost:8080
We just need to run "cucumber" task with Gradle in the root project folder:

```
./gradlew :cleanTest :test
```

Or execute test runner java file RunTests.java from your ID.

### Run against not local instances
By default, tests are executed against localhost with http protocol and using port 8080.

If you want to run against another instance you have access to can use the flag -Durl=protocol://url:port/. You must specify the three of them: protocol + url + port.

For example, to run against 192.168.40.58 with protocol http and port 7300, just use:

```
./gradlew :cleanTest :test -Durl=http://192.168.40.58:7300/
```

### Run with other browsers
Default browser is Chrome. To run in another browser, we need to have it installed in our OS and use -Dbrowser=browserName flag.

For example, to run with FireFox, just use:

```
./gradlew :cleanTest :test -Dbrowser=firefox
```

### Run for continuous integration environments
IF we want to ru our tests in a continuous integration environment we can add the  flag -Dci=true. This will run browsers headless. If we don't set it, or we set any other value than true, test are executed in normal mode. 

Use example:

```
./gradlew :cleanTest :test -Dci=true
```

## Test results
After test execution, a basic HTML report is automatically generated in ./target/ folder.


## Contact
If you have any further question, just email me us to david.gutierrez@liferay.com or magdalena.jedraszak@liferay.com
