package pe.joedayz.sample.axonbank;

import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import pe.joedayz.sample.axonbank.account.Account;
import pe.joedayz.sample.axonbank.coreapi.CreateAccountCommand;
import pe.joedayz.sample.axonbank.coreapi.WithdrawMoneyCommand;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

public class Application {


    public static void main(String[] args) {
        Configuration config = DefaultConfigurer.defaultConfiguration()
                .configureAggregate(Account.class)
                .configureEmbeddedEventStore(c -> new InMemoryEventStorageEngine())
                .configureCommandBus(c -> new AsynchronousCommandBus())
                .buildConfiguration();

        config.start();
        config.commandBus().dispatch(asCommandMessage(new CreateAccountCommand("4321", 500)));
        config.commandBus().dispatch(asCommandMessage(new WithdrawMoneyCommand("4321", 250)));
        config.commandBus().dispatch(asCommandMessage(new WithdrawMoneyCommand("4321", 251)));

    }
}
