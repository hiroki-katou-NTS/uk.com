package nts.uk.ctx.at.request.app.find.dialog.annualholiday;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
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

	/**
	 * UKDesign.UniversalK.就業.KDL_ダイアログ.KDL020_年休ダイアログ.アルゴリズム.5.年休ダイアログ作成（社員選択用）.5.年休ダイアログ作成（社員選択用）
	 * @param sID
	 * @param baseDate
	 * @return
	 */
	public AnnualHolidayDto getAnnualHoliDayDto(String sID, GeneralDate baseDate) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();

		String cId = AppContexts.user().companyId();
		AnnualHolidayDto result = new AnnualHolidayDto();

		// Step 10-1.年休の設定を取得する
		AnnualHolidaySetOutput annualHd = AbsenceTenProcess.getSettingForAnnualHoliday(require, cId);
		// Step 取得した年休管理区分　＝＝　false and 取得した時間年休管理区分　＝＝　false
		if (!annualHd.isYearHolidayManagerFlg() && !annualHd.isSuspensionTimeYearFlg()) {
			result.setAnnualLeaveGrant(new ArrayList<>());
			result.setEmployees(new ArrayList<>());
			result.setAnnualLeaveManagementFg(false);
			return result;
		}
		result.setAnnualLeaveManagementFg(true);
		// Step No.210次回年休付与日を取得する
		result.setAnnualLeaveGrant(holidayAdapter.acquireNextHolidayGrantDate(cId, sID, baseDate));
		// Step No.323年休出勤率と労働日数を計算する
		holidayAdapter.getDaysPerYear(cId, sID).ifPresent(x -> result.setAttendNextHoliday(x));
		// Step 社員に対応する締め期間を取得する
		DatePeriod closingPeriod = ClosureService.findClosurePeriod(require, cacheCarrier, sID, baseDate);
		// Step No.198 基準日時点の年休残数を取得する
		ReNumAnnLeaReferenceDateImport reNumAnnLeave = leaveAdapter.getReferDateAnnualLeaveRemainNumber(sID, baseDate);
		// Step 「次回年休付与」を作成
		ReNumAnnLeaReferenceDateDto reNumAnnLeaveDto = ReNumAnnLeaReferenceDateDto.builder()
			.annualLeaveGrantExports(reNumAnnLeave.getAnnualLeaveGrantExports().stream()
				.map((item) -> {
					return AnnualLeaveGrantDto.builder()
						.daysUsedNo(item.getDaysUsedNo())
						.deadline(item.getDeadline()).grantDate(item.getGrantDate())
						.grantNumber(item.getGrantNumber()).remainDays(item.getRemainDays())
						.remainMinutes(item.getRemainMinutes())
						.expiredInCurrentMonthFg(closingPeriod == null ? false : closingPeriod.end().afterOrEquals(item.getDeadline()))
						.build();
				})
				.collect(Collectors.toList()))
			.build();
		reNumAnnLeaveDto.setAnnualLeaveManageInforExports(reNumAnnLeave.getAnnualLeaveManageInforExports());
		reNumAnnLeaveDto.setAnnualLeaveRemainNumberExport(reNumAnnLeave.getAnnualLeaveRemainNumberExport());
		result.setReNumAnnLeave(reNumAnnLeaveDto);
		return result;

	}
}