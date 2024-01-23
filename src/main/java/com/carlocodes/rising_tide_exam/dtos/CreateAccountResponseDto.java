package com.carlocodes.rising_tide_exam.dtos;

public class CreateAccountResponseDto extends ResponseDto {
    private Long customerNumber;

    public Long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Long customerNumber) {
        this.customerNumber = customerNumber;
    }

    @Override
    public String toString() {
        return "CreateAccountResponseDto{" +
                "customerNumber=" + customerNumber +
                '}';
    }
}
