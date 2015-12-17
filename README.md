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



