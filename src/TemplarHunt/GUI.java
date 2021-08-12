package TemplarHunt;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Scanner;

import static TemplarHunt.GameEngine.IMMUNITY_TIME;
import static TemplarHunt.PFigureList.NUM_ZONES;

/**
 * This class handles the interaction of the elements of the GUI that make
 * up the game, including collisions, moving elements around, and
 * displaying various menus.
 *
 * @author Devon Lee
 */
public class GUI extends Application
{
   public static final int STAGE_SIZE = 816;
   public static final int NUM_ROUNDS = 7;

   private static final int GAME_SPEED = 20;
   private static final int PLAYER_MOVE_DISTANCE = 50;

   // Game State Identifiers
   private static final char TITLE = 'T';
   private static final char IN_GAME = 'G';
   private static final char BETWEEN_ROUNDS = 'B';
   private static final char PAUSED = 'P';
   private static final char END = 'E';
   private static final char QUIT = 'Q';

   // Sound Effect File Paths
   private static final String SOUND_EFFECT_FOLDER_FILE_PATH =             //TODO Change path here to make sound effects work!
                               "/Users/Devon/Website/Portfolio/" +
                               "TemplarHunt/Sound Effects/";
   private static final String GOOD_COLLISION_SOUND = "HitConfirm.wav";
   private static final String BAD_COLLISION_SOUND = "Buzzer.wav";
   private static final String WIN_ROUND_SOUND = "WinRound.wav";
   private static final String LOSE_ROUND_SOUND = "LoseRound.wav";

   private Stage primaryStage;
   private final Pane rootPane = new Pane();
   private final Pane menuPane = new Pane();
   private final Pane[] roundPanes = new Pane[NUM_ROUNDS];

   private TitleScreen titleScreen;
   private PauseScreen pauseScreen;
   private PostRoundScreen postRoundScreen;
   private EndScreen endScreen;

   // Title Screen Logic Variables
   private boolean showInsts = false;
   private boolean showCtrls = false;
   private boolean showScores = false;

   // Game Components
   private HighScoresData highScoresData;
   private GameEngine engine;
   private PFigureList figs;
   private Timeline animation;
   private boolean gameMuted = false;

   // Game State Logic Variables
   private char gameState;
   private char previousGameState;
   private boolean endRoundManually = false;
   private boolean alreadyPressedT = false;
   private boolean alreadyPressedQ = false;

   boolean hasEnteredName = false;

   private Scanner jin = new Scanner(System.in);

