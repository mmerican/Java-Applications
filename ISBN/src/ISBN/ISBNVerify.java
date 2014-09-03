package ISBN;

import java.util.*;

public class ISBNVerify {
	StringBuilder isbnNum;
	
	public ISBNVerify(String isbn_num) {
		isbnNum = new StringBuilder(isbn_num);
	}
	
	public char getCheckDigit() {
		return isbnNum.charAt(isbnNum.length() - 1);
	}
	
	public boolean verify() {
		char check = this.getCheckDigit();
		verify(check);
		
		return false;
	}
	
	public boolean verify(char checkDigit) {
		List<Integer> numbers = new ArrayList<>();
		for (int i = 0; i < (isbnNum.length() - 1); i++) {
			if (isbnNum.charAt(i) != '-') {
				numbers.add((int)(isbnNum.charAt(i)));
			}
		}
		int isbnLength = numbers.size();
		char comp;
		if (isbnLength == 10) {
			comp = vCompOld(numbers);
		} else if (isbnLength == 13) {
			comp = vCompOld(numbers);
		} else {
			throw new InvalidISBNException("Invalid ISBN Number");
		}
		
		if (comp == checkDigit) {
			return true;
		} else {
			return false;
		}
	}
	
	private char vCompOld(List<Integer> numbers) {
		int i = 10;
		int check = 0;
		for (int x : numbers) {
			check += (i * x);
			i--;
		}
		check = check % 11;
		if (check >= 0 && check <= 9) {
			return Character.forDigit(check, 10);
		} else {
			return 'X';
		}
	}
	
	protected char vCompNew(List<Integer> numbers) {
		boolean evenOdd = false;
		int check = 0;
		for (int x : numbers) {
			if (!evenOdd) {
				check += x;
			} else {
				check += (x * 3);
			}
		}
		check = check % 10;
		if (check >= 0 && check <= 9) {
			return Character.forDigit(check, 10);
		} else {
			return 'X';
		}
	}
}
