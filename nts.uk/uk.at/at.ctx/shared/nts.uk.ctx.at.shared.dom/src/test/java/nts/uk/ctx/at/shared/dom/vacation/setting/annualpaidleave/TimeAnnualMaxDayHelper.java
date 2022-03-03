package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

public class TimeAnnualMaxDayHelper {
	public static TimeAnnualMaxDay createTimeAnnualMaxDay() {
		return new TimeAnnualMaxDay(ManageDistinct.YES, MaxDayReference.CompanyUniform, new MaxTimeDay(1));
	}
	
	public static TimeAnnualMaxDay createTimeAnnualMaxDay_ManageDistinct_NO(ManageDistinct manageDistinct) {
		return new TimeAnnualMaxDay(manageDistinct, MaxDayReference.CompanyUniform, new MaxTimeDay(1));
	}
	
	public static TimeAnnualMaxDay createTimeAnnualMaxDay_ManageDistinct_YES(ManageDistinct manageDistinct) {
		return new TimeAnnualMaxDay(manageDistinct, MaxDayReference.CompanyUniform, new MaxTimeDay(1));
	}
	
	public static TimeAnnualMaxDay createTimeAnnualMaxDay_ManageDistinct_YES_CompanyUniform(
			ManageDistinct manageDistinct, MaxDayReference dayReference, MaxTimeDay maxNumberUniformCompany) {
		return new TimeAnnualMaxDay(manageDistinct, dayReference, maxNumberUniformCompany);
	}
}
