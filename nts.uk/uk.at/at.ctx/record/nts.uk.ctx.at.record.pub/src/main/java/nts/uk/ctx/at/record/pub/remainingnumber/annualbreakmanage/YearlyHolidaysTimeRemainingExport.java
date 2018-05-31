package nts.uk.ctx.at.record.pub.remainingnumber.annualbreakmanage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
@Setter
public class YearlyHolidaysTimeRemainingExport {
	private GeneralDate annualHolidayGrantDay;
	private Double annualRemaining;
	private Double annualRemainingGrantTime;
}
