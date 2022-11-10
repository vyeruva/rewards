# Rewards calculator

This service calculates the rewards points based on customer's transactions.

A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every
dollar spent between $50 and $100 in each transaction.
(e.g., a $120 purchase = 2x$20 + 1x$50 = 90 points).

Currently, transactions are seeded from a seed file. This can be extended later to be able to accept transactions
through an endpoint.

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.rewards.rewards.RewardsApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Endpoints

The service provides the following endpoints

```http
GET /rewards
```
### Response

```javascript
[
  {
    "name" : string,
    "id" : string,
    "total_rewards": number,
    "rewards": [
      "month": number,
      "year": number,
      "rewards": number
    ]
  }
]
```

```http
GET /rewards/id
```
### Response

```javascript
{
  "name" : string,
  "id" : string,
  "total_rewards": number,
  "rewards": [
    "month": number,
    "year": number,
    "rewards": number
  ]
}
```

## FAQ

Q: Is the month zero indexed vs one indexed <br/>
A: One indexed. 1 = January and 12 = December

## Limitations

Currently, the max of total points that the app can reasonably process is less than 2147483647 (Integer.MAX_VALUE). 
This is because the points are calculated using integers. Can be changed to BigInteger to fix this limitation.

## Tech Debt
The Query to fetch the transactions goes back 90 days from the current date and time. For example, 
the date and time today is 11-09-2022 at 9:00 PM EST. The query picks all transactions which are created on or after 08-09-2022 at 9:00 PM.
I am not sure if this fits the requirements correctly. Does the requirement mean that we need to return all transactions in the month of 
August, September, October and November or is going back 90 days fine? Need to clarify the requirements and make changes as necessary.