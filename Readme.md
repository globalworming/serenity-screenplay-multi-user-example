## example on how to integrate serenity bdd screenplay pattern into spring testing

this includes integration testing of the [ChatController](src/test/java/com/example/websocketdemo/controller/ChatControllerTest.java)

and [end-to-end testing](src/test/java/com/example/e2e/testcases/chat) with multiple actors

circleci build [![CircleCI](https://circleci.com/gh/globalworming/serenity-screenplay-multi-user-example/tree/master.svg?style=svg)](https://circleci.com/gh/globalworming/serenity-screenplay-multi-user-example/tree/master)

## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

3. Chrome


## Run the tests

```bash
mvn clean verify
```


## have a look at the report
`./target/site/serenity/index.html`
