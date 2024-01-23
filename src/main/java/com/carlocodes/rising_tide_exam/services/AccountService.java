package com.carlocodes.rising_tide_exam.services;

import com.carlocodes.rising_tide_exam.dtos.AccountDto;
import com.carlocodes.rising_tide_exam.dtos.CreateAccountResponseDto;
import com.carlocodes.rising_tide_exam.dtos.CustomerDto;
import com.carlocodes.rising_tide_exam.entities.Account;
import com.carlocodes.rising_tide_exam.entities.Customer;
import com.carlocodes.rising_tide_exam.exceptions.CustomerNotFoundException;
import com.carlocodes.rising_tide_exam.exceptions.ValidationException;
import com.carlocodes.rising_tide_exam.repositories.AccountRepository;
import com.carlocodes.rising_tide_exam.repositories.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountService(AccountRepository accountRepository,
                          CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    public CreateAccountResponseDto createAccount(CustomerDto customerDto) throws Exception {
        try {
            if (Objects.isNull(customerDto.getCustomerEmail()))
                throw new ValidationException("Email is required field");

            Customer customer = createCustomer(customerDto);

            CreateAccountResponseDto createAccountResponseDto = new CreateAccountResponseDto();
            createAccountResponseDto.setCustomerNumber(customer.getId());
            createAccountResponseDto.setTransactionStatusCode(HttpStatus.CREATED.value());
            createAccountResponseDto.setTransactionStatusDescription("Customer account created");

            return createAccountResponseDto;
        } catch (Exception e) {
            throw new Exception(String.format("Create account failed for %s due to %s",
                    customerDto.getCustomerName(), e.getMessage()), e);
        }
    }

    private Customer createCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setCustomerName(customerDto.getCustomerName());
        customer.setCustomerMobile(customerDto.getCustomerMobile());
        customer.setCustomerEmail(customerDto.getCustomerEmail());
        customer.setAddress1(customerDto.getAddress1());
        customer.setAddress2(customerDto.getAddress2());

        List<Account> accounts = customerDto.getAccounts().stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());

        customer.setAccounts(saveAccounts(accounts));

        return customerRepository.save(customer);
    }

    private List<Account> saveAccounts(List<Account> accounts) {
        return accountRepository.saveAll(accounts);
    }

    private Account mapToEntity(AccountDto accountDto) {
        Account account = new Account();
        account.setAccountType(accountDto.getAccountType());
        account.setAvailableBalance(accountDto.getAvailableBalance());
        return account;
    }

    public CustomerDto getCustomerAccounts(Long customerId) throws Exception {
        try {
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found!"));

            CustomerDto customerDto = mapToDto(customer);
            customerDto.setTransactionStatusCode(HttpStatus.FOUND.value());
            customerDto.setTransactionStatusDescription("Customer Account found");

            return customerDto;
        } catch (Exception e) {
            throw new Exception(String.format("Get customer accounts failed for %d due to %s",
                    customerId, e.getMessage()), e);
        }
    }

    private CustomerDto mapToDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerNumber(customer.getId());
        customerDto.setCustomerName(customer.getCustomerName());
        customerDto.setCustomerMobile(customer.getCustomerMobile());
        customerDto.setCustomerEmail(customer.getCustomerEmail());
        customerDto.setAddress1(customer.getAddress1());
        customerDto.setAddress2(customer.getAddress2());

        List<AccountDto> accountDtos = customer.getAccounts().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        customerDto.setAccounts(accountDtos);

        return customerDto;
    }

    private AccountDto mapToDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber(account.getId());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setAvailableBalance(account.getAvailableBalance());

        return accountDto;
    }
}
