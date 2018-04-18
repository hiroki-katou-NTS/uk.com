package nts.uk.ctx.at.record.pub.remainingnumber.annualbreakmanage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingDayNumber;

@AllArgsConstructor
@Getter
@Setter
public class YearlyHolidaysTimeRemainingExport {
	private GeneralDate annualHolidayGrantDay;
	private AnnualLeaveRemainingDayNumber annualRemaining;
	private AnnualLeaveRemainingDayNumber annualRemainingGrantTime;
}
