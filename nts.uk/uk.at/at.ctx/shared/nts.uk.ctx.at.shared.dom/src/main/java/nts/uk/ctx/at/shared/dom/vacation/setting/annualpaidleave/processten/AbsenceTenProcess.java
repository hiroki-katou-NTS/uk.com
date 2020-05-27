package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcessImpl.Require;

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
	public SubstitutionHolidayOutput getSettingForSubstituteHolidayRequire(Require require, CacheCarrier cacheCarrier,String companyID, String employeeID,
			GeneralDate baseDate);
	/**
	 * @author hoatt
	 * 10-3.振休の設定を取得する
	 * @param companyID
	 * @param employeeID
	 * @param baseDate
	 * @return
	 */
	public LeaveSetOutput getSetForLeave(String companyID, String employeeID, GeneralDate baseDate);
	public LeaveSetOutput getSetForLeaveRequire(Require require, String companyID, String employeeID, GeneralDate baseDate);
	/**
	 * @author hoatt
	 * 10-4.積立年休の設定を取得する
	 * @param companyID
	 * @param employeeID
	 * @param baseDate
	 * @return
	 */
	public boolean getSetForYearlyReserved(String companyID, String employeeID, GeneralDate baseDate);
	
}
