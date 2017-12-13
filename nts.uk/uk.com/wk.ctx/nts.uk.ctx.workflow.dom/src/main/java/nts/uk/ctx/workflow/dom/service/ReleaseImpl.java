package nts.uk.ctx.workflow.dom.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalFrame;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class ReleaseImpl implements ReleaseService {
	
	@Inject
	private ApprovalRootStateRepository approvalRootStateRepository;
	
	@Inject
	private JudgmentApprovalStatusService judgmentApprovalStatusService;

	@Override
	public void doRelease(String companyID, String rootStateID) {
		Optional<ApprovalRootState> opApprovalRootState = approvalRootStateRepository.findEmploymentApp(companyID, rootStateID);
		if(!opApprovalRootState.isPresent()){
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootState approvalRootState = opApprovalRootState.get();
		approvalRootState.getListApprovalPhaseState().stream().forEach(approvalPhaseState -> {
			List<String> approvers = judgmentApprovalStatusService.getApproverFromPhase(approvalPhaseState);
			if(CollectionUtil.isEmpty(approvers)){
				return;
			}
			Boolean phaseNotApprovalFlag = approvalPhaseState.getApprovalAtr().equals(ApprovalAtr.UNAPPROVED);
			for(ApprovalFrame approvalFrame : approvalPhaseState.getListApprovalFrame()){
				phaseNotApprovalFlag = Boolean.logicalAnd(phaseNotApprovalFlag, approvalFrame.getApprovalAtr().equals(ApprovalAtr.UNAPPROVED));
			}
			if(phaseNotApprovalFlag.equals(Boolean.TRUE)){
				return;
			}
			approvalPhaseState.getListApprovalFrame().forEach(approvalFrame -> {
				approvalFrame.setApprovalAtr(ApprovalAtr.UNAPPROVED);
				approvalFrame.setApproverID("");
				approvalFrame.setRepresenterID("");
			});
			approvalPhaseState.setApprovalAtr(ApprovalAtr.UNAPPROVED);
		});
		approvalRootStateRepository.update(approvalRootState);
	}

}
