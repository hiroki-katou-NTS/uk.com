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
import nts.uk.ctx.workflow.dom.service.output.ApprovalRepresenterOutput;
import nts.uk.ctx.workflow.dom.service.output.ApprovalStatusOutput;
import nts.uk.ctx.workflow.dom.service.output.ApproverPersonOutput;
import nts.uk.ctx.workflow.dom.service.output.ApproverPersonOutputNew;
import nts.uk.shr.com.context.AppContexts;

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
	@Inject
	private ApprovalSettingRepository repoApprSet;

	@Override
	public Boolean judgmentTargetPersonIsApprover(String companyID, String rootStateID, String employeeID, Integer rootType) {
		// 承認者フラグ = false（初期化）
		Boolean approverFlag = false;
		// ドメインモデル「承認ルートインスタンス」を取得する
		Optional<ApprovalRootState> opApprovalRootState = approvalRootStateRepository.findByID(rootStateID, rootType);
		if(!opApprovalRootState.isPresent()){
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootState approvalRootState = opApprovalRootState.get();
		// 承認者リストをクリアする
		List<String> listApprover = new ArrayList<>();
		// ドメインモデル「承認フェーズインスタンス」．順序を1～5の順でループする
		approvalRootState.getListApprovalPhaseState().stream().forEach(approvalPhaseState -> {
			// アルゴリズム「承認フェーズ毎の承認者を取得する」を実行する(getApproverFromPhase)
			List<String> approvers = this.getApproverFromPhase(approvalPhaseState);
			// 承認者社員ID一覧(output)を承認者リストに追加する
			listApprover.addAll(approvers);
		});
		// 承認者リストから重複な承認者を削除する
		List<String> newListApprover = listApprover.stream().distinct().collect(Collectors.toList());
		// 指定した社員が承認者であるかチェックする
		if(newListApprover.contains(employeeID)){
			// 承認者フラグ = true
			approverFlag = true;
			return approverFlag;
		} 
		// アルゴリズム「承認代行情報の取得処理」を実行する
		ApprovalRepresenterOutput approvalAgentOutput = collectApprovalAgentInforService.getApprovalAgentInfor(companyID, newListApprover);
		// 指定した社員が代行承認者かチェックする
		if(approvalAgentOutput.getListAgent().contains(employeeID)){
			// 承認者フラグ = true
			approverFlag = true;
			return approverFlag;
		}
		// 指定した社員が承認代行者として承認を行ったかチェックする
		for(ApprovalPhaseState approvalPhaseState : approvalRootState.getListApprovalPhaseState()){
			for(ApprovalFrame approvalFrame : approvalPhaseState.getListApprovalFrame()){
				for(ApproverInfor approverInfor : approvalFrame.getLstApproverInfo()) {
					if(Strings.isNotBlank(approverInfor.getAgentID()) && approverInfor.getAgentID().equals(employeeID)){
						// 承認者フラグ = true
						approverFlag = true;
						break;
					}
				}
				if(approverFlag.equals(Boolean.TRUE)){
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
	public ApprovalBehaviorAtr determineApprovalStatus(String companyID, String rootStateID, Integer rootType) {
		ApprovalBehaviorAtr approvalAtr = ApprovalBehaviorAtr.UNAPPROVED;
		// ドメインモデル「承認ルートインスタンス」を取得する
		Optional<ApprovalRootState> opApprovalRootState = approvalRootStateRepository.findByID(rootStateID, rootType);
		if(!opApprovalRootState.isPresent()){
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootState approvalRootState = opApprovalRootState.get();
		// ドメインモデル「承認フェーズインスタンス」．順序5～1の順でループする
		approvalRootState.getListApprovalPhaseState().sort(Comparator.comparing(ApprovalPhaseState::getPhaseOrder).reversed());
		for(ApprovalPhaseState approvalPhaseState : approvalRootState.getListApprovalPhaseState()){
			// アルゴリズム「承認フェーズ毎の承認者を取得する」を実行する
			List<String> approvers = this.getApproverFromPhase(approvalPhaseState);
			if(CollectionUtil.isEmpty(approvers)){
				continue;
			}
			// ループ中のドメインモデル「承認フェーズインスタンス」．承認区分をチェックする
			if(approvalPhaseState.getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED)){
				// ステータス =　承認済
				approvalAtr = ApprovalBehaviorAtr.APPROVED;
				break;
			}
			// ループ中の承認フェーズが承認中のフェーズかチェックする
			Optional<ApprovalPhaseState> previousPhaseResult = approvalRootState.getListApprovalPhaseState().stream()
				.filter(x -> x.getPhaseOrder() < approvalPhaseState.getPhaseOrder())
				.filter(x -> !x.getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED))
				.findAny();
			Optional<ApprovalPhaseState> afterPhaseResult = approvalRootState.getListApprovalPhaseState().stream()
					.filter(x -> x.getPhaseOrder() > approvalPhaseState.getPhaseOrder())
					.filter(x -> !x.getApprovalAtr().equals(ApprovalBehaviorAtr.UNAPPROVED))
					.findAny();
			if(!previousPhaseResult.isPresent()&&(!afterPhaseResult.isPresent()||approvalPhaseState.getPhaseOrder()==1)){
				// ステータス =　ループ中のドメインモデル「承認フェーズインスタンス」．承認区分
				approvalAtr = approvalPhaseState.getApprovalAtr();
				break;
			}
		}
		return approvalAtr;
	}

	@Override
	public ApproverPersonOutputNew judgmentTargetPersonCanApprove(String companyID, String rootStateID, String employeeID, Integer rootType) {
		// 承認できるフラグ
		Boolean authorFlag = false;
		// 指定する社員の承認区分
		ApprovalBehaviorAtr approvalAtr = ApprovalBehaviorAtr.UNAPPROVED;
		// 代行期限切れフラグ
		Boolean expirationAgentFlag = false; 
		// 承認中フェーズの承認区分
		ApprovalBehaviorAtr approvalPhaseAtr = ApprovalBehaviorAtr.UNAPPROVED;
		// ドメインモデル「承認ルートインスタンス」を取得する
		Optional<ApprovalRootState> opApprovalRootState = approvalRootStateRepository.findByID(rootStateID, rootType);
		if(!opApprovalRootState.isPresent()){
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootState approvalRootState = opApprovalRootState.get();
		approvalRootState.getListApprovalPhaseState().sort(Comparator.comparing(ApprovalPhaseState::getPhaseOrder));
		// 過去フェーズフラグ = false
		Boolean pastPhaseFlag = false;
		//hoatt 2018.12.14
		//EA修正履歴 No.3020
		//ドメインモデル「承認設定」を取得する
		Optional<PrincipalApprovalFlg> flg = repoApprSet.getPrincipalByCompanyId(companyID);
		if((!flg.isPresent() || flg.get().equals(PrincipalApprovalFlg.NOT_PRINCIPAL)) &&
				approvalRootState.getEmployeeID().equals(AppContexts.user().employeeId())){
			//本人による承認＝false　＆　申請者＝ログイン社員IDの場合
			return new ApproverPersonOutputNew(authorFlag, approvalAtr, expirationAgentFlag, approvalPhaseAtr);
		}
		// ドメインモデル「承認フェーズインスタンス」．順序5～1の順でループする
		for(ApprovalPhaseState approvalPhaseState : approvalRootState.getListApprovalPhaseState()){
			// アルゴリズム「承認フェーズ毎の承認者を取得する」を実行する
			List<String> approvers = this.getApproverFromPhase(approvalPhaseState);
			if(CollectionUtil.isEmpty(approvers)){
				continue;
			}
			// ループ中の承認フェーズが承認中のフェーズかチェックする
			Boolean judgmentResult = this.judgmentLoopApprovalPhase(approvalRootState, approvalPhaseState, pastPhaseFlag);
			if(judgmentResult){
				approvalPhaseAtr = approvalPhaseState.getApprovalAtr();
				// アルゴリズム「承認状況の判断」を実行する
				ApprovalStatusOutput approvalStatusOutput = this.judmentApprovalStatus(companyID, approvalPhaseState, employeeID);
				authorFlag = approvalStatusOutput.getApprovableFlag();
				approvalAtr = approvalStatusOutput.getApprovalAtr();
				expirationAgentFlag = approvalStatusOutput.getSubExpFlag(); 
				// ループ中の承認フェーズの承認枠がすべて「未承認」
				for(ApprovalFrame approvalFrame : approvalPhaseState.getListApprovalFrame()) {
					for(ApproverInfor approverInfor : approvalFrame.getLstApproverInfo()) {
						if(approverInfor.getApprovalAtr()!=ApprovalBehaviorAtr.UNAPPROVED) {
							pastPhaseFlag = true;
						}
						if(pastPhaseFlag) {
							break;
						}
					}
					if(pastPhaseFlag) {
						break;
					}
				}
			} else {
				// 過去フェーズフラグをチェックする
				if(pastPhaseFlag.equals(Boolean.FALSE)){
					continue;
				}
				// アルゴリズム「承認状況の判断」を実行する
				ApprovalStatusOutput approvalStatusOutput = this.judmentApprovalStatus(companyID, approvalPhaseState, employeeID);
				authorFlag = false;
				approvalAtr = approvalStatusOutput.getApprovalAtr();
			}
			if(authorFlag.equals(Boolean.TRUE)){
				break;
			}
		}
		return new ApproverPersonOutputNew(authorFlag, approvalAtr, expirationAgentFlag, approvalPhaseAtr);
	}

	@Override
	public List<String> getApproverFromPhase(ApprovalPhaseState approvalPhaseState) {
		List<String> listApprover = new ArrayList<>();
		approvalPhaseState.getListApprovalFrame().forEach(approvalFrame -> {
			List<String> approvers = approvalFrame.getLstApproverInfo().stream().map(x -> x.getApproverID()).collect(Collectors.toList());
			listApprover.addAll(approvers);
		});
		List<String> newListApprover = listApprover.stream().distinct().collect(Collectors.toList());
		return newListApprover;
	}
	
	@Override
	public ApprovalStatusOutput judmentApprovalStatus(String companyID, ApprovalPhaseState approvalPhaseState, String employeeID) {
		// 承認者フラグ
		Boolean approvalFlag = false;
		// 承認区分
		ApprovalBehaviorAtr approvalAtr = ApprovalBehaviorAtr.UNAPPROVED;
		// 承認できるフラグ
		Boolean approvableFlag = false;
		// 代行期限切れフラグ
		Boolean subExpFlag = false;
		for(ApprovalFrame approvalFrame : approvalPhaseState.getListApprovalFrame()){
			for(ApproverInfor approverInfor : approvalFrame.getLstApproverInfo()) {
				// 指定する社員が承認者として承認を行ったかチェックするCheck xem nhan vien chi dinh la approver và da tien hanh approve chua
				if(approverInfor.getApproverID().equals(employeeID) && approvalPhaseState.getApprovalAtr()==ApprovalBehaviorAtr.APPROVED){
					approvalFlag = true;
					approvalAtr = approverInfor.getApprovalAtr();
					approvableFlag = true;
					subExpFlag = false;
					if(approvalPhaseState.getApprovalForm()==ApprovalForm.SINGLE_APPROVED && approverInfor.getApprovalAtr()!=ApprovalBehaviorAtr.APPROVED) {
						approvableFlag = false;
					}
					continue;
				}
				// 指定する社員が代行承認者として承認を行ったかチェックするCheck nhan vien chi dinh la approver thay the đa tien hanh apporve chua
				List<String> listApprover = Arrays.asList(approverInfor.getApproverID());
				if(Strings.isNotBlank(approverInfor.getAgentID()) && approverInfor.getAgentID().equals(employeeID)){
					approvalFlag = true;
					approvalAtr = approverInfor.getApprovalAtr();
					approvableFlag = true;
					// アルゴリズム「指定した社員が指定した承認者リストの代行承認者かの判断」を実行する
					// Thuc hien thuat toan (phan doan xem nhan vien chi dinh co phai la approver thay the trong list approver ma da d chi dinh hay khong)
					subExpFlag = !this.judgmentAgentListByEmployee(companyID, employeeID, listApprover);
					continue;
				}
				// 指定する社員が承認者かチェックするCheck nhan vien chi dinh phai la approver hay ko
				if(!listApprover.contains(employeeID)){
					// アルゴリズム「承認代行情報の取得処理」を実行するThuc hien thuat toan (xu ly lay thong tin thay the approver)
					ApprovalRepresenterOutput approvalRepresenterOutput = collectApprovalAgentInforService.getApprovalAgentInfor(companyID, listApprover);
					if(!approvalRepresenterOutput.getListAgent().contains(employeeID)){
						continue;
					}
				}
				approvalFlag = true;
				approvalAtr = approverInfor.getApprovalAtr();
				approvableFlag = true;
				subExpFlag = false;
			}
		};
		// 確定枠が存在するかチェックするCheck xem confirm frame co ton tai hay khong
		Optional<ApprovalFrame> opApprovalFrameConfirm = approvalPhaseState.getListApprovalFrame().stream().filter(x -> x.getConfirmAtr().equals(ConfirmPerson.CONFIRM)).findAny();
		if(opApprovalFrameConfirm.isPresent()){
			ApprovalFrame approvalFrameConfirm = opApprovalFrameConfirm.get();
			// 確定枠の承認状況をチェックするCheck trang thay approve cua confirm frame
			for(ApproverInfor approverInfor : approvalFrameConfirm.getLstApproverInfo()) {
				if(!approverInfor.getApprovalAtr().equals(ApprovalBehaviorAtr.UNAPPROVED)&&
						((!approverInfor.getApproverID().equals(employeeID))||
						(Strings.isNotBlank(approverInfor.getAgentID()) && !approverInfor.getAgentID().equals(employeeID)))){
						approvableFlag = false;
						break;
					}
			}
		}
		// 否認した承認枠が存在するかチェックする
		Optional<ApproverInfor> opApproverInfor = Optional.empty();
		for(ApprovalFrame approvalFrame : approvalPhaseState.getListApprovalFrame()) {
			for(ApproverInfor approverInfor : approvalFrame.getLstApproverInfo()) {
				if(approverInfor.getApprovalAtr().equals(ApprovalBehaviorAtr.DENIAL)) {
					opApproverInfor = Optional.of(approverInfor);
					break;
				}
			}
			if(opApproverInfor.isPresent()) {
				break;
			}
		}
		if(opApproverInfor.isPresent()){
			// 否認枠の否認を行った承認者は指定する社員かチェックする
			ApproverInfor denyApproverInfor = opApproverInfor.get();
			if(denyApproverInfor.getApproverID().equals(employeeID) || 
			(Strings.isNotBlank(denyApproverInfor.getAgentID()) && denyApproverInfor.getAgentID().equals(employeeID))){
				approvableFlag = true;
			} else {
				approvableFlag = false;
			}
		}
		return new ApprovalStatusOutput(approvalFlag, approvalAtr, approvableFlag, subExpFlag);
	}

	@Override
	public Boolean judgmentAgentListByEmployee(String companyID, String employeeID, List<String> listApprover) {
		ApprovalRepresenterOutput approvalRepresenterOutput = collectApprovalAgentInforService.getApprovalAgentInfor(companyID, listApprover);
		if(approvalRepresenterOutput.getListAgent().contains(employeeID)){
			return true;
		}
		return false;
	}

	@Override
	public Boolean judgmentLoopApprovalPhase(ApprovalRootState approvalRootState, ApprovalPhaseState currentPhase, Boolean pastPhaseFlg) {
		// 過去フェーズフラグ＝trueの場合
		if(pastPhaseFlg) {
			return false;
		}
		
		// パラメータのループ中のフェーズ番号をチェックする
		if(approvalRootState.getListApprovalPhaseState().size()==1) {
			if(approvalRootState.getListApprovalPhaseState().get(0).getApprovalAtr()==ApprovalBehaviorAtr.ORIGINAL_REMAND){
				return false;
			}
			return true;
		}
		if(currentPhase.getPhaseOrder()==5){
			if(currentPhase.getApprovalAtr()==ApprovalBehaviorAtr.ORIGINAL_REMAND){
				return false;
			}
			return true;
		}
		ApprovalPhaseState lowestPhase = approvalRootState.getListApprovalPhaseState()
				.stream().sorted(Comparator.comparing(ApprovalPhaseState::getPhaseOrder).reversed())
				.findFirst().get();
		if(lowestPhase.getPhaseOrder()==currentPhase.getPhaseOrder()){
			return true;
		}
		
		// ループ中のフェーズの番号-１から、降順にループする
		ApprovalPhaseState lowerPhase = approvalRootState.getListApprovalPhaseState()
				.stream().filter(x -> x.getPhaseOrder()>currentPhase.getPhaseOrder())
				.sorted(Comparator.comparing(ApprovalPhaseState::getPhaseOrder))
				.findFirst().get();
		if(lowerPhase.getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED)){
			return true;
		}
		return false;
	}

	@Override
	public ApprovalStatusOutput judmentApprovalStatusNodataDatabaseAcess(String companyID,
			ApprovalPhaseState approvalPhaseState, String employeeID, List<String> agents) {
		// 承認者フラグ
		Boolean approvalFlag = false;
		// 承認区分
		ApprovalBehaviorAtr approvalAtr = ApprovalBehaviorAtr.UNAPPROVED;
		// 承認できるフラグ
		Boolean approvableFlag = false;
		// 代行期限切れフラグ
		Boolean subExpFlag = false;
		for(ApprovalFrame approvalFrame : approvalPhaseState.getListApprovalFrame()){
			for(ApproverInfor approverInfor : approvalFrame.getLstApproverInfo()) {
				// 指定する社員が承認者として承認を行ったかチェックするCheck xem nhan vien chi dinh la approver và da tien hanh approve chua
				if(approverInfor.getApproverID().equals(employeeID) && approvalPhaseState.getApprovalAtr()==ApprovalBehaviorAtr.APPROVED){
					approvalFlag = true;
					approvalAtr = approverInfor.getApprovalAtr();
					approvableFlag = true;
					subExpFlag = false;
					if(approvalPhaseState.getApprovalForm()==ApprovalForm.SINGLE_APPROVED && approverInfor.getApprovalAtr()!=ApprovalBehaviorAtr.APPROVED) {
						approvableFlag = false;
					}
					continue;
				}
				// 指定する社員が代行承認者として承認を行ったかチェックするCheck nhan vien chi dinh la approver thay the đa tien hanh apporve chua
				List<String> listApprover = Arrays.asList(approverInfor.getApproverID());
				if(Strings.isNotBlank(approverInfor.getAgentID()) && approverInfor.getAgentID().equals(employeeID)){
					approvalFlag = true;
					approvalAtr = approverInfor.getApprovalAtr();
					approvableFlag = true;
					subExpFlag = false;
					continue;
				}
				// 指定する社員が承認者かチェックするCheck nhan vien chi dinh phai la approver hay ko
				if(!listApprover.contains(employeeID)){
					// 指定する社員が代行承認者かチェックするCheck xem nhan vien chi dinh co phai la approver thay the hay khong
					if(!agents.contains(employeeID)){
						continue;
					}
				}
				approvalFlag = true;
				approvalAtr = approverInfor.getApprovalAtr();
				approvableFlag = true;
				subExpFlag = false;
			}
		};
		// 確定枠が存在するかチェックするCheck xem confirm frame co ton tai hay khong
		Optional<ApprovalFrame> opApprovalFrameConfirm = approvalPhaseState.getListApprovalFrame().stream().filter(x -> x.getConfirmAtr().equals(ConfirmPerson.CONFIRM)).findAny();
		if(opApprovalFrameConfirm.isPresent()){
			ApprovalFrame approvalFrameConfirm = opApprovalFrameConfirm.get();
			// 確定枠の承認状況をチェックするCheck trang thay approve cua confirm frame
			for(ApproverInfor approverInfor : approvalFrameConfirm.getLstApproverInfo()) {
				if(!approverInfor.getApprovalAtr().equals(ApprovalBehaviorAtr.UNAPPROVED)&&
						((!approverInfor.getApproverID().equals(employeeID))||
						(Strings.isNotBlank(approverInfor.getAgentID()) && !approverInfor.getAgentID().equals(employeeID)))){
						approvableFlag = false;
						break;
					}
			}
		}
		// 否認した承認枠が存在するかチェックする
		Optional<ApproverInfor> opApproverInfor = Optional.empty();
		for(ApprovalFrame approvalFrame : approvalPhaseState.getListApprovalFrame()) {
			for(ApproverInfor approverInfor : approvalFrame.getLstApproverInfo()) {
				if(approverInfor.getApprovalAtr().equals(ApprovalBehaviorAtr.DENIAL)) {
					opApproverInfor = Optional.of(approverInfor);
					break;
				}
			}
			if(opApproverInfor.isPresent()) {
				break;
			}
		}
		// 否認枠の否認を行った承認者は指定する社員かチェックする
		if(opApproverInfor.isPresent()){
			ApproverInfor denyApproverInfor = opApproverInfor.get();
			if(denyApproverInfor.getApproverID().equals(employeeID) || 
			(Strings.isNotBlank(denyApproverInfor.getAgentID()) && denyApproverInfor.getAgentID().equals(employeeID))){
				approvableFlag = true;
			} else {
				approvableFlag = false;
			}
		}
		return new ApprovalStatusOutput(approvalFlag, approvalAtr, approvableFlag, subExpFlag);
	}

	@Override
	public ApproverPersonOutput judgmentTargetPerCanApproveNoDB(ApprovalRootState approvalRootState, String approverID) {
		// Đối ứng SPR
		String companyID = "000000000000-0001";
		String loginCompanyID = AppContexts.user().companyId();
		if(Strings.isNotBlank(loginCompanyID)){
			companyID = loginCompanyID;
		}
		String employeeID = approverID;
		// 承認できるフラグ
		Boolean authorFlag = false;
		// 指定する社員の承認区分
		ApprovalBehaviorAtr approvalAtr = ApprovalBehaviorAtr.UNAPPROVED;
		// 代行期限切れフラグ
		Boolean expirationAgentFlag = false; 
		approvalRootState.getListApprovalPhaseState().sort(Comparator.comparing(ApprovalPhaseState::getPhaseOrder).reversed());
		// 過去フェーズフラグ = false
		Boolean pastPhaseFlag = false;
		// ドメインモデル「承認フェーズインスタンス」．順序5～1の順でループする
		for(ApprovalPhaseState approvalPhaseState : approvalRootState.getListApprovalPhaseState()){
			// アルゴリズム「承認フェーズ毎の承認者を取得する」を実行する
			List<String> approvers = this.getApproverFromPhase(approvalPhaseState);
			if(CollectionUtil.isEmpty(approvers)){
				continue;
			}
			// ループ中の承認フェーズが承認中のフェーズかチェックする
			Boolean judgmentResult = this.judgmentLoopApprovalPhase(approvalRootState, approvalPhaseState, pastPhaseFlag);
			if(judgmentResult){
				// アルゴリズム「承認状況の判断」を実行する
				ApprovalStatusOutput approvalStatusOutput = this.judmentApprovalStatus(companyID, approvalPhaseState, employeeID);
				authorFlag = approvalStatusOutput.getApprovableFlag();
				approvalAtr = approvalStatusOutput.getApprovalAtr();
				expirationAgentFlag = approvalStatusOutput.getSubExpFlag(); 
				// ループ中の承認フェーズの承認枠がすべて「未承認」
				for(ApprovalFrame approvalFrame : approvalPhaseState.getListApprovalFrame()) {
					for(ApproverInfor approverInfor : approvalFrame.getLstApproverInfo()) {
						if(approverInfor.getApprovalAtr()!=ApprovalBehaviorAtr.UNAPPROVED) {
							pastPhaseFlag = true;
						}
						if(pastPhaseFlag) {
							break;
						}
					}
					if(pastPhaseFlag) {
						break;
					}
				}
			} else {
				// 過去フェーズフラグをチェックする
				if(pastPhaseFlag.equals(Boolean.FALSE)){
					continue;
				}
				// アルゴリズム「承認状況の判断」を実行する
				ApprovalStatusOutput approvalStatusOutput = this.judmentApprovalStatus(companyID, approvalPhaseState, employeeID);
				authorFlag = false;
				approvalAtr = approvalStatusOutput.getApprovalAtr();
			}
			if(authorFlag.equals(Boolean.TRUE)){
				break;
			}
		}
		return new ApproverPersonOutput(authorFlag, approvalAtr, expirationAgentFlag);
	}
}
