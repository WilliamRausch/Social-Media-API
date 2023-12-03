package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
public class MessageDAO {
    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        if(!message.message_text.isBlank()){
            System.out.println("LENGTH PASSED");
     try{
            String sql = "INSERT INTO MESSAGE (posted_by, message_text, time_posted_epoch) VALUES ( ?, ?, ?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
preparedStatement.setInt(1, message.getPosted_by());
preparedStatement.setString(2, message.getMessage_text());
preparedStatement.setLong(3, message.getTime_posted_epoch());
    
            
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message( generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
        return null;
    }
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        System.out.println("GETTING MESSAGES");
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM Message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
    public Message getMessage(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
       
            String sql = "SELECT * FROM Message WHERE message_id = ?;" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
       
           
          
            while(rs.next()){
                
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
               return message;
            }
            System.out.println("MESSAGE BY ID TEST" );
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
}
public Message deleteMessage(int id){
    Connection connection = ConnectionUtil.getConnection();
    try {
   
        String sql = "DELETE FROM Message WHERE message_id = ?;" ;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        preparedStatement.executeUpdate();
   
       
   
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    return null;
}
public Message updateMessage(int id, String text){
    Connection connection = ConnectionUtil.getConnection();
    try {
   
        String sql = "UPDATe Message SET message_text = ? WHERE message_id = ? ;" ;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, text);
 preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
   
       
   
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    return null;
}
}
