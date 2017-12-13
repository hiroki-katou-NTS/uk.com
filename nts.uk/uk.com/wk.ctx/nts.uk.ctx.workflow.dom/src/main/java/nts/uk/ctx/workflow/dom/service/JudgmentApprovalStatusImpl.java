package nts.uk.ctx.workflow.dom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalFrame;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRepresenterOutput;
import nts.uk.ctx.workflow.dom.service.output.ApproverPersonOutput;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JudgmentApprovalStatusImpl implements JudgmentApprovalStatusService {
	
	@Inject
	private ApprovalRootStateRepository approvalRootStateRepository;
	
	@Inject
	private CollectApprovalAgentInforService collectApprovalAgentInforService;

	@Override
	public Boolean judgmentTargetPersonIsApprover(String companyID, String rootStateID, String employeeID) {
		Boolean approverFlag = false;
		Optional<ApprovalRootState> opApprovalRootState = approvalRootStateRepository.findEmploymentApp(companyID, rootStateID);
		if(!opApprovalRootState.isPresent()){
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootState approvalRootState = opApprovalRootState.get();
		List<String> listApprover = new ArrayList<>();
		approvalRootState.getListApprovalPhaseState().stream().forEach(approvalPhaseState -> {
			List<String> approvers = this.getApproverFromPhase(approvalPhaseState);
			listApprover.addAll(approvers);
		});
		List<String> newListApprover = listApprover.stream().distinct().collect(Collectors.toList());
		if(newListApprover.contains(employeeID)){
			approverFlag = true;
			return approverFlag;
		} 
		ApprovalRepresenterOutput approvalAgentOutput = collectApprovalAgentInforService.getApprovalAgentInfor(companyID, newListApprover);
		if(approvalAgentOutput.getListAgent().contains(employeeID)){
			approverFlag = true;
			return approverFlag;
		}
		for(ApprovalPhaseState approvalPhaseState : approvalRootState.getListApprovalPhaseState()){
			for(ApprovalFrame approvalFrame : approvalPhaseState.getListApprovalFrame()){
				if(approvalFrame.getRepresenterID().equals(employeeID)){
					approverFlag = true;
					break;
				}
			}
			if(approverFlag.equals(Boolean.TRUE)){
				break;
			}
		}
		return approverFlag;
	}

	@Override
	public ApprovalBehaviorAtr judgmentApprovalStatus(String companyID, String rootStateID) {
		ApprovalBehaviorAtr approvalAtr = ApprovalBehaviorAtr.UNAPPROVED;
		Optional<ApprovalRootState> opApprovalRootState = approvalRootStateRepository.findEmploymentApp(companyID, rootStateID);
		if(!opApprovalRootState.isPresent()){
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootState approvalRootState = opApprovalRootState.get();
		for(ApprovalPhaseState approvalPhaseState : approvalRootState.getListApprovalPhaseState()){
			List<String> approvers = this.getApproverFromPhase(approvalPhaseState);
			if(CollectionUtil.isEmpty(approvers)){
				continue;
			}
			if(approvalPhaseState.getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED)){
				approvalAtr = ApprovalBehaviorAtr.APPROVED;
				break;
			}
			/*ループ中の承認フェーズが承認中のフェーズかの判断条件：

			ループ中の承認フェーズより、後ろの承認フェーズには誰も承認を行ってない
			AND
			ループ中の承認フェーズより、前の承認フェーズ全てが承認済
			
			if(ループ中の承認フェーズが承認中のフェーズかチェックする)*/
			approvalAtr = approvalPhaseState.getApprovalAtr();
		}
		return approvalAtr;
	}

	@Override
	public ApproverPersonOutput judgmentTargetPersonCanApprove(String companyID, String rootStateID, String employeeID) {
		Boolean authorFlag = false;
		ApprovalBehaviorAtr approvalAtr = ApprovalBehaviorAtr.UNAPPROVED;
		Boolean expirationAgentFlag = false; 
		Optional<ApprovalRootState> opApprovalRootState = approvalRootStateRepository.findEmploymentApp(companyID, rootStateID);
		if(!opApprovalRootState.isPresent()){
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootState approvalRootState = opApprovalRootState.get();
		for(ApprovalPhaseState approvalPhaseState : approvalRootState.getListApprovalPhaseState()){
			Boolean pastPhaseFlag = false;
			List<String> approvers = this.getApproverFromPhase(approvalPhaseState);
			if(CollectionUtil.isEmpty(approvers)){
				
			}
		}
		return new ApproverPersonOutput(authorFlag, approvalAtr, expirationAgentFlag);
	}

	@Override
	public List<String> getApproverFromPhase(ApprovalPhaseState approvalPhaseState) {
		List<String> listApprover = new ArrayList<>();
		approvalPhaseState.getListApprovalFrame().forEach(approvalFrame -> {
			List<String> approvers = approvalFrame.getListApproverState().stream().map(x -> x.getApproverID()).collect(Collectors.toList());
			listApprover.addAll(approvers);
		});
		List<String> newListApprover = listApprover.stream().distinct().collect(Collectors.toList());
		return newListApprover;
	}

}
