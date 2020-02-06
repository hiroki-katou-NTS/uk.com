package nts.uk.screen.at.app.dailyperformance.correction.dto.workplacehist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkPlaceIdPeriodAtScreen {
	/** The date range. */
	// 配属期間
	private DatePeriod datePeriod;

	/** The workplace id. */
	// 職場ID
	private String workplaceId;
}
