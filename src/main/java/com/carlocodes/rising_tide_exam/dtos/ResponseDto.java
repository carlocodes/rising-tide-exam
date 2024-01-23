package com.carlocodes.rising_tide_exam.dtos;

public class ResponseDto {
    private Integer transactionStatusCode;
    private String transactionStatusDescription;

    public Integer getTransactionStatusCode() {
        return transactionStatusCode;
    }

    public void setTransactionStatusCode(Integer transactionStatusCode) {
        this.transactionStatusCode = transactionStatusCode;
    }

    public String getTransactionStatusDescription() {
        return transactionStatusDescription;
    }

    public void setTransactionStatusDescription(String transactionStatusDescription) {
        this.transactionStatusDescription = transactionStatusDescription;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "transactionStatusCode=" + transactionStatusCode +
                ", transactionStatusDescription='" + transactionStatusDescription + '\'' +
                '}';
    }
}
