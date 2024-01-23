package com.carlocodes.rising_tide_exam.services;

import com.carlocodes.rising_tide_exam.dtos.AccountDto;
import com.carlocodes.rising_tide_exam.dtos.CreateAccountResponseDto;
import com.carlocodes.rising_tide_exam.dtos.CustomerDto;
import com.carlocodes.rising_tide_exam.entities.Account;
import com.carlocodes.rising_tide_exam.entities.Customer;
import com.carlocodes.rising_tide_exam.enums.AccountType;
import com.carlocodes.rising_tide_exam.repositories.AccountRepository;
import com.carlocodes.rising_tide_exam.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @InjectMocks
    private AccountService accountService;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void createAccountTest() throws Exception {
        CustomerDto customerDto = customerDto();
        Account account = account();
        Customer customer = customer();

        when(accountRepository.saveAll(any())).thenReturn(List.of(account));
        when(customerRepository.save(any())).thenReturn(customer);

        CreateAccountResponseDto createAccountResponseDto = accountService.createAccount(customerDto);

        assertNotNull(createAccountResponseDto);
        assertEquals(customer.getId(), createAccountResponseDto.getCustomerNumber());
        assertEquals(HttpStatus.CREATED.value(), createAccountResponseDto.getTransactionStatusCode());
        assertEquals("Customer account created", createAccountResponseDto.getTransactionStatusDescription());

        verify(accountRepository, times(1)).saveAll(any());
        verify(customerRepository, times(1)).save(any());
    }

    // Exception scenario
    @Test
    public void createAccountNoEmailTest() {
        CustomerDto customerDto = customerDto();
        customerDto.setCustomerEmail(null);

        Throwable exception = assertThrows(Exception.class, () -> {
            accountService.createAccount(customerDto);
        });

        assertEquals("Create account failed for Gian due to Email is required field", exception.getMessage());
    }

    @Test
    public void getCustomerAccountsTest() throws Exception {
        Long customerId = 1L;
        Customer customer = customer();

        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));

        CustomerDto customerDto = accountService.getCustomerAccounts(customerId);

        assertNotNull(customerDto);
        assertEquals(customer.getId(), customerDto.getCustomerNumber());
        assertEquals(customer.getCustomerName(), customerDto.getCustomerName());
        assertEquals(customer.getCustomerMobile(), customerDto.getCustomerMobile());
        assertEquals(customer.getCustomerEmail(), customerDto.getCustomerEmail());
        assertEquals(customer.getAddress1(), customerDto.getAddress1());
        assertEquals(customer.getAddress2(), customerDto.getAddress2());

        assertNotNull(customerDto.getAccounts());
        assertEquals(customer.getAccounts().get(0).getId(), customerDto.getAccounts().get(0).getAccountNumber());
        assertEquals(customer.getAccounts().get(0).getAccountType(), customerDto.getAccounts().get(0).getAccountType());
        assertEquals(customer.getAccounts().get(0).getAvailableBalance(), customerDto.getAccounts().get(0).getAvailableBalance());
    }

    @Test
    public void getCustomerAccountsNoCustomerTest() {
        Long customerId = 2L;

        when(customerRepository.findById(any())).thenReturn(Optional.empty());

        Throwable exception = assertThrows(Exception.class, () -> {
            accountService.getCustomerAccounts(customerId);
        });

        assertEquals("Get customer accounts failed for 2 due to Customer not found!", exception.getMessage());
    }

    public CustomerDto customerDto() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerName("Gian");
        customerDto.setCustomerMobile("09776177381");
        customerDto.setCustomerEmail("carlonickpour@gmail.com");
        customerDto.setAddress1("Address 1");
        customerDto.setAddress2("Address 2");
        customerDto.setAccounts(List.of(accountDto()));
        return customerDto;
    }

    public AccountDto accountDto() {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountType(AccountType.S);
        accountDto.setAvailableBalance(BigDecimal.valueOf(500));
        return accountDto;
    }

    public Customer customer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setCustomerName("Gian");
        customer.setCustomerMobile("09776177381");
        customer.setCustomerEmail("carlonickpour@gmail.com");
        customer.setAddress1("Address 1");
        customer.setAddress2("Address 2");
        customer.setAccounts(List.of(account()));
        return customer;
    }

    public Account account() {
        Account account = new Account();
        account.setId(1L);
        account.setAccountType(AccountType.S);
        account.setAvailableBalance(BigDecimal.valueOf(500));
        return account;
    }
}
