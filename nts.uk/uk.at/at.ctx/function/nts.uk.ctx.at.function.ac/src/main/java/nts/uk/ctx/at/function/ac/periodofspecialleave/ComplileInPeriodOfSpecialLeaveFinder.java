package nts.uk.ctx.at.function.ac.periodofspecialleave;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.ComplileInPeriodOfSpecialLeaveAdapter;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.ComplileInPeriodOfSpecialLeaveImported;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.SpecialHolidayRemainDataImported;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.export.SpecialHolidayRemainDataSevice;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveManagementService;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class ComplileInPeriodOfSpecialLeaveFinder implements ComplileInPeriodOfSpecialLeaveAdapter {

	@Inject
	private SpecialLeaveManagementService specialLeaveManagementService;

	@Inject
	private SpecialHolidayRemainDataSevice specialHolidayRemainDataSevice;

	@Override
	public List<ComplileInPeriodOfSpecialLeaveImported> complileInPeriodOfSpecialLeave(String cid, String sid,
			DatePeriod complileDate, boolean mode, GeneralDate baseDate, int specialLeaveCode, boolean mngAtr) {
		// 273
		specialLeaveManagementService.complileInPeriodOfSpecialLeave(cid, sid, complileDate, mode, baseDate,
				specialLeaveCode, mngAtr);
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SpecialHolidayRemainDataImported> getSpeHoliOfConfirmedMonthly(String sid, YearMonth startMonth,
			YearMonth endMonth) {
		// 263
		specialHolidayRemainDataSevice.getSpeHoliOfConfirmedMonthly(sid, startMonth, endMonth);
		// TODO Auto-generated method stub
		return null;
	}

}
