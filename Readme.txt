Techstack:
- Spring boot
- JPA
- Spring security with JWT
- Spring validation

Database:
- H2database

Project endpoint:

1. /login: Use this endpoint to get JWT token. Current have only one dummy user.
URL:
	http://20.212.171.61:8080/login
Method:
	POST
Header:
	Content-Type: application/json
Body:
{
    "username": "user",
    "password": "user"
}

2. /addBattery: Allow add new battery
URL:
	http://20.212.171.61:8080/addBattery
Method:
	POST
Header:
	Content-Type: application/json
	Authorization: Bearer <jwt token>
Body:
{
    "batteries" : [
        {
            "name": "battery4",
            "postcode": "6010",
            "capacity": 170
        },
        
        {
            "name": "battery5",
            "postcode": "6010",
            "capacity": 170
        }
    ]
}

3. /search: Search list of batteries base on input postcode range
URL:
	http://20.212.171.61:8080/search
Method:
	GET
Header:
	Authorization: Bearer <jwt token>
URL params:
	- start: start postcode (optional)
	- end: end postcode (optional)

Refer:
Powerledger.postman_collection.json
  
