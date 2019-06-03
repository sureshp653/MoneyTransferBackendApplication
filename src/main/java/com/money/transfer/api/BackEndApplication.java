package com.money.transfer.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.money.transfer.api.configuration.component.BackEndApplicationComponent;
import com.money.transfer.api.configuration.component.DaggerBackEndApplicationComponent;
import com.money.transfer.api.controller.AccountController;
import com.money.transfer.api.controller.TransactionController;
import com.money.transfer.api.model.Response;
import io.javalin.ForbiddenResponse;
import io.javalin.Javalin;
import io.javalin.JavalinEvent;
import io.javalin.json.JavalinJson;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import static io.javalin.apibuilder.ApiBuilder.*;

public class BackEndApplication {

    private static final Logger LOG = LoggerFactory.getLogger(BackEndApplication.class);

    private static final int PORT = 8080;
    static Javalin backEndApp=null;

    public static void main(String[] args){

        final BackEndApplicationComponent backEndApplicationComponent = DaggerBackEndApplicationComponent.create();

        final Gson gson = new GsonBuilder().create();
        JavalinJson.setFromJsonMapper(gson::fromJson);
        JavalinJson.setToJsonMapper(gson::toJson);


        startService(backEndApplicationComponent);
    }

    private static void startService(BackEndApplicationComponent backEndApplicationComponent){

        final AccountController accountController = backEndApplicationComponent.accountController();
        final TransactionController transactionController = backEndApplicationComponent.transactionController();

        backEndApp = Javalin.create()
                .event(JavalinEvent.SERVER_STARTED, () -> LOG.info("server has started"))
                .event(JavalinEvent.SERVER_START_FAILED, () -> LOG.error("server start has failed"))
                .requestLogger((context, executionTimeMs) ->
                        LOG.info("{} ms\t {}\t {} {}",
                                executionTimeMs,
                                context.req.getMethod(),
                                context.req.getRequestURI(),
                                context.req.getParameterMap().toString().replaceAll("^.|.$", "")
                        ))
                .start(PORT);


        backEndApp.get("/", context -> {
            throw new ForbiddenResponse();
        });

        backEndApp.get("/health", context ->
                context
                        .status(200)
                        .json(Response.builder().message("OK").build())
        );

        backEndApp.routes(() -> {
            path("/account", () -> {
                path(":id", () -> {
                    get(accountController::getOne);
                    delete(accountController::delete);
                });
                get(accountController::getAll);
                post(accountController::create);
            });

            path("/transaction", () -> {
                path(":id", () -> get(transactionController::getOne));
                get(transactionController::getAll);
                post(transactionController::commit);
            });
        });


        backEndApp.exception(Exception.class, (exception, context) -> {
            context.status(500);
            LOG.error("error occurred", exception);
        });

    }

    public static boolean stopService(){
        try {
            backEndApp.stop();
        } catch (Exception ex){
            return false;
        }
        return true;
    }
}
