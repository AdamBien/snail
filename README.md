# snail
Java EE Is Too Fast For Stress Testing--Snail Helps You With That.
Snail delays all responses with a dynamically configurable amount of time.

## Requirements

Java 8 / Java EE 7 with JAX-RS endpoints

## Installation

````xml
 <dependency>
	 <groupId>com.airhacks</groupId>
	 <artifactId>snail</artifactId>
	 <version>[RECENT_VERSION]</version>
 </dependency>
```

## Configuration

1. Set property with: snail-slowdown-in-ms=[DELAY IN MS].
2. Override the system property with an equally-named header.

## Verification

Snail attaches the currently configured delay to each request.

## System Test

The system test for the snail filter (documentation purposes):

```java
    @Rule
    JAXRSClientProvider provider = JAXRSClientProvider.buildWithURI("http://localhost:8080/snail-sample/resources/snails");

    @Test
    public void snails() {
        final String delay = "1000";
        long start = System.currentTimeMillis();
        Response response = provider.
                target().
                request().
                header(DelayProvider.DELAY_HEADER_NAME, delay).
                get();
        assertThat(response.getStatus(), is(200));
        String delayValue = response.getHeaderString(DelayProvider.DELAY_HEADER_NAME);
        long end = (System.currentTimeMillis() - start);
        assertThat(delayValue, is(delay));
        assertTrue(end > Long.valueOf(delayValue));
    }
````



