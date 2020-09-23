package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SEmpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.actuallock.ActualLockAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.actuallock.ActualLockImport;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.PeriodCurrentMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;

/**
 * 実績ロック中かチェックする
 *
 */
@Stateless
public class ActualLockingCheckImpl implements ActualLockingCheck {

	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithmService;
	
	@Inject
	private EmployeeRequestAdapter employeeAdaptor;
	
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;
	
	@Inject
	private ActualLockAdapter actualLockAdapter;

	@Override
	public boolean check(boolean canAppAchievementLock, String companyID, String employeeID, GeneralDate appDate) {
		// INPUT．実績修正がロック状態なら申請できないをチェックする
		if (!canAppAchievementLock) {
			// アルゴリズム「社員の当月の期間を算出する」を実行する(thực hiện xử lý 「社員の当月の期間を算出する」)
			PeriodCurrentMonth periodCurrentMonth = this.otherCommonAlgorithmService
					.employeePeriodCurrentMonthCalculate(companyID, employeeID, appDate);
			// 当月の申請かチェックする(check xem có phải tháng hiện tại hay không)
			if (appDate.afterOrEquals(periodCurrentMonth.getStartDate())
					&& appDate.beforeOrEquals(periodCurrentMonth.getEndDate())) {
				SEmpHistImport empHistImport = employeeAdaptor.getEmpHist(companyID,
						employeeID, appDate);
				if (empHistImport == null || empHistImport.getEmploymentCode() == null) {
					throw new BusinessException("Msg_426");
				}
				String employmentCD = empHistImport.getEmploymentCode();
				Optional<ClosureEmployment> closureEmployment = closureEmploymentRepository
						.findByEmploymentCD(companyID, employmentCD);
				if (!closureEmployment.isPresent()) {
					throw new RuntimeException(
							"Not found ClosureEmployment in table KCLMT_CLOSURE_EMPLOYMENT, employment =" + employmentCD);
				}
				Optional<ActualLockImport> actualLockImport = actualLockAdapter.findByID(companyID,
						closureEmployment.get().getClosureId());
				if (!actualLockImport.isPresent()) {
					return false;
				}
				// 「Imported(申請承認)「実績確定状態」．日別実績のロックと月別実績のロックをチェックする(check thông tin 「Imported(申請承認)「実績確定状態」．日別実績のロックと月別実績のロック)
				if (actualLockImport.get().getDailyLockState() == 1
						|| actualLockImport.get().getMonthlyLockState() == 1) {
					return true;
				}
			}
		}
		return false;
	}
}
