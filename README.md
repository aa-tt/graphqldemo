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

Note: the graphql service is only accessible at POST /graphql endpoint with no @RestController needed in the springboot app server. Also, all updates are by Mutation type and all fetch are by Query type. The POST payload from client should contain query and mutation schema.
Additionally, subscription is accessible at POST /subscriptions endpoint by default with payload from client should contain subscription schema.

Testing subscription
- From mac:
```bash
  (echo '{"id":"1", "type":"start", "payload":{"query":"subscription userUpdated($id: ID!) { userUpdated(id: $id) { id name email } }","variables":{"id":"1"}}}') | websocat ws://localhost:8080/subscriptions
```
- From nodejs client:
```bash
  node graphqlClientSubscription
```