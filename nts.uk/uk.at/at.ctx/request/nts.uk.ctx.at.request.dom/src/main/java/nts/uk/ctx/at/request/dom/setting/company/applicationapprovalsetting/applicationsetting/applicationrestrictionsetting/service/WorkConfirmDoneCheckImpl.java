package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;

import nts.uk.ctx.at.request.dom.application.common.adapter.record.workfixed.WorkFixedAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.applimitset.AppLimitSetting;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;

/**
 * 就業確定済みかのチェック
 *
 */
@Stateless
public class WorkConfirmDoneCheckImpl implements WorkConfirmDoneCheck {

	@Inject
	private WorkFixedAdapter workFixedAdater;

	@Inject
	private WorkplaceAdapter workplaceAdapter;

	@Override
	public boolean check(AppLimitSetting appLimitSetting, String companyID, String employeeID, GeneralDate appDate, Optional<ClosureEmployment> closureEmployment) {
		if (!appLimitSetting.getCanAppFinishWork()) {
			GeneralDate systemDate = GeneralDate.today();
			WkpHistImport wkpHistImport = workplaceAdapter.findWkpBySid(employeeID, systemDate);
			if(wkpHistImport == null){
				throw new RuntimeException("Not found workplaceID");
			}
			// 「Imported(申請承認)「実績確定状態」．所属職場の就業確定区分をチェックする(check
			// 「Imported(申請承認)「実績確定状態」．所属職場の就業確定区分)
			if (this.workFixedAdater.getEmploymentFixedStatus(companyID, appDate, wkpHistImport.getWorkplaceId(),
					closureEmployment.get().getClosureId())) {
				return true;
			}
		}
		return false;
	}
}
