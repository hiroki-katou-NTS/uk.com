package nts.uk.screen.at.app.ktgwidget.find.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class YearlyHolidayInfo {

	/*年休残数（日数）*/
	private int day = 0;
	/*年休残数（時間）*/
	private TimeOT hours = new TimeOT(0, 0);
	/*半休残数*/
	private int remaining = 0;
	/*時間年休上限*/
	private TimeOT timeYearLimit = new TimeOT(0, 0);
}
