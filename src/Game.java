public class Game {
    private Room currentRoom;
    private Parser parser;
    private Player player;
    Room volcano;
    Room burningForest;
    boolean finished;

    public Game(){
        parser = new Parser();
        player = new Player();

    }

    public static void main(String[]args){
        Game game = new Game();
        game.createRooms();
        game.play();
    }
    private void createRooms(){
        Room cityRuins = new Room("As you wake up from the eruption, you see a bunch of skeletons and destroyed buildings. You should get out of here ASAP.", "ruins long description");
        burningForest = new Room("You have entered a once beautiful forest, but now it is burning in front of your eyes. You be careful as you avoid burning vines of the fauna here.", "burnforest long description");
        volcano = new Room("As you carefully go over the lava lake, you finally made it to the volcano. The smell of burning ash and lava fills your lungs, which makes you cough.", "volcano long description");
        Room pier = new Room("You manage to make it to the pier, one of the places of your childhood. Even though you want to enter the water, you can't as it is too toxic from the ash.", "pier long description");
        Room jungleTemple = new Room("You enter a deep and alive forest, and in front of you is a temple. You have no way to go around it.", "temple description");
        Room sanctuary = new Room("You manage to make it to the remains of civilization. You smile as you see people rushing towards you, and you collapse on the ground.", "csanctuary long description");
        Room ashPile = new Room("You approach a pile of ash. You see something glowing in it.", "You look around and see the burning forest all around you. You can't stay here for long, otherwise you will die. You look in the pile of ash and see magicMagma. It seems like a good ingredient in a fire resistance potion...");

        cityRuins.setExit("west", pier);
        cityRuins.setExit("north", jungleTemple);
        cityRuins.setExit("south", burningForest);
        pier.setExit("east", cityRuins);
        burningForest.setExit("north", cityRuins);
        burningForest.setExit("west", volcano);
        burningForest.setExit("south", ashPile);
        volcano.setExit("east", burningForest);
        jungleTemple.setExit("east", sanctuary);
        ashPile.setExit("north", burningForest);


        Item dirtyWater = new Item();
        Item iron = new Item();
        Item sword = new Item();
        Item waterPot = new Item();
        Item treeBranch = new Item();
        Item magicMagma = new Item();

        cityRuins.setItem("iron", iron);
        pier.setItem("waterPot", waterPot);
        burningForest.setItem("branch",treeBranch);
        pier.setItem("dirtyWater", dirtyWater);
        ashPile.setItem("magicMagma", magicMagma);


        currentRoom = cityRuins;
    }
    public void play(){
        printWelcome();
        finished = false;
        while(!finished){
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thanks for playing");
    }
    private boolean processCommand(Command command){
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();
        switch(commandWord){
            case UNKNOWN:
                System.out.println("I don't know what you mean");
                break;
            case HELP:
                printHelp();
                break;
            case GO:
                goRoom(command);
                break;
            case QUIT:
                wantToQuit = true;
                break;
            case LOOK:
                look(command);
                break;
            case GRAB:
                grab(command);
                break;
            case DROP:
                drop(command);
                break;
            case CRAFT:
                craft(command);
                break;
        }

        return wantToQuit;
    }

    private void craft(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Craft what?");
            return;
        }
        String item = command.getSecondWord();

        if(player.getInventoryMap().containsKey("branch") && player.getInventoryMap().containsKey("iron") && currentRoom.equals(volcano)) {
            player.setItem("sword", new Item());
            player.getItem("iron");
            player.getItem("branch");
            System.out.println("[Successfully crafted a sword]");
        }if(player.getInventoryMap().containsKey("magicMagma") && player.getInventoryMap().containsKey("dirtyWater")) {
            player.setItem("firePot", new Item());
            player.getItem("magicMagma");
            player.getItem("dirtyWater");
        }


    }
    private void printHelp(){
        System.out.println("You are lost.");
        System.out.println("You used to live in a giant city. Now when the volcano erupted, most organic life died.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void look(Command command){
        if(command.hasSecondWord()){
            System.out.println("You can't look at " + command.getSecondWord());
            return;
        }
        System.out.println(currentRoom.getLongDescription());
        System.out.println(player.getItemString());
    }

    private void goRoom(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Go where?");
            return;
        }
        String direction = command.getSecondWord();
        Room nextRoom = currentRoom.getExit(direction);

        if(nextRoom == null){
            System.out.println("There is no door!");
        }else{
            if(currentRoom.equals(burningForest) && direction == "east" && player.getInventoryMap().containsKey("iron") == false){
                finished = true;
            }
            currentRoom = nextRoom;
            System.out.println(currentRoom.getShortDescription());
        }
    }

    private void grab(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Grab what?");
            return;
        }
        String item = command.getSecondWord();
        Item grabbedItem = currentRoom.getItem(item);

        if(item == null){
            System.out.println("You can't grab " + command.getSecondWord());
        }else{
            player.setItem(item, grabbedItem);
        }
    }

    private void drop(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Drop what?");
            return;
        }
        String item = command.getSecondWord();
        Item droppedItem = player.getItem(item);

        if(item == null){
            System.out.println("You can't drop " + command.getSecondWord());
        }else{
            currentRoom.setItem(item,droppedItem);
        }
    }

    private boolean quit(Command command){
        if(command.hasSecondWord()){
            System.out.println("You can't quit " + command.getSecondWord());
            return false;
        }else{
            return true;
        }
    }
    private void printWelcome(){
        System.out.println();
        System.out.println("Welcome to my text adventure game!");
        System.out.println("You wake up to find yourself in the ruins of a once great civilization. You need to escape!");
        System.out.println("Type \"help\" if you need assistance");
        System.out.println();
        //System.out.println("We will print a long room description here");
    }
}