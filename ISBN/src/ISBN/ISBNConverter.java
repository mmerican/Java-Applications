package ISBN;

import java.util.ArrayList;
import java.util.List;

public class ISBNConverter extends ISBNVerify {
	
	public ISBNConverter(String isbn_num) {
		super(isbn_num);
	}
	
	public String convert() {
		StringBuilder converted = new StringBuilder(isbnNum.toString());
		converted.insert(0,  "978");
		List<Integer> numbers = new ArrayList<>();
		char conv;
		for (int i = 0; i < (converted.length() - 1); i++) {
			if (converted.charAt(i) != '-') {
				numbers.add((int)(converted.charAt(i)));
			}
		}
		if (numbers.size() != 12) {
			throw new InvalidISBNException("Invalid ISBN Number");
		} else {
			conv = vCompNew(numbers);
		}
		converted.setCharAt((converted.length() - 1), conv);
		return converted.toString();
	}
}
