package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeAnnualRoundProcesCla;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;

public class AnnualPaidLeaveSettingHelper {
	public static ManageAnnualSetting createManageAnnualSetting() {
		HalfDayManage halfDayManage = new HalfDayManage(ManageDistinct.NO, MaxDayReference.CompanyUniform, 
				new AnnualNumberDay(1), RoundProcessingClassification.FractionManagementNo);
		return new ManageAnnualSetting(halfDayManage, null, null);
	}
	
	public static TimeAnnualSetting createTimeAnnualSetting() {
		TimeAnnualMaxDay annualMaxDay = AnnualPaidLeaveSettingHelper.createTimeAnnualMaxDay();
		return new TimeAnnualSetting(annualMaxDay, 
				TimeAnnualRoundProcesCla.RoundUpToTheDay, null,
				new TimeVacationDigestUnit(ManageDistinct.NO, TimeDigestiveUnit.OneHour));
	}
	
	public static TimeAnnualMaxDay createTimeAnnualMaxDay() {
		return new TimeAnnualMaxDay(ManageDistinct.NO, MaxDayReference.CompanyUniform, new MaxTimeDay(1));
	}
	
	public static AnnualPaidLeaveSetting createAnnualPaidLeaveSetting(){
		AcquisitionSetting acquisitionSetting = new AcquisitionSetting(AnnualPriority.FIFO);
		ManageAnnualSetting annualSetting = AnnualPaidLeaveSettingHelper.createManageAnnualSetting();
		TimeAnnualSetting timeSetting = AnnualPaidLeaveSettingHelper.createTimeAnnualSetting();
		AnnualPaidLeaveSetting leaveSetting = new AnnualPaidLeaveSetting("000000000003-0004", acquisitionSetting, 
				ManageDistinct.NO, annualSetting, timeSetting);
		return leaveSetting;
	}
	
	public static AnnualPaidLeaveSetting createAnnualPaidLeaveSetting(ManageDistinct manageDistinct){
		AcquisitionSetting acquisitionSetting = new AcquisitionSetting(AnnualPriority.FIFO);
		ManageAnnualSetting annualSetting = AnnualPaidLeaveSettingHelper.createManageAnnualSetting();
		TimeAnnualSetting timeSetting = AnnualPaidLeaveSettingHelper.createTimeAnnualSetting();
		AnnualPaidLeaveSetting leaveSetting = new AnnualPaidLeaveSetting("000000000003-0004", acquisitionSetting, 
				manageDistinct, annualSetting, timeSetting);
		return leaveSetting;
	}
	
	public static AnnualPaidLeaveSetting createAnnualPaidLeaveSetting(TimeAnnualSetting timeSetting, ManageDistinct manageDistinct){
		AcquisitionSetting acquisitionSetting = new AcquisitionSetting(AnnualPriority.FIFO);
		AnnualPaidLeaveSetting leaveSetting = new AnnualPaidLeaveSetting("000000000003-0004", acquisitionSetting, 
				manageDistinct, AnnualPaidLeaveSettingHelper.createManageAnnualSetting(), timeSetting);
		return leaveSetting;
	}
	
	public static AnnualPaidLeaveSetting createAnnualPaidLeaveSetting(TimeAnnualSetting timeSetting, ManageAnnualSetting annualSetting, ManageDistinct manageDistinct){
		AcquisitionSetting acquisitionSetting = new AcquisitionSetting(AnnualPriority.FIFO);
		AnnualPaidLeaveSetting leaveSetting = new AnnualPaidLeaveSetting("000000000003-0004", acquisitionSetting, 
				manageDistinct, annualSetting, timeSetting);
		return leaveSetting;
	}
	
	public static TimeAnnualSetting createTimeAnnualSetting(TimeAnnualMaxDay annualMaxDay, ManageDistinct timeManageType) {
		return new TimeAnnualSetting(annualMaxDay, 
				TimeAnnualRoundProcesCla.RoundUpToTheDay, null,
				new TimeVacationDigestUnit(timeManageType, TimeDigestiveUnit.OneHour));
	}
	
	public static TimeAnnualMaxDay createTimeAnnualMaxDay(ManageDistinct manageType) {
		return new TimeAnnualMaxDay(manageType, MaxDayReference.CompanyUniform, new MaxTimeDay(1));
	}
	
	public static HalfDayManage createHalfDayManage(ManageDistinct manageType) {
		HalfDayManage halfDayManage = new HalfDayManage(manageType, MaxDayReference.CompanyUniform, 
				new AnnualNumberDay(1), RoundProcessingClassification.FractionManagementNo);
		return halfDayManage;
	}
	
	public static ManageAnnualSetting createManageAnnualSetting(HalfDayManage halfDayManage) {
		return new ManageAnnualSetting(halfDayManage, null, null);
	}
}
