package pl.bs.accountorganizer.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pl.bs.accountorganizer.controllers.msg.AccountMsg;
import pl.bs.accountorganizer.models.Account;
import pl.bs.accountorganizer.models.CompanyAccount;
import pl.bs.accountorganizer.repositories.AccountRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository = mock(AccountRepository.class, Mockito.RETURNS_DEEP_STUBS);;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void create_EmailChanged_ShouldUpdateEmail() {
        //given
        AccountMsg accountMsg = new AccountMsg(
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


        CompanyAccount companyAccount = new CompanyAccount("janulinek", "Jan", "Kowalski", "111111111", "222222222", "ul. Zielona 2/4, 00-123 Warszawa", "COM_ID");
        Account presentAccount = new Account("janulinek", "000001", "stary.email@kowalski.pl", companyAccount);
        Account account = new Account("janulinek", "000001", "jan@kowalski.pl", companyAccount);
        account.setPreviousEmail("stary.email@kowalski.pl");

        //when
        when(accountRepository.getByLogin("janulinek")).thenReturn(presentAccount);

        //then
        Account result = accountService.create(accountMsg, companyAccount);
        assertEquals(account.getLogin(), result.getLogin());
        assertEquals(account.getEmail(), result.getEmail());
        assertEquals(account.getPreviousEmail(), result.getPreviousEmail());
        assertEquals(account.getId(), result.getId());
        assertEquals(account.getDetailedAccount(), result.getDetailedAccount());


    }

    @Test
    void create_LoginChanged_ShouldUpdateLogin() {
        //given
        AccountMsg accountMsg = new AccountMsg(
                "Jan",
                "Kowalski",
                "1234567890",
                "Kowalstwo.com",
                "jan@kowalski.pl",
                "111111111",
                "222222222",
                "polonez",
                "000001",
                "ul. Zielona 2/4, 00-123 Warszawa");


        CompanyAccount companyAccount = new CompanyAccount("janulinek", "Jan", "Kowalski", "111111111", "222222222", "ul. Zielona 2/4, 00-123 Warszawa", "COM_ID");
        Account presentAccount = new Account("janulinek", "000001", "janl@kowalski.pl", companyAccount);
        Account account = new Account("polonez", "000001", "jan@kowalski.pl", companyAccount);

        //when
        when(accountRepository.getByLogin("janulinek")).thenReturn(presentAccount);

        //then
        Account result = accountService.create(accountMsg, companyAccount);
        assertEquals(account.getLogin(), result.getLogin());
        assertEquals(account.getEmail(), result.getEmail());
        assertEquals(account.getPreviousEmail(), result.getPreviousEmail());
        assertEquals(account.getId(), result.getId());
        assertEquals(account.getDetailedAccount(), result.getDetailedAccount());

    }

    @Test
    void create_DetailsChanged_ShouldUpdateAccountDetails() {
        //given
        AccountMsg accountMsg = new AccountMsg(
                "Marek",
                "Nowak",
                "1234567890",
                "Kowalstwo.com",
                "jan@kowalski.pl",
                "444444444",
                "555555555",
                "janulinek",
                "000001",
                "ul. Niebieska 3/5, 61-123 Poznań");


        CompanyAccount presentCompanyAccount = new CompanyAccount("janulinek", "Jan", "Kowalski", "111111111", "222222222", "ul. Zielona 2/4, 00-123 Warszawa", "COM_ID");
        Account presentAccount = new Account("janulinek", "000001", "jan@kowalski.pl", presentCompanyAccount);
        CompanyAccount companyAccount = new CompanyAccount("janulinek", "Marek", "Nowak", "444444444", "555555555", "ul. Niebieska 3/5, 61-123 Poznań", "COM_ID");
        Account account = new Account("janulinek", "000001", "jan@kowalski.pl", companyAccount);

        //when
        when(accountRepository.getByLogin("janulinek")).thenReturn(presentAccount);

        //then
        Account result = accountService.create(accountMsg, companyAccount);
        assertEquals(account.getLogin(), result.getLogin());
        assertEquals(account.getEmail(), result.getEmail());
        assertEquals(account.getPreviousEmail(), result.getPreviousEmail());
        assertEquals(account.getId(), result.getId());
        assertEquals(account.getDetailedAccount(), result.getDetailedAccount());


    }



    @Test
    void generateNewId_TwiceGeneratedExistingId_ShouldInvoke3times() {
        //given
        CompanyAccount companyAccount = new CompanyAccount("janulinek", "Jan", "Kowalski", "111111111", "222222222", "ul. Zielona 2/4, 00-123 Warszawa", "COM_ID");
        Account account = new Account("janulinek", "000001", "jan@kowalski.pl", companyAccount);

        //when
        when(accountRepository.getById(any(String.class)))
                .thenReturn(account)
                .thenReturn(account)
                .thenReturn(null);

        //then
        assertDoesNotThrow(() -> accountService.generateNewId());
        verify(accountRepository, times(3)).getById(anyString());

    }

    @Test
    void isLoginUpdated_NullId_ShouldBeFalse() {
        String id = null;
        String login = "maluch126p";
        assertFalse(accountService.isLoginUpdated(id, login));
    }

    @Test
    void isLoginUpdated_UpdatedLogin_ShouldBeTrue() {
        //given
        String id = "000002";
        String login = "maluch126p";
        Account account = new Account("polonez", "000002", "pawel@korba.pl", null);

        //when
        when(accountRepository.getById("000002")).thenReturn(account);

        //then
        assertTrue(accountService.isLoginUpdated(id, login));
    }

    @Test
    void isLoginUpdated_LoginNotUpdated_ShouldBeFalse() {
        //given
        String id = "000002";
        String login = "maluch126p";
        Account account = new Account("maluch126p", "000002", "pawel@korba.pl", null);

        //when
        when(accountRepository.getById("000002")).thenReturn(account);

        //then
        assertFalse(accountService.isLoginUpdated(id, login));
    }

}