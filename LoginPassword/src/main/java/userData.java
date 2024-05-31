public class userData {
    private Integer chosenNumber;

    public userData(){
        chosenNumber = 0;
    }

    public userData(Integer number){
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
