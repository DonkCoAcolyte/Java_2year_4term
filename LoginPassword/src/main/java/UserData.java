public class UserData {
    private Integer chosenNumber;

    public UserData(){
        chosenNumber = 0;
    }

    public UserData(Integer number){
        chosenNumber = number;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////
    public Integer getChosenNumber(){
        return chosenNumber;
    }

    public void setChosenNumber(Integer number){
        chosenNumber = number;
    }
}
