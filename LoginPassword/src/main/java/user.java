public class user {
    private final String login;
    private String password;
    private userData data;

    public user(String givenLogin, String givenPassword){
        login = givenLogin;
        password = givenPassword;
        data = new userData();
    }

//////////////////////////////////////////////////////////////////////////////////////////

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public userData getData() {
        return data;
    }

    //////////////////////////////////////////////////////////////////////////////////////////


    public void setData(userData data) {
        this.data = data;
    }
}
