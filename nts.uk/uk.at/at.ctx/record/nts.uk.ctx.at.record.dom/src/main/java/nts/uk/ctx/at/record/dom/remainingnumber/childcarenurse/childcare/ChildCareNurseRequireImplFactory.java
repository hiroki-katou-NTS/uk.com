package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;

@Stateless
public class ChildCareNurseRequireImplFactory {

	@Inject
	private WorkingConditionItemRepository workingConditionItemRepo;
	@Inject
	private AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepo;
	@Inject
	private ClosureStatusManagementRepository closureStatusManagementRepo;
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;
	@Inject
	private ClosureRepository ClosureRepo;
	@Inject
	private EmpEmployeeAdapter empEmployeeAdapter;
	@Inject
	private NursingLeaveSettingRepository nursingLeaveSettingRepo;
	@Inject
	private ChildCareUsedNumberRepository childCareUsedNumberRepository;
	@Inject
	private CareUsedNumberRepository careUsedNumberRepository;

	public ChildCareNurseRequireImpl createRequireImpl() {
		return new ChildCareNurseRequireImpl(
				workingConditionItemRepo,
				annualPaidLeaveSettingRepo,
				closureStatusManagementRepo,
				closureEmploymentRepo,
				shareEmploymentAdapter,
				ClosureRepo,
				empEmployeeAdapter,
				nursingLeaveSettingRepo,
				childCareUsedNumberRepository,
				careUsedNumberRepository
				);
	}

}
