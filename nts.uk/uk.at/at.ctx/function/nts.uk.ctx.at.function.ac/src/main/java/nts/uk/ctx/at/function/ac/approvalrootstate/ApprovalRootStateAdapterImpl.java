package nts.uk.ctx.at.function.ac.approvalrootstate;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.approvalrootstate.ApprovalPhaseStateImport;
import nts.uk.ctx.at.function.dom.adapter.approvalrootstate.ApprovalRootStateAdapter;
import nts.uk.ctx.at.function.dom.adapter.approvalrootstate.ApprovalRootStateImport;
import nts.uk.ctx.at.function.dom.adapter.approvalrootstate.ApproverStateImport;
import nts.uk.ctx.workflow.pub.approvalrootstate.ApprovalRootStateExport;
import nts.uk.ctx.workflow.pub.approvalrootstate.ApprovalRootStatePub;

@Stateless
public class ApprovalRootStateAdapterImpl implements ApprovalRootStateAdapter {

	@Inject
	private ApprovalRootStatePub approvalRootStatePub;
	
	@Override
	public List<ApprovalRootStateImport> findByEmployeesAndPeriod(List<String> empIds, DatePeriod period,
			int rootType) {
		return approvalRootStatePub.findByEmployeesAndPeriod(empIds, period, rootType)
			.stream().map(a -> this.toImport(a)).collect(Collectors.toList());
	}

	@Override
	public List<ApprovalRootStateImport> findByAgentApproverAndPeriod(String companyId, List<String> approvers,
			DatePeriod period) {
		return approvalRootStatePub.findByAgentApproverAndPeriod(companyId, approvers, period)
			.stream().map(a -> this.toImport(a)).collect(Collectors.toList());
	}
	
	private ApprovalRootStateImport toImport(ApprovalRootStateExport export) {
		List<ApprovalPhaseStateImport> appPhases = export.getListApprovalPhaseState().stream().map(p -> 
				new ApprovalPhaseStateImport(p.getPhaseOrder(), p.getApprovalAtr()))
			.collect(Collectors.toList());
		return new ApprovalRootStateImport(export.getRootStateID(), export.getApprovalRecordDate(), 
				export.getEmployeeID(), appPhases);
	}

	@Override
	public List<ApproverStateImport> findApprovalRootStateIds(String companyId, List<String> approverIds, DatePeriod period) {
		return approvalRootStatePub.findApprovalRootStateIds(companyId, approverIds, period)
				.stream().map(a -> new ApproverStateImport(a.getRootStateId(), a.getApproverId(), a.getAppDate()))
				.collect(Collectors.toList());
	}

}
