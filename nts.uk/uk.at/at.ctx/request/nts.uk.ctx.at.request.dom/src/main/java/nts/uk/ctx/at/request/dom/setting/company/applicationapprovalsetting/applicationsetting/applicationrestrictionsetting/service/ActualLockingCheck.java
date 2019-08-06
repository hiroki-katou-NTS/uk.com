package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.actuallock.ActualLockImport;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.applimitset.AppLimitSetting;

/**
 * 実績ロック中かチェックする
 *
 */
public interface ActualLockingCheck {
	public boolean check(AppLimitSetting appLimitSetting, String companyID, String employeeID, GeneralDate appDate, Optional<ActualLockImport> actualLockImport);
}