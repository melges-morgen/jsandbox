package ru.rusal.database;

import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.*;

/**
 * Class corresponding record in the user table.
 *
 * Each user is necessarily associated with a record in
 * the accounts table. The password is stored in the form of SHA-2 hash.
 *
 * Reference to the account is specified by account id (account_id) in the special column.
 *
 * @author melges
 */
@Entity
@Table(name = "users")
@NamedQueries(
        @NamedQuery(name = "getUserByLogin", query = "SELECT u FROM User u WHERE u.login = :login")
)
public class User {
    static final String salt = "Fuiqu+oh;rooro!a0ohz)u9xoolieloo]xei2aayaixaeng9ochoPieree!bosha";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, name = "login")
    private String login;

    @Column(name = "password")
    private String passwordHash;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    /**
     * Function calculates password hash.
     *
     * With a view on cryptographic, we would hashing the password
     * with a salt added in the form of a random string and
     * login. This will avoid from getting the same hash for the same
     * password and the random string excludes attack by rainbow tables.
     * @param login user login.
     * @param password user password.
     * @return password string in hex.
     */
    public static String calculateHash(String login, String password) {
        return DigestUtils.sha512Hex(String.format("%s%s%s", password, salt, login));
    }

    /**
     * Construct empty object
     */
    public User() {
        account = new Account();
    }

    /**
     * Construct object which describe user, password will be hashed.
     * @param login login of the user
     * @param password plain text password of the user
     */
    public User(String login, String password) {
        this.login = login;
        this.passwordHash = calculateHash(login, password);
        account = new Account();
    }

    /**
     *
     * @return id of user in the database.
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @return user login.
     */
    public String getLogin() {
        return login;
    }

    /**
     *
     * @param login login to set.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     *
     * @return hashed password.
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     *
     * @param passwordHash value which will be saved in password cell in database.
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     *
     * @param password plain text password which will be hashed and saved to database.
     */
    public void setPassword(String password) {
        passwordHash = calculateHash(login, password);
    }

    /**
     *
     * @return account row linked with represented user.
     */
    public Account getAccount() {
        return account;
    }

    /**
     *
     * @param account row which will be linked with represented user.
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return String.format("[%s: %f]", login, account.getBalance());
    }
}
