package pl.bs.accountorganizer.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.bs.accountorganizer.controllers.msg.AccountMsg;
import pl.bs.accountorganizer.models.Account;
import pl.bs.accountorganizer.models.Company;
import pl.bs.accountorganizer.models.CompanyAccount;
import pl.bs.accountorganizer.models.DetailedAccount;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AccountOrganizerFacadeTest {

    @Mock
    private AccountService accountService;
    @Mock
    private CompanyService companyService;
    @Mock
    private CompanyAccountService companyAccountService;
    @InjectMocks
    private AccountOrganizerFacade accountOrganizerFacade;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void organizeAndCreateAccounts_2AccountsFor1Company_SholudCreateCompanyAnd2CompanyAccounts() {
        //given
        List<AccountMsg> accountMsgs = new ArrayList<>();
        AccountMsg accountMsg1 = new AccountMsg(
                "Jan",
                "Kowalski",
                "1234567890",
                "Kowalstwo.com",
                "jan@kowalski.pl",
                "111111111",
                "222222222",
                "janulinek",
                "000001",
                "ul. Zielona 2/4, 00-123 Warszawa");
        AccountMsg accountMsg2 = new AccountMsg(
                "Marek",
                "Nowak",
                "123-456-78-90",
                "Kowalstwo.com",
                "marek@nowak.pl",
                "333333333",
                "222222222",
                "mnowak",
                "000002",
                "ul. Zielona 2/4, 00-123 Warszawa");
        accountMsgs.add(accountMsg1);
        accountMsgs.add(accountMsg2);

        Company company = new Company("COM_ID", "1234567890", "Kowalstwo.com", new ArrayList<>());
        CompanyAccount companyAccount1 = new CompanyAccount("janulinek", "Jan", "Kowalski", "111111111", "222222222", "ul. Zielona 2/4, 00-123 Warszawa", "COM_ID");
        CompanyAccount companyAccount2 = new CompanyAccount("mnowak", "Marek", "Nowak", "333333333", "222222222", "ul. Zielona 2/4, 00-123 Warszawa", "COM_ID");
        Account account1 = new Account("janulinek", "000001", "jan@kowalski.pl", companyAccount1);
        Account account2 = new Account("mnowak", "000002", "marek@nowak.pl", companyAccount2);
        Account account3 = new Account("COM_ID", "COM_ID", null, company);

        //when
        when(companyService.create(eq(accountMsg1), any(String.class))).thenReturn(company);
        when(companyService.companyNotExists("1234567890")).thenReturn(true);
        when(companyService.companyNotExists("123-456-78-90")).thenReturn(false);
        when(companyService.getCompanyLoginByNip(any(String.class))).thenReturn("COM_ID");
        when(accountService.createParent(any(AccountMsg.class), any(Company.class), any(String.class))).thenReturn(account3);
        when(accountService.create(accountMsg1, companyAccount1)).thenReturn(account1);
        when(accountService.create(accountMsg1, companyAccount1)).thenReturn(account2);
        when(accountService.generateNewId()).thenReturn("COM_ID");
        when(companyAccountService.create(eq(accountMsg1), any(String.class))).thenReturn(companyAccount1);
        when(companyAccountService.create(eq(accountMsg2), any(String.class))).thenReturn(companyAccount2);

        //then
        assertDoesNotThrow(() -> accountOrganizerFacade.organizeAndCreateAccounts(accountMsgs));
        verify(accountService, times(2)).create(any(AccountMsg.class), any(DetailedAccount.class));
        verify(accountService, times(1)).createParent(any(AccountMsg.class), any(DetailedAccount.class), eq("COM_ID"));
    }

    @Test
    void organizeAndCreateAccounts_NoLoginInRequest_SholudThrow400() {
        //given
        List<AccountMsg> accountMsgs = new ArrayList<>();
        AccountMsg accountMsg = new AccountMsg(
                "Pawel",
                "Korba",
                "1234567890",
                "Korbex sp. z o. o.",
                "maluch126p@korbex.pl",
                "111111111",
                "222222222",
                null,
                "000001",
                "ul. Zielona 2/4, 00-123 Warszawa");
        accountMsgs.add(accountMsg);

        //when

        //then
        RuntimeException exception = assertThrows(BadRequestException.class, () -> accountOrganizerFacade.organizeAndCreateAccounts(accountMsgs));
        assertEquals("Login can not be null. New structure of posted accounts not created.", exception.getMessage());

    }

    @Test
    void organizeAndCreate_NoLoginInRequest_SholudThrow400() {
        //given
        List<AccountMsg> accountMsgs = new ArrayList<>();
        AccountMsg accountMsg = new AccountMsg(
                "Pawel",
                "Korba",
                "1234567890",
                "Korbex sp. z o. o.",
                "maluch126p@korbex.pl",
                "111111111",
                "222222222",
                null,
                "000001",
                "ul. Zielona 2/4, 00-123 Warszawa");
        accountMsgs.add(accountMsg);

        //when

        //then
        RuntimeException exception = assertThrows(BadRequestException.class, () -> accountOrganizerFacade.organizeAndCreate(accountMsg));
        assertEquals("Login can not be null. New structure of posted accounts not created.", exception.getMessage());
    }

    @Test
    void organizeAndCreateAccounts_MissingAddressInRequest_SholudThrow400() {
        //given
        List<AccountMsg> accountMsgs = new ArrayList<>();
        AccountMsg accountMsg = new AccountMsg(
                "Pawel",
                "Korba",
                null,
                null,
                "maluch126p@korbex.pl",
                "111111111",
                "222222222",
                "maluch126p",
                "000001",
                null);
        accountMsgs.add(accountMsg);

        //when

        //then
        RuntimeException exception = assertThrows(BadRequestException.class, () -> accountOrganizerFacade.organizeAndCreateAccounts(accountMsgs));
        assertEquals("User account can not be created because of missing data in account: maluch126p. New structure of posted accounts not created.", exception.getMessage());
    }
}