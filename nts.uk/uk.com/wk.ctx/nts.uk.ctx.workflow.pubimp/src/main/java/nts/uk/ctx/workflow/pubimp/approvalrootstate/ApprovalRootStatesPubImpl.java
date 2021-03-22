package nts.uk.ctx.workflow.pubimp.approvalrootstate;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalFrame;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApproverInfor;
import nts.uk.ctx.workflow.pub.approvalrootstate.ApprovalPhaseStateExport;
import nts.uk.ctx.workflow.pub.approvalrootstate.ApprovalRootStateExport;
import nts.uk.ctx.workflow.pub.approvalrootstate.ApprovalRootStatePub;
import nts.uk.ctx.workflow.pub.approvalrootstate.ApproverStateExport;

@Stateless
public class ApprovalRootStatesPubImpl implements ApprovalRootStatePub {
	
	@Inject
	private ApprovalRootStateRepository approvalRootStateRepo;

	@Override
	public List<ApprovalRootStateExport> findByEmployeesAndPeriod(List<String> empIds, DatePeriod period,
			int rootType) {
		return approvalRootStateRepo.findAppByListEmployeeIDRecordDate(period.start(), period.end(), empIds, rootType)
			.stream().map(a -> this.toExport(a)).collect(Collectors.toList());
	}

	@Override
	public List<ApprovalRootStateExport> findByAgentApproverAndPeriod(String companyId, List<String> approverIds,
			DatePeriod period) {
		return approvalRootStateRepo.findByApproverAndPeriod(companyId, period.start(), period.end(), approverIds)
				.stream().map(a -> this.toExport(a)).collect(Collectors.toList());
	}
	
	private ApprovalRootStateExport toExport(ApprovalRootState root) {
		List<ApprovalPhaseStateExport> appPhases = root.getListApprovalPhaseState().stream().map(p -> 
				new ApprovalPhaseStateExport(p.getPhaseOrder(), p.getApprovalAtr().value))
			.collect(Collectors.toList());
		return new ApprovalRootStateExport(root.getRootStateID(), root.getApprovalRecordDate(), 
				root.getEmployeeID(), appPhases);
	}

	@Override
	public List<ApproverStateExport> findApprovalRootStateIds(String companyId, List<String> approverIds, DatePeriod period) {
		return approvalRootStateRepo.findApprovalRootStateIds(companyId, approverIds, period.start(), period.end())
					.stream().map(a -> {
						ApproverStateExport export = new ApproverStateExport();
						export.setRootStateId(a.getRootStateID());
						List<ApprovalPhaseState> phaseList = a.getListApprovalPhaseState();
						
						if (!phaseList.isEmpty()) {
							ApprovalPhaseState phase = phaseList.get(0);
							List<ApprovalFrame> frameList = phase.getListApprovalFrame();
							if (!frameList.isEmpty()) {
								ApprovalFrame frame = frameList.get(0);
								export.setAppDate(frame.getAppDate());
								List<ApproverInfor> approverInforList = frame.getLstApproverInfo();
								if (!approverInforList.isEmpty()) {
									export.setApproverId(approverInforList.get(0).getApproverID());
								}
							}
						}
						
						return export;
					}).collect(Collectors.toList());
	}

}
