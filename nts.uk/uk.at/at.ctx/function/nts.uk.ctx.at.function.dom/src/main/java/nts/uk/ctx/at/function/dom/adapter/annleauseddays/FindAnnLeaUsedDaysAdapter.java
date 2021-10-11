package nts.uk.ctx.at.function.dom.adapter.annleauseddays;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;

public interface FindAnnLeaUsedDaysAdapter {
    AnnualLeaveUsedDayNumber findUsedDays(String employeeId, GeneralDate criteriaDate);

}
