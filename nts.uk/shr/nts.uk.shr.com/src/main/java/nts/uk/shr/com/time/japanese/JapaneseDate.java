package nts.uk.shr.com.time.japanese;

import nts.arc.time.GeneralDate;

public class JapaneseDate {
	
	private final GeneralDate date;
	
	private final JapaneseEras eras;
	
	public JapaneseDate(GeneralDate date, JapaneseEras eras) {
		this.date = date;
		this.eras = eras;
	}
	
}
