public class Game {
    private Room currentRoom;
    private Parser parser;
    private Player player;
    Room volcano;
    Room burningForest;
    boolean finished;
    Room sanctuary;
    Room ocean;
    Room jungleTemple;
    Boolean defeatedAnimal = false;
    Boolean fireResistance = false;
    Boolean waterResistance = false;
    boolean wantToQuit = false;

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
        Room cityRuins = new Room("As you wake up from the eruption, you see a bunch of skeletons and destroyed buildings. You should get out of here ASAP.", " As you look around, you see skyscrapers still burning in the bright yellow flame. You smell the rotten flesh of human corpses as well as the ash floating in the air. The sky is black from all of the smoke, and as you look to the ground, you see yourself standing on a pile of bones.");
        burningForest = new Room("You have entered a once beautiful forest, but now it is burning in front of your eyes. You be careful as you avoid burning vines of the fauna here.", "As you look around, you see all the once mighty fauna slowly get burned to ash. You hear the screams of animals getting burned alive by the flames that are destroying this once-sturdy ecosystem. As you choke on ash, you see a deer get impaled by a burning stick from above. That branch could make a good hilt on a weapon...");
        volcano = new Room("As you carefully go over the lava lake, you finally made it to the volcano. The smell of burning ash and lava fills your lungs, which makes you cough.", "When you look around, you see a huge lake of molten lava surrounding you. The volcano is easily 300 feet tall, with some lava still running down its steep cliffs. You smell the burning ash, which suddenly gives you a flashback to the eruption. When your city was burned by the flaming fireballs sent by this singular volcano. ");
        Room pier = new Room("You manage to make it to the pier, one of the places of your childhood. Even though you want to enter the water, you can't as it is too toxic from the ash.", "As you look around, you notice that the pier if creaking and molding because of the toxic ash in the ocean." +
                "You wish you could swim, but doing so without a water resistance potion will kil you. You see a sparkling glint in the ocean, and you realize it is a hunk of iron. As you turn back to look at the volcano, you see a shiny bottle with a blue bubbling substance in it. It looks somewhat like a potion...");
        jungleTemple = new Room("You enter a jungle temple and you see the sanctuary to the east. However, you sense you are in danger and should look around.", "As you look around, you see a might jaguar in your path. As it approaches, you are about to become its next meal for today."+
                "You need to kill this beast to get to the sanctuary. If only you had something to kill it with...");
        sanctuary = new Room("You manage to make it to the remains of civilization. You smile as you see people rushing towards you, and you collapse on the ground.", "As you look around, you are overwhelmed by relief. You have escaped the ruins of civilization and made it to Sanctuary, where all of the survivors have come together to form a new city. You can finally relax, eat, and enjoy yourself for the first time in a while.");
        Room ashPile = new Room("You approach a pile of ash. You see something glowing in it.", "You look around and see the burning forest all around you. You can't stay here for long, otherwise you will die. You look in the pile of ash and see magicMagma. It seems like a good ingredient in a fire resistance potion...");
        ocean = new Room("As you jump into the ocean, you feel the effects of the potion around you. You better hurry up before it fades away!", "As you enter the ocean, you see a bunch of dead fish skeletons on the ocean floor as well as a hunk of iron, perfect for crafting a weapon. There is tons of ash floating around and you would certainly be dead if it weren't for the water potion.");

        cityRuins.setExit("west", pier);
        cityRuins.setExit("north", jungleTemple);
        cityRuins.setExit("south", burningForest);
        pier.setExit("east", cityRuins);
        pier.setExit("west",ocean);
        burningForest.setExit("north", cityRuins);
        burningForest.setExit("east", volcano);
        burningForest.setExit("south", ashPile);
        volcano.setExit("west", burningForest);
        jungleTemple.setExit("east", sanctuary);
        ashPile.setExit("north", burningForest);
        ocean.setExit("east", pier);

        Item dirtyWater = new Item();
        Item iron = new Item();
        Item sword = new Item();
        Item waterPot = new Item();
        Item treeBranch = new Item();
        Item magicMagma = new Item();

        ocean.setItem("iron", iron);
        pier.setItem("waterPot", waterPot);
        pier.setItem("dirtyWater", dirtyWater);
        burningForest.setItem("branch",treeBranch);
        ashPile.setItem("magicMagma", magicMagma);


