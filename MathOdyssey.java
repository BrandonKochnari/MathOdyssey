/* Written by - Brandon Kochnari
 * Tuesday, August 1, 2023
 *
 * This project entails a puzzle game, Math Odyssey™, which prompts the user to answer multiple choice and short answer
 * math questions while keeping track of score and counting down a timer. Meanwhile, consecutive correct answers are
 * rewarded with power ups including skip, extra time and extra score. Each Quest is designed to tackle a certain area
 * of math with questions that increase in difficulty each time to engage all users. At the end, the adventurer's
 * score is saved into a txt file which holds a scoreboard of all that completed the odyssey, along with a name and game duration.
 */

// Various imports

package ICS3U;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;
import java.util.*;

public class MathOdyssey {

    // Global variable initialization

    // Instance variables for IO and counter
    private static Scanner scanner;
    private static int score = 0;
    private static int answerStreak = 0;
    private static int extraScoreCounter = 0;
    private static int seconds = 303;
    private static int correctAnswerCounter = 0;


    // Other variables
    private static boolean skip = false;
    private static boolean extraScore = false;
    private static int remainingSeconds;
    private static int minutes;
    private static boolean isPaused = false;
    private static boolean gameOver = false;
    private static final String[] quest = {"FIRST QUEST: ALGEBRA", "SECOND QUEST: GEOMETRY", "FINAL QUEST: CALCULUS"};


    // Method used for music

