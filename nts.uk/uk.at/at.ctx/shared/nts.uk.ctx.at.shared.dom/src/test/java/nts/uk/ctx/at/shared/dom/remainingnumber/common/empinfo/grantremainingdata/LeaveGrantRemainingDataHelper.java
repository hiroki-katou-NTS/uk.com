package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveNumberInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;

public class LeaveGrantRemainingDataHelper {
	public static LeaveGrantRemainingData leaveGrantRemainingData(String employeeId, GeneralDate date,double days, Integer minutes,
			double usedays, Integer useminutes, double remaindays, Integer remainminutes) {
		return LeaveGrantRemainingData.of(employeeId, date, date.addYears(2), LeaveExpirationStatus.AVAILABLE,
				GrantRemainRegisterType.MONTH_CLOSE,
				new LeaveNumberInfo(
						LeaveGrantNumber.of(new LeaveGrantDayNumber(days), Optional.of(new LeaveGrantTime(minutes))),
						new LeaveUsedNumber(usedays, useminutes), new LeaveRemainingNumber(remaindays, remainminutes)));
	}
}