        currentRoom = cityRuins;
    }
    public void play(){
        printWelcome();
        finished = false;
        while(!finished){
            Command command = parser.getCommand();
            finished = processCommand(command);
            if(currentRoom.equals(sanctuary)){
                System.out.println("Congratulations! You won the game!");
                finished = true;
                wantToQuit = true;
            }
        }
        System.out.println("Thanks for playing");
    }
    private boolean processCommand(Command command){
        wantToQuit = false;

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
            case ATTACK:
                attack(command);
                break;
            case DRINK:
                drink(command);
                break;
        }

        return wantToQuit;
    }


    private void drink(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Drink what?");
            return;
        }
        String drink = command.getSecondWord();

        if(drink.equals("firePot") && player.getInventoryMap().containsKey("firePot")){
            fireResistance = true;
            System.out.println("As you drink your fire potion, you feel a strange and tingly feeling. You are now resistant to fire.");
            player.getItem("firePot");
        }else if(drink.equals("waterPot") && player.getInventoryMap().containsKey("waterPot")){
            waterResistance = true;
            System.out.println("As you drink you water potion, you feel a strange bubbly feeling. You are now resistant to the toxic water of the ocean.");
            player.getItem("waterPot");
        }else if(drink.equals("dirtyWater")&& player.getInventoryMap().containsKey("dirtyWater")){
            System.out.println("As you drink the water, you feel sick. You begin to barf out all of your guts and die a brutal death. If only you used it to craft a potion...");
            System.out.println("[YOU DIED]");
            wantToQuit = true;
        }else{
            System.out.println("You can't drink: " + drink);
        }

    }
    private void attack(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Attack what?");
            return;
        }
        String victim = command.getSecondWord();
        if(victim.equals(
                "jaguar") && player.getInventoryMap().containsKey("sword")){
            System.out.println("You attack the jaguar with all of your might, and you successfully slay the jaguar. You are free to go to the sanctuary.");
            defeatedAnimal = true;
        }else if(victim.equals("jaguar") && !player.getInventoryMap().containsKey("sword")){
            System.out.println("You attack with jaguar, but you do not have a weapon to kill it. You died!");
            wantToQuit = true;

        }else {
            System.out.println("You can't attack " + victim);
        }
    }

    private void craft(Command command){
        if(!command.hasSecondWord()){
            System.out.println("Craft what?");
            return;
        }
        String item = command.getSecondWord();

        if(player.getInventoryMap().containsKey("branch") && player.getInventoryMap().containsKey("iron")) {
            System.out.println("You have the right materials, but you need something hot to shape the iron into a blade. If only there was a place with lava....");
        }
        if(player.getInventoryMap().containsKey("branch") && player.getInventoryMap().containsKey("iron") && currentRoom.equals(volcano)) {
            player.setItem("sword", new Item());
            player.getItem("iron");
            player.getItem("branch");
            System.out.println("[Successfully crafted a sword]");
        }if(player.getInventoryMap().containsKey("magicMagma") && player.getInventoryMap().containsKey("dirtyWater")) {
            player.setItem("firePot", new Item());
            player.getItem("magicMagma");
            player.getItem("dirtyWater");
            System.out.println("[Successfully crafted a fire potion]");
        }


    }
    private void printHelp(){
        System.out.println("You don't remember much when the volcano erupted. All you know is that you need to escape.");
        System.out.println();
        System.out.println("===================================");
        System.out.println("Here are some crafting recipes essential to your victory.");
        System.out.println("Tree branch + iron --> Sword [Must be in volcano to craft]");
        System.out.println("magicMagma + dirtyWater --> fire resistance potion [Makes you resistant to fire]");
        System.out.println("To note, the water potion is called waterPot and the fire potion is called firePot");
        System.out.println("===================================");
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
            System.out.println("You can't go in that direction!");
        }else{
            if(currentRoom.equals(burningForest) && direction.equals("east") && fireResistance.equals(false)){
                System.out.println("As you try to make it to the volcano, you trip and fall into lava. Sad you don't have a fire resistance potion... ");
                System.out.println("[YOU DIED]");
                wantToQuit = true;
            }else if(currentRoom.equals(ocean) && direction.equals("west") && waterResistance.equals(false)){
                System.out.println("As you try to enter the ocean, your body dissolves in the acidic sea. If only you had a water potion to protect you...");
                System.out.println("[YOU DIED]");
                wantToQuit = true;
            }else if(currentRoom.equals(jungleTemple) && direction.equals("east") && defeatedAnimal.equals(false)){
                System.out.println("As you try to go to the exit the jaguar eats you alive.");
                System.out.println("[YOU DIED]");
                wantToQuit = true;
            }else{
                currentRoom = nextRoom;
                System.out.println(currentRoom.getShortDescription());
            }
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
        System.out.println("===================================");
        System.out.println("Here are some crafting recipes essential to your victory.");
        System.out.println("Tree branch + iron --> Sword [Must be in volcano to craft]");
        System.out.println("magicMagma + dirtyWater --> fire resistance potion [Makes you resistant to fire]");
        System.out.println("To note, the water potion is called waterPot and the fire potion is called firePot");
        System.out.println("===================================");
        System.out.println();
        //System.out.println("We will print a long room description here");
    }
}