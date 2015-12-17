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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

/**
 *
 * @author airhacks.com
 */
@Provider
public class DelayProvider implements WriterInterceptor {

    private long delay;
    static final String DELAY_KEY = "snail-slowdown-in-ms";

    public DelayProvider() {
        String slowdown = System.getProperty(DELAY_KEY, "0");
        this.delay = convert(slowdown);
    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        MultivaluedMap<String, Object> headers = context.getHeaders();
        if (delay > 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                throw new IllegalStateException(ex);
            }
        }
        long start = System.currentTimeMillis();
        try {
            context.proceed();
        } finally {
            long duration = (System.currentTimeMillis() - start);
            headers.add("snail-request-duration", duration);
        }
        headers.add(DELAY_KEY, delay);
    }

    public static long convert(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }
}
