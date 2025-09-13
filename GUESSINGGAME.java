package Oasis;
import java.util.Random;
import java.util.Scanner;

public class GUESSINGGAME {
    public static int min_range = 1;
    public static int max_range = 100;
    public static int max_attempts = 5;
    public static int max_rounds = 3;
    
    public static void main(String[] args) {
        Random random = new Random();
        Scanner sc = new Scanner(System.in);
        int totalscore = 0;
        
        System.out.println("Welcome to number guessing game!\n");
        System.out.println("Total Number of Rounds: 3\n");
        System.out.println("No. of Attempts for each round: 5\n");
        
        for (int i = 1; i <= max_rounds; i++) {  // Fixed: i <= max_rounds for 3 rounds
            int number = random.nextInt(max_range - min_range + 1) + min_range;  // Safer random generation
            int current_attempts = 0;
            boolean guessed = false;
            
            System.out.printf("--- Current Round %d ---\n", i);
            System.out.printf("Guess the number between %d and %d in %d attempts.\n", min_range, max_range, max_attempts);
            
            while (current_attempts < max_attempts) {
                System.out.print("Enter your guess: ");
                if (sc.hasNextInt()) {
                    int guess_number = sc.nextInt();
                    if (guess_number < min_range || guess_number > max_range) {
                        System.out.printf("Please enter a number between %d and %d.\n", min_range, max_range);
                        continue;  // Don't count invalid guess as an attempt
                    }
                    current_attempts++;
                    
                    if (guess_number == number) {
                        int score = max_attempts - current_attempts + 1;  // Adjusted: +1 so first guess = 5 points
                        totalscore += score;  // Fixed: += operator
                        System.out.printf("Congratulations! You guessed the number %d.\n", number);
                        System.out.printf("Attempts used: %d | Round score: %d\n", current_attempts, score);
                        guessed = true;
                        break;
                    } else if (guess_number < number) {
                        System.out.printf("Too low! The number is greater than %d.\n", guess_number);
                        System.out.printf("Attempts left: %d\n", max_attempts - current_attempts);
                    } else {
                        System.out.printf("Too high! The number is less than %d.\n", guess_number);
                        System.out.printf("Attempts left: %d\n", max_attempts - current_attempts);
                    }
                } else {
                    System.out.println("Invalid input. Please enter an integer.");
                    sc.next();  // Clear invalid input
                }
            }
            
            if (!guessed) {  // Fixed: Use flag for cleaner check
                System.out.printf("You lost Round %d! Out of attempts.\n", i);
                System.out.printf("The number was: %d\n", number);
                // Optional: totalscore += 0; (no points for loss)
            }
            System.out.println();  // Spacing between rounds
        }
        
        System.out.printf("--- Game Over ---\n");
        System.out.printf("Total Score: %d (max possible: %d)\n", totalscore, max_attempts * max_rounds);
        sc.close();
    }
}