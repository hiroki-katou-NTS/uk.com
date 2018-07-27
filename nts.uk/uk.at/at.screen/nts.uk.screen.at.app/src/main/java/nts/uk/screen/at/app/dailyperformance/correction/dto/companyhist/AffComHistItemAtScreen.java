package nts.uk.screen.at.app.dailyperformance.correction.dto.companyhist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AffComHistItemAtScreen {

	private String employeeId;

	/** 所属期間 */
	private DatePeriod datePeriod;
}
