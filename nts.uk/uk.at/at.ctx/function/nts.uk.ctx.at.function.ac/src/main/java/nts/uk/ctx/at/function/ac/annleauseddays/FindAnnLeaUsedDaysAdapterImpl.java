package nts.uk.ctx.at.function.ac.annleauseddays;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.annleauseddays.FindAnnLeaUsedDaysAdapter;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.FindAnnLeaUsedDaysFromPreviousToNextGrantDatePub;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class FindAnnLeaUsedDaysAdapterImpl implements FindAnnLeaUsedDaysAdapter {

    @Inject
    private FindAnnLeaUsedDaysFromPreviousToNextGrantDatePub nextGrantDatePub;
    @Override
    public AnnualLeaveUsedDayNumber findUsedDays(String employeeId, GeneralDate criteriaDate) {
        return nextGrantDatePub.findUsedDays(employeeId,criteriaDate);
    }
}
