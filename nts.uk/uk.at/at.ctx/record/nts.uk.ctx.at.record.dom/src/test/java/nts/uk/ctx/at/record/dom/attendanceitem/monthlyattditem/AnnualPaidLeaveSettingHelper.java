package nts.uk.ctx.at.record.dom.attendanceitem.monthlyattditem;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeAnnualRoundProcesCla;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AcquisitionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualNumberDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPriority;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.HalfDayManage;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.MaxDayReference;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.MaxTimeDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.RoundProcessingClassification;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.TimeAnnualMaxDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.TimeAnnualSetting;

public class AnnualPaidLeaveSettingHelper {
	public static ManageAnnualSetting createManageAnnualSetting() {
		HalfDayManage halfDayManage = HalfDayManage.builder()
				.manageType(ManageDistinct.NO)
				.reference(MaxDayReference.CompanyUniform)
				.maxNumberUniformCompany(new AnnualNumberDay(1))
				.roundProcesCla(RoundProcessingClassification.FractionManagementNo)
				.build();
		return new ManageAnnualSetting(halfDayManage, null, null);
	}
	
	public static TimeAnnualSetting createTimeAnnualSetting() {
		TimeAnnualMaxDay annualMaxDay = AnnualPaidLeaveSettingHelper.createTimeAnnualMaxDay();
		return new TimeAnnualSetting(annualMaxDay, 
				TimeAnnualRoundProcesCla.TruncateOnDay0, null,
				new TimeVacationDigestUnit(ManageDistinct.NO, TimeDigestiveUnit.OneHour));
	}
	
	public static TimeAnnualMaxDay createTimeAnnualMaxDay() {
		return new TimeAnnualMaxDay(ManageDistinct.NO, MaxDayReference.CompanyUniform, new MaxTimeDay(1));
	}
	
	public static AnnualPaidLeaveSetting createAnnualPaidLeaveSetting(){
		AcquisitionSetting acquisitionSetting = AcquisitionSetting.builder().annualPriority(AnnualPriority.FIFO).build();
		ManageAnnualSetting annualSetting = AnnualPaidLeaveSettingHelper.createManageAnnualSetting();
		TimeAnnualSetting timeSetting = AnnualPaidLeaveSettingHelper.createTimeAnnualSetting();
		AnnualPaidLeaveSetting leaveSetting = new AnnualPaidLeaveSetting("000000000003-0004", acquisitionSetting, 
				ManageDistinct.NO, annualSetting, timeSetting);
		return leaveSetting;
	}
	
	public static AnnualPaidLeaveSetting createAnnualPaidLeaveSetting(ManageDistinct manageDistinct){
		AcquisitionSetting acquisitionSetting = AcquisitionSetting.builder().annualPriority(AnnualPriority.FIFO).build();
		ManageAnnualSetting annualSetting = AnnualPaidLeaveSettingHelper.createManageAnnualSetting();
		TimeAnnualSetting timeSetting = AnnualPaidLeaveSettingHelper.createTimeAnnualSetting();
		AnnualPaidLeaveSetting leaveSetting = new AnnualPaidLeaveSetting("000000000003-0004", acquisitionSetting, 
				manageDistinct, annualSetting, timeSetting);
		return leaveSetting;
	}
	
	public static AnnualPaidLeaveSetting createAnnualPaidLeaveSetting(TimeAnnualSetting timeSetting, ManageDistinct manageDistinct){
		AcquisitionSetting acquisitionSetting = AcquisitionSetting.builder().annualPriority(AnnualPriority.FIFO).build();
		AnnualPaidLeaveSetting leaveSetting = new AnnualPaidLeaveSetting("000000000003-0004", acquisitionSetting, 
				manageDistinct, AnnualPaidLeaveSettingHelper.createManageAnnualSetting(), timeSetting);
		return leaveSetting;
	}
	
	public static AnnualPaidLeaveSetting createAnnualPaidLeaveSetting(TimeAnnualSetting timeSetting, ManageAnnualSetting annualSetting, ManageDistinct manageDistinct){
		AcquisitionSetting acquisitionSetting = AcquisitionSetting.builder().annualPriority(AnnualPriority.FIFO).build();
		AnnualPaidLeaveSetting leaveSetting = new AnnualPaidLeaveSetting("000000000003-0004", acquisitionSetting, 
				manageDistinct, annualSetting, timeSetting);
		return leaveSetting;
	}
	
	public static TimeAnnualSetting createTimeAnnualSetting(TimeAnnualMaxDay annualMaxDay, ManageDistinct timeManageType) {
		return new TimeAnnualSetting(annualMaxDay, 
				TimeAnnualRoundProcesCla.TruncateOnDay0, null,
				new TimeVacationDigestUnit(timeManageType, TimeDigestiveUnit.OneHour));
	}
	
	public static TimeAnnualMaxDay createTimeAnnualMaxDay(ManageDistinct manageType) {
		return new TimeAnnualMaxDay(manageType, MaxDayReference.CompanyUniform, new MaxTimeDay(1));
	}
	
	public static HalfDayManage createHalfDayManage(ManageDistinct manageType) {
		HalfDayManage halfDayManage = HalfDayManage.builder()
				.manageType(manageType)
				.reference(MaxDayReference.CompanyUniform)
				.maxNumberUniformCompany(new AnnualNumberDay(1))
				.roundProcesCla(RoundProcessingClassification.FractionManagementNo)
				.build();
		return halfDayManage;
	}
	
	public static ManageAnnualSetting createManageAnnualSetting(HalfDayManage halfDayManage) {
		return new ManageAnnualSetting(halfDayManage, null, null);
	}
}
