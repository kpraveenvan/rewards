## Start the Application using the mvn command: mvn clean install spring-boot:run (or simply run the RewardsApplication.java) and it starts the rewards application with an inmemory H2 database.

Create an initial Data Set: CSV File data.csv and Content inside the file is:

```csv
consumerId,first_name,last_name,transaction_date,transaction_amount
1,Praveen,vanga,08/01/2021,121.00
1,Praveen,vanga,07/01/2021,124.00
1,Praveen,vanga,06/01/2021,126.00
1,Praveen,vanga,05/01/2021,190.00
1,Praveen,vanga,04/01/2021,9.00
1,Praveen,vanga,03/01/2021,120.00
2,Praveen,Kumar,08/01/2021,67.00
2,Praveen,Kumar,07/01/2021,14.50
2,Praveen,Kumar,06/01/2021,190.67
2,Praveen,Kumar,05/01/2021,66.21
2,Praveen,Kumar,04/01/2021,120.21
2,Praveen,Kumar,03/01/2021,1500.00
```

# CURL Command used to create an initial dataset (Make sure you select the approriate location for data.csv):

curl --location --request POST 'http://localhost:8080/consumerTransactions/upload' \
--header 'Content-Type: application/csv' \
--form 'file=@"/C:/Users/prave/Downloads/data.csv"'

# Getting the rewards earned by customers Monthly and total rewards in last 3 months use the CURL:

curl --location --request GET 'http://localhost:8080/consumerTransactions/consumerPoints'

The Response for the service call is as below for the dataset:

```json
[
    {
        "customerId": 1,
        "firstName": "Praveen",
        "lastName": "vanga",
        "monthlyRewards": [
            {
                "month": "JUNE",
                "pointsForMonth": 102
            },
            {
                "month": "MAY",
                "pointsForMonth": 230
            },
            {
                "month": "AUGUST",
                "pointsForMonth": 92
            },
            {
                "month": "JULY",
                "pointsForMonth": 98
            }
        ],
        "totalPoints": 522.0
    },
    {
        "customerId": 2,
        "firstName": "Praveen",
        "lastName": "Kumar",
        "monthlyRewards": [
            {
                "month": "JUNE",
                "pointsForMonth": 230
            },
            {
                "month": "MAY",
                "pointsForMonth": 50
            },
            {
                "month": "AUGUST",
                "pointsForMonth": 50
            },
            {
                "month": "JULY",
                "pointsForMonth": 14
            }
        ],
        "totalPoints": 344.0
    }
]
```





