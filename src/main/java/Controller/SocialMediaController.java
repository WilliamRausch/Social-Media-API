package Controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.AccountService;
import Service.MessageService;
import java.util.*;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;


    public SocialMediaController(){
       this.accountService = new AccountService();
       this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {

        Javalin app = Javalin.create();
        System.out.println("THIS IS A TESTTTTT");
        app.post("/register", this::createAccountHandle);
        app.post("/login", this::exampleHandler);
        app.post("/messages", this::createMessageHandle);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}", this::getMessageHandler);
        app.delete("messages/{message_id}", this::deleteMessageHandler);
        app.get("localhost:8080/accounts/{account_id}/messages", this::exampleHandler);
        app.patch("messages/{message_id}", this::updateMessageHandler);
        app.get("example-endpoint", this::exampleHandler);
        

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        System.out.println("sample HELLO");
        context.json("sample text");
    }
    private void createAccountHandle(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        System.out.println("HELLO");
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(400);
        }
    }
    private void createMessageHandle(Context ctx) throws JsonProcessingException {
        System.out.println("creating message");
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage!=null){
            ctx.json(mapper.writeValueAsString(addedMessage));
        }else{
            ctx.status(400);
        }
    }
    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }
    private void getMessageHandler(Context ctx) {
        String id = ctx.pathParam("message_id");
        int id2 = Integer.parseInt(id);
      
        Message message = messageService.getMessage(id2);
        
        //ctx.json(message);
        System.out.println("THE BLANK ONE" + message);
        if (message == null) {
          ctx.json("");
        } else {
            ctx.json(message);
        }
        System.out.println("GETTING SINGULAR MESSAGE TEST" + id);
    }
    private void deleteMessageHandler(Context ctx) {
        String id = ctx.pathParam("message_id");
        int id2 = Integer.parseInt(id);
      
        Message message = messageService.getMessage(id2);
        
        //ctx.json(message);
        System.out.println("THE BLANK ONE" + message);
        if (message == null) {
          ctx.json("");
        } else {
            messageService.deleteMessage(id2);
            ctx.json(message);
        }
     
    }
    private void updateMessageHandler(Context ctx) {
        String id = ctx.pathParam("message_id");
        int id2 = Integer.parseInt(id);
        Message message = messageService.getMessage(id2);
      

     
            messageService.updateMessage(id2, "updated message");
            Message message2 = messageService.getMessage(id2);
            ctx.json(message2);
        
     
    }
}