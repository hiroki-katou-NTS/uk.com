package nts.uk.ctx.workflow.pubimp.hrapprovalstate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.PrincipalApprovalFlg;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalForm;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmPerson;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;
import nts.uk.ctx.workflow.dom.hrapproverstatemana.ApprovalFrameHr;
import nts.uk.ctx.workflow.dom.hrapproverstatemana.ApprovalPhaseStateHr;
import nts.uk.ctx.workflow.dom.hrapproverstatemana.ApprovalRootStateHr;
import nts.uk.ctx.workflow.dom.hrapproverstatemana.ApprovalRootStateHrRepository;
import nts.uk.ctx.workflow.dom.hrapproverstatemana.ApproverInforHr;
import nts.uk.ctx.workflow.dom.service.CollectApprovalAgentInforService;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRepresenterOutput;
import nts.uk.ctx.workflow.pub.hrapprovalstate.ApprovalStateHrPub;
import nts.uk.ctx.workflow.pub.hrapprovalstate.input.ApprovalStateHrImport;
import nts.uk.ctx.workflow.pub.hrapprovalstate.input.ApproverInfoHrImport;
import nts.uk.ctx.workflow.pub.hrapprovalstate.input.FrameHrImport;
import nts.uk.ctx.workflow.pub.hrapprovalstate.input.PhaseStateHrImport;
import nts.uk.ctx.workflow.pub.hrapprovalstate.output.ApprovalRootStateHrExport;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class ApprovalStateHrPubImpl implements ApprovalStateHrPub{

	@Inject
	private ApprovalRootStateHrRepository repoApprStateHr;
	
	@Inject
	private CollectApprovalAgentInforService collectApprAgentSv;
	
	@Inject
	private ApprovalSettingRepository repoApprSet;
	
	/**
	 * 1.人事承認フェーズ毎の承認者を取得する(getApproverFromPhase)
	 * @author hoatt
	 * @param 人事承認フェーズ phaseHr
	 * @return 承認者社員ID一覧
	 */
	@Override
	public List<String> getApproverFromPhaseHr(PhaseStateHrImport phaseHr) {
		//ループ中の「人事承認枠」．承認者リストを承認者社員ID一覧に追加する
		List<String> listApprover = new ArrayList<>();
		for(FrameHrImport frame : phaseHr.getLstApprovalFrame()){
			listApprover.addAll(frame.getLstApproverInfo().stream()
					.map(c -> c.getApproverID()).collect(Collectors.toList()));
		}
		//社員ID重複な承認者を消す(xóa người xác nhận bị trùng)
		List<String> newListApprover = listApprover.stream().distinct().collect(Collectors.toList());
		return newListApprover;
	}
	/**
	 * [RQ631]申請書の承認者と状況を取得する
	 * @author hoatt
	 * @param インスタンスID rootStateID
	 * @return
	 */
	@Override
	public ApprovalRootStateHrExport getApprovalRootStateHr(String rootStateID) {
		//エラーフラグ　＝　False、人事承認状態　＝　Empty (Error flag = False, HR approval state = Empty)
		boolean errorFlg = false;
		ApprovalStateHrImport apprState = null;
		//人事承認状況を取得する
		Optional<ApprovalRootStateHr> opRoot = repoApprStateHr.getById(rootStateID);
		if(!opRoot.isPresent()){
			//エラーフラグ　＝　True (Error flag = True)
			errorFlg = true;
			return new ApprovalRootStateHrExport(errorFlg, apprState);
		}
		ApprovalRootStateHr root = opRoot.get();
		//人事承認状態を作成 (Tạo HR approval state)
		apprState = new ApprovalStateHrImport(rootStateID, root.getAppDate(), root.getEmployeeID(), false,
				root.getLstPhaseState().stream()
					.map(ph -> new PhaseStateHrImport(ph.getPhaseOrder(), ph.getApprovalAtr().value, 
						ph.getApprovalForm().value, ph.getLstApprovalFrame().stream()
						.map(fr -> new FrameHrImport(fr.getFrameOrder(), 
								fr.getConfirmAtr().value, fr.getAppDate(),
								fr.getLstApproverInfo().stream()
									.map(c -> new ApproverInfoHrImport(c.getApproverID(), c.getApprovalAtr().value, c.getAgentID(),
											c.getApprovalDate(), c.getApprovalReason())
									).collect(Collectors.toList()))
						).collect(Collectors.toList()))
					).collect(Collectors.toList()));
		//承認完了フラグ
		boolean reflectFlag = this.isApprovedAllHr(apprState);
		apprState.setReflectFlag(reflectFlag);
		//エラーフラグ、人事承認状態　を渡す ( Truyền  HR approval state, Error flag)
		return new ApprovalRootStateHrExport(errorFlg, apprState);
	}
	/**
	 * [RQ632]申請書を承認する
	 * @author hoatt
	 * @param インスタンスID rootStateID
	 * @param 社員ID employeeID
	 * @param コメント comment
	 * @return 承認フェーズ枠番
	 */
	@Override
	public Integer approveHr(String rootStateID, String employeeID, String comment) {
		//承認フェーズ枠番 = 0(初期化) (khởi tạo phaseOrder = 0)
		Integer phaseOrder = 0;
		//ドメインモデル「人事承認ルートインスタンス」を取得する
		Optional<ApprovalRootStateHr> opRoot = repoApprStateHr.getById(rootStateID);
		if(!opRoot.isPresent()){//0件
			//状態：承認ルート取得失敗 (trạng thái: get ApprovalRoot thất bại)
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: " + rootStateID);
		}
		String companyID = AppContexts.user().companyId();
		ApprovalRootStateHr root = opRoot.get();
		
		//ドメインモデル「承認設定」を取得する
		Optional<PrincipalApprovalFlg> flg = repoApprSet.getPrincipalByCompanyId(companyID);
		if((!flg.isPresent() || flg.get().equals(PrincipalApprovalFlg.NOT_PRINCIPAL)) &&
				root.getEmployeeID().equals(AppContexts.user().employeeId())){
			//本人による承認＝false　＆　申請者＝ログイン社員IDの場合
			return phaseOrder;
		}
		
		root.getLstPhaseState().sort(Comparator.comparing(ApprovalPhaseStateHr::getPhaseOrder).reversed());
		//ドメインモデル「人事承認フェーズインスタンス」．順序を1～5の順でループする(loop phase từ 1- 5) 
		for(ApprovalPhaseStateHr phase : root.getLstPhaseState()){
			//ドメインモデル「人事承認フェーズインスタンス」．承認区分をチェックする(check ApprovalAtr)
			if(phase.getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED)){//ドメインモデル「承認フェーズインスタンス」．承認区分が承認済
				continue;
			}
			//ドメインモデル「人事承認フェーズインスタンス」．承認区分が承認済じゃない
			List<ApprovalFrameHr> lstFrame = phase.getLstApprovalFrame();
			lstFrame.sort(Comparator.comparing(ApprovalFrameHr::getFrameOrder));
			//ドメインモデル「人事承認フェーズインスタンス」．「人事承認枠」1～5ループする(loop frame từ 1～5)
			for(ApprovalFrameHr frame : lstFrame){
				for(ApproverInforHr appr : frame.getLstApproverInfo()) {
					if(appr.getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED)){
						continue;
					}
					//ループ中の承認枠が未承認かチェックする(check trạng thái của frame)
					if(appr.getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED)){//「人事承認枠」．承認区分 == 未承認
						List<String> listApprover = Arrays.asList(appr.getApproverID());
						//指定する社員が該当承認枠の承認者かチェックする
						if(!this.checkExist(listApprover, appr.getAgentID(), employeeID)){//false
							//承認代行情報の取得処理
							ApprovalRepresenterOutput apprAgent = collectApprAgentSv.getApprovalAgentInfor(companyID, listApprover);
							//指定する社員が代行承認者かチェックする
							if(apprAgent.getListAgent().contains(employeeID)){//true
								//(ドメインモデル「人事承認枠」)承認区分=「承認済」、代行者=INPUT．社員ID
								this.setStateRQ632(appr, employeeID, comment);
								continue;
							}
						}else{
							this.setStateRQ632(appr, "", comment);
							continue;
						}
					} else {//「人事承認枠」．承認区分 ≠ 未承認
						// if文： ドメインモデル「承認枠」．承認者 == INPUT．社員ID　OR ドメインモデル「承認枠」．代行者 == INPUT．社員ID
						if(!this.checkExist(Arrays.asList(appr.getApproverID()), appr.getAgentID(), employeeID)){
							continue;
						}
						//(ループ中の「人事承認枠」)承認区分=「承認済」、代行者=空
						this.setStateRQ632(appr, "", comment);
						continue;
					}
				}
			}
			//Ket thuc loop frame
			Boolean apprPhaseCompFlag = this.isApprovalPhaseComplete(phase);
			if(apprPhaseCompFlag.equals(Boolean.FALSE)){
				phase.setApprovalAtr(ApprovalBehaviorAtr.UNAPPROVED);
				break;
			}
			phase.setApprovalAtr(ApprovalBehaviorAtr.APPROVED);
			phaseOrder = phase.getPhaseOrder();
		}
		repoApprStateHr.update(root);
		return phaseOrder;
	}
	private void setStateRQ632(ApproverInforHr appr, String employeeID, String comment) {
		appr.setApprovalAtr(ApprovalBehaviorAtr.APPROVED);
		appr.setAgentID(employeeID);
		appr.setApprovalDate(GeneralDate.today());
		appr.setApprovalReason(comment);
	}
	/**
	 * [RQ633]申請書を否認する
	 * @author hoatt
	 * @param インスタンスID rootStateID
	 * @param 社員ID employeeID
	 * @param コメント comment
	 * @return 否認を実行したかフラグ(true, false)
					true：否認を実行した
					false：否認を実行しなかった
	 */
	@Override
	public Boolean denyHr(String rootStateID, String employeeID, String comment) {
		//否認を実行したかフラグ=false（初期化）
		Boolean executedFlag = false;
		//ドメインモデル「人事承認ルートインスタンス」を取得する
		Optional<ApprovalRootStateHr> opRoot = repoApprStateHr.getById(rootStateID);
		if(!opRoot.isPresent()){
			//状態：承認ルート取得失敗
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootStateHr root = opRoot.get();
		root.getLstPhaseState().sort(Comparator.comparing(ApprovalPhaseStateHr::getPhaseOrder));
		//ドメインモデル「人事承認フェーズインスタンス」．順序を1～5の順でループする
		for(ApprovalPhaseStateHr phase : root.getLstPhaseState()){
			//1.人事承認フェーズ毎の承認者を取得する(getApproverFromPhaseHr)
			List<String> lstApprover = this.getApproverFromPhaseHr(this.convertToImport(phase));
			if(lstApprover.isEmpty()){
				continue;
			}
			Optional<ApproverInforHr> notUnApproved = this.getNotUnApproved(phase.getLstApprovalFrame());
			
			//ループ中の人事承認フェーズには承認を行ったか(Approval phase đang xử lý được approval chưa)
			Boolean phaseNotApprovalFlag = phase.getApprovalAtr().equals(ApprovalBehaviorAtr.UNAPPROVED) && !notUnApproved.isPresent();
			if(phaseNotApprovalFlag.equals(Boolean.TRUE)){
				//1.否認できるかチェックする
				Boolean canDenyCheckFlag = this.checkCanDeny(root, phase.getPhaseOrder() - 1, employeeID);
				if(canDenyCheckFlag.equals(Boolean.FALSE)){
					continue;
				}
			}
			//ドメインモデル「人事承認フェーズインスタンス」．「人事承認枠」1～5ループする(loop frame 1～5)
			List<ApprovalFrameHr> lstFrame = phase.getLstApprovalFrame();
			lstFrame.sort(Comparator.comparing(ApprovalFrameHr::getFrameOrder));
			for(ApprovalFrameHr frame : lstFrame){
				for(ApproverInforHr appr : frame.getLstApproverInfo()) {
					//ループ中の承認枠が未承認かチェックする(Check frame đang loop đã approval chưa)
					if(appr.getApprovalAtr().equals(ApprovalBehaviorAtr.UNAPPROVED)){//「人事承認枠」．承認区分 == 未承認
						//指定する社員が承認者かチェックする
						if(!appr.getApproverID().equals(employeeID)){//false
							//承認代行情報の取得処理(Lấy thông tin đại diện approval)
							ApprovalRepresenterOutput apprAgent = collectApprAgentSv.getApprovalAgentInfor(AppContexts.user().companyId(), Arrays.asList(appr.getApproverID()));
							//指定する社員が代行承認者かチェックする
	//						if文： 返す結果の承認代行者リスト. Contains(INPUT．社員ID)
							if(apprAgent.getListAgent().contains(employeeID)){//true
								this.setStateRQ633(appr, employeeID, comment);
								executedFlag = true;
							}
							continue;
						}
					} else {//「人事承認枠」．承認区分 ≠ 未承認
						if(!this.checkExist(Arrays.asList(appr.getApproverID()), appr.getAgentID(), employeeID)){
							continue;
						}
					}
					this.setStateRQ633(appr, "", comment);
					executedFlag = true;
				}
			}
			//ドメインモデル「人事承認ルートインスタンス」の承認状態をUpdateする
			repoApprStateHr.update(root);
			return executedFlag;
		}
		return executedFlag;
	}
	private void setStateRQ633(ApproverInforHr appr, String employeeID, String comment) {
		appr.setApprovalAtr(ApprovalBehaviorAtr.DENIAL);
		appr.setAgentID(employeeID);
		appr.setApprovalDate(GeneralDate.today());
		appr.setApprovalReason(comment);
		appr.setApprovalAtr(ApprovalBehaviorAtr.DENIAL);
	}
	/**
	 * [RQ634]申請書を差し戻しする
	 * 申請書を差し戻しする（承認者まで）
	 * @author hoatt
	 * @param インスタンスID rootStateID
	 * @param 「人事承認フェーズインスタンス」．順序 phaseOrder
	 */
	@Override
	public void remandForApproverHr(String rootStateID, Integer phaseOrder) {
		//ドメインモデル「人事承認ルートインスタンス」を取得する
		Optional<ApprovalRootStateHr> opApprovalRootState = repoApprStateHr.getById(rootStateID);
		if(!opApprovalRootState.isPresent()){
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootStateHr root = opApprovalRootState.get();
		List<ApprovalPhaseStateHr> listPhase = root.getLstPhaseState();
		listPhase.sort(Comparator.comparing(ApprovalPhaseStateHr::getPhaseOrder).reversed());
		List<ApprovalPhaseStateHr> listUpperPhase = listPhase.stream().filter(x -> x.getPhaseOrder() >= phaseOrder).collect(Collectors.toList());
		//差し戻し実行者のフェーズから差し戻し先のフェーズまでループする(loop từ phase của người thực hiện trả về tới phase của destination trả về)
		listUpperPhase.forEach(phase -> {
			phase.getLstApprovalFrame().forEach(frame -> {
				frame.getLstApproverInfo().forEach(appr -> {
					appr.setApprovalAtr(ApprovalBehaviorAtr.UNAPPROVED);
					appr.setAgentID("");;
					appr.setApprovalDate(null);
					appr.setApprovalReason("");
				});
				
			});
			phase.setApprovalAtr(ApprovalBehaviorAtr.UNAPPROVED);
		});
		ApprovalPhaseStateHr currentPhase = listPhase.stream().filter(x -> x.getPhaseOrder()== phaseOrder).findFirst().get();
		currentPhase.setApprovalAtr(ApprovalBehaviorAtr.REMAND);
		//ドメインモデル「人事承認枠」の承認状態をUpdateする
		repoApprStateHr.update(root);
	}
	/**
	 * [RQ634]申請書を差し戻しする
	 * 申請書を差し戻しする（申請本人まで）
	 * @author hoatt
	 * @param インスタンスID rootStateID
	 */
	@Override
	public void remandForApplicantHr(String rootStateID) {
		//一括解除する
		this.releaseAllHr(rootStateID);
		//ドメインモデル「人事承認フェーズインスタンス」．人事承認フェーズ１．承認区分をupdateする
		repoApprStateHr.getById(rootStateID).ifPresent(appRoot -> {
			appRoot.getLstPhaseState().sort(Comparator.comparing(ApprovalPhaseStateHr::getPhaseOrder));
			appRoot.getLstPhaseState().get(0).setApprovalAtr(ApprovalBehaviorAtr.ORIGINAL_REMAND);
			repoApprStateHr.update(appRoot);
		});
	}
	/**
	 * [RQ635]申請書を解除する
	 * @author hoatt
	 * @param インスタンスID rootStateID
	 * @param 社員ID employeeID
	 * @return ・解除を実行したかフラグ(true, false)
					true：解除を実行した
					false：解除を実行しなかった
	 */
	@Override
	public Boolean releaseHr(String rootStateID, String employeeID) {
		//解除を実行したかフラグ=false（初期化）
		Boolean executedFlag = false;
		//ドメインモデル「人事承認ルートインスタンス」を取得する
		Optional<ApprovalRootStateHr> opRootState = repoApprStateHr.getById(rootStateID);
		if(!opRootState.isPresent()){
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootStateHr root = opRootState.get();
		//109827 hoatt 2020.03.03
		root.getLstPhaseState().sort(Comparator.comparing(ApprovalPhaseStateHr::getPhaseOrder));
		//ドメインモデル「人事承認フェーズインスタンス」．順序を1～5の順でループする
		for(ApprovalPhaseStateHr phaseHr : root.getLstPhaseState()){
			//1.人事承認フェーズ毎の承認者を取得する(getApproverFromPhaseHr)
			List<String> listApprover = this.getApproverFromPhaseHr(this.convertToImport(phaseHr));
			if(CollectionUtil.isEmpty(listApprover)){
				continue;
			}
			Optional<ApproverInforHr> notUnApproved = this.getNotUnApproved(phaseHr.getLstApprovalFrame());
			//指定する社員が承認を行った承認者かチェックする
			Boolean phaseNotApprovalFlag = phaseHr.getApprovalAtr().equals(ApprovalBehaviorAtr.UNAPPROVED) && !notUnApproved.isPresent();
			if(phaseNotApprovalFlag.equals(Boolean.TRUE)){
				continue;
			}
			//1.解除できるかチェックする
			Boolean canRelease = this.canReleaseCheckHr(phaseHr, employeeID);
			if(canRelease.equals(Boolean.FALSE)){
				break;
			}
			//ドメインモデル「人事承認フェーズインスタンス」．「人事承認枠」1～5ループする
			//指定する社員が承認を行った承認者かチェックする
			phaseHr.getLstApprovalFrame().forEach(frame -> {
				frame.getLstApproverInfo().forEach(appr -> {
					if(!appr.getApprovalAtr().equals(ApprovalBehaviorAtr.UNAPPROVED) && 
							this.checkExist(Arrays.asList(appr.getApproverID()), appr.getAgentID(), employeeID)){
						//(ドメインモデル「人事承認枠」)承認区分=「未承認」、代行者=空
						appr.setApprovalAtr(ApprovalBehaviorAtr.UNAPPROVED);
						appr.setAgentID("");
						appr.setApprovalDate(null);
						appr.setApprovalReason("");
					}
				});
				
			});
			//「人事承認フェーズインスタンス」．承認区分=「未承認」
			phaseHr.setApprovalAtr(ApprovalBehaviorAtr.UNAPPROVED);
			//ドメインモデル「人事承認ルートインスタンス」の承認状態をUpdateする
			repoApprStateHr.update(root);
			//解除を実行したかフラグ=true
			executedFlag = true;
		}
		return executedFlag;
	}
	/**
	 * [RQ637]承認ルートインスタンスを新規作成する
	 * @author hoatt
	 * @param 人事承認状態 apprSttHr
	 * @return エラーフラグ　＝　True 失敗した場合 
	 * 		        エラーフラグ　＝　False OK場合 
	 */
	@Override
	public boolean createApprStateHr(ApprovalStateHrImport apprSttHr) {
		//エラーフラグ　＝　True
		if(apprSttHr == null) return true;
		try{
			//convert data
			ApprovalRootStateHr root = new ApprovalRootStateHr(apprSttHr.getRootStateID(),
				apprSttHr.getAppDate(), apprSttHr.getEmployeeID(),
				apprSttHr.getLstPhaseState().stream().map(ph -> ApprovalPhaseStateHr.convert(ph.getPhaseOrder(),
						ph.getApprovalAtr(), ph.getApprovalForm(), 
						ph.getLstApprovalFrame().stream().map(fr -> ApprovalFrameHr.convert(fr.getFrameOrder(),
								fr.getConfirmAtr(), fr.getAppDate(), fr.getLstApproverInfo().stream()
								.map(appr -> ApproverInforHr.convert(appr.getApproverID(), appr.getApprovalAtr(),
										appr.getAgentID(), appr.getApprovalDate(), appr.getApprovalReason()))
								.collect(Collectors.toList()))
								).collect(Collectors.toList()))
						).collect(Collectors.toList())
				);
			//ドメインモデル「人事承認ルートインスタンス」を追加
			repoApprStateHr.insert(root);
			return false;
		}catch(Exception ex){
			//エラーフラグ　＝　True
			return true;
		}
	}
	//1.解除できるかチェックする
	public Boolean canReleaseCheckHr(ApprovalPhaseStateHr phaseState, String employeeID) {
		//「承認フェーズインスタンス」．承認形態をチェックする
		if(phaseState.getApprovalForm().equals(ApprovalForm.EVERYONE_APPROVED)){//「承認フェーズインスタンス」．承認形態が全員承認
			for(ApprovalFrameHr frame : phaseState.getLstApprovalFrame()) {
				for(ApproverInforHr appr : frame.getLstApproverInfo()) {
					if(appr.getApproverID().equals(employeeID) ||
							(Strings.isNotBlank(appr.getAgentID()) && appr.getAgentID().equals(employeeID))){
						return true;
					}
				}
			}
			return false;
		}
		//「承認フェーズインスタンス」．承認形態が誰か一人
		//確定者を設定したかチェックする
		Optional<ApprovalFrameHr> opConfirmFrame = phaseState.getLstApprovalFrame().stream().filter(x -> x.getConfirmAtr().equals(ConfirmPerson.CONFIRM)).findAny();
		if(opConfirmFrame.isPresent()){//if文がtrue
			ApprovalFrameHr frame = opConfirmFrame.get();
			
			for(ApproverInforHr appr : frame.getLstApproverInfo()) {
				if(appr.getApproverID().equals(employeeID) ||
						(Strings.isNotBlank(appr.getAgentID()) && appr.getAgentID().equals(employeeID))){
					return true;
				}
			}
			return false;
		}
		//if文がfalse
		//指定する社員が承認を行った承認者かチェックする
		for(ApprovalFrameHr frame : phaseState.getLstApprovalFrame()) {
			for(ApproverInforHr appr : frame.getLstApproverInfo()) {
				if(appr.getApproverID().equals(employeeID) ||
						(Strings.isNotBlank(appr.getAgentID()) && appr.getAgentID().equals(employeeID))){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 一括解除する
	 * @param companyID
	 * @param rootStateID
	 */
	public void releaseAllHr(String rootStateID) {
		//ドメインモデル「人事承認ルートインスタンス」を取得する
		Optional<ApprovalRootStateHr> opRoot = repoApprStateHr.getById(rootStateID);
		if(!opRoot.isPresent()){
			throw new RuntimeException("状態：承認ルート取得失敗"+System.getProperty("line.separator")+"error: ApprovalRootState, ID: "+rootStateID);
		}
		ApprovalRootStateHr root = opRoot.get();
		root.getLstPhaseState().sort(Comparator.comparing(ApprovalPhaseStateHr::getPhaseOrder).reversed());
		//ドメインモデル「人事承認フェーズインスタンス」．順序を5～1の順でループする
		root.getLstPhaseState().stream().forEach(phase -> {
			phase.getLstApprovalFrame().forEach(frame -> {
				frame.getLstApproverInfo().forEach(appr ->{
					appr.setApprovalAtr(ApprovalBehaviorAtr.UNAPPROVED);
					appr.setAgentID("");
					appr.setApprovalDate(null);
					appr.setApprovalReason("");
				});
				
			});
			phase.setApprovalAtr(ApprovalBehaviorAtr.UNAPPROVED);
		});
		//ドメインモデル「人事承認ルートインスタンス」の承認状態をUpdateする
		repoApprStateHr.update(root);
	}
	/**
	 * 1.否認できるかチェックする
	 * @param 人事承認ルートインスタンス root
	 * @param 開始順序 order
	 * @param 社員ID employeeID
	 * @return ・否認できるフラグ(true, false)
					true：否認できる
					false：否認できない
	 */
	public Boolean checkCanDeny(ApprovalRootStateHr root, Integer order, String employeeID) {
		//終了状態：否認できるフラグ = true
		Boolean canDenyFlag = true;
		//ループ開始順序の異常をチェックする(check thu tu bat dau xu ly loop)
		if(root.getLstPhaseState().size() == 1 || order > 5){
			return canDenyFlag;
		}
		List<ApprovalPhaseStateHr> lstLoop = root.getLstPhaseState()
				.stream().filter(x -> x.getPhaseOrder() >= order)
				.sorted(Comparator.comparing(ApprovalPhaseStateHr::getPhaseOrder).reversed())
				.collect(Collectors.toList());
		//ループ開始順序(input)～１の順でループ
		for(ApprovalPhaseStateHr phase : lstLoop){
			//1.人事承認フェーズ毎の承認者を取得する(getApproverFromPhaseHr)
			List<String> lstApprverId = this.getApproverFromPhaseHr(this.convertToImport(phase));
			if(lstApprverId.isEmpty()){
				continue;
			}
			//ループ中の「人事承認フェーズインスタンス」．承認区分をチェックする(check trạng thái approvalAtr của phase)
			if(!phase.getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED)){//「人事承認フェーズインスタンス」．承認区分 ≠ 承認済
				//終了状態：否認できるフラグ = false
				canDenyFlag = false;
				return canDenyFlag;
			}
			//「人事承認フェーズインスタンス」．承認区分 = 承認済
			//該当人事承認フェーズに確定者が設定したかチェックする(Approval phase đang xử lý có người 確定者 hay không)
			Optional<ApprovalFrameHr> opFrameConf = phase.getLstApprovalFrame().stream()
				 	.filter(x -> x.getConfirmAtr().equals(ConfirmPerson.CONFIRM)).findAny();
			if(!opFrameConf.isPresent()){//false
				//承認を行ったのは指定する社員かチェックする
				Optional<ApproverInforHr> denyApproverInfor = Optional.empty();
				for(ApprovalFrameHr frame : phase.getLstApprovalFrame()) {
					for(ApproverInforHr appr : frame.getLstApproverInfo()) {
						if(appr.getApproverID().equals(employeeID)||
							(Strings.isNotBlank(appr.getAgentID()) && appr.getAgentID().equals(employeeID))){
							denyApproverInfor = Optional.of(appr);
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
			//true
			ApprovalFrameHr frameConf = opFrameConf.get();
			//指定する社員が確定者として承認を行ったかチェックする
			for(ApproverInforHr appr : frameConf.getLstApproverInfo()) {
				if(appr.getApproverID().equals(employeeID)||
					(Strings.isNotBlank(appr.getAgentID()) && appr.getAgentID().equals(employeeID))){
					canDenyFlag = false;
				} else {
					canDenyFlag = true;
				}
				return canDenyFlag;
			}
		}
		return canDenyFlag;
	}
	
	//convert from domain to import
	private PhaseStateHrImport convertToImport(ApprovalPhaseStateHr phase){
		return new PhaseStateHrImport(phase.getPhaseOrder(), phase.getApprovalAtr().value, phase.getApprovalForm().value,
				phase.getLstApprovalFrame().stream().map(c -> 
				new FrameHrImport(c.getFrameOrder(), c.getConfirmAtr().value, c.getAppDate(),
						c.getLstApproverInfo().stream()
						.map(appr -> new ApproverInfoHrImport(appr.getApproverID(), appr.getApprovalAtr().value,
								appr.getAgentID(), appr.getApprovalDate(), appr.getApprovalReason()))
						.collect(Collectors.toList()))
				).collect(Collectors.toList()));
	}
	//check 指定する社員が承認者かチェックする
	private boolean checkExist(List<String> lstApproverId, String agentId, String employeeId){
		if(lstApproverId.contains(employeeId) || (Strings.isNotBlank(agentId) && agentId.equals(employeeId))){
			return true;
		}
		return false;
	}
	/**
	 * 1.指定する人事承認フェーズの承認が完了したか
	 * @param ドメインモデル「承認フェーズインスタンス」 phase
	 * @return 承認完了フラグ(true, false)
				　true：指定する承認フェーズの承認が完了
				　false：指定する承認フェーズの承認がまだ未完了
	 */
	public Boolean isApprovalPhaseComplete(ApprovalPhaseStateHr phase) {
		String companyID = AppContexts.user().companyId();
		
		//人事承認フェーズインスタンス」．承認形態をチェックする
		if(phase.getApprovalForm().equals(ApprovalForm.EVERYONE_APPROVED)){//「人事承認フェーズインスタンス」．承認形態が全員承認
			//承認完了フラグ = true（初期化）
			//全員承認したかチェックする
			Optional<ApproverInforHr> notApproved = this.getNotApproved(phase.getLstApprovalFrame());
			if(!notApproved.isPresent()){
				//承認完了フラグ = true
				return true;
			}
			//以外
			//アルゴリズム「未承認の承認者一覧を取得する」を実行する(「Lấy list approver chưa approval)
			List<String> listUnapprApprover = this.getUnapproveApproverFromPhase(phase);
			//承認代行情報の取得処理
			ApprovalRepresenterOutput apprAgent = collectApprAgentSv.getApprovalAgentInfor(companyID, listUnapprApprover);
			if(apprAgent.getAllPathSetFlag().equals(Boolean.TRUE)){
				//承認完了フラグ = true
				return true;
			}
			return false;
		}
		//「人事承認フェーズインスタンス」．承認形態が誰か一人 
		//承認完了フラグ = false（初期化）
		Optional<ApprovalFrameHr> opFrameConfirm = phase.getLstApprovalFrame().stream().filter(x -> x.getConfirmAtr().equals(ConfirmPerson.CONFIRM)).findAny();
		if(opFrameConfirm.isPresent()){
			ApprovalFrameHr approvalFrame = opFrameConfirm.get();
			Optional<ApproverInforHr> appr = approvalFrame.getLstApproverInfo().stream()
					.filter(x -> x.getApprovalAtr()==ApprovalBehaviorAtr.APPROVED).findAny();
			if(appr.isPresent()){
				return true;
			}
			List<String> listApprover = approvalFrame.getLstApproverInfo().stream()
					.map(c -> c.getApproverID()).collect(Collectors.toList());
			ApprovalRepresenterOutput apprAgent = collectApprAgentSv.getApprovalAgentInfor(companyID, listApprover);
			if(apprAgent.getAllPathSetFlag().equals(Boolean.TRUE)){
				return true;
			}
			return false;
		}
		Optional<ApproverInforHr> appr = this.getApproved(phase);
		if(appr.isPresent()){
			return true;
		}
		//1.承認フェーズ毎の承認者を取得する(getApproverFromPhase)
		List<String> listApprover = this.getApproverFromPhaseHr(this.convertToImport(phase));
		ApprovalRepresenterOutput apprAgent = collectApprAgentSv.getApprovalAgentInfor(companyID, listApprover);
		return apprAgent.getAllPathSetFlag().equals(Boolean.TRUE);
	}
	private Optional<ApproverInforHr> getNotApproved(List<ApprovalFrameHr> listFrame) {
		for(ApprovalFrameHr frame : listFrame) {
			for(ApproverInforHr appr : frame.getLstApproverInfo()) {
				if(!appr.getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED)) {
					return Optional.of(appr);
				}
			}
		}
		return Optional.empty();
	}
	private Optional<ApproverInforHr> getNotUnApproved(List<ApprovalFrameHr> listFrame) {
		for(ApprovalFrameHr frame : listFrame) {
			for(ApproverInforHr appr : frame.getLstApproverInfo()) {
				if(!appr.getApprovalAtr().equals(ApprovalBehaviorAtr.UNAPPROVED)) {
					return Optional.of(appr);
				}
			}
		}
		return Optional.empty();
	}
	private List<String> getUnapproveApproverFromPhase(ApprovalPhaseStateHr phase) {
		List<String> lstResult = new ArrayList<>();
		phase.getLstApprovalFrame().forEach(frame -> {
			frame.getLstApproverInfo().forEach(appr -> {
				if(appr.getApprovalAtr().equals(ApprovalBehaviorAtr.UNAPPROVED)){
					lstResult.add(appr.getApproverID());
				}
			});
		});
		return lstResult.stream().distinct().collect(Collectors.toList());
	}
	public Optional<ApproverInforHr> getApproved(ApprovalPhaseStateHr phase) {
		for(ApprovalFrameHr frame : phase.getLstApprovalFrame()) {
			for(ApproverInforHr appr : frame.getLstApproverInfo()) {
				if(appr.getApprovalAtr().equals(ApprovalBehaviorAtr.APPROVED)) {
					return Optional.of(appr);
				}
			}
		}
		return Optional.empty();
	}
	/**
	 * 2.承認全体が完了したか
	 * @param 承認ルートインスタンス apprState
	 * @return 承認完了フラグ(true, false)
				　true：承認全体が完了
				　false：承認全体がまだ未完了
	 */
	@Override
	public Boolean isApprovedAllHr(ApprovalStateHrImport apprState) {
		//承認完了フラグ = false（初期化）
		Boolean approveAllFlag = false;
		apprState.getLstPhaseState().stream()
				.sorted((a,b) -> a.getPhaseOrder().compareTo(b.getPhaseOrder()))
				.collect(Collectors.toList());
		for(PhaseStateHrImport phase : apprState.getLstPhaseState()){
			//「承認フェーズインスタンス」．承認区分が承認済かチェックする
			if(phase.getApprovalAtr() == ApprovalBehaviorAtr.APPROVED.value){
				approveAllFlag = true;
				break;
			}
			//1.人事承認フェーズ毎の承認者を取得する
			List<String> listApprover = this.getApproverFromPhaseHr(phase);
			//承認者一覧(output)に承認者がいるかチェックする
			if(CollectionUtil.isEmpty(listApprover)){
				continue;
			}
			//承認代行情報の取得処理
			ApprovalRepresenterOutput apprAgentOutput = collectApprAgentSv.getApprovalAgentInfor(AppContexts.user().companyId(), listApprover);
			if(apprAgentOutput.getAllPathSetFlag().equals(Boolean.FALSE)){
				break;
			}
		}
		return approveAllFlag;
	}
}
