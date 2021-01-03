package com.wallet.handler;

import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.http.MutableHeaders;
import ratpack.registry.Registry;

public class CORSHandler implements Handler {
    @Override
    public void handle(Context ctx) {
        MutableHeaders headers = ctx.getResponse().getHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, content-length, authorization, accept, response-type");
        ctx.next(Registry.single(String.class, "https://" + ctx.getRequest().getHeaders().get("Host")));
    }
}