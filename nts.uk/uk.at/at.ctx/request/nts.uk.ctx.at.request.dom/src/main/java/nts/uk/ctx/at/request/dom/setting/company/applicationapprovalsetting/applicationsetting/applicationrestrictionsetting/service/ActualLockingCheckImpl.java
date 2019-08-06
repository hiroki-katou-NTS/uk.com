package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.actuallock.ActualLockImport;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.PeriodCurrentMonth;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.applimitset.AppLimitSetting;

/**
 * 実績ロック中かチェックする
 *
 */
@Stateless
public class ActualLockingCheckImpl implements ActualLockingCheck {

	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithmService;

	@Override
	public boolean check(AppLimitSetting appLimitSetting, String companyID, String employeeID, GeneralDate appDate, Optional<ActualLockImport> actualLockImport) {
		if (!actualLockImport.isPresent()) {
			return false;
		}
		// 「申請制限設定」．実績修正がロック状態なら申請できない)
		if (!appLimitSetting.getCanAppAchievementLock()) {
			// 4.社員の当月の期間を算出する
			PeriodCurrentMonth periodCurrentMonth = this.otherCommonAlgorithmService
					.employeePeriodCurrentMonthCalculate(companyID, employeeID, appDate);
			if (appDate.afterOrEquals(periodCurrentMonth.getStartDate())
					&& appDate.beforeOrEquals(periodCurrentMonth.getEndDate())) {
				if (actualLockImport.get().getDailyLockState() == 1
						|| actualLockImport.get().getMonthlyLockState() == 1) {
					return true;
				}
			}
		}
		return false;
	}
}
