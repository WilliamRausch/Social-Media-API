package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;

public class AccountDAO {
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        if(account.password.length() >= 4 && !account.username.isBlank()){
            System.out.println("LENGTH PASSED");
     try{
            String sql = "INSERT INTO Account (username, password) VALUES (?, ?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1, account.getUsername());
preparedStatement.setString(2, account.getPassword());
    
            
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
        return null;
    }
}