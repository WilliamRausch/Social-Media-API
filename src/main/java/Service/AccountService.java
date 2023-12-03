package Service;

import DAO.AccountDAO;
import Model.Account;

import java.util.List;


public class AccountService {
    private AccountDAO accountDAO;
  
    public AccountService(){
        accountDAO = new AccountDAO();
    }
  
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
   
    public Account addAccount(Account account) {
        Account created = accountDAO.insertAccount(account);
        return created;
    }
    public Account login(String username, String password){
        Account logged = accountDAO.login(username, password);

        return logged;
    }
}
