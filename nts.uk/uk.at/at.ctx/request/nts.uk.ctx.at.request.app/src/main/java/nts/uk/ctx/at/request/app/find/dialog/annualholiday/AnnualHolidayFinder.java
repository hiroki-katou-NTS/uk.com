package nts.uk.ctx.at.request.app.find.dialog.annualholiday;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.dialog.annualholiday.dto.AnnualHolidayDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualholidaymanagement.AnnualHolidayManagementAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.AnnLeaveRemainNumberAdapter;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AnnualHolidayFinder {

	@Inject
	private AnnualHolidayManagementAdapter holidayAdapter;

	@Inject
	private AnnLeaveRemainNumberAdapter leaveAdapter;

	public AnnualHolidayDto starPage(GeneralDate baseDate, String sID) {

		String cId = AppContexts.user().companyId();

		AnnualHolidayDto result = new AnnualHolidayDto();

		result.setAnnualLeaveGrant(holidayAdapter.acquireNextHolidayGrantDate(cId, sID));
		
		holidayAdapter.getDaysPerYear(cId, sID).ifPresent(x -> result.setAttendNextHoliday(x));
		
		result.setReNumAnnLeave(leaveAdapter.getReferDateAnnualLeaveRemainNumber(sID, baseDate));

		return result;
	}

}
