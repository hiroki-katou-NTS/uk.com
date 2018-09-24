package nts.uk.ctx.at.request.app.find.dialog.annualholiday;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.dialog.annualholiday.dto.AnnualHolidayDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualholidaymanagement.AnnualHolidayManagementAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.AnnLeaveRemainNumberAdapter;
import nts.uk.ctx.at.shared.app.find.vacation.setting.annualpaidleave.AnnualPaidLeaveFinder;
import nts.uk.ctx.at.shared.app.find.vacation.setting.annualpaidleave.dto.AnnualPaidLeaveSettingFindDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AnnualHolidayFinder {

	@Inject
	private AnnualHolidayManagementAdapter holidayAdapter;

	@Inject
	private AnnLeaveRemainNumberAdapter leaveAdapter;

	@Inject
	private AtEmployeeAdapter EmpAdapter;
	
	@Inject
	private AnnualPaidLeaveFinder annualFinder;

	public AnnualHolidayDto starPage(GeneralDate baseDate, List<String> sIDs) {

		String sID = sIDs.get(0);
		// 228
		List<EmployeeInfoImport> employees = EmpAdapter.getByListSID(sIDs);

		AnnualHolidayDto result = getAnnualHoliDayDto(sID, baseDate);
		
		// ドメインモデル「年休設定」を取得する(lấy dữ liệu domain 「年休設定」)
		AnnualPaidLeaveSettingFindDto annualSetDto = this.annualFinder.findByCompanyId();
		result.setAnnualSet(annualSetDto);
		result.setEmployees(employees);

		return result;
	}

	public AnnualHolidayDto getAnnualHoliDayDto(String sID, GeneralDate baseDate) {

		String cId = AppContexts.user().companyId();
		AnnualHolidayDto result = new AnnualHolidayDto();
		// 210
		result.setAnnualLeaveGrant(holidayAdapter.acquireNextHolidayGrantDate(cId, sID, baseDate));
		// 323
		holidayAdapter.getDaysPerYear(cId, sID).ifPresent(x -> result.setAttendNextHoliday(x));
		// 198
		result.setReNumAnnLeave(leaveAdapter.getReferDateAnnualLeaveRemainNumber(sID, baseDate));

		return result;
	}

}
