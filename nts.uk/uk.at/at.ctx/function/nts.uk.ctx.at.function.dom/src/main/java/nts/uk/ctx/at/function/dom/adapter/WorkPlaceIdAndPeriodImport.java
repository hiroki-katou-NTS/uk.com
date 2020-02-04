package nts.uk.ctx.at.function.dom.adapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkPlaceIdAndPeriodImport {

	/** The date range. */
	// 配属期間
	private DatePeriod datePeriod;

	/** The workplace id. */
	// 職場ID
	private String workplaceId;

}
