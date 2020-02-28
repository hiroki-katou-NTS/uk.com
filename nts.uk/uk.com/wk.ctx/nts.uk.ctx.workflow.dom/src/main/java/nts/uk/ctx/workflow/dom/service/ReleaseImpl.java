package nts.uk.ctx.workflow.dom.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalForm;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmPerson;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalFrame;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApproverInfor;

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
	public Boolean doRelease(String companyID, String rootStateID, String employeeID, Integer rootType) {
		Boolean executedFlag = false;
		Optional<ApprovalRootState> opApprovalRootState = approvalRootStateRepository.findByID(rootStateID, rootType);
		if(!opApprovalRootState.isPresent()){
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootState approvalRootState = opApprovalRootState.get();
		approvalRootState.getListApprovalPhaseState().sort(Comparator.comparing(ApprovalPhaseState::getPhaseOrder));
		for(ApprovalPhaseState approvalPhaseState : approvalRootState.getListApprovalPhaseState()){
			List<String> listApprover = judgmentApprovalStatusService.getApproverFromPhase(approvalPhaseState);
			if(CollectionUtil.isEmpty(listApprover)){
				continue;
			}
			Optional<ApproverInfor> notUnApproved = approvalPhaseState.getNotUnApproved();
			boolean phaseNotApprovalFlag = approvalPhaseState.getApprovalAtr().equals(ApprovalBehaviorAtr.UNAPPROVED)&&!notUnApproved.isPresent();
			if(phaseNotApprovalFlag){
				continue;
			}
			boolean canRelease = this.canReleaseCheck(approvalPhaseState, employeeID);
			if(!canRelease){
				break;
			}
			approvalPhaseState.getListApprovalFrame().forEach(approvalFrame -> {
				approvalFrame.getLstApproverInfo().forEach(approverInfor -> {
					if(approverInfor.getApproverID().equals(employeeID) ||
							(Strings.isNotBlank(approverInfor.getAgentID()) && approverInfor.getAgentID().equals(employeeID))){
						approverInfor.setApprovalAtr(ApprovalBehaviorAtr.UNAPPROVED);
						approverInfor.setAgentID("");
						approverInfor.setApprovalDate(null);
						approverInfor.setApprovalReason("");
					}
				});
			});
			approvalPhaseState.setApprovalAtr(ApprovalBehaviorAtr.UNAPPROVED);
			approvalRootStateRepository.update(approvalRootState, rootType);
			executedFlag = true;
		}
		return executedFlag;
	}

	@Override
	public Boolean canReleaseCheck(ApprovalPhaseState approvalPhaseState, String employeeID) {
		if(approvalPhaseState.getApprovalForm().equals(ApprovalForm.EVERYONE_APPROVED)){
			for(ApprovalFrame approvalFrame : approvalPhaseState.getListApprovalFrame()) {
				for(ApproverInfor approverInfor : approvalFrame.getLstApproverInfo()) {
					if(approverInfor.getApproverID().equals(employeeID) ||
							(Strings.isNotBlank(approverInfor.getAgentID()) && approverInfor.getAgentID().equals(employeeID))){
						return true;
					}
				}
			}
			return false;
		}
		Optional<ApprovalFrame> opConfirmFrame = approvalPhaseState.getListApprovalFrame().stream().filter(x -> x.getConfirmAtr().equals(ConfirmPerson.CONFIRM)).findAny();
		if(opConfirmFrame.isPresent()){
			ApprovalFrame approvalFrame = opConfirmFrame.get();
			for(ApproverInfor approverInfor : approvalFrame.getLstApproverInfo()) {
				if(approverInfor.getApproverID().equals(employeeID) ||
						(Strings.isNotBlank(approverInfor.getAgentID()) && approverInfor.getAgentID().equals(employeeID))){
					return true;
				}
			}
			return false;
		}
		for(ApprovalFrame approvalFrame : approvalPhaseState.getListApprovalFrame()) {
			for(ApproverInfor approverInfor : approvalFrame.getLstApproverInfo()) {
				if(approverInfor.getApproverID().equals(employeeID) ||
						(Strings.isNotBlank(approverInfor.getAgentID()) && approverInfor.getAgentID().equals(employeeID))){
					return true;
				}
			}
		}
		return false;
	}

}
