package nts.uk.ctx.workflow.dom.service;

import java.util.ArrayList;
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
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.PrincipalApprovalFlg;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalForm;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmPerson;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalFrame;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApproverInfor;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRepresenterInforOutput;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRepresenterOutput;
import nts.uk.ctx.workflow.dom.service.output.RepresenterInforOutput;
import nts.uk.shr.com.context.AppContexts;

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
	
	@Inject
	private ApprovalSettingRepository repoApprSet;
	
	//承認する
	@Override
	public Integer doApprove(String rootStateID, String employeeID, String memo) {
		String companyID = AppContexts.user().companyId();
		Integer approvalPhaseNumber = 0;
		//ドメインモデル「承認ルートインスタンス」を取得する
		Optional<ApprovalRootState> opApprovalRootState = approvalRootStateRepository.findByID(rootStateID, 0);
		if(!opApprovalRootState.isPresent()){//0件
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootState approvalRootState = opApprovalRootState.get();
		//hoatt 2018.12.14
		//EA修正履歴 No.3013
		//ドメインモデル「承認設定」を取得する
		Optional<PrincipalApprovalFlg> flg = repoApprSet.getPrincipalByCompanyId(companyID);
		if((!flg.isPresent() || flg.get().equals(PrincipalApprovalFlg.NOT_PRINCIPAL)) &&
				approvalRootState.getEmployeeID().equals(AppContexts.user().employeeId())){
			//本人による承認＝false　＆　申請者＝ログイン社員IDの場合
			return approvalPhaseNumber;
		}
		// ドメインモデル「承認フェーズインスタンス」．順序を5～1の順でループする(loop domain model 「承認フェーズインスタンス」． thứ tự từ 5～1) 
		approvalRootState.getListApprovalPhaseState().sort(Comparator.comparing(ApprovalPhaseState::getPhaseOrder).reversed());
		for(ApprovalPhaseState approvalPhaseState : approvalRootState.getListApprovalPhaseState()){
			// ドメインモデル「承認フェーズインスタンス」．承認区分をチェックする(check domain 「承認フェーズインスタンス」．承認区分)
			if(approvalPhaseState.getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED)){
				continue;
			}
			boolean breakLoop = false;
			// ドメインモデル「承認フェーズインスタンス」．「承認枠」1～5ループする(loop xử lý domain「承認フェーズインスタンス」．「承認枠」1～5)
			for(ApprovalFrame approvalFrame : approvalPhaseState.getListApprovalFrame()) {
				for(ApproverInfor approverInfor : approvalFrame.getLstApproverInfo()) {
					// 承認者情報．承認区分をチェック
					if(approverInfor.getApprovalAtr().equals(ApprovalBehaviorAtr.UNAPPROVED)){
						String approverID = approverInfor.getApproverID();
						if(!approverID.equals(employeeID)){
							// 承認代行情報の取得処理
							ApprovalRepresenterOutput approvalRepresenterOutput = collectApprovalAgentInforService.getApprovalAgentInfor(companyID, Arrays.asList(approverID));
							if(approvalRepresenterOutput.getListAgent().contains(employeeID)){
								approverInfor.setApprovalAtr(ApprovalBehaviorAtr.APPROVED);
								approverInfor.setAgentID(employeeID);
								approverInfor.setApprovalDate(GeneralDateTime.now());
								approverInfor.setApprovalReason(memo);
								breakLoop = true;
							} else {
								continue;
							}
						} else {
							// ループ中の承認者情報．承認区分　＝　承認済み
							approverInfor.setApprovalAtr(ApprovalBehaviorAtr.APPROVED);
							approverInfor.setApproverID(employeeID);
							approverInfor.setAgentID("");
							approverInfor.setApprovalDate(GeneralDateTime.now());
							approverInfor.setApprovalReason(memo);
							breakLoop = true;
						}
					} else {
						// if文： ドメインモデル「承認枠」．承認者 == INPUT．社員ID　OR ドメインモデル「承認枠」．代行者 == INPUT．社員ID
						if(!(approverInfor.getApproverID().equals(employeeID))||
							(Strings.isNotBlank(approverInfor.getAgentID())&&approverInfor.getAgentID().equals(employeeID))){
							continue;
						}
						// ループ中の承認者情報．承認区分　＝　承認済み
						if(Strings.isNotBlank(approverInfor.getAgentID())&&approverInfor.getAgentID().equals(employeeID)) {
							approverInfor.setApprovalAtr(ApprovalBehaviorAtr.APPROVED);
							approverInfor.setAgentID(employeeID);
							approverInfor.setApprovalDate(GeneralDateTime.now());
							approverInfor.setApprovalReason(memo);
							breakLoop = true;
						}
						if(approverInfor.getApproverID().equals(employeeID)) {
							approverInfor.setApprovalAtr(ApprovalBehaviorAtr.APPROVED);
							approverInfor.setApproverID(employeeID);
							approverInfor.setAgentID("");
							approverInfor.setApprovalDate(GeneralDateTime.now());
							approverInfor.setApprovalReason(memo);
							breakLoop = true;
						}
					}
					if(breakLoop) {
						break;
					}
				}
				if(breakLoop) {
					break;
				}
			}
			// アルゴリズム「指定する承認フェーズの承認が完了したか」を実行する(thực hiện thuật toán 「」)
			Boolean approveApprovalPhaseStateFlag = this.isApproveApprovalPhaseStateComplete(companyID, approvalPhaseState);
			// 承認完了フラグをチェックする(check 承認完了フラグ)
			if(approveApprovalPhaseStateFlag.equals(Boolean.FALSE)){
				// ループ中のドメインモデル「承認フェーズインスタンス」．承認区分 = 未承認 (domain model trong loop 「承認フェーズインスタンス」．phân khu chứng nhận = chưa chứng nhận)
				approvalPhaseState.setApprovalAtr(ApprovalBehaviorAtr.UNAPPROVED);
				break;
			}
			// ループ中のドメインモデル「承認フェーズインスタンス」．承認区分 = 承認済(domain model trong loop 「承認フェーズインスタンス」．phân khu chứng nhận = đã confirm)
			approvalPhaseState.setApprovalAtr(ApprovalBehaviorAtr.APPROVED);
			// 承認フェーズ枠番 = ループ中の「承認フェーズインスタンス」．順序 (số hiệu khung phase chứng nhận = 「承認フェーズインスタンス」trong loop. thứ tự)
			approvalPhaseNumber = approvalPhaseState.getPhaseOrder();
		}
		// ドメインモデル「承認ルートインスタンス」の承認状態をUpdateする(update trạng thái chứng nhận của domain model 「」)
		approvalRootStateRepository.update(approvalRootState, 0);
		return approvalPhaseNumber;
	}
	
	@Override
	public Boolean isApproveApprovalPhaseStateComplete(String companyID, ApprovalPhaseState approvalPhaseState) {
		// 「承認フェーズインスタンス」．承認形態をチェックする(check 「ApprovalPhaseInstance」．approvalForm)
		ApprovalForm approvalForm = approvalPhaseState.getApprovalForm();
		if(approvalForm==ApprovalForm.EVERYONE_APPROVED){
			// 全員承認したかチェックする(check xem tất cả người xác nhận đã xác nhận chưa)
			Optional<ApproverInfor> notApproved = approvalPhaseState.getNotApproved();
			if(!notApproved.isPresent()){
				return true;
			}
			// アルゴリズム「未承認の承認者一覧を取得する」を実行する(thực hiện xử lý 「Lấy list approver chưa  xác nhận」)
			List<String> listUnapproveApprover = this.getUnapproveApproverFromPhase(approvalPhaseState);
			// アルゴリズム「承認代行情報の取得処理」を実行する(thực hiện xử lý 「 lấy thông đại hiện approve」)
			ApprovalRepresenterOutput approvalRepresenterOutput = collectApprovalAgentInforService.getApprovalAgentInfor(companyID, listUnapproveApprover);
			if(approvalRepresenterOutput.getAllPathSetFlag().equals(Boolean.TRUE)){
				return true;
			}
			return false;
		}
		// 確定者を設定したかチェックする(check có cài đặt người 確定者 hay không)
		Optional<ApprovalFrame> opApprovalFrameIsConfirm = approvalPhaseState.getListApprovalFrame().stream()
				.filter(x -> x.getConfirmAtr().equals(ConfirmPerson.CONFIRM)).findAny();
		if(opApprovalFrameIsConfirm.isPresent()){
			ApprovalFrame approvalFrame = opApprovalFrameIsConfirm.get();
			// 確定者が承認済かチェックする(check người xác nhận đã xác nhận hay chưa)
			Optional<ApproverInfor> approvedApproverInfor = approvalFrame.getLstApproverInfo().stream()
				.filter(x -> x.getApprovalAtr()==ApprovalBehaviorAtr.APPROVED).findAny();	
			if(approvedApproverInfor.isPresent()){
				return true;
			}
			List<String> listApprover = approvalFrame.getLstApproverInfo().stream().map(x -> x.getApproverID()).collect(Collectors.toList());
			// アルゴリズム「承認代行情報の取得処理」を実行する(thực hiện xử lý 「Lấy thông tin đại diện approve」)
			ApprovalRepresenterOutput approvalRepresenterOutput = collectApprovalAgentInforService.getApprovalAgentInfor(companyID, listApprover);
			if(approvalRepresenterOutput.getAllPathSetFlag().equals(Boolean.TRUE)){
				return true;
			}
			return false;
		}
		// 承認済の承認枠があるかチェックする(check xem có approvalFrame đã được xác nhận hay khồng)
		Optional<ApproverInfor> approvedApproverInfor = approvalPhaseState.getApproved();
		if(approvedApproverInfor.isPresent()){
			return true;
		}
		// アルゴリズム「承認フェーズ毎の承認者を取得する」を実行する(thực hiện xử lý 「Lấy approver cho mỗi approvalPhase」)
		List<String> listApprover = judgmentApprovalStatusService.getApproverFromPhase(approvalPhaseState);
		// アルゴリズム「承認代行情報の取得処理」を実行する(thực hiện xử lý 「lấy thông tin đại diện approve」)
		ApprovalRepresenterOutput approvalRepresenterOutput = collectApprovalAgentInforService.getApprovalAgentInfor(companyID, listApprover);
		if(approvalRepresenterOutput.getAllPathSetFlag().equals(Boolean.TRUE)){
			return true;
		}
		return false;
	}

	@Override
	public Boolean isApproveAllComplete(String rootStateID) {
		String companyID = AppContexts.user().companyId();
		Boolean approveAllFlag = false;
		Optional<ApprovalRootState> opApprovalRootState = approvalRootStateRepository.findByID(rootStateID, 0);
		if(!opApprovalRootState.isPresent()){
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootState approvalRootState = opApprovalRootState.get();
		approvalRootState.getListApprovalPhaseState().sort(Comparator.comparing(ApprovalPhaseState::getPhaseOrder));
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
			approvalFrame.getLstApproverInfo().forEach(approverInfor -> {
				if(approverInfor.getApprovalAtr().equals(ApprovalBehaviorAtr.UNAPPROVED)){
					listUnapproveApprover.add(approverInfor.getApproverID());
				}
			});
		});
		return listUnapproveApprover.stream().distinct().collect(Collectors.toList());
	}
	
	@Override
	public List<String> getNextApprovalPhaseStateMailList(String rootStateID, Integer approvalPhaseStateNumber) {
		String companyID = AppContexts.user().companyId();
		List<String> mailList = new ArrayList<>();
		Optional<ApprovalRootState> opApprovalRootState = approvalRootStateRepository.findByID(rootStateID, 0);
		if(!opApprovalRootState.isPresent()){
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootState approvalRootState = opApprovalRootState.get();
		if(!(approvalPhaseStateNumber>=1&&approvalPhaseStateNumber<=5)){
			return mailList;
		}
		Integer i = approvalPhaseStateNumber;
		List<ApprovalPhaseState> afterList = approvalRootState.getListApprovalPhaseState().stream()
				.filter(x -> x.getPhaseOrder() >= approvalPhaseStateNumber).collect(Collectors.toList());
		for(ApprovalPhaseState approvalPhaseState : afterList){
			List<String> listApprover = judgmentApprovalStatusService.getApproverFromPhase(approvalPhaseState);
			if(CollectionUtil.isEmpty(listApprover)){
				i++;
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
