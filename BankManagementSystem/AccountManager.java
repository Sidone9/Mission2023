package BankManagementSystem;

import javax.naming.ldap.PagedResultsControl;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.Scanner;

public class AccountManager {
    private Connection connection;
    private Scanner scanner;

    public AccountManager(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void credit_money(long account_number)throws SQLException {
        scanner.nextLine();
        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Security Pin: ");
        String security_pin = scanner.nextLine();

        try{
            connection.setAutoCommit(false);
            if(account_number != 0){
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts WHERE account_number = ? AND security_pin = ?");
                preparedStatement.setLong(1,account_number);
                preparedStatement.setString(2,security_pin);
                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    String credit_query = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(credit_query);
                    preparedStatement1.setDouble(1,amount);
                    preparedStatement1.setLong(2,account_number);
                    int affectedrows = preparedStatement1.executeUpdate();
                    if(affectedrows > 0){
                        System.out.println("Rs."+ amount+ " Credited Successfully!");
                        connection.commit();
                        connection.setAutoCommit(true);
                    }else{
                        System.out.println("Transaction Failed!!");
                        connection.rollback();
                        connection.setAutoCommit(true);
                    }
                }else{
                    System.out.println("Invalid Security Pin!");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }


    public void debit_money(long account_number)throws SQLException {
        scanner.nextLine();
        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Security Pin: ");
        String security_pin = scanner.nextLine();

        try{
            connection.setAutoCommit(false);
            if(account_number != 0){
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts WHERE account_number = ? AND security_pin = ?");
                preparedStatement.setLong(1,account_number);
                preparedStatement.setString(2,security_pin);
                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    double current_balance = resultSet.getDouble("balance");
                    if(amount <= current_balance){
                        String debit_query = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
                        PreparedStatement preparedStatement1 = connection.prepareStatement(debit_query);
                        preparedStatement1.setDouble(1,amount);
                        preparedStatement1.setLong(2,account_number);
                        int affectedrows = preparedStatement1.executeUpdate();
                        if(affectedrows>0){
                            System.out.println("Rs."+ amount + " debited successfully");
                            connection.commit();
                            connection.setAutoCommit(true);
                        }else{
                            System.out.println("Transaction failed");
                            connection.rollback();
                            connection.setAutoCommit(true);
                        }
                    }else{
                        System.out.println("Insufficient balance!!");
                    }
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }

    public void transfer_money(long sender_account_number)throws SQLException{
        scanner.nextLine();
        System.out.print("Enter Receiver Account Number: ");
        long receiver_account_number = scanner.nextLong();
        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Security PIN: ");
        String security_pin = scanner.nextLine();

        try{
            connection.setAutoCommit(false);
            if(sender_account_number!= 0 && receiver_account_number != 0){
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts WHERE account_number = ? AND security_pin = ?");
                preparedStatement.setLong(1,sender_account_number);
                preparedStatement.setString(2,security_pin);
                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    double current_balance = resultSet.getDouble("balance");
                    if(amount<= current_balance){
                        // debit and credit queries...
                        String debit_query = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
                        String credit_query = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";

                        // credit and debit prepared statement....
                        PreparedStatement creditpreparedstatement = connection.prepareStatement(credit_query);
                        PreparedStatement debitpreparedstatement  = connection.prepareStatement(debit_query);

                        // set values for credit and debit...
                        creditpreparedstatement.setDouble(1,amount);
                        creditpreparedstatement.setLong(2,receiver_account_number);
                        debitpreparedstatement.setDouble(1,amount);
                        debitpreparedstatement.setLong(2,sender_account_number);
                        int affectedrows1 = creditpreparedstatement.executeUpdate();
                        int affectedrows2 = debitpreparedstatement.executeUpdate();
                        if(affectedrows1 > 0 && affectedrows2 > 0){
                            System.out.println("Transaction Successfully!!");
                            connection.commit();
                            connection.setAutoCommit(true);
                            return;
                        }else{
                            System.out.println("Transaction Failed!!");
                            connection.rollback();
                            connection.setAutoCommit(true);
                        }
                    }else{
                        System.out.println("Insufficient Balance!!");
                    }
                }else{
                    System.out.println("Invalid Security PIN!!");
                }
            }else{
                System.out.println("Invalid Account Number!!");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }

    public void getbalance(long account_number){
        scanner.nextLine();
        System.out.println("Enter Security PIN: ");
        String security_pin = scanner.nextLine();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("select balance from accounts where account_number = ? and security_pin = ?");
            preparedStatement.setLong(1,account_number);
            preparedStatement.setString(2,security_pin);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                double balance = resultSet.getDouble("balance");
                System.out.println("Balance: " + balance);
            }else{
                System.out.println("Invalid PIN!!");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
