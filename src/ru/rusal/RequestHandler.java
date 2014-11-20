package ru.rusal;

import ru.rusal.database.CustomerController;
import ru.rusal.database.User;
import ru.rusal.exceptions.InvalidUserException;

import java.util.regex.Pattern;

/**
 * Main logic class, decisions on any action taken in this class.
 *
 * @author melges
 */
public class RequestHandler {
    static final Pattern telephoneExpression = Pattern.compile("\\d{11}");

    /**
     * Register new user.
     *
     * @param login user name for new user.
     * @param password plain text password for new user (will be hashed).
     * @return on success - 0; if login does not meet the format requirements - 2;
     * if user with specified name already exist - 1.
     */
    static public int registerUser(String login, String password) {
        if(!telephoneExpression.matcher(login).matches())
            return 2;

        CustomerController customerController = new CustomerController();
        if(customerController.isUserExist(login))
            return 1;

        User user = new User(login, password);
        customerController.persist(user);

        return 0;
    }

    /**
     * Function get customer balance if login and password correct.
     *
     * @param login user name.
     * @param password user password.
     * @return customer balance, or throw an exception.
     * @throws InvalidUserException if pair login-password was incorrect.
     */
    public static double getCustomerBalance(String login, String password) throws InvalidUserException {
        CustomerController customerController = new CustomerController();

        if(!customerController.verifyPassword(login, password))
            throw new InvalidUserException(login, password);

        User user = customerController.getUserByLogin(login);

        return user.getAccount().getBalance();
    }

    /**
     * Submit customer payment if login and password correct.
     * Payment may be less or above zero.
     *
     * @param login user name.
     * @param password user password.
     * @param payment amount of payment.
     * @return balance after payment.
     * @throws InvalidUserException
     */
    public static double submitCustomerPayment(String login, String password, double payment) throws InvalidUserException {
        CustomerController customerController = new CustomerController();

        if(!customerController.verifyPassword(login, password))
            throw new InvalidUserException(login, password);

        User user = customerController.getUserByLogin(login);

        double newBalance = user.getAccount().getBalance() + payment;
        user.getAccount().setBalance(newBalance);
        customerController.persist(user);

        return newBalance;
    }
}
