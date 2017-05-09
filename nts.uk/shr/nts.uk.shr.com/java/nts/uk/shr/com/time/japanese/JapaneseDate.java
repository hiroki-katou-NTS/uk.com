package nts.uk.shr.com.time.japanese;

import nts.arc.time.GeneralDate;

public class JapaneseDate {
	
	private final GeneralDate date;
	
	private final JapaneseEraName era;
	
	public JapaneseDate(GeneralDate date, JapaneseEraName era) {
		this.date = date;
		this.era = era;
	}
	
	public String toString () {
		StringBuilder result = new StringBuilder(era.getName()); 
		result.append((this.date.year() - this.era.startDate().year()) + "年");		
		result.append(this.date.month() + "月");
		result.append(this.date.day() + "日");
		return result.toString();
	}
}
