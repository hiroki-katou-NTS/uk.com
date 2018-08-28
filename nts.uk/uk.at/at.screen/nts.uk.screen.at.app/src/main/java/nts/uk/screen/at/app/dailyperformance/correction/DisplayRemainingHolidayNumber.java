package nts.uk.screen.at.app.dailyperformance.correction;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.AnnLeaveRemainNumberAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.ReNumAnnLeaReferenceDateImport;
import nts.uk.ctx.at.shared.app.find.vacation.setting.subst.dto.SubstVacationSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcess;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AnnualHolidaySetOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.LeaveSetOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.SubstitutionHolidayOutput;
import nts.uk.screen.at.app.dailyperformance.correction.dto.Com60HVacationDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.CompensLeaveComDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.HolidayRemainNumberDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ReserveLeaveDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.SubstVacationDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.YearHolidaySettingDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT - 休暇残数を表示する
 *
 */

@Stateless
public class DisplayRemainingHolidayNumber {

	@Inject
	private AbsenceTenProcess absenceProc;

	@Inject
	private AnnLeaveRemainNumberAdapter annLeaveRemainAdapter;

	public YearHolidaySettingDto getAnnualLeaveSetting(String companyId, String employeeId, GeneralDate date) {
		AnnualHolidaySetOutput output = absenceProc.getSettingForAnnualHoliday(companyId);
		if (output.isYearHolidayManagerFlg()) {
			ReNumAnnLeaReferenceDateImport remainNum = annLeaveRemainAdapter
					.getReferDateAnnualLeaveRemainNumber(employeeId, date);
			return new YearHolidaySettingDto(output.isYearHolidayManagerFlg(), output.isSuspensionTimeYearFlg(), 1, 2);
		} else {
			return new YearHolidaySettingDto(false, false, null, null);
		}
	}

	public ReserveLeaveDto getReserveLeaveSetting(String companyId, String employeeId, GeneralDate date) {
		boolean manageAtr = absenceProc.getSetForYearlyReserved(companyId, employeeId, date);
		if (manageAtr) {
			return new ReserveLeaveDto(manageAtr, 4);
		} else 
			return new ReserveLeaveDto(false, null);
	}
	
	public SubstVacationDto getSubsitutionVacationSetting(String companyId, String employeeId, GeneralDate date) {
		LeaveSetOutput output = absenceProc.getSetForLeave(companyId, employeeId, date);
		if (output.isSubManageFlag()) {
			// TODO: call requestlist506
			return new SubstVacationDto(output.isSubManageFlag(), 2);
		}
		return new SubstVacationDto(false, null);
	}

	public CompensLeaveComDto getCompensatoryLeaveSetting(String companyId, String employeeId, GeneralDate date) {
		SubstitutionHolidayOutput output = absenceProc.getSettingForSubstituteHoliday(companyId, employeeId, date);
		if (output != null && output.isSubstitutionFlg()) {
			// TODO: call requestlist505
			return new CompensLeaveComDto(output.isSubstitutionFlg(), output.isTimeOfPeriodFlg(), 1, 2);
		} else {
			return new CompensLeaveComDto(false, false, null, null);
		}
	}

	public Com60HVacationDto getCom60HVacationSetting(String companyId, String employeeId, GeneralDate date) {
		Com60HVacationDto output = new Com60HVacationDto("", false, null, null);
		return output;
	}
	
	public HolidayRemainNumberDto getRemainingHolidayNumber(String employeeId) {
		String companyId = AppContexts.user().companyId();
		GeneralDate baseDate = GeneralDate.today();
		HolidayRemainNumberDto result = new HolidayRemainNumberDto();
		result.setAnnualLeave(this.getAnnualLeaveSetting(companyId, employeeId, baseDate));
		result.setCompensatoryLeave(this.getCompensatoryLeaveSetting(companyId, employeeId, baseDate));
		result.setReserveLeave(this.getReserveLeaveSetting(companyId, employeeId, baseDate));
		result.setSubstitutionLeave(this.getSubsitutionVacationSetting(companyId, employeeId, baseDate));
		result.setCom60HVacation(this.getCom60HVacationSetting(companyId, employeeId, baseDate));
		return result;
	}

}
