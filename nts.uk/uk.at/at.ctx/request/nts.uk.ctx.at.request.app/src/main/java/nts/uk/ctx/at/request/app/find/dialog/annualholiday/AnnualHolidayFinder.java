package nts.uk.ctx.at.request.app.find.dialog.annualholiday;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.uk.ctx.at.request.app.find.dialog.annualholiday.dto.AnnualHolidayDto;
import nts.uk.ctx.at.request.app.find.dialog.annualholiday.dto.AnnualLeaveGrantDto;
import nts.uk.ctx.at.request.app.find.dialog.annualholiday.dto.ReNumAnnLeaReferenceDateDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualholidaymanagement.AnnualHolidayManagementAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.AnnLeaveRemainNumberAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.ReNumAnnLeaReferenceDateImport;
import nts.uk.ctx.at.shared.app.find.vacation.setting.annualpaidleave.AnnualPaidLeaveFinder;
import nts.uk.ctx.at.shared.app.find.vacation.setting.annualpaidleave.dto.AnnualPaidLeaveSettingFindDto;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require.RemainNumberTempRequireService;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcess;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AnnualHolidaySetOutput;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
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
	
	@Inject
	private RemainNumberTempRequireService requireService;

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
		val require = requireService.createRequire();
        val cacheCarrier = new CacheCarrier();

		String cId = AppContexts.user().companyId();
		AnnualHolidayDto result = new AnnualHolidayDto();

		// 10-1.年休の設定を取得する
		AnnualHolidaySetOutput annualHd = AbsenceTenProcess.getSettingForAnnualHoliday(require, cId);
		if (!annualHd.isYearHolidayManagerFlg() || !annualHd.isSuspensionTimeYearFlg()) {
			result.setAnnualLeaveGrant(new ArrayList<>());
			result.setEmployees(new ArrayList<>());
			result.setAnnualLeaveManagementFg(false);
			return result;
		}
		result.setAnnualLeaveManagementFg(true);
		// 210
		result.setAnnualLeaveGrant(holidayAdapter.acquireNextHolidayGrantDate(cId, sID, baseDate));
		// 323
		holidayAdapter.getDaysPerYear(cId, sID).ifPresent(x -> result.setAttendNextHoliday(x));
		DatePeriod closingPeriod = ClosureService.findClosurePeriod(require, cacheCarrier, sID, baseDate);
		// 198
		ReNumAnnLeaReferenceDateImport reNumAnnLeave = leaveAdapter.getReferDateAnnualLeaveRemainNumber(sID, baseDate);
		ReNumAnnLeaReferenceDateDto reNumAnnLeaveDto = new ReNumAnnLeaReferenceDateDto();
		reNumAnnLeaveDto.setAnnualLeaveGrantExports(reNumAnnLeave.getAnnualLeaveGrantExports().stream().map((item) -> {
			AnnualLeaveGrantDto itemDto = new AnnualLeaveGrantDto();
			itemDto.setDaysUsedNo(item.getDaysUsedNo());
			itemDto.setDeadline(item.getDeadline());
			itemDto.setGrantDate(item.getGrantDate());
			itemDto.setGrantNumber(item.getGrantNumber());
			itemDto.setRemainDays(item.getRemainDays());
			itemDto.setRemainMinutes(item.getRemainMinutes());
			itemDto.setExpiredInCurrentMonthFg(closingPeriod.contains(item.getDeadline()));
			return itemDto;
		}).collect(Collectors.toList()));
		reNumAnnLeaveDto.setAnnualLeaveManageInforExports(reNumAnnLeave.getAnnualLeaveManageInforExports());
		reNumAnnLeaveDto.setAnnualLeaveRemainNumberExport(reNumAnnLeave.getAnnualLeaveRemainNumberExport());
		result.setReNumAnnLeave(reNumAnnLeaveDto);

		return result;

	}
}