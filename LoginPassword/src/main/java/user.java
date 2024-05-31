public class user {
    private final String login;
    private String password;

    private String sessionId;
    private userData data;

    public user(String givenLogin, String givenPassword, String givenSessionId){
        login = givenLogin;
        password = givenPassword;
        sessionId = givenSessionId;
        data = new userData();
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

    public userData getData() {
        return data;
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    public void setSessionId(String givenSessionId){
        this.sessionId = givenSessionId;
    }
    public void setData(userData data) {
        this.data = data;
    }
}
