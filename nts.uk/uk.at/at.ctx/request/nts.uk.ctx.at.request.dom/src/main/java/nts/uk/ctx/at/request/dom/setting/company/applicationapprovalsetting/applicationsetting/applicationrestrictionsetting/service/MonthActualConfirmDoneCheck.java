package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service;

import nts.arc.time.GeneralDate;

/**
 * 月別確認済みかのチェックする
 *
 */
/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
public interface MonthActualConfirmDoneCheck {
	/**
	 * 
	 * @param canAppAchievementMonthConfirm
	 * @param companyID
	 * @param employeeID
	 * @param appDate
	 * @return
	 */
	public boolean check(boolean overtimeCheck, boolean canAppAchievementMonthConfirm, String companyID, String employeeID, GeneralDate appDate);
}