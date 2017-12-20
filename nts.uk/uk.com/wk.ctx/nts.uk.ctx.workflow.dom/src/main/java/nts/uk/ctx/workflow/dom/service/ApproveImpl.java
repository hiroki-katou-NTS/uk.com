package nts.uk.ctx.workflow.dom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalForm;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalFrame;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRepresenterInforOutput;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRepresenterOutput;
import nts.uk.ctx.workflow.dom.service.output.RepresenterInforOutput;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class ApproveImpl implements ApproveService {
	
	@Inject
	private ApprovalRootStateRepository approvalRootStateRepository;
	
	@Inject
	private JudgmentApprovalStatusService judgmentApprovalStatusService;
	
	@Inject
	private CollectApprovalAgentInforService collectApprovalAgentInforService;
	
	@Override
	public Integer doApprove(String companyID, String rootStateID, String employeeID) {
		Integer approvalPhaseNumber = 0;
		Optional<ApprovalRootState> opApprovalRootState = approvalRootStateRepository.findEmploymentApp(rootStateID);
		if(!opApprovalRootState.isPresent()){
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootState approvalRootState = opApprovalRootState.get();
		for(ApprovalPhaseState approvalPhaseState : approvalRootState.getListApprovalPhaseState()){
			if(approvalPhaseState.getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED)){
				continue;
			}
			approvalPhaseState.getListApprovalFrame().forEach(approvalFrame -> {
				if(approvalFrame.getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED)){
					return;
				}
				List<String> listApprover = approvalFrame.getListApproverState().stream().map(x -> x.getApproverID()).collect(Collectors.toList());
				if(listApprover.contains(employeeID)){
					approvalFrame.setApprovalAtr(ApprovalBehaviorAtr.APPROVED);
					approvalFrame.setApproverID(employeeID);
					approvalFrame.setRepresenterID("");
					return;
				}
				ApprovalRepresenterOutput approvalRepresenterOutput = collectApprovalAgentInforService.getApprovalAgentInfor(companyID, listApprover);
				if(approvalRepresenterOutput.getListAgent().contains(employeeID)){
					approvalFrame.setApprovalAtr(ApprovalBehaviorAtr.APPROVED);
					approvalFrame.setApproverID("");
					approvalFrame.setRepresenterID(employeeID);
					return;
				}
			});
			Boolean approveApprovalPhaseStateFlag = this.isApproveApprovalPhaseStateComplete(companyID, approvalPhaseState);
			if(approveApprovalPhaseStateFlag.equals(Boolean.FALSE)){
				break;
			}
			approvalPhaseState.setApprovalAtr(ApprovalBehaviorAtr.APPROVED);
			approvalPhaseNumber = approvalPhaseState.getPhaseOrder();
		}
		approvalRootStateRepository.update(approvalRootState);
		return approvalPhaseNumber;
	}
	
	@Override
	public Boolean isApproveApprovalPhaseStateComplete(String companyID, ApprovalPhaseState approvalPhaseState) {
		if(approvalPhaseState.getApprovalForm().equals(ApprovalForm.EVERYONEAPPROVED)){
			Optional<ApprovalFrame> opApprovalFrameNotApprove = approvalPhaseState.getListApprovalFrame().stream()
			.filter(x -> !x.getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED)).findAny();
			if(!opApprovalFrameNotApprove.isPresent()){
				return true;
			}
			List<String> listUnapproveApprover = this.getUnapproveApproverFromPhase(approvalPhaseState);
			ApprovalRepresenterOutput approvalRepresenterOutput = collectApprovalAgentInforService.getApprovalAgentInfor(companyID, listUnapproveApprover);
			if(approvalRepresenterOutput.getAllPathSetFlag().equals(Boolean.TRUE)){
				return true;
			}
			return false;
		}
		Optional<ApprovalFrame> opApprovalFrameIsConfirm = approvalPhaseState.getListApprovalFrame().stream().filter(x -> x.getConfirmAtr().equals(Boolean.TRUE)).findAny();
		if(opApprovalFrameIsConfirm.isPresent()){
			ApprovalFrame approvalFrame = opApprovalFrameIsConfirm.get();
			if(approvalFrame.getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED)){
				return true;
			}
			List<String> listApprover = approvalFrame.getListApproverState().stream().map(x -> x.getApproverID()).collect(Collectors.toList());
			ApprovalRepresenterOutput approvalRepresenterOutput = collectApprovalAgentInforService.getApprovalAgentInfor(companyID, listApprover);
			if(approvalRepresenterOutput.getAllPathSetFlag().equals(Boolean.TRUE)){
				return true;
			}
			return false;
		}
		Optional<ApprovalFrame> opApprovalFrameIsApprove = approvalPhaseState.getListApprovalFrame().stream().filter(x -> x.getConfirmAtr().equals(Boolean.TRUE)).findAny();
		if(opApprovalFrameIsApprove.isPresent()){
			return true;
		}
		List<String> listApprover = judgmentApprovalStatusService.getApproverFromPhase(approvalPhaseState);
		ApprovalRepresenterOutput approvalRepresenterOutput = collectApprovalAgentInforService.getApprovalAgentInfor(companyID, listApprover);
		if(approvalRepresenterOutput.getAllPathSetFlag().equals(Boolean.TRUE)){
			return true;
		}
		return false;
	}

	@Override
	public Boolean isApproveAllComplete(String companyID, String rootStateID) {
		Boolean approveAllFlag = false;
		Optional<ApprovalRootState> opApprovalRootState = approvalRootStateRepository.findEmploymentApp(rootStateID);
		if(!opApprovalRootState.isPresent()){
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootState approvalRootState = opApprovalRootState.get();
		for(ApprovalPhaseState approvalPhaseState : approvalRootState.getListApprovalPhaseState()){
			if(approvalPhaseState.getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED)){
				approveAllFlag = true;
				break;
			}
			List<String> listApprover = judgmentApprovalStatusService.getApproverFromPhase(approvalPhaseState);
			if(CollectionUtil.isEmpty(listApprover)){
				continue;
			}
			ApprovalRepresenterOutput approvalRepresenterOutput = collectApprovalAgentInforService.getApprovalAgentInfor(companyID, listApprover);
			if(approvalRepresenterOutput.getAllPathSetFlag().equals(Boolean.FALSE)){
				break;
			}
		}
		return approveAllFlag;
	}

	@Override
	public List<String> getUnapproveApproverFromPhase(ApprovalPhaseState approvalPhaseState) {
		List<String> listUnapproveApprover = new ArrayList<>();
		approvalPhaseState.getListApprovalFrame().forEach(approvalFrame -> {
			if(approvalFrame.getApprovalAtr().equals(ApprovalBehaviorAtr.UNAPPROVED)){
				List<String> approvers = approvalFrame.getListApproverState().stream().map(x -> x.getApproverID()).collect(Collectors.toList());
				listUnapproveApprover.addAll(approvers);
			}
		});
		return listUnapproveApprover.stream().distinct().collect(Collectors.toList());
	}
	
	@Override
	public List<String> getNextApprovalPhaseStateMailList(String companyID, String rootStateID,
			Integer approvalPhaseStateNumber) {
		List<String> mailList = new ArrayList<>();
		Optional<ApprovalRootState> opApprovalRootState = approvalRootStateRepository.findEmploymentApp(rootStateID);
		if(!opApprovalRootState.isPresent()){
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootState approvalRootState = opApprovalRootState.get();
		if(!(approvalPhaseStateNumber>=1&&approvalPhaseStateNumber<=5)){
			return mailList;
		}
		Integer i = 0;
		while(i<=5){
			ApprovalPhaseState approvalPhaseState = approvalRootState.getListApprovalPhaseState()
					.stream().filter(x -> x.getPhaseOrder()==i).findFirst()
					.orElseThrow(() -> new RuntimeException("error: approvalRootState: "+rootStateID+", phaseNumber: "+i));
			List<String> listApprover = judgmentApprovalStatusService.getApproverFromPhase(approvalPhaseState);
			if(CollectionUtil.isEmpty(listApprover)){
				continue;
			}
			List<String> listUnapproveApprover = this.getUnapproveApproverFromPhase(approvalPhaseState);
			if(CollectionUtil.isEmpty(listUnapproveApprover)){
				break;
			}
			ApprovalRepresenterOutput approvalRepresenterOutput = collectApprovalAgentInforService.getApprovalAgentInfor(companyID, listUnapproveApprover);
			List<String> destinationList = this.judgmentDestination(approvalRepresenterOutput.getListApprovalAgentInfor());
			mailList.addAll(destinationList);
			break;
		}
		return mailList;
	}

	@Override
	public List<String> judgmentDestination(List<ApprovalRepresenterInforOutput> listApprovalRepresenterInforOutput) {
		List<String> destinationList = new ArrayList<>();
		if(CollectionUtil.isEmpty(listApprovalRepresenterInforOutput)){
			return destinationList;
		}
		listApprovalRepresenterInforOutput.stream().forEach(approvalRepresenterInforOutput -> {
			if(approvalRepresenterInforOutput.getRepresenter().getValue().equals(RepresenterInforOutput.None_Information)){
				destinationList.add(approvalRepresenterInforOutput.getApprover());
				return;
			}
			if(approvalRepresenterInforOutput.getRepresenter().getValue().equals(RepresenterInforOutput.Path_Information)){
				return;
			}
			destinationList.add(approvalRepresenterInforOutput.getApprover());
			destinationList.add(approvalRepresenterInforOutput.getRepresenter().getValue());
		});
		return destinationList;
	}
}
