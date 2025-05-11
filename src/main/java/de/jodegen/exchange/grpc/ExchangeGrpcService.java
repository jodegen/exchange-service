package de.jodegen.exchange.grpc;

import de.jodegen.exchange.service.ExchangeRateService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class ExchangeGrpcService extends ExchangeServiceGrpc.ExchangeServiceImplBase {

    private final ExchangeRateService exchangeRateService;

    @Override
    public void getRate(CurrencyRequest request, StreamObserver<ExchangeRateResponse> responseObserver) {
        String currencyCode = request.getCurrencyCode();
        var exchangeRate = exchangeRateService.getExchangeRate(currencyCode);
        var response = ExchangeRateResponse.newBuilder()
                .setCurrencyCode(exchangeRate.getCurrencyCode())
                .setRate(exchangeRate.getRate().doubleValue())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllRates(Empty request, StreamObserver<ExchangeRateListResponse> responseObserver) {
        var exchangeRates = exchangeRateService.getExchangeRates();
        var responseBuilder = ExchangeRateListResponse.newBuilder();

        for (var exchangeRate : exchangeRates) {
            var response = ExchangeRateResponse.newBuilder()
                    .setCurrencyCode(exchangeRate.getCurrencyCode())
                    .setRate(exchangeRate.getRate().doubleValue())
                    .build();
            responseBuilder.addRates(response);
        }
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}

