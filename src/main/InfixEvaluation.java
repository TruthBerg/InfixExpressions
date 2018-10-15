package main;

/**
 * This program evaluates and produces the value of a given arithmetic expression
 * using a stack data structure.
 * 
 * We make the following assumptions about the input expression:
 * All operands are positive integers.
 * Only the following operators are allowed: +, –, *, and /Expression is fully parenthesized. In other words, each operator has a pair of “(“ and “)” surrounding its two operands.
 * 
 * @author emmettgreenberg
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class InfixEvaluation {

	public static void main(String[] args) throws IOException {
		
		// create a stack to store expressions (to be printed alongside their results)
		LinkedStack<String> expressions = new LinkedStack<String>();
		
		// create two stacks
		LinkedStack<String> operators = new LinkedStack<String>();
		LinkedStack<Integer> operands = new LinkedStack<Integer>();
		
		// create array list to store tokens
		ArrayList<String> tokens = new ArrayList<String>();
		
		// read input file
		// set up scanner
		Scanner in = new Scanner(new File("src/infix_expressions.txt"));
		while (in.hasNext()) {
			String str =  in.nextLine();
			// add to list of expression
			expressions.push(str);
			// split the line into tokens at each space
			String[] splitStr = str.split(" ");
			// add each token to array list
			for (String s: splitStr) {
				tokens.add(s);
			}
		}
		in.close();
		
		// scan tokens
		for (String t: tokens) {
			if (strIsInt(t)) {
				operands.push(Integer.parseInt(t));
			}
			else if (t.equals("(")) {
				continue;
			}
			else if (t.equals(")")) {				// do the operation
				applyOp(operators, operands);
			}
			else {				// t is operator: add to operators stack
				operators.push(t);
			}
		}
			
		// combine expressions with their result
		LinkedStack<String> expressionsWithResult = new LinkedStack<>();
		while (!expressions.isEmpty() && !operands.isEmpty()) {
			String exp = expressions.pop();
			int res = operands.pop();
			expressionsWithResult.push("The value of " + exp + " is " + res); // push expression w/ result onto new stack
		}
		
		// print expressions along with results in the order they appear in the file
		while (!expressionsWithResult.isEmpty()) {
			System.out.println(expressionsWithResult.pop());
		}
	}
	
	/**
	 * Applies the top operator to the top two operands in the correct order and pushes the result to the stack of operands
	 * @param operators
	 * @param operands
	 */
	public static void applyOp(LinkedStack<String> operators, LinkedStack<Integer> operands) {
//		2. While operator stack is not empty:
//			2.1 Pop operator
//			2.2 Pop two operands
//			2.3 Apply operator to operands in the correct order
//			2.4 push result to operands stack
		while (!operators.isEmpty() && operands.size() >= 2) {
			int b = operands.pop();
			int a = operands.pop();
			String op = operators.pop();
			
			int res = -1;
			if (op.equals("+")) res = a + b;
			else if (op.equals("-") || op.equals("–")) res = a - b;
			else if (op.equals("*")) res = a * b;
			else if (op.equals("/")) res = a / b;
			else System.out.println("Unrecognizable operator: " + op);;
			
			operands.push(res);
		}
	}
	
	/**
	 * Returns true if a string can be parsed into an integer, otherwise it catches the exception and returns false
	 * @param str
	 * @return
	 */
	private static boolean strIsInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		}
		catch (NumberFormatException npe) {
			return false;
		}
	}
}
