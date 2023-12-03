package Service;


import DAO.MessageDAO;
import Model.Message;

import java.util.List;
public class MessageService {
    private MessageDAO messageDAO;
    public MessageService(){
        messageDAO = new MessageDAO();
    }
  
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }
   
    public Message addMessage(Message account) {
        Message created = messageDAO.insertMessage(account);
        return created;
    }
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
    public Message getMessage(int id){
        return messageDAO.getMessage(id);

    }
    public Message deleteMessage(int id){
       return messageDAO.deleteMessage(id);
    }
    public Message updateMessage(int id, String text){
        return messageDAO.updateMessage(id, text);
     }
     public List<Message> getAllMessagesByUser(int id) {
        return messageDAO.getAllMessagesByUser(id);
    }
}