    // Method used for main menu
    public static void main(String[] args) {
        // Initialize the scanner
        scanner = new Scanner(System.in);

        System.out.println("""

                //////////////////*** MATH ODYSSEY ***//////////////////
                Learn the rules first BEFORE you play the game""");

        try {
            while (true) {
                System.out.print("""
                                              
                        1. Play Adventure
                        2. Scoreboard
                        3. Rules
                        4. Quit Program
                        Option:\s""");

                // Reads user input
                String userInput = scanner.nextLine();

                // Strings used instead of integers to prevent crash upon string input
                switch (userInput) {
                    case "1":
                        play();
                        break;
                    case "2":
                        scoreboard();
                        break;
                    case "3":
                        rules();
                        break;
                    case "4":
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                        break;
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method for displaying adventure questions
    private static void play() throws InterruptedException, IOException, UnsupportedAudioFileException, LineUnavailableException {
        // Gets file source
        URL url = MathOdyssey.class.getResource("BG_Music.wav");
        assert url != null;
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(url);
        Clip clip = AudioSystem.getClip();
        // Opens audio file
        clip.open(audioInput);
        // Begins audio file
        clip.start();
        // Loops audio file
        clip.loop(Clip.LOOP_CONTINUOUSLY);

        if (clip.isRunning()) {
            clip.stop();
        }

        System.out.print("\n");
        // Start at 3, go down by 1 until before reaching 0
        for (int i = 3; i > 0; i--) {
            System.out.println("Odyssey will begin in " + i + "...");
            // Pauses console output for 1000 milliseconds or 1 second
            Thread.sleep(1000);
        }

        // Initializing timer
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (isPaused) {
                    return;
                }
                if (seconds >= 0) {
                    minutes = seconds / 60;
                    // Mod: remainder when divided by 60
                    remainingSeconds = seconds % 60;
                    if (seconds == 0) {
                        gameOver = true;
                        System.out.println("\nSORRY, TIME'S UP! ENTER ANY KEY TO CONTINUE:");
                        timer.cancel();
                        return;
                    }
                    seconds--;
                }
            }
        };
        // Timer updates every 1000 milliseconds or 1 second
        timer.schedule(task, 0, 1000);


        while (!gameOver) {
            System.out.println("\n***** " + quest[0] + " *****");
            Thread.sleep(1000);
            System.out.println("\nTimer now begins at 05:00, GO!");
            Thread.sleep(1500);

            // Uses ask question method to determine whether each question is right or wrong, and checks for power up requirements, usage etc.
            askQuestion("\n1. Solve for x: x + 4 = 10.", "a. 4\nb. 14\nc. 6\nd. 0", "c", "6", false);
            Thread.sleep(1000);
            // Required under each question due to while loop
            if (gameOver) {
                break;
            }
            askQuestion("\n2. Solve for x: 2x - 3 = 5.", "a. 4\nb. 1\nc. 2\nd.Invalid", "a", "4", false);
            Thread.sleep(1000);
            if (gameOver) {
                break;
            }
            askQuestion("\n3. Solve for x: x/2 - 1/4 = 3/4.", "a. 4\nb. -2\nc. 1\nd. 2", "d", "2", false);
            Thread.sleep(1000);
            if (gameOver) {
                break;
            }
            askQuestion("\n4. Solve for x: x^2 - 4 = 0.", "a. 2\nb. 1,-1\nc. 2,-2\nd. Invalid", "c", "2,-2", false);
            Thread.sleep(1000);
            if (gameOver) {
                break;
            }
            askQuestion("\n5. Solve for x and y: x + y = 6, x - y = 2.", "a. x = 2, y = 4\nb. x = 4, y = 2\nc. x = -2, y = -4\nd. x = -4, y = -2", "b", "x = 4, y = 2", false);
            Thread.sleep(1000);
            if (gameOver) {
                break;
            }
            askQuestion("\n6. If 3/4 of a number is 15, what is the number?", "a. 10\nb. 18\nc. 30\nd. 20", "d", "20", false);
            Thread.sleep(1000);
            if (gameOver) {
                break;
            }
            askQuestion("\n7. A book and a pen together cost $11. The book costs $10 more than the pen. How much does the pen cost?", "a. $1\nb. $0.75\nc. $0.50\nd. Not enough information", "c", "$0.50", false);
            Thread.sleep(1000);
            if (gameOver) {
                break;
            }

            System.out.println("\n***** " + quest[1] + " *****");
            Thread.sleep(1000);
            askQuestion("\n1. How many sides in a hexagon?", "a. 4\nb. 6\nc. 8\nd. 16", "b", "6", false);
            Thread.sleep(1000);
            if (gameOver) {
                break;
            }
            askQuestion("\n2. What is the area of a rectangle with l = 5 units, w = 7 units?", "a. 35u\nb. 12u^2\nc. 12u\nd. 35u^2", "d", "35u^2", false);
            Thread.sleep(1000);
            if (gameOver) {
                break;
            }
            askQuestion("\n3. What is the term for a line that passes through the center of a circle and touches two points?", "a. radius\nb. circumference\nc. diameter\nd. arc", "c", "diameter", false);
            Thread.sleep(1000);
            if (gameOver) {
                break;
            }
            askQuestion("\n4. What is the sum of the angles in a quadrilateral?", "a. 360 degrees\nb. 180 degrees\nc. 90 degrees\nd. 720 degrees", "a", "360 degrees", false);
            Thread.sleep(1000);
            if (gameOver) {
                break;
            }
            askQuestion("\n5. If the two legs of a right triangle are 5 and 12 units, what is the length of the hypotenuse?", "a. 7u\nb. 13\nc. 7u\nd. 13u", "d", "13u", false);
            Thread.sleep(1000);
            if (gameOver) {
                break;
            }
            askQuestion("\n6. If the side of a cube is 3 units, what is the volume?", "a. 81u^3\nb. 27u^3\nc. 9u^3\nd. 6u^3", "b", "27u^3", false);
            Thread.sleep(1000);
            if (gameOver) {
                break;
            }
            askQuestion("\n7. A tree casts a shadow that is 10 feet long. The angles between the ground and the tip of the tree's shadow and vice versa are 45 degrees. How tall is the tree?", "a. 5ft\nb. 10ft\nc. 20ft\nd. Not enough information", "b", "10ft", false);
            Thread.sleep(1000);
            if (gameOver) {
                break;
            }

            System.out.println("\n***** " + quest[2] + " *****");
            Thread.sleep(1000);
            askQuestion("\n1. Evaluate the limit as x approaches 2 for the function f(x) = x^2.", "a. 4\nb. 2\nc. 1\nd. 0", "a", "4", false);
            Thread.sleep(1000);
            if (gameOver) {
                break;
            }
            askQuestion("\n2. Calculate the slope of the line passing through points (2,5) and (4,9).", "a. -7/3\nb. -2/3\nc. 7/3\nd. 2", "d", "2", false);
            Thread.sleep(1000);
            if (gameOver) {
                break;
            }
            askQuestion("\n3. Find the derivative of the function f(x) = x^3.", "a. x^2\nb. 3x\nc. x\nd. 3x^2", "d", "3x^2", false);
            Thread.sleep(1000);
            if (gameOver) {
                break;
            }
            askQuestion("\n4. Find the derivative of the function f(x) = 3x^2 + 2x - 1.", "a. 3x + 2\nb. 3x^2 + 2x\nc. 6x + 2\nd. 6x^2 + 2x", "c", "6x + 2", false);
            Thread.sleep(1000);
            if (gameOver) {
                break;
            }
            askQuestion("\n5. Find the integral of the function f(x) = 3x^2.", "a. 3x^2 + C\nb. x^3\nc. 6x\nd. x^3 + C", "d", "x^3 + C", false);
            Thread.sleep(1000);
            if (gameOver) {
                break;
            }
            askQuestion("\n6. Find the x-coordinate of the points where the graph y = x^2 - 4 intersects the axis.", "a. x = 2\nb. x = 2,-2\nc. x = 4\nd. x = 4,-4", "b", "x = 2,-2", false);
            Thread.sleep(1000);
            if (gameOver) {
                break;
            }
            askQuestion("\n7. A car travels at a speed increasing linearly with time. After 1 hour, it is going 60 km/h. After 2 hours, it is going 120 km/h. What's the car's acceleration?", "a. 60km/h\nb. 120km/h\nc. 20km/h\nd. Not enough information", "a", "60km/h", false);
            Thread.sleep(1000);
            if (gameOver) {
                break;
            }

            // Turns all power ups false
            skip = false;
            extraScore = false;
            // Pauses timer
            isPaused = true;
            System.out.println("\nTimer stopped");
            Thread.sleep(500);

            System.out.println("\nONLY FOR THE BRAVE: Would you like to answer a bonus question? You get 20 points for a right answer but lose 10 for wrong. Power ups do not apply! (y/n)");
            // User input read as lowercase to accept more forms of inputs
            String reply = scanner.nextLine().toLowerCase();

            // If reply is not "y" or "n"
            while (!Objects.equals(reply, "y") && !Objects.equals(reply, "n")) {
                System.out.println("Invalid choice. Please enter 'y' or 'n':");
                reply = scanner.nextLine().toLowerCase();
            }
            // If reply is "y"
            if (Objects.equals(reply, "y")) {
                System.out.println("\n***** BONUS QUESTION *****");
                Thread.sleep(1000);
                askQuestion("\nWhat does the following expression, sin^2(x) + cos^2(x), equal to? Hint: If you don't already have this memorized, its best to guess :)", "a. 4\nb. 2\nc. 1\nd. 0", "c", "1", true);
                Thread.sleep(1000);

            }
            break;
        }

        // Final game stats
        System.out.println("\nCONGRATULATIONS, YOU'VE COMPLETED THE ODYSSEY!\n\nFinal Game Stats:\nFinal Score: " + score + "\nCorrect Answers: " + correctAnswerCounter + "/21" + "\nGame Duration: " + (298 - seconds) + " seconds");
        Thread.sleep(5000);
        // Initializes FileWriter to input into scoreboard
        PrintWriter writer = new PrintWriter(new FileWriter("ICS3U - Summer School/scoreboard.txt", true));
        System.out.println("\nEnter your name to add to scoreboard: ");
        String name = scanner.nextLine();
        // Enters info into writer to write into scoreboard.txt
        writer.write(name + ", " + score + " points, " + (298 - seconds) + " seconds" + System.lineSeparator());
        System.out.println("\nYour name, score and game duration have been added to scoreboard. You will now be returned to the main menu...");
        // Resetting progress for next game
        seconds = 303;
        score = 0;
        answerStreak = 0;
        correctAnswerCounter = 0;
        extraScoreCounter = 0;
        clip.stop();
        writer.close();
        Thread.sleep(2500);
    }

    // Method used for answering questions and power ups
    public static void askQuestion(String question, String options, String correctAnswerL, String correctAnswerN, boolean isBonus) throws InterruptedException {
        // Printing the prompt for player answer and section for adventurer's answer
        System.out.println(question);
        System.out.println(options);
        System.out.print("Your Answer: ");


        // Reads user input in lower case to accept more forms of answers
        String answer = scanner.nextLine().toLowerCase();
        if (seconds == 0) {
            return;
        }

        // Boolean for when the answer is correct
        boolean correct = Objects.equals(answer, correctAnswerL) || Objects.equals(answer, correctAnswerN)
                || Objects.equals(answer, correctAnswerL.toLowerCase());

        // If answer is correct
        if (correct || skip) {
            // If bonus question
            if (isBonus) {
                // Add 20 points
                score += 20;
                System.out.println("✅✅✅\nSEE I KNEW YOU COULD DO IT! ALWAYS BET ON YOURSELF! Oh and...\nYOU GAINED 20 POINTS! SCORE INCREASED TO " + score + "!");
                Thread.sleep(1000);
            }
            else if (extraScore) {
                score += 10;
                answerStreak++;
                extraScoreCounter++;
                correctAnswerCounter++;
                System.out.println("✅✅✅\nYOU GAINED 10 POINTS USING **EXTRA SCORE**! SCORE INCREASED TO " + score + "!");
                // Print method used for minutes:seconds format
                System.out.printf("Time remaining: %02d:%02d\n", minutes, remainingSeconds);
            }
            else if (skip) {
                score += 8;
                skip = false;
                answerStreak = 0;
                correctAnswerCounter++;
                System.out.println("⏭️✅⏭️\nYOU GAINED 8 FREE POINTS USING **SKIP**! SCORE INCREASED TO " + score + "!");
                System.out.printf("Time remaining: %02d:%02d\n", minutes, remainingSeconds);
            }
            else {
                score += 5;
                answerStreak++;
                correctAnswerCounter++;
                System.out.println("✅✅✅\nYOU GAINED 5 POINTS! SCORE INCREASED TO " + score + "!");
                System.out.printf("Time remaining: %02d:%02d\n", minutes, remainingSeconds);
            }

            // Power up activation
            if (answerStreak == 3) {
                // Initializes random number generator
                Random random = new Random();
                // Generates 1 random integer between 1 and 3 inclusive
                int randomNumber = random.nextInt(3) + 1;

                // If 1 is generated: Skip power up
                if (randomNumber == 1) {
                    skip = true;
                    System.out.println("\n⏭️⏭️⏭️\nYOU'RE ON FIRE! POWER UP **SKIP** RECEIVED — ENTER ANY KEY TO ACTIVATE IN NEXT QUESTION!");
                    System.out.printf("Time remaining: %02d:%02d\n", minutes, remainingSeconds);
                    Thread.sleep(2000);
                    answerStreak = 0;
                    return;
                }
                // If 2 is generated: Extra score power up
                if (randomNumber == 2) {
                    extraScore = true;
                    System.out.println("\n\uD83C\uDFC6\uD83C\uDFC6\uD83C\uDFC6\nYOU'RE ON FIRE! POWER UP **EXTRA SCORE** RECEIVED — 2X POINT MULTIPLIER FOR NEXT 2 QUESTIONS!");
                    Thread.sleep(1500);
                }
                // If 3 is generated: Extra time power up
                if (randomNumber == 3) {
                    seconds += 15;
                    minutes = seconds / 60;
                    remainingSeconds = seconds % 60;
                    System.out.printf("\n⌛⌛⌛\nYOU'RE ON FIRE! POWER UP **EXTRA TIME** RECEIVED — 15 SECONDS ADDED TO TIMER: %02d:%02d\n", minutes, remainingSeconds);
                    answerStreak = 0;
                    Thread.sleep(1000);
                }
                answerStreak = 0;
            }
        }
        else {
            if (isBonus) {
                score -= 10;
                if (score < 0) {
                    score = 0;
                    // Provides real answer
                    System.out.println("❌❌❌\nOOF, AT LEAST YOU HAD FAITH IN YOURSELF! right?\nAnswer: " + correctAnswerL + " or " + correctAnswerN + "\n\nYOU LOST 10 POINTS! SCORE DECREASED TO MINIMUM LIMIT 0!");
                }
                else {
                    System.out.println("❌❌❌\nAnswer: " + correctAnswerL + " or " + correctAnswerN + "\n\nYOU LOST 10 POINTS! SCORE DECREASED TO " + score + "!");
                    answerStreak = 0;
                    System.out.printf("Time remaining: %02d:%02d\n", minutes, remainingSeconds);
                }
            }
            else {
                score -= 3;
                if (score < 0) {
                    score = 0;
                    System.out.println("❌❌❌\nAnswer: " + correctAnswerL + " or " + correctAnswerN + "\n\nYOU LOST 3 POINTS! SCORE DECREASED TO MINIMUM LIMIT 0!");
                    System.out.printf("Time remaining: %02d:%02d\n", minutes, remainingSeconds);
                }
                else {
                    System.out.println("❌❌❌\nAnswer: " + correctAnswerL + " or " + correctAnswerN + "\n\nYOU LOST 3 POINTS! SCORE DECREASED TO " + score + "!");
                    System.out.printf("Time remaining: %02d:%02d\n", minutes, remainingSeconds);
                }
                answerStreak = 0;
                if (extraScore) {
                    extraScoreCounter++;
                }
            }
        }
        // Extra score power up reaches its limit
        if (extraScoreCounter == 2) {
            System.out.println("POWER UP **EXTRA SCORE** USED!");
            Thread.sleep(500);
            answerStreak = 0;
            extraScore = false;
            extraScoreCounter = 0;
        }
    }

    // Method used for scoreboard options screen
    private static void scoreboard() throws IOException {

        System.out.print("""
            
            1. Search in scoreboard
            2. Modify scoreboard
            3. Delete from scoreboard
            4. Return to main menu
            Option:\s""");

        String userInput = scanner.nextLine();

        switch (userInput) {
            case "1":
                searchInScoreboard();
                break;
            case "2":
                modifyScoreboard();
                break;
            case "3":
                deleteFromScoreboard();
                break;
            case "4":
                return;
            default:
                System.out.println("Invalid choice. Try again.");
                break;
        }
        // Returns to scoreboard
        scoreboard();
    }

    // Method used for searching in scoreboard
    private static void searchInScoreboard() throws IOException {
        System.out.println("\nEnter the adventurer's name to search for or 'Q' to quit (case sensitive search): ");
        String name = scanner.nextLine();
        // If user inputs "Q"
        if (name.equalsIgnoreCase("Q")) {
            return;
        }
        // FileReader initialization for scoreboard
        BufferedReader reader = new BufferedReader(new FileReader("ICS3U - Summer School/scoreboard.txt"));
        String line;
        // Boolean determining whether name input is located
        boolean found = false;
        while ((line = reader.readLine()) != null) {
            if (line.contains(name)) {
                // Prints entire line onto console
                System.out.println(line);
                found = true;
                break;
            }
        }
        // If name input is not located
        if (!found) {
            System.out.println("Adventurer not found");
        }
        reader.close();
    }

    // Method used for modifying scoreboard
    private static void modifyScoreboard() throws IOException {
        while (true) {
            System.out.println("\nEnter the adventurer's name to modify or 'Q' to quit (case sensitive search): ");
            String oldName = scanner.nextLine();
            if (oldName.equalsIgnoreCase("Q")) {
                return;
            }
            // Creates new temporary file to write in
            String tempFile = "temp.txt";
            File newFile = new File(tempFile);
            // Initializes scoreboard.txt file
            File oldFile = new File("ICS3U - Summer School/scoreboard.txt");

            // Initializes reading 'oldFile' and writing in 'newFile'
            BufferedReader reader = new BufferedReader(new FileReader(oldFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));

            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains(oldName)) {
                    System.out.println("Enter the new name: ");
                    String newName = scanner.nextLine();
                    System.out.println("Enter the new game duration in seconds: ");
                    String time = scanner.nextLine();
                    System.out.println("Enter the new score: ");
                    // If score is an integer
                    if (scanner.hasNextInt()) {
                        int score = scanner.nextInt();
                        scanner.nextLine();
                        writer.write(newName + ", " + score + " points, " + time + " seconds" + System.lineSeparator());
                        found = true;
                    }
                    // If score is not an integer
                    else {
                        System.out.println("Invalid score. The score must be an integer");
                        scanner.nextLine();
                        break;
                    }
                    break;
                }
                else {
                    writer.write(line + System.lineSeparator());
                }
            }
            // If adventurer name is found
            if (found) {
                System.out.println("Adventurer modified successfully");
                reader.close();
                writer.close();
                // Deletes old file and renames new file to old file
                oldFile.delete();
                newFile.renameTo(oldFile);
                return;
            }
            // If adventurer is not found
            if (line == null) {
                System.out.println("Adventurer not found");
            }
            reader.close();
            writer.close();
        }
    }

