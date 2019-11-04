package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.applimitset.AppLimitSetting;

/**
 * 月別確認済みかのチェックする
 *
 */
public interface MonthActualConfirmDoneCheck {
	public boolean check(AppLimitSetting appLimitSetting, String companyID, String employeeID, GeneralDate appDate);
}