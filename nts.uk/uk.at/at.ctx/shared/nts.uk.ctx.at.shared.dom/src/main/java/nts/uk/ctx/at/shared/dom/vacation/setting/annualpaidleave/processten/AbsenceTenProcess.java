package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten;

import nts.arc.time.GeneralDate;

public interface AbsenceTenProcess {
	/**
	 * 10-1.年休の設定を取得する
	 * @param companyID
	 * @return
	 */
	public AnnualHolidaySetOutput getSettingForAnnualHoliday(String companyID);
	/**
	 * 10-2.代休の設定を取得する
	 * @param companyID
	 * @param employeeID
	 * @param baseDate
	 * @return
	 */
	public SubstitutionHolidayOutput getSettingForSubstituteHoliday(String companyID, String employeeID,
			GeneralDate baseDate);
}
