import { createClient } from 'graphql-ws';
import WebSocket from 'ws';

// Create the WebSocket client
const client = createClient({
    url: 'ws://localhost:8080/subscriptions', // Replace with your GraphQL subscription endpoint
    webSocketImpl: WebSocket,
    connectionParams: {
      protocol: 'graphql-ws',
    },
    connectionAckWaitTimeout: 5000, // Optional: Set a timeout for the connection acknowledgment
    lazy: true, // Optional: Only connect when there are active subscriptions
    keepAlive: 12000, // Optional: Send keep-alive messages every 12 seconds
});

// Define the subscription query
const SUBSCRIPTION_QUERY = `
  subscription userUpdated($id: ID!) {
    userUpdated(id: $id) {
      id
      name
      email
    }
  }
`;

// Start the subscription
(async () => {
    const onNext = (data) => {
        console.log('Subscription data:', JSON.stringify(data, null, 2));
    };

    const onError = (error) => {
        console.error('Subscription error:', error);
    };

    const onComplete = () => {
        console.log('Subscription complete');
    };

    const unsubscribe = await client.subscribe(
        {
            query: SUBSCRIPTION_QUERY,
            variables: { id: 1 }, // Replace with the user ID you want to listen to
        },
        {
            next: onNext,
            error: onError,
            complete: onComplete,
        }
    );

    // Unsubscribe after some time (optional)
    setTimeout(() => {
        unsubscribe();
        console.log('Unsubscribed from userUpdated');
    }, 60000); // Unsubscribe after 60 seconds
})();
