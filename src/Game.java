public class Game {
    private Room currentRoom;
    public Game(){

    }

    public static void main(String[]args){
        Game game = new Game();
        game.createRooms();
        game.play();
    }
    private void createRooms(){
        Room cityRuins = new Room("As you wake up from the eruption, you see a bunch of skeletons and destroyed buildings. You should get out of here ASAP.");
        Room burningForest = new Room("You have entered a once beautiful forest, but now it is burning in front of your eyes. You be careful as you avoid burning vines of the fauna here.");
        Room volcano = new Room("As you carefully go over the lava lake, you finally made it to the volcano. The smell of burning ash and lava fills your lungs, which makes you cough.");
        Room pier = new Room("You manage to make it to the pier, one of the places of your childhood. Even though you want to enter the water, you can't as it is too toxic from the ash.");
        Room jungleMaze = new Room("You enter a deep and alive forest, but it seems like you are trapped in a maze. You do not know which path leads to where.");
        Room sanctuary = new Room("You manage to make it to the remains of civilization. You smile as you see people rushing towards you, and you collapse on the ground.");
    }
    public void play(){
        printWelcome();
        boolean finished = false;
        while(!finished){

        }
        System.out.println("Thanks for playing");
    }
    private void printWelcome(){
        System.out.println();
        System.out.println("Welcome to my text adventure game!");
        System.out.println("You will find yourself in a garden maze, desperate to escape!");
        System.out.println("Type \"help\" if you need assistance");
        System.out.println();
        System.out.println("We will print a long room description here");
    }
}
