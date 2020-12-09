package nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkPlaceIdAndPeriodImportAl {
	/** The date range. */
	// 配属期間
	private DatePeriod datePeriod;

	/** The workplace id. */
	// 職場ID
	private String workplaceId;
}
