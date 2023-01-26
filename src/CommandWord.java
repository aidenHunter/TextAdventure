public enum CommandWord {

    GO("go"), QUIT("quit"), HELP ("help"), UNKNOWN("?"), LOOK("look"), GRAB("grab"), DROP("drop"), CRAFT("craft");
    private String commandString;
    CommandWord(String commandString){
        this.commandString = commandString;
    }
    public String toString(){
        return commandString;
    }
}
