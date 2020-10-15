package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SEmpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.workfixed.WorkFixedAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;

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
	
	@Inject
	private EmployeeRequestAdapter employeeAdaptor;
	
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;

	@Override
	public boolean check(boolean canAppFinishWork, String companyID, String employeeID, GeneralDate appDate) {
		// INPUT．就業確定済の場合申請できないをチェックする
		if (!canAppFinishWork) {
			GeneralDate systemDate = GeneralDate.today();
			WkpHistImport wkpHistImport = workplaceAdapter.findWkpBySid(employeeID, systemDate);
			if(wkpHistImport == null){
				throw new RuntimeException("Not found workplaceID");
			}
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
			// 「Imported(申請承認)「実績確定状態」．所属職場の就業確定区分をチェックする
			if (this.workFixedAdater.checkWkpConfirmedToWork(companyID, appDate, wkpHistImport.getWorkplaceId(),
					closureEmployment.get().getClosureId())) {
				return true;
			}
		}
		return false;
	}
}
