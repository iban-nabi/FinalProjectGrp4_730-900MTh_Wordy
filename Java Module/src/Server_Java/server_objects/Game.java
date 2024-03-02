package Server_Java.server_objects;

import CORBA_IDL.InvalidWordException;
import CORBA_IDL.NoWordException;
import CORBA_IDL.ShortWordException;
import CORBA_IDL.WordRepeatedException;
import Server_Java.server_tools.RandomLettersGenerator;
import Server_Java.server_tools.UpdateLeaderboards;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Game{
    private int gameID;
    private String gameDate;
    public final List<String> players = new ArrayList<>();
    private final List<String> wordList;
    private final List<String> playerInputs = new ArrayList<>();
    private final List<String> overallPlayerInputs = new ArrayList<>();
    private final List<String> wordsEntered = new ArrayList<>();
    private final List<String> winnerList = new ArrayList<>();
    private final List<String> longestWordList = new ArrayList<>();
    private List<Character> letterList = new ArrayList<>();
    private String gameWinner=" ";
    private String currentWinner="";
    public int countDown;
    private int timerCount=0;

    private boolean gameRunning  = false;
    private boolean gameClosed;
    private boolean flag;


    public Game(String gameOwner, int gameID, List<String> wordList){
        countDown = 11000;
        players.add(gameOwner);
        this.gameID=gameID;
        this.wordList=wordList;
        gameClosed=false;
        flag=false;
        flagTimer = false;
        processDate();
    }

    private void processDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        gameDate = dtf.format(now);
    }

    public void joinGame(){
        if(!flag){
            flag =true;
            Timer startTimer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if(players.size()==1){
                        gameClosed = true;
                    }else{
                        generateLetters();
                        gameClosed = true;
                    }
                }
            };
            startTimer.schedule(task,countDown);
            updateStartCountDown();
        }
    }

    boolean flagTimer;
    public boolean runGame() throws InterruptedException {
        if (!flagTimer){
            countDown = 11000;
            timerCount = 0;
            updateStartCountDown();
            flagTimer = true;
        }
        playerInputs.clear();
        wordsEntered.clear();
        flag = false;
        final boolean[] runFlag = new boolean[1];
        Timer startTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runFlag[0] = false;
            }
        };
        startTimer.schedule(task,countDown);
        Thread.sleep(countDown);
        initializer = 0;
        flagTimer = runFlag[0];
        return runFlag[0];
    }

    public List<Character> getLetterList() {
        return letterList;
    }

    public void enterWord(String word) throws WordRepeatedException, NoWordException, ShortWordException,
            InvalidWordException {
        String[] playerInput = word.split(":");
        if(playerInput[1].length()>4){
            if(wordList.contains(playerInput[1])){
                if(!wordsEntered.contains(playerInput[1])){
                    char[] charArray = playerInput[1].toCharArray();
                    List<Character> list = new ArrayList<>();
                    for(char letter:charArray) {
                        list.add(Character.toUpperCase(letter));
                    }

                    for(char letter:list){
                        if(Collections.frequency(letterList, letter)<Collections.frequency(list, letter)){
                            throw new InvalidWordException("Use the corresponding letters given");
                        }
                    }
                    playerInputs.add(word);
                    overallPlayerInputs.add(word);
                    wordsEntered.add(playerInput[1]);
                }else{
                    throw new WordRepeatedException("Word Repeated");
                }
            }else{
                throw new NoWordException("No Such Word");
            }
        }else{
            throw new ShortWordException("Word entered is to short");
        }
    }

    public String getRoundWinner(){
        if(!flag){
            flag = true;
            if(players.size()==1){
                currentWinner = players.get(0);
            }else{
                List<String>tieWords = new ArrayList<>();
                String lwRound = "";
                String wRound = "";
                if(!playerInputs.isEmpty()){
                    for(String w : playerInputs){
                        String[] playerInput = w.split(":");
                        if(playerInput[1].length()==lwRound.length() && !Objects.equals(playerInput[0], wRound)){
                            lwRound = playerInput[1];
                            tieWords.add(lwRound);
                        }else if(playerInput[1].length()>lwRound.length()){
                            wRound = playerInput[0];
                            lwRound = playerInput[1];
                            tieWords.clear();
                            tieWords.add(lwRound);
                        }
                    }
                    if(tieWords.size()==1){
                        currentWinner = wRound+":"+lwRound;
                        winnerList.add(wRound);
                        longestWordList.add(lwRound);
                    }else{
                        currentWinner = "tie";
                    }
                }else{
                    currentWinner = "tie";
                }
            }
            generateLetters();
        }
        return currentWinner;
    }

    public String getGameWinner() {
        UpdateLeaderboards updateLeaderboards = new UpdateLeaderboards();
        if(players.size()==1){
            gameWinner = players.get(0);
            updateLeaderboards.updateUserWin(gameWinner);
            gameClosed = true;
        }else{
            if(winnerList.size()>=3 && Objects.equals(gameWinner, " ")){
                for(String p : winnerList){
                    if(Collections.frequency(winnerList,p)==3){
                        gameWinner = p;
                        updateLeaderboards.updateUserWin(gameWinner);
                        updateLeaderboards.updateLongestWordLeaderboards(gameDate,overallPlayerInputs);
                        gameClosed = true;
                        break;
                    }
                }
            }
        }
        return gameWinner;
    }

    private void generateLetters(){
        letterList.clear();
        RandomLettersGenerator randomLettersGenerator = new RandomLettersGenerator();
        letterList=randomLettersGenerator.generateLetters();
    }

    private void updateStartCountDown(){
        final boolean[] flag = {true};

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                while(countDown>0){
                    countDown=countDown-1000;
                    timerCount++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        timer.schedule(task,0);
    }

    public int getMyNoOfWins(String username){
        return Collections.frequency(winnerList,username);
    }

    int initializer=0;
    public void initializeGame(){
        initializer++;
        while(initializer!=players.size()){
            System.out.print("");
        }
    }


    public int getTimer() {
        return timerCount;
    }

    public String[] getPlayers() {
        String[] playersArray = new String[players.size()];
        for(int i = 0; i<playersArray.length; i++){
            playersArray[i]=players.get(i);
        }
        return playersArray;
    }

    public void quitGame(String username){
        players.remove(username);
    }

    public int getGameID() {
        return gameID;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public List<String> getPlayerInputs() {
        return playerInputs;
    }

    public boolean isGameClosed() {
        return gameClosed;
    }

    public void addPlayer(String player){
        players.add(player);
    }
}
