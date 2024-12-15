import fetch from 'node-fetch';

const query = `
  query {
    user(id: 1) {
      id
      name
      email
    }
  }
`;

fetch('http://localhost:8080/graphql', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({ query }),
})
  .then(response => response.json())
  .then(data => console.log('data returned:', data))
  .catch(error => console.error('Error:', error));