package nts.uk.ctx.workflow.dom.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmPerson;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalFrame;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApproverInfor;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRepresenterOutput;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class DenyImpl implements DenyService {
	
	@Inject
	private ApprovalRootStateRepository approvalRootStateRepository;
	
	@Inject
	private JudgmentApprovalStatusService judgmentApprovalStatusService;
	
	@Inject
	private CollectApprovalAgentInforService collectApprovalAgentInforService;

	@Override
	public Boolean doDeny(String rootStateID, String employeeID, String memo) {
		String companyID = AppContexts.user().companyId();
		// 否認を実行したかフラグ=false（初期化）
		Boolean executedFlag = false;
		// ドメインモデル「承認ルートインスタンス」を取得する
		Optional<ApprovalRootState> opApprovalRootState = approvalRootStateRepository.findByID(rootStateID, 0);
		if(!opApprovalRootState.isPresent()){
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootState approvalRootState = opApprovalRootState.get();
		// ドメインモデル「承認フェーズインスタンス」．順序を1～5の順でループする
		approvalRootState.getListApprovalPhaseState().sort(Comparator.comparing(ApprovalPhaseState::getPhaseOrder));
		for(ApprovalPhaseState approvalPhaseState : approvalRootState.getListApprovalPhaseState()){
			// アルゴリズム「承認フェーズ毎の承認者を取得する」を実行する(thực hiện xử lý 「承認フェーズ毎の承認者を取得する」)
			List<String> approvers = judgmentApprovalStatusService.getApproverFromPhase(approvalPhaseState);
			if(CollectionUtil.isEmpty(approvers)){
				continue;
			}
			Optional<ApproverInfor> notUnApproved = approvalPhaseState.getNotUnApproved();
			// ループ中の承認フェーズには承認を行ったか(Approval phase đang xử lý được xác nhận chưa)
			boolean phaseNotApprovalFlag = (approvalPhaseState.getApprovalAtr() == ApprovalBehaviorAtr.UNAPPROVED)&&!notUnApproved.isPresent();
			if(phaseNotApprovalFlag){
				// 3.1.否認できるかチェックする(canDenyCheck)
				boolean canDenyCheckFlag = this.canDenyCheck(approvalRootState, approvalPhaseState.getPhaseOrder()+1, employeeID);
				if(!canDenyCheckFlag){
					continue;
				}
			}
			// ドメインモデル「承認フェーズインスタンス」．「承認枠」1～5ループする(loop domain 「承認フェーズインスタンス」．「承認枠」1～5)
			for(ApprovalFrame approvalFrame : approvalPhaseState.getListApprovalFrame()){
				for(ApproverInfor approverInfor : approvalFrame.getLstApproverInfo()) {
					if(approverInfor.getApprovalAtr() == ApprovalBehaviorAtr.UNAPPROVED){
						String approverID = approverInfor.getApproverID();
						if(!approverID.equals(employeeID)){
							ApprovalRepresenterOutput approvalRepresenterOutput = collectApprovalAgentInforService.getApprovalAgentInfor(companyID, Arrays.asList(approverID));
							if(approvalRepresenterOutput.getListAgent().contains(employeeID)){
								approverInfor.setApprovalAtr(ApprovalBehaviorAtr.DENIAL);
								approverInfor.setAgentID(employeeID);
								approverInfor.setApprovalDate(GeneralDateTime.now());
								approverInfor.setApprovalReason(memo);
								approvalPhaseState.setApprovalAtr(ApprovalBehaviorAtr.DENIAL);
								executedFlag = true;
							} else {
								continue;
							}
							
						} else {
							approverInfor.setApprovalAtr(ApprovalBehaviorAtr.DENIAL);
							approverInfor.setAgentID("");
							approverInfor.setApprovalDate(GeneralDateTime.now());
							approverInfor.setApprovalReason(memo);
							approvalPhaseState.setApprovalAtr(ApprovalBehaviorAtr.DENIAL);
							executedFlag = true;
						}
					} else {
						if(!(approverInfor.getApproverID().equals(employeeID)||
							(Strings.isNotBlank(approverInfor.getAgentID())&&approverInfor.getAgentID().equals(employeeID)))){
							continue;
						}
						if(Strings.isNotBlank(approverInfor.getAgentID())&&approverInfor.getAgentID().equals(employeeID)) {
							approverInfor.setApprovalAtr(ApprovalBehaviorAtr.DENIAL);
							approverInfor.setAgentID(employeeID);
							approverInfor.setApprovalDate(GeneralDateTime.now());
							approverInfor.setApprovalReason(memo);
							approvalPhaseState.setApprovalAtr(ApprovalBehaviorAtr.DENIAL);
							executedFlag = true;
						}
						if(approverInfor.getApproverID().equals(employeeID)) {
							approverInfor.setApprovalAtr(ApprovalBehaviorAtr.DENIAL);
							approverInfor.setApproverID(employeeID);
							approverInfor.setAgentID("");
							approverInfor.setApprovalDate(GeneralDateTime.now());
							approverInfor.setApprovalReason(memo);
							approvalPhaseState.setApprovalAtr(ApprovalBehaviorAtr.DENIAL);
							executedFlag = true;
						}
					}
					if(executedFlag) {
						break;
					}
				}
				if(executedFlag) {
					break;
				}
			}
			// ドメインモデル「承認ルートインスタンス」の承認状態をUpdateする
			approvalRootStateRepository.update(approvalRootState, 0);
			break;
		}
		return executedFlag;
	}

	@Override
	public Boolean canDenyCheck(ApprovalRootState approvalRootState, Integer order, String employeeID) {
		Boolean canDenyFlag = true;
		if(approvalRootState.getListApprovalPhaseState().size()==1){
			return canDenyFlag;
		}
		if(order > 5){
			return canDenyFlag;
		}
		List<ApprovalPhaseState> lowerPhaseLst = approvalRootState.getListApprovalPhaseState()
				.stream().filter(x -> x.getPhaseOrder()>=order)
				.sorted(Comparator.comparing(ApprovalPhaseState::getPhaseOrder))
				.collect(Collectors.toList());
		if(CollectionUtil.isEmpty(lowerPhaseLst)) {
			return canDenyFlag;
		}
		ApprovalPhaseState lowerPhase = lowerPhaseLst.get(0);
		if(!lowerPhase.getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED)){
			canDenyFlag = false;
			return canDenyFlag;
		}
		Optional<ApprovalFrame> opConfirmApprovalFrame = lowerPhase.getListApprovalFrame().stream()
		 	.filter(x -> x.getConfirmAtr().equals(ConfirmPerson.CONFIRM)).findAny();
		if(opConfirmApprovalFrame.isPresent()){
			ApprovalFrame confirmApprovalFrame = opConfirmApprovalFrame.get();
			for(ApproverInfor approverInfor : confirmApprovalFrame.getLstApproverInfo()) {
				if(approverInfor.getApproverID().equals(employeeID)||
					(Strings.isNotBlank(approverInfor.getAgentID())&&approverInfor.getAgentID().equals(employeeID))){
					canDenyFlag = false;
				} else {
					canDenyFlag = true;
				}
				return canDenyFlag;
			}
		}
		Optional<ApproverInfor> denyApproverInfor = Optional.empty();
		for(ApprovalFrame approvalFrame : lowerPhase.getListApprovalFrame()) {
			for(ApproverInfor approverInfor : approvalFrame.getLstApproverInfo()) {
				if(approverInfor.getApproverID().equals(employeeID)||
					(Strings.isNotBlank(approverInfor.getAgentID())&&approverInfor.getAgentID().equals(employeeID))){
					denyApproverInfor = Optional.of(approverInfor);
				}
				if(denyApproverInfor.isPresent()) {
					break;
				}
			}
			if(denyApproverInfor.isPresent()) {
				break;
			}
		}
		if(denyApproverInfor.isPresent()) {
			canDenyFlag = false;
		} else {
			canDenyFlag = true;
		}
		return canDenyFlag;
	}
}