   /**
    * Starts the game with a non-resizable window
    *
    * @param primaryStage The main window for the GUI
    */
   @Override
   public void start(Stage primaryStage)
   {
      this.primaryStage = primaryStage;
      preGameSetup();

      EventHandler<ActionEvent> eventHandler = e ->
      {
         if(gameState == IN_GAME)
            handleRoundMechanics();
         else if(gameState == PAUSED)
            handlePauseScreenMechanics();
         else if(gameState == BETWEEN_ROUNDS)
            handlePostRoundScreenMechanics();
         else if(gameState == TITLE)
            handleTitleScreenMechanics();
         else if(gameState == QUIT)
            quitProgram();
      };

      animation = new Timeline(
                  new KeyFrame(Duration.millis(GAME_SPEED), eventHandler));
      animation.setCycleCount(Timeline.INDEFINITE);
      animation.play();

      primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, e ->
      {
         if(gameState == IN_GAME)
            handleRoundControls(e.getCode());
         else if(gameState == BETWEEN_ROUNDS)
            handlePostRoundControls(e.getCode());
         else if(gameState == PAUSED)
            handlePauseScreenControls(e.getCode());
         else if(gameState == TITLE)
            handleTitleScreenControls(e.getCode());
         else if(gameState == END)
            handleEndScreenControls(e.getCode());
      });
   }

   /**
    * Sets stage, panes, and menu screens that only need to be set once per
    * run of the program
    */
   private void preGameSetup()
   {
      primaryStage.setTitle("Assignment5 - Final Project");
      primaryStage.setScene(new Scene(rootPane, STAGE_SIZE, STAGE_SIZE));
      primaryStage.setResizable(false);
      primaryStage.show();

      rootPane.getChildren().add(menuPane);

      highScoresData = new HighScoresData();

      titleScreen = new TitleScreen(menuPane);
      titleScreen.updateHighScores(highScoresData);

      pauseScreen = new PauseScreen(menuPane);
      postRoundScreen = new PostRoundScreen(menuPane);

      endScreen = new EndScreen(highScoresData, menuPane);
      endScreen.hide();

      gameState = TITLE;
   }

   /**
    * Sets up panes and calls the first round set up; this needs to be done
    * every time the user starts a game
    */
   private void gameSetUp()
   {
      for(int i = 0; i < roundPanes.length; i++)
      {
         roundPanes[i] = new Pane();
         rootPane.getChildren().add(roundPanes[i]);
      }
      gameMuted = false;
      hasEnteredName = false;
      postRoundScreen.hide();
      roundSetup();
   }

   /**
    * Creates a new game engine, set of game objects and timers, and starts
    * the clock and animation to begin a round of the game
    */
   private void roundSetup()
   {
      engine = new GameEngine();

      getRoundPane().setVisible(true);

      figs = new PFigureList(engine.getRoundID(), getRoundPane());

      getRoundPane().getChildren().add(figs.getPlayer());
      getRoundPane().getChildren().add(figs.getScoreboard());
      for(int i = 0; i < figs.getNumEnemies(); i++)
         getRoundPane().getChildren().add(figs.getEnemy(i));
      for(int i = 0; i < NUM_ZONES; i++)
         getRoundPane().getChildren().add(figs.getZones()[i]);

      pauseScreen.setPane(getRoundPane());
      pauseScreen.addElementsToCurrentPane();
      pauseScreen.hide();

      postRoundScreen.setPane(getRoundPane());

      gameState = IN_GAME;
      engine.start();
      animation.play();
   }

   /**
    * Called every cycle of the animation, this method contains the logic
    * for what should be done when the user is viewing the title screen
    */
   private void handleTitleScreenMechanics()
   {
      titleScreen.show();

      if(showInsts)
         titleScreen.showInstructions();
      else if(showCtrls)
         titleScreen.showControls();
      else if(showScores)
         titleScreen.showScores();
      else
      {
         titleScreen.hideInstructions();
         titleScreen.hideControls();
         titleScreen.hideScores();
      }
   }

   /**
    * Called every cycle of the animation, this method contains the logic
    * for what should be done when the user is actively playing the game
    */
   private void handleRoundMechanics()
   {
      if(engine.sufficientCoolDownTime())
      {
         for(int i = 0; i < figs.getNumEnemies(); i++)
            if(figs.getPlayer().collidedWith(figs.getEnemy(i)) &&
               !figs.getEnemy(i).isTagged())
            {
               int zoneMulti = figs.getEnemy(i).handleCollision(
                                                figs.getZones());
               if(zoneMulti > 0)
               {
                  playSound(GOOD_COLLISION_SOUND);
                  engine.resetCoolDownTimer();
               }
               else if(zoneMulti < 0)
               {
                  playSound(BAD_COLLISION_SOUND);
                  figs.getPlayer().paralyze();
                  engine.resetCoolDownTimer(IMMUNITY_TIME);
               }
               else
                  engine.resetCoolDownTimer();
               engine.scoreEnemyCollision(zoneMulti,
                      figs.getEnemy(i).isSpecialEnemy());
            }

         if(figs.getPlayer().collidedWith(figs.getScoreboard()))
         {
            engine.scoreSignCollision();
            engine.resetCoolDownTimer();
         }

         if(Enemy.needToResetTags() && engine.sufficientCoolDownTime())
         {
            for(int i = 0; i < Enemy.getNumFigs(); i++)
               figs.getEnemy(i).unTag();
            engine.resetCoolDownTimer();
         }
      }

      figs.hideMoveDrawAll(engine.getTimeRemaining(),
                           engine.getRoundScore(),
                           engine.getTotalScore(),
                           highScoresData.getHighestScore());

      if(engine.roundOver() || endRoundManually)
      {
         endRoundManually = false;
         gameState = BETWEEN_ROUNDS;
         if(engine.passedLevel() || engine.lastRound())
            playSound(WIN_ROUND_SOUND);
         else
            playSound(LOSE_ROUND_SOUND);
         postRoundScreen.updateScores(engine.getRoundID(),
                                      engine.getRoundScore(),
                                      engine.getTotalScore(),
                                      engine.passedLevel());
         postRoundScreen.addElementsToCurrentPane();
      }
   }

   /**
    * Called every cycle of the animation, this method contains the logic
    * for what should be done when the user is viewing the pause screen
    */
   private void handlePauseScreenMechanics()
   {
      animation.pause();
      pauseScreen.show();

      if(alreadyPressedT)
         pauseScreen.showConfirmBox(TITLE);
      else if(alreadyPressedQ)
         pauseScreen.showConfirmBox(QUIT);
      else
         pauseScreen.hideConfirmBox();
   }

   /**
    * Called every cycle of the animation, this method contains the logic
    * for what should be done when the user is viewing the end-of-round
    * screen
    */
   private void handlePostRoundScreenMechanics()
   {
      animation.pause();
      postRoundScreen.show();
      postRoundScreen.updateScores(engine.getRoundID(),
                                   engine.getRoundScore(),
                                   engine.getTotalScore(),
                                   engine.passedLevel());

      if(alreadyPressedT)
         postRoundScreen.showConfirmBox(TITLE);
      else if(alreadyPressedQ)
         postRoundScreen.showConfirmBox(QUIT);
      else
         postRoundScreen.hideConfirmBox();

      figs.resetFigCount();
   }

   /**
    * Quits the program by stopping all necessary game processes and hiding
    * various GUI elements
    */
   private void quitProgram()
   {
      if(previousGameState == IN_GAME ||
         previousGameState == BETWEEN_ROUNDS ||
         previousGameState == PAUSED)
         engine.pauseTimer();

      animation.stop();
      rootPane.setVisible(false);
      primaryStage.close();
      Platform.exit();
   }

   /**
    * Calls appropriate functions depending on the key pressed on the title
    * screen
    *
    * @param cmd The key-press to be evaluated
    */
   private void handleTitleScreenControls(KeyCode cmd)
   {
      switch(cmd)
      {
         case P:
            titleScreen.hide();
            gameSetUp();
            getRoundPane().requestFocus();
            break;
         case ENTER:
            if(!showInsts && !showCtrls && !showScores)
            {
               titleScreen.hide();
               gameSetUp();
               getRoundPane().requestFocus();
            }
            else // Hides whichever screen is showing if applicable
            {
               showInsts = false;
               showCtrls = false;
               showScores = false;
            }
            break;
         case I:
            if(!showCtrls && !showScores)
               showInsts = !showInsts;
            break;
         case C:
            if(!showInsts && !showScores)
               showCtrls = !showCtrls;
            break;
         case H:
            if(!showInsts && !showCtrls)
               showScores = !showScores;
            break;
         case T:
         case B:
            showInsts = false;
            showCtrls = false;
            showScores = false;
            break;
         case Q:
            gameState = QUIT;
      }

      if(showCtrls)
         movePlayer(titleScreen.getTestDummy(), cmd);
   }

   /**
    * Calls appropriate functions depending on the key pressed during the
    * game
    *
    * @param cmd The key-press to be evaluated
    */
   private void handleRoundControls(KeyCode cmd)
   {
      switch(cmd)
      {
         case P:
            engine.pauseTimer();
            gameState = PAUSED;
            break;
         case M:
            gameMuted = !gameMuted;
            break;
         case O:
            engine.setOverride();
            break;
         case U:
            if(engine.getOverrideStatus())
               engine.addCheatPoints();
            break;
         case I:
            if(engine.getOverrideStatus())
               engine.subtractCheatPoints();
            break;
         case E:
            if(engine.getOverrideStatus())
               endRoundManually = true;
      }
      if(!figs.getPlayer().isParalyzed())
      {
         movePlayer(figs.getPlayer(), cmd);
         figs.getPlayer().toFront();
      }
   }

   /**
    * Calls appropriate functions depending on the key pressed on the pause
    * screen
    *
    * @param cmd The key-press to be evaluated
    */
   private void handlePauseScreenControls(KeyCode cmd)
   {
      switch(cmd)
      {
         case P:
            gameState = IN_GAME;
            pauseScreen.hide();
            engine.resumeTimer();
            engine.resetCoolDownTimer();
            animation.play();
            break;
         case M:
            gameMuted = !gameMuted;
            break;
         case O:
            engine.setOverride();
            break;
         default:
            quitControlLogic(cmd);
      }

      if(engine.getOverrideStatus())
         movePlayer(figs.getPlayer(), cmd);
   }

   /**
    * Calls appropriate functions depending on the key pressed on the
    * intermediary round screens
    *
    * @param cmd The key-press to be evaluated
    */
   private void handlePostRoundControls(KeyCode cmd)
   {
      switch(cmd)
      {
         case C:
         case ENTER:
            getRoundPane().setVisible(false);
            postRoundScreen.hide();
            if(engine.passedLevel())
               roundSetup();
            else
            {
               gameState = END;
               getRoundPane().setVisible(false);
               endScreen.show(highScoresData.isHighScore(
                              engine.getTotalScore()),
                              engine.getTotalScore());
               animation.play();
            }
            break;
         case O:
         case E:
            if(engine.getOverrideStatus() && !engine.lastRound())
            {
               getRoundPane().setVisible(false);
               roundSetup();
            }
            break;
         default:
            if(engine.passedLevel())
               quitControlLogic(cmd);
      }
   }

   /**
    * Calls appropriate functions depending on the key pressed on the end
    * screen
    *
    * @param cmd The key-press to be evaluated
    */
   private void handleEndScreenControls(KeyCode cmd)
   {
      if(hasEnteredName || !highScoresData.isHighScore(
                            engine.getTotalScore()))
      {
         switch(cmd)
         {
            case ENTER:
            case P:
               endScreen.hide();
               figs.resetFigCount();
               engine.resetGame();
               gameSetUp();
               getRoundPane().requestFocus();
               break;
            case T:
               titleScreen.updateHighScores(highScoresData);
               endScreen.hide();
               figs.resetFigCount();
               engine.resetGame();
               gameState = TITLE;
               break;
            case Q:
               previousGameState = gameState;
               gameState = QUIT;
         }
      }
      else
         switch(cmd)
         {
            case ENTER:
               highScoresData.setHighScore(endScreen.getName(),
                                           engine.getTotalScore());
               endScreen.embedHighScore(highScoresData);
               hasEnteredName = true;
         }
   }

   /**
    * Controls the logic of the confirmation box; ensures the proper
    * sequence of keys is hit so as to not cause a player to accidently
    * quit during a game
    *
    * @param cmd The key-press to be evaluated
    */
   private void quitControlLogic(KeyCode cmd)
   {
      switch(cmd)
      {
         case T:
            if(alreadyPressedT) // Confirm box is showing for title screen
            {
               gameState = TITLE;
               getRoundPane().setVisible(false);
               figs.resetFigCount();
               engine.resetGame();
               pauseScreen.hide();
               alreadyPressedT = false;
            }
            else
               alreadyPressedT = true;
            animation.play();
            break;
         case Q:
            if(alreadyPressedQ) // Confirm box is showing for quit
               gameState = QUIT;
            else
               alreadyPressedQ = true;
            animation.play();
            break;
         case Y:
            if(alreadyPressedT) // Confirm box is showing for title screen
            {
               gameState = TITLE;
               getRoundPane().setVisible(false);
               figs.resetFigCount();
               engine.resetGame();
               pauseScreen.hide();
               alreadyPressedT = false;
            }
            else if(alreadyPressedQ) // Confirm box is showing for quit
               gameState = QUIT;
            animation.play();
            break;
         case N:
         case B:
            alreadyPressedT = false;
            alreadyPressedQ = false;
            animation.play();
      }
   }

   /**
    * Moves the calling StickFig object in one of four directions depending
    * on the key pressed; takes WASD and arrow key inputs
    *
    * @param player The StickFig to be moved
    * @param command The key-press to be evaluated
    */
   public void movePlayer(Player player, KeyCode command)
   {
      player.hide();
      switch(command)
      {
         case UP:
            player.move(0, -PLAYER_MOVE_DISTANCE);
            break;
         case DOWN:
            player.move(0, PLAYER_MOVE_DISTANCE);
            break;
         case LEFT:
            player.move(-PLAYER_MOVE_DISTANCE, 0);
            break;
         case RIGHT:
            player.move(PLAYER_MOVE_DISTANCE, 0);
            break;
         case W:
            player.move(0, -PLAYER_MOVE_DISTANCE);
            break;
         case S:
            player.move(0, PLAYER_MOVE_DISTANCE);
            break;
         case A:
            player.move(-PLAYER_MOVE_DISTANCE, 0);
            break;
         case D:
            player.move(PLAYER_MOVE_DISTANCE, 0);
      }
      player.draw();
   }

   /**
    * Gets the current pane being used for the round
    *
    * @return The pane of index roundID - 1
    */
   private Pane getRoundPane()
   {
      return roundPanes[engine.getRoundID() - 1];
   }

   /**
    * Plays a sound file from the local drive
    *
    * All sounds obtained from the free game sound library of mixkit.com
    *
    * @param soundFileName The name of the file to be played
    */
   private void playSound(String soundFileName)
   {
      if(!gameMuted)
      {
         String filePath = SOUND_EFFECT_FOLDER_FILE_PATH + soundFileName;
         try
         {
            java.io.File soundFile = new java.io.File(filePath);
            javax.sound.sampled.AudioInputStream audioIn = javax.sound.
               sampled.AudioSystem.getAudioInputStream(soundFile);
            javax.sound.sampled.Clip clip =
               javax.sound.sampled.AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
         }
         catch(Exception e)
         {
            System.out.println("A sound effect could not be played.");
         }
      }
   }


   /**
    * Main method--used mostly for launching program from places that do
    * not fully support JavaFX
    *
    * @param args Default method for main methods
    */
   public static void main(String[] args)
   {
       launch(args);
   }
}