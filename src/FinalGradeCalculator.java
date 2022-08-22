/*
 * Created by: Prem Patel
 * Purpose: Calculates the final weighted grade in a class. 
 * 			If the final has not been taken, the program will output the lowest score a student can score on a final to earn their desired grade as a final grade.
 * 			If the final was taken, the program calculates the final grade of the course.
 */
import java.util.Scanner;

public class FinalGradeCalculator {
	public static void main(String[] args) {
		// Initialize Scanner and Variables
		double weight = 0, possiblePoints = 0, earnedPoints;
		double grade = 0.0, desired, diff, totalWeight = 0;
		boolean predict = true;
		int len;
		Category[] cat = null;
		try (
			Scanner input = new Scanner(System.in);
		){
			// Print Help Message
			System.out.printf("This program needs to know what makes up your overall grade.%nPretend for a moment your course is similar to the example shown in the ReadMe:%n%n"
					+ "Assignments (Category #1), Exam 1 (Category #2), Exam 2 (Category #3), and Final Exam (Category #4) are all seperate categories.%nThe total number of categories in this case? 4.%n%n"
					+ "Exam 1 is worth 25 percent of the overall course grade in the README example.%nWe scored 97.5 out of 100 (or 24.38 points out of 25)."
					+ "%nWhen the program asks for weight?\t\tEnter 25.%nWhen the program asks for maximum points?\tEnter 100 (OR 25).%nWhen the program asks for your score?\t\tEnter 97.5 (OR 24.38)."
					+ "%n%n-------------------------------------------------------------------------------------%n");
			// Prompt user for number of categories (example: Assignments, Homework, Exam 1, Exam 2)
			// & create an array of Categories
			System.out.print("Input the number of grading categories of your class/course: ");
			len = input.nextInt();
			if (len > 0) {
				cat = new Category[len];
			} else {
				throw new UnsupportedInputException(len, "number of categories cannot be negative.");
			}
			
			// Run a for-loop and ask user to input values necessary for calculations
			for (int i = 0; i < cat.length; i++) {
				System.out.printf("%nEnter the weight of Category %d, in terms of %c of grade: ", i + 1, '%');
				weight = input.nextDouble();
				totalWeight += weight;
				
				// Exception Handling for weighted
				if (weight < 0) {
					throw new UnsupportedInputException(weight, "a Negative Weight.");
				} else if (weight > 100) {
					throw new UnsupportedInputException(weight, "exceeds 100% weight.");
				} else if (totalWeight > 100) {
					throw new UnsupportedInputException(totalWeight, "total weight cannot exceed 100%.");
				}
				System.out.printf("Enter the maximum amount of available points in Category %d: ", i + 1);
				possiblePoints = input.nextDouble();	
				
				System.out.printf("Enter the amount of points you've scored in Category %d: ", i + 1);
				earnedPoints = input.nextDouble();
				if (earnedPoints > possiblePoints) {
					throw new UnsupportedInputException(earnedPoints, "earned points outweigh maximum possible points.");
				} 
				//Initialize a Category and retrieve the current final grade (so far)
				cat[i] = new Category(weight, possiblePoints, earnedPoints);
				grade += (cat[i].getGrade() * cat[i].getWeight()) * 100.0;
				
				// Prompt user if the next category is the final exam
				System.out.println();
				System.out.print("Is the next (& last) category the final exam? (y/n): ");
				
				if (input.next().charAt(0) == 'y') {
					// Prompt user for weight and maximum points
					System.out.printf("%nEnter the weight of your final exam: ");
					weight = input.nextDouble();
					totalWeight += weight;
					
					// Exception Handling for weighted
					if (weight < 0) {
						throw new UnsupportedInputException(weight, "is a Negative Weight.");
					} else if (weight > 100) {
						throw new UnsupportedInputException(weight, "exceeds 100% weight.");
					} else if (totalWeight > 100) {
						throw new UnsupportedInputException(totalWeight, "total weight exceeded 100%.");
					}
					
					//weight = weight / 100.0;
					System.out.printf("%nEnter the maximum amount of available points: ");
					possiblePoints = input.nextDouble();	
					// Prompt user if they wish to calculate their final grade or determine the lowest possible score on the final to end with a desired grade
					System.out.printf("%nHave you taken the final yet? (y/n): ");
					
					if (input.next().charAt(0) == 'y') {
						// Continue as normal if the final has already been taken
						System.out.printf("%nEnter the amount of points you've scored: ");
						earnedPoints = input.nextDouble();
						if (earnedPoints > possiblePoints) {
							throw new UnsupportedInputException(earnedPoints, "earned points outweigh maximum possible points.");
						} 
						// Initialize a Category and retrieve the current final grade (so far)
						cat[i + 1] = new Category(weight, possiblePoints, earnedPoints);
						grade += (cat[i + 1].getGrade() * cat[i + 1].getWeight()) * 100.0;
						
						predict = false;
					} else {
						cat[i + 1] = new Category(weight);
						weight = weight / 100;
					}
					// Break out of the loop if the final is the last category
					break;
				}
			}
			// Invoke a method that prints the weight and grade in every category 
			printReport(cat);
			
			// Display the current grade (if the final was not taken, this would be the grade if a final was never taken (or a zero was scored).
			if (predict) {
				System.out.printf("Your current grade if you didn't take the final (or got a zero) is: %.1f", grade);
			} else {
				System.out.printf("Your final grade is: %.1f", grade);
			}
			
			while (predict) {
				// Prompt user for desired grade
				System.out.printf("%nWhat is your desired final grade?: ");
				desired = input.nextDouble();
				// Calculate the difference
				diff = desired - grade;
				
				if (diff > 0) {
					System.out.printf("You need %.2f points to reach your desired grade of %.1f.%nThis is equivilant to a score of %.2f%c.", 
												diff, desired, diff / weight, '%');
				} else {
					System.out.printf("CONGRATULATIONS!%nBased on your input, even if you got the "
							+ "lowest score imaginable, you've already earned enough points for your final desired grade of %.1f.", desired);
				}
				
				// Prompt user if they desire to test another possible final desired grade
				System.out.printf("%n%nWould you like to enter another desired grade? (y/n): ");
				if (input.next().charAt(0) == 'n') {
					predict = false;
				}
			}
		} catch (UnsupportedInputException e) {
			System.out.print(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void printReport(Category[] array) {
		// Print contents of Category array, break if a null is encountered anywhere
		for (int i = 0; i < array.length; i++) {
			if (array[i] == null) break;
			System.out.printf("Category %d:\t%s%n", i + 1, array[i]);
		}
		System.out.printf("%n%n");
	}
}


