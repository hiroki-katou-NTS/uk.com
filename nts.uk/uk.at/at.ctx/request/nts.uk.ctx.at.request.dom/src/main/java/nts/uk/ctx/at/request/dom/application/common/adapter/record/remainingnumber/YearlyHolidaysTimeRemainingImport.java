package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
@AllArgsConstructor
@Value
public class YearlyHolidaysTimeRemainingImport {
	private GeneralDate annualHolidayGrantDay;
	private Double annualRemaining;
	private Double annualRemainingGrantTime;
}
