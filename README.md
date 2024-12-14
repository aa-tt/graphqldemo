## Springboot Graphql application
Resource exposed on server using graphql and accessed by nodejs client using fetch api.

#### Steps for starting server
```bash
mvn clean package -f pom.xml

mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

This will start the server with dev profile at `localhost:8080`.
#### Steps for setting data
- Test the server health by hitting the url in browser: `http://localhost:8080/actuator/health`.
- Set up a `User` record in the `h2` running in memory, by following below steps.
- Access db at url: `http://localhost:8080/h2-console`
- Insert a record in db
```bash
INSERT INTO USER(name, email) VALUES ('aa', 'aa@aa.com');
```
- Exit the db in the browser

#### Steps for starting client
```bash
cd client
npm install
npm start
```
On running `graphqlClient.js`, Following oputput can be seen in the console.
```bash
data returned: { data: { updateUser: { id: '1', name: 'aa1' } } }
```
The updated name can be checked in the db too.