package controller;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StringCalculator {
	
	private static final String SEPARATOR = ",";
	private static final String META_CHAR = ".$|()[{^?*+\\";
	private static Function<String, Boolean> separatorIsDefined = s -> s.startsWith("//") && s.length() > 2;
	private static Function<String, String> getSeparator = s -> s.length() > 3 ? s.substring(2,3) : SEPARATOR;
	private static BiFunction<String, String, String> modifySeparator = (s, sep) -> s.replaceAll(sep, SEPARATOR).replaceAll("\n", SEPARATOR);

	public static void main(String[] args) {
		try {
			if (args.length == 0)
				System.err.println("No argument");
			else if (args[0].length() < (args[0].startsWith("//") ? 4 : 0))
				System.err.println("Argument format incorrect\n"
						+ "It should be either\n"
						+ "<number>,<number>...\n"
						+ "or\n"
						+ "//<separator><number><separator><number>...");
			else
				System.out.println(add(args[0]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int add(String numbers) throws Exception {
		String newSeparator = getSeparator.apply(numbers);
		
		if (META_CHAR.contains(newSeparator)) {
			throw new Exception("'" + newSeparator + "' can't be selected as separator becaus it is a meta caracter (" + META_CHAR + ")" );
		}
		
		String finalNumbers = "";
		
		if (separatorIsDefined.apply(numbers)) {
			finalNumbers = modifySeparator.apply(numbers, newSeparator).substring(3);
		} else if (!numbers.isEmpty()){
			finalNumbers = numbers.replaceAll("\n", SEPARATOR);			
		}
		
		List<String> numbersList = Arrays.asList(finalNumbers.trim().split(SEPARATOR));
		
		if(finalNumbers.contains("-"))
			throw new NumberFormatException("Negatives not allowed : " + numbersList.stream().filter(s -> s.startsWith("-")).collect(Collectors.toList()));
		
		return numbersList.stream()
						  .filter(s -> !s.isEmpty())
						  .mapToInt(Integer::parseInt)
						  .sum();
	}

}
