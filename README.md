How to start Postgresql DB locally: 

1) Navigate to project root
2) Run command 'docker-compose up -d' 

PS: To clean DB totally (for example in case of migration rewrite),  need to clean up docker mapped volume with commands:
1) run command  'rm -rf ~/apps/postgres'
2) Restart Postgres container 'docker-compose up -d' 


User creation example (from postman):

curl --location --request POST 'localhost:8080/api/v1/user/register' \
--header 'Content-Type: application/json' \
--data-raw '{
"email": "someone@test.com",
"password" : "123467"
}'