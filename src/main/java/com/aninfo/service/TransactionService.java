package com.aninfo.service;

import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.model.TransactionType;
import com.aninfo.repository.AccountRepository;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    public TransactionRepository transactionRepository;
    @Autowired
    public AccountRepository accountRepository;

    public Transaction createTransaction(Transaction transaction){

        Transaction savedTransaction = transactionRepository.save(transaction);

        // Obtener la cuenta asociada a la transacción
        Account account = accountRepository.findById(transaction.getAccountCbu())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        // Actualizar el balance de la cuenta según el tipo de transacción
        if (transaction.getType() == TransactionType.DEPOSIT) {
            account.setBalance(account.getBalance() + transaction.getAmount());
        } else if (transaction.getType() == TransactionType.WITHDRAW) {
            account.setBalance(account.getBalance() - transaction.getAmount());
        }

        // Guardar los cambios en la cuenta
        accountRepository.save(account);

        return savedTransaction;
    }

    //Leer todas las transacciones de una cuenta dada
    public List<Transaction> getAllTransactionsByAccountCbu(Long cbu) {
        return transactionRepository.findAllByAccountCbu(cbu);
    }

    //Leer una transacción particular
    public Transaction getTransactionByID(Long id){
        return transactionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Transaction not found"));
    }

    //Eliminar transacción por id - opte por actualizar la cuenta
    // a la hora de eliminar la transacción, es decir revertir el proceso
    public Transaction deleteTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        // Obtener la cuenta asociada a la transacción
        Account account = accountRepository.findById(transaction.getAccountCbu())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        // Actualizar el balance de la cuenta según el tipo de transacción
        if (transaction.getType() == TransactionType.DEPOSIT) {
            account.setBalance(account.getBalance() - transaction.getAmount());
        } else if (transaction.getType() == TransactionType.WITHDRAW) {
            account.setBalance(account.getBalance() + transaction.getAmount());
        }

        // Guardar los cambios en la cuenta
        accountRepository.save(account);

        transactionRepository.delete(transaction);
        return transaction;
    }
}
