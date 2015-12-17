package com.airhacks;

import javax.json.Json;
import javax.json.JsonArray;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author airhacks.com
 */
@Path("snails")
public class SnailsResource {

    @GET
    public JsonArray snails() {
        return Json.createArrayBuilder().
                add("speedy").
                add("turbo")
                .add("rocket").
                build();
    }

}
