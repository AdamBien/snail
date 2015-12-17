/*
 * Copyright 2015 Adam Bien.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.airhacks.snail.rs;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author airhacks.com
 */
@Provider
public class DelayProvider implements ContainerResponseFilter {

    private long delay;
    public static final String DELAY_HEADER_NAME = "snail-slowdown-in-ms";

    public DelayProvider() {
        String slowdown = System.getProperty(DELAY_HEADER_NAME, "0");
        this.delay = convert(slowdown);
        System.out.println("snail initialized with: " + this.delay + " ms.");
    }

    public static long convert(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        System.out.println("responseContext = " + requestContext.getHeaders());
        String delayConfig = requestContext.getHeaderString(DELAY_HEADER_NAME);
        if (delayConfig != null) {
            this.delay = convert(delayConfig);
        }
        if (delay > 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                throw new IllegalStateException(ex);
            }
        }
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
        headers.putSingle(DELAY_HEADER_NAME, delay);

    }
}
