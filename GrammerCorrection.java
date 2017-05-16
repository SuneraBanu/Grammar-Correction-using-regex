import java.util.*;
import java.util.regex.*;

public class GrammarCorrection {
	public static void main(String[] args) {
		System.out.println("Enter a sentence: ");
		Scanner sc = new Scanner(System.in);
		String sentence = sc.nextLine();
		GrammarCorrection gc = new GrammarCorrection();
		gc.checkGrammar(sentence);
	}

	public void checkGrammar(String sentence) {

		System.out.println("\nYou entered: " + sentence);
		sentence = trimSpaces(sentence);
		// Should not contain empty statement
		if (Pattern.matches("^$", sentence)) {
			System.out.println("Please enter a sentence to evaluate.");

			// Should not contain more than 50 character
		} else if (!Pattern.matches("[\\S\\s]{1,50}$", sentence)) {
			System.out.println("Sorry, you have entered a sentence longer than 50 characters.");

			// Should not contain special character
		} else if (!Pattern.matches("[a-zA-Z0-9\\s][^#@$%&*();:/\\-+`~-]{1,50}$", sentence)) {
			System.out.println("Please enter a proper sentence.");

			// Should not contain only number
		} else if (Pattern.matches("^[^a-zA-Z][0-9]{1,50}$", sentence)) {
			System.out.println("A sentence cannot contain only numbers.");

			// Capitalize FirstCharacter
		} else {

			if (!Pattern.matches("[A-Z]{1}[\\S\\s]$", sentence)) {
				sentence = capitalizeFirstLetter(sentence);
			}

			// adding full stop at the end of the statement
			if (!Pattern.matches("[\\S\\s]{1,50}[\\.]$", sentence)) {
				sentence = addFullStop(sentence);

			}

			sentence = checkQuestion(sentence);
			// Removing and adding comma in the correct place
			Pattern pattern = Pattern.compile("^.*[,].*$");
			Matcher match = pattern.matcher(sentence);
			boolean comma = match.find();
			if (comma) {
				sentence = formatCommas(sentence);
			}
			// Adding question mark
			pattern = Pattern.compile("^.*[?].*$");
			match = pattern.matcher(sentence);
			boolean question = match.find();
			if (question) {
				sentence = cleanQuestions(sentence);
			}

		}
		System.out.println("Correct sentence: " + sentence);

	}

	// replace multiple dot with single dot
	public String addFullStop(String sentence) {
		String cleanedString = sentence.replaceAll("\\s+", " ").replaceAll("\\.+", ".");
		cleanedString = cleanedString.replaceAll("( \\. )+|( \\.)+|(\\. )+", ".").replaceAll("\\.+", ".");
		cleanedString = cleanedString.replaceFirst("^\\.", "").replaceFirst("\\.$", "");
		cleanedString = cleanedString.replace(".", ". ");
		cleanedString += ".";
		return cleanedString;
	}

	// method for capitalizing First Letter
	public String capitalizeFirstLetter(String sentence) {
		int counter = 0;
		for (int i = 0; i < sentence.length(); i++) {
			if ((sentence.charAt(i) + "").equals(" ")) {
				counter++;
			} else {
				break;
			}
		}
		if (counter > 0) {
			sentence = sentence.trim();
		}
		return ("" + sentence.charAt(0)).toUpperCase() + "" + sentence.substring(1).toLowerCase();
	}

	// methods to add question mark
	public String addQuestionMark(String sentence) {
		sentence = trimSpaces(sentence);
		return sentence.substring(0, sentence.length()) + "?";
	}

	// method to trim space
	public String trimSpaces(String sentence) {
		if (Pattern.matches("[\\S\\s]{1,50}[.\\?\\s,]$", sentence)) {
			sentence = sentence.substring(0, sentence.length() - 1);
			sentence = trimSpaces(sentence);

		}
		sentence = sentence.replaceAll("\\s+", " ");
		return sentence.trim();
	}

	// method to format commas
	public String formatCommas(String sentence) {
		String cleanedString = sentence.replaceAll("\\s+", " ").replaceAll(",+", ",");
		cleanedString = cleanedString.replaceAll("( , )+|( ,)+|(, )+", ",").replaceAll(",+", ",");
		cleanedString = cleanedString.replaceFirst("^,", "").replaceFirst(",$", "");
		cleanedString = cleanedString.replace(",", ", ");

		return cleanedString;
	}

	// clearing unnecessary question mark
	public String cleanQuestions(String sentence) {
		String cleanedString = sentence.replaceAll("\\?+", "?");
		cleanedString = cleanedString.replaceAll("( \\? )+|( \\?)+|(\\? )+", "?").replaceAll("\\?+", "?");
		cleanedString = cleanedString.replaceFirst("^\\?", "").replaceFirst("\\?$", "");
		cleanedString = cleanedString.replace("?", "? ");
		String temp = "", temp1 = "";
		String[] split_string = cleanedString.split("\\?");
		for (int i = 0; i < split_string.length; i++) {
			temp = capitalizeFirstLetter(split_string[i].trim());
			temp1 += " " + checkQuestion(temp);
		}
		return temp1;
	}

	// separates sentences accordingly
	public String separateSentences(String sentence) {
		String temp = "", temp1 = "";
		String[] split_string = sentence.split("\\.");
		for (int i = 0; i < split_string.length; i++) {
			temp = capitalizeFirstLetter(split_string[i].trim());
			temp1 += " " + checkQuestion(temp);

		}
		return temp1;
	}

	// method checking to place question mark
	public String checkQuestion(String sentence) {
		Pattern pattern = Pattern.compile("(\\w+).*");
		Matcher match = pattern.matcher(sentence);
		match.find();
		String words[] = { "What", "When", "Why", "Where", "How", "Can", "Is", "Who", "Are", "Does", "Did", "Will",
				"Could", "Would", "Shall", "At" };
		int counter = 0;
		for (int i = 0; i < words.length; i++) {
			if (match.group(1).equals(words[i])) {
				counter++;
			}
		}
		if (counter > 0) {
			sentence = addQuestionMark(sentence);
		} else {
			sentence = addFullStop(sentence);
		}
		return sentence;
	}

}
