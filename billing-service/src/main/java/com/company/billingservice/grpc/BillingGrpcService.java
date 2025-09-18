package com.company.billingservice.grpc;

import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

@Override
    public void createBillingAccount(billing.BillingRequest billingRequest,
                                     StreamObserver<BillingResponse>responseObserver){

    log.info("createBillingAccount request received {} ",billingRequest.toString());

    //Business logic - e .g save to database perform calculates
    BillingResponse billingResponse = BillingResponse.newBuilder()
            .setAccountId("12345")
            .setStatus("Success")
            .build();

    responseObserver.onNext(billingResponse);
    responseObserver.onCompleted();

}

}
