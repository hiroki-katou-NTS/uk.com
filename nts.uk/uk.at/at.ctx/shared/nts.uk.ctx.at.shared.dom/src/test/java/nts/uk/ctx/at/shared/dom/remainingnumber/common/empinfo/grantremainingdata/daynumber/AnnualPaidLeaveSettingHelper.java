package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeAnnualRoundProcesCla;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AcquisitionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPriority;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ContractTimeRound;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.DayTimeAnnualLeave;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.HalfDayManage;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.MaxDayReference;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.MaxTimeDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.RemainingNumberSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.RetentionYear;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.TimeAnnualLeaveTimeDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.TimeAnnualMaxDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.TimeAnnualSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.YearLyOfNumberDays;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;

public class AnnualPaidLeaveSettingHelper {
	
	public static AnnualPaidLeaveSetting annualPaidLeaveSetting(String companyId){
	return new AnnualPaidLeaveSetting(
			companyId,
			AcquisitionSetting.builder().annualPriority(AnnualPriority.FIFO).build(),
			ManageDistinct.YES,
			new ManageAnnualSetting(
					HalfDayManage.builder().manageType(ManageDistinct.NO).build(),
					RemainingNumberSetting.builder().retentionYear(new RetentionYear(2)).build(),
					new YearLyOfNumberDays(200.0)),
			new TimeAnnualSetting(
					new TimeAnnualMaxDay(ManageDistinct.NO, MaxDayReference.CompanyUniform, new MaxTimeDay(10)),
					TimeAnnualRoundProcesCla.RoundUpToTheDay,
					new TimeAnnualLeaveTimeDay(
							DayTimeAnnualLeave.Company_wide_Uniform, 
							Optional.of(new LaborContractTime(480)),
							Optional.of(ContractTimeRound.Do_not_round)),
					new TimeVacationDigestUnit(ManageDistinct.YES, TimeDigestiveUnit.OneMinute)
					)
			);
	}
}
