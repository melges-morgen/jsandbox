package ru.rusal.database;

import javax.persistence.*;

/**
 * Class corresponding record in the account table.
 *
 * Each user is necessarily mapped by record in
 * the user table.
 *
 * @author melges
 */
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    Long id;

    @Column(name = "balance")
    private double balance;

    @OneToOne(mappedBy = "account")
    private User owner;

    /**
     * Construct empty account.
     */
    public Account() {
        id = null;
        balance = 0.0;
    }

    /**
     *
     * @return id of represented account.
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @return balance value.
     */
    public double getBalance() {
        return balance;
    }

    /**
     *
     * @param balance new balance value.
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     *
     * @return user record which is linked with represented account.
     */
    public User getOwner() {
        return owner;
    }

    /**
     *
     * @param owner new account owner.
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return String.format("[%s]", balance);
    }
}
