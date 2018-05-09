package nts.uk.screen.at.app.ktgwidget.find.dto;

import lombok.Value;

@Value
public class YearlyHolidayInfo {

	/*年休残数（日数）*/
	private int day;
	/*年休残数（時間）*/
	private TimeOT hours;
	/*半休残数*/
	private int remaining;
	/*時間年休上限*/
	private TimeOT timeYearLimit;
}
