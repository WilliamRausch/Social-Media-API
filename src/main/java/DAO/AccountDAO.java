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
    public Account login(String username, String password){
        Connection connection = ConnectionUtil.getConnection();
        Account failed = new Account();
     try{
            String sql = "SELECT * FROM Account WHERE username = ? OR password = ?;" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1, username);
preparedStatement.setString(2, password);
    
ResultSet rs = preparedStatement.executeQuery();

while(rs.next() ){
   String name1 = rs.getString("username");
   String name2 = username;
   
    if( name1.equals(name2) && rs.getString("password").equals(password)){
       
    Account account = new Account(rs.getInt("account_id"),rs.getString("username"), rs.getString("password"));
   return account;
    }else{
return failed;
    }
}
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    
        return failed;
    }
}
