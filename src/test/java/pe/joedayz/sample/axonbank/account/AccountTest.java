package pe.joedayz.sample.axonbank.account;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;
import pe.joedayz.sample.axonbank.coreapi.AccountCreatedEvent;
import pe.joedayz.sample.axonbank.coreapi.CreateAccountCommand;
import pe.joedayz.sample.axonbank.coreapi.MoneyWithdrawEvent;
import pe.joedayz.sample.axonbank.coreapi.WithdrawMoneyCommand;


public class AccountTest {

    private FixtureConfiguration<Account> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new AggregateTestFixture(Account.class);
    }

    @Test
    public void testCreateAccount() throws Exception{
        fixture.givenNoPriorActivity()
                .when(new CreateAccountCommand("1234", 1000))
                .expectEvents(new AccountCreatedEvent("1234", 1000));
    }


    @Test
    public void testWithdrawReasonableAmount(){
        fixture.given(new AccountCreatedEvent("1234", 1000))
                .when(new WithdrawMoneyCommand("1234", 600))
                .expectEvents(new MoneyWithdrawEvent("1234", 600, -600));
    }

    @Test
    public void testWithdrawAbsurdAmount(){
        fixture.given(new AccountCreatedEvent("1234", 1000))
                .when(new WithdrawMoneyCommand("1234", 1005))
                .expectNoEvents()
                .expectException(OverdraftLimitExceededException.class);
    }

    @Test
    public void testWithdrawTwice() {
        fixture.given(new AccountCreatedEvent("1234", 1000),
        new MoneyWithdrawEvent("1234", 999, -999))
                .when(new WithdrawMoneyCommand("1234", 2))
                .expectNoEvents()
                .expectException(OverdraftLimitExceededException.class);


    }



}