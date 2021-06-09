package nts.uk.ctx.workflow.dom.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalComment;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
import nts.uk.ctx.workflow.dom.service.output.ApproverApprovedOutput;
import nts.uk.ctx.workflow.dom.service.output.ApproverWithFlagOutput;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class ReleaseAllAtOnceImpl implements ReleaseAllAtOnceService {
	
	@Inject
	private ApprovalRootStateRepository approvalRootStateRepository;
	
	@Inject
	private JudgmentApprovalStatusService judgmentApprovalStatusService;

	@Override
	public void doReleaseAllAtOnce(String companyID, String rootStateID, Integer rootType) {
		Optional<ApprovalRootState> opApprovalRootState = approvalRootStateRepository.findByID(rootStateID, rootType);
		if(!opApprovalRootState.isPresent()){
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootState approvalRootState = opApprovalRootState.get();
		approvalRootState.getListApprovalPhaseState().sort(Comparator.comparing(ApprovalPhaseState::getPhaseOrder).reversed());
		approvalRootState.getListApprovalPhaseState().stream().forEach(approvalPhaseState -> {
			approvalPhaseState.getListApprovalFrame().forEach(approvalFrame -> {
				approvalFrame.getLstApproverInfo().forEach(approverInfor -> {
					approverInfor.setApprovalAtr(ApprovalBehaviorAtr.UNAPPROVED);
					approverInfor.setAgentID("");
					approverInfor.setApprovalDate(null);
					approverInfor.setApprovalReason(new ApprovalComment(""));
				});
			});
			approvalPhaseState.setApprovalAtr(ApprovalBehaviorAtr.UNAPPROVED);
		});
		approvalRootStateRepository.update(approvalRootState, rootType);
	}

	@Override
	public ApproverApprovedOutput getApproverApproved(String rootStateID, Integer rootType) {
		List<ApproverWithFlagOutput> listApproverWithFlagOutput = new ArrayList<>();
		List<String> listApprover = new ArrayList<>();
		Optional<ApprovalRootState> opApprovalRootState = approvalRootStateRepository.findByID(rootStateID, rootType);
		if(!opApprovalRootState.isPresent()){
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootState approvalRootState = opApprovalRootState.get();
		for(ApprovalPhaseState approvalPhaseState : approvalRootState.getListApprovalPhaseState()){
			List<String> approvers = judgmentApprovalStatusService.getApproverFromPhase(approvalPhaseState);
			if(CollectionUtil.isEmpty(approvers)){
				continue;
			}
			approvalPhaseState.getListApprovalFrame().forEach(approvalFrame -> {
				approvalFrame.getLstApproverInfo().forEach(approverInfor -> {
					if(approverInfor.getApprovalAtr().equals(ApprovalBehaviorAtr.UNAPPROVED)){
						return;
					}
					if(Strings.isBlank(approverInfor.getAgentID())){
						listApproverWithFlagOutput.add(new ApproverWithFlagOutput(approverInfor.getApproverID(), false));
					} else {
						listApproverWithFlagOutput.add(new ApproverWithFlagOutput(approverInfor.getAgentID(), true));
					}
					listApprover.addAll(approvalFrame.getLstApproverInfo().stream().map(x -> x.getApproverID()).collect(Collectors.toList()));
				});
			});
			break;
		}
		return new ApproverApprovedOutput(listApproverWithFlagOutput, listApprover);
	}

}
