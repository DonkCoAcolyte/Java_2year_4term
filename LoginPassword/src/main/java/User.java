public class User {
    private final String login;
    private String password;

    private String sessionId;
    private UserData data;

    public User(String givenLogin, String givenPassword, String givenSessionId){
        login = givenLogin;
        password = givenPassword;
        sessionId = givenSessionId;
        data = new UserData();
    }

//////////////////////////////////////////////////////////////////////////////////////////

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getSessionId() {
        return sessionId;
    }

    public UserData getData() {
        return data;
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    public void setSessionId(String givenSessionId){
        this.sessionId = givenSessionId;
    }
    public void setData(UserData data) {
        this.data = data;
    }
}
