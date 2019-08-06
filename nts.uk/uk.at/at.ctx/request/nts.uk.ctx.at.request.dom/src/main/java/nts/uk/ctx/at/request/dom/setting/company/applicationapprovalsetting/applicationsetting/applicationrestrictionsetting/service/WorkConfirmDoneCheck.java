package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.applimitset.AppLimitSetting;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;

/**
 * 就業確定済みかのチェック
 *
 */
public interface WorkConfirmDoneCheck {
	public boolean check(AppLimitSetting appLimitSetting, String companyID, String employeeID, GeneralDate appDate, Optional<ClosureEmployment> closureEmployment);
}