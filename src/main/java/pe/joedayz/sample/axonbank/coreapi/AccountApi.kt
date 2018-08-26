package pe.joedayz.sample.axonbank.coreapi

import org.axonframework.commandhandling.TargetAggregateIdentifier

class CreateAccountCommand( val accountId: String, val overdraftLimit: Int)

class WithdrawMoneyCommand(@TargetAggregateIdentifier val accountId: String, val amount: Int)

class AccountCreatedEvent( val accountId: String, val overdraftLimit: Int)

class MoneyWithdrawEvent( val accountId: String, val amount: Int, val balance : Int)

