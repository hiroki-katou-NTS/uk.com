package nts.uk.ctx.at.record.pub.remainingnumber.annualbreakmanage;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Value
public class YearlyHolidaysTimeRemainingExport {
	private GeneralDate annualHolidayGrantDay;
	private int annualRemaining;
	private int annualRemainingGrantTime;
}