    // Method used to delete from scoreboard
    private static void deleteFromScoreboard() throws IOException {
        while (true) {
            System.out.println("\nEnter the adventurer's name to delete or 'Q' to quit (case sensitive search): ");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("Q")) {
                return;
            }
            // Creates new temporary file
            String tempFile = "temp.txt";
            File newFile = new File(tempFile);
            // Initializes scoreboard.txt file
            File oldFile = new File("ICS3U - Summer School/scoreboard.txt");

            // Initializes reading 'oldFile' and writing in 'newFile'
            BufferedReader reader = new BufferedReader(new FileReader(oldFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));

            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(name)) {
                    writer.write(line + System.lineSeparator());
                }
                else {
                    found = true;
                }
            }
            // If adventurer name is found
            if (found) {
                System.out.println("Adventurer deleted successfully");
                reader.close();
                writer.close();
                oldFile.delete();
                newFile.renameTo(oldFile);
                return;
            }
            // If adventurer name is not found
            else {
                System.out.println("Adventurer not found");
            }
            reader.close();
            writer.close();
        }
    }

    // Method used for displaying rules
    private static void rules() {
        System.out.println("""
                
                1. Once you click play, the timer starting at 5 minutes will begin counting down shortly after, so BE READY!
                2. You will first be prompted 7 questions based on Algebra, then Geometry and finally Calculus (Don't worry you should not need a calculator).
                3. Please answer each question with either the letter or the exact answer after the correct letter.
                4. As you progress each quest, the questions will become more difficult. Beware, the last question in each quest might just boggle your mind!
                5. While progressing, your score will be added up and will be displayed after each question (5 points gained for right answer, 3 subtracted otherwise).
                6. If 3 questions are answered right in a row, you will gain a single power up randomly:
                  - Skip: skips next question and gives 8 free points.
                  - Extra time: adds 15 seconds to your timer.
                  - Extra score: 2x score multiplier for 2 questions, if answered right.
                7. A bonus question will be prompted at the end for those who dare. You will gain 20 points if answered correctly, but lose 10 otherwise.
                8. GOOD LUCK!!!""");

        // Allows any key to be entered to return to main menu
        System.out.println("\nEnter any key to return to main menu");
    }
}