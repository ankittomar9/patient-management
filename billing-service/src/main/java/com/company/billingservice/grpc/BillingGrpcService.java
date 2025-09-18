package com.company.billingservice.grpc;

import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

@Override
    public void createBillingAccount(billing.BillingRequest billingRequest,
                                     StreamObserver<BillingResponse>responseObserver){


}

}
