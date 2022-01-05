package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

public class HalfDayManageHelper {
	public static HalfDayManage createHalfDayManage(ManageDistinct manageType) {
		return new HalfDayManage(manageType, MaxDayReference.CompanyUniform, 
				new AnnualNumberDay(1), RoundProcessingClassification.FractionManagementNo);
	}
}
