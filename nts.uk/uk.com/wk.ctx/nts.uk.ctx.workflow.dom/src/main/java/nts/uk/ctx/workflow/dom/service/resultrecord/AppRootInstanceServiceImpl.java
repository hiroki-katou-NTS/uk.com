package nts.uk.ctx.workflow.dom.service.resultrecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.PersonImport;
import nts.uk.ctx.workflow.dom.agent.AgentRepository;
import nts.uk.ctx.workflow.dom.agent.output.AgentInfoOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalForm;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmPerson;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalFrame;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApproverState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.RootType;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootConfirm;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootConfirmRepository;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootInstance;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootInstanceRepository;
import nts.uk.ctx.workflow.dom.resultrecord.RecordRootType;
import nts.uk.ctx.workflow.dom.service.ApprovalRootStateStatusService;
import nts.uk.ctx.workflow.dom.service.CollectApprovalAgentInforService;
import nts.uk.ctx.workflow.dom.service.JudgmentApprovalStatusService;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRepresenterOutput;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRootStateStatus;
import nts.uk.ctx.workflow.dom.service.output.ApprovalStatusOutput;
import nts.uk.ctx.workflow.dom.service.output.ApproverPersonOutput;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AppRootInstanceServiceImpl implements AppRootInstanceService {
	
	@Inject
	private AppRootConfirmRepository appRootConfirmRepository;
	
	@Inject
	private ApprovalRootStateStatusService approvalRootStateStatusService;
	
	@Inject
	private AppRootInstanceRepository appRootInstanceRepository; 
	
	@Inject
	private JudgmentApprovalStatusService judgmentApprovalStatusService;
	
	@Inject
	private CollectApprovalAgentInforService collectApprovalAgentInforService;
	
	@Inject
	private EmployeeAdapter employeeAdapter;
	
	@Inject
	private AgentRepository agentRepository;

	@Override
	public List<ApprovalRootStateStatus> getAppRootStatusByEmpsPeriod(List<String> employeeIDLst, DatePeriod period, RecordRootType rootType) {
		String companyID = AppContexts.user().companyId();
		List<ApprovalRootStateStatus> appRootStatusLst = new ArrayList<>();
		// 対象者と期間から承認ルート中間データを取得する
		List<AppRootInstancePeriod> appRootInstancePeriodLst = this.getAppRootInstanceByEmpPeriod(employeeIDLst, period, rootType);
		// INPUT．対象者社員IDの先頭から最後へループ
		employeeIDLst.forEach(employeeIDLoop -> {
			// INPUT．期間の開始日から終了日へループ
			for(GeneralDate loopDate = period.start(); loopDate.beforeOrEquals(period.end()); loopDate = loopDate.addDays(1)){
				// 対象日の承認ルート中間データを取得する
				AppRootInstance appRootInstance = this.getAppRootInstanceByDate(loopDate, 
						appRootInstancePeriodLst.stream().filter(x -> x.getEmployeeID().equals(employeeIDLoop)).findAny().get().getAppRootInstanceLst());
				if(appRootInstance==null){
					continue;
				}
				// 対象日の就業実績確認状態を取得する
				AppRootConfirm appRootConfirm = this.getAppRootConfirmByDate(companyID, employeeIDLoop, loopDate, rootType);
				// 中間データから承認ルートインスタンスに変換する
				ApprovalRootState approvalRootState = this.convertFromAppRootInstance(appRootInstance, appRootConfirm);
				// 承認ルート状況を取得する
				appRootStatusLst.addAll(approvalRootStateStatusService.getApprovalRootStateStatus(Arrays.asList(approvalRootState)));
			}
		});
		return appRootStatusLst;
	}

	@Override
	public List<AppRootInstancePeriod> getAppRootInstanceByEmpPeriod(List<String> employeeIDLst, DatePeriod period,
			RecordRootType rootType) {
		List<AppRootInstance> appRootInstanceLst = appRootInstanceRepository.findByEmpLstPeriod(employeeIDLst, period, rootType);
		List<AppRootInstancePeriod> dbList = appRootInstanceLst.stream().collect(Collectors.groupingBy(AppRootInstance::getEmployeeID)).entrySet().stream()
				.map(x -> new AppRootInstancePeriod(x.getKey(), x.getValue())).collect(Collectors.toList());
		List<AppRootInstancePeriod> result = new ArrayList<>();
		employeeIDLst.forEach(x -> {
			Optional<AppRootInstancePeriod> opAppRootInstancePeriod = dbList.stream().filter(y -> y.getEmployeeID().equals(x)).findAny();
			if(opAppRootInstancePeriod.isPresent()){
				result.add(new AppRootInstancePeriod(x, opAppRootInstancePeriod.get().getAppRootInstanceLst()));
			} else {
				result.add(new AppRootInstancePeriod(x, Collections.emptyList()));
			}
		});
		return result;
	}

	@Override
	public AppRootInstance getAppRootInstanceByDate(GeneralDate date, List<AppRootInstance> appRootInstanceLst) {
		// 承認ルートなしフラグ=true
		boolean noAppRootFlag = true;
		AppRootInstance result = null;
		// INPUT．承認ルート中間データ一覧の先頭から最後へループする
		for(AppRootInstance appRootInstance : appRootInstanceLst){
			// ループ中の「承認ルート中間データ」．履歴期間．開始日とINPUT．年月日を比較する
			if(appRootInstance.getDatePeriod().start().beforeOrEquals(date)){
				// 承認ルートなしフラグ=false
				noAppRootFlag = false;
				result = appRootInstance;
				break;
			}
		}
		// 承認ルートなしフラグをチェックする
		if(noAppRootFlag){
			return null;
		}
		return result;
	}

	@Override
	public AppRootConfirm getAppRootConfirmByDate(String companyID, String employeeID, GeneralDate date,
			RecordRootType rootType) {
		// ドメインモデル「就業実績確認状態」を取得する
		Optional<AppRootConfirm> opAppRootConfirm = appRootConfirmRepository.findByEmpDate(companyID, employeeID, date, rootType);
		if(!opAppRootConfirm.isPresent()){
			throw new BusinessException("error on process: 対象日の就業実績確認状態を取得する");
		}
		return opAppRootConfirm.get();
	}
	
	@Override
	public ApprovalRootState convertFromAppRootInstance(AppRootInstance appRootInstance, AppRootConfirm appRootConfirm) {
		// output「承認ルートインスタンス」を初期化
		ApprovalRootState approvalRootState = new ApprovalRootState();
		approvalRootState.setRootType(EnumAdaptor.valueOf(appRootInstance.getRootType().value, RootType.class));
		approvalRootState.setEmployeeID(appRootInstance.getEmployeeID());
		approvalRootState.setApprovalRecordDate(appRootConfirm.getRecordDate());
		// ドメインモデル「承認ルート中間データ」の値をoutput「承認ルートインスタンス」に入れる
		appRootInstance.getListAppPhase().forEach(appPhaseInstance -> {
			ApprovalPhaseState approvalPhaseState = new ApprovalPhaseState();
			approvalPhaseState.setApprovalAtr(ApprovalBehaviorAtr.UNAPPROVED);
			approvalPhaseState.setApprovalForm(appPhaseInstance.getApprovalForm());
			approvalPhaseState.setPhaseOrder(appPhaseInstance.getPhaseOrder());
			approvalPhaseState.setApprovalForm(ApprovalForm.EVERYONE_APPROVED);
			appPhaseInstance.getListAppFrame().forEach(appFrameInstance -> {
				ApprovalFrame approvalFrame = new ApprovalFrame();
				approvalFrame.setFrameOrder(appFrameInstance.getFrameOrder());
				approvalFrame.setConfirmAtr(appFrameInstance.isConfirmAtr() ? ConfirmPerson.CONFIRM : ConfirmPerson.NOT_CONFIRM);
				approvalFrame.setApprovalAtr(ApprovalBehaviorAtr.UNAPPROVED);
				appFrameInstance.getListApprover().forEach(approver -> {
					ApproverState approverState = new ApproverState();
					approverState.setCompanyID(appRootInstance.getCompanyID());
					approverState.setDate(appRootConfirm.getRecordDate());
					approverState.setApproverID(approver);
					approvalFrame.getListApproverState().add(approverState);
				});
				approvalPhaseState.getListApprovalFrame().add(approvalFrame);
			});
			approvalRootState.getListApprovalPhaseState().add(approvalPhaseState);
		});
		// ドメインモデル「就業実績確認状態」の値をoutput「承認ルートインスタンス」に入れる
		appRootConfirm.getListAppPhase().forEach(appPhaseConfirm -> {
			ApprovalPhaseState approvalPhaseState = approvalRootState.getListApprovalPhaseState().stream()
					.filter(x -> x.getPhaseOrder()==appPhaseConfirm.getPhaseOrder()).findAny().get();
			approvalPhaseState.setApprovalAtr(appPhaseConfirm.getAppPhaseAtr());
			appPhaseConfirm.getListAppFrame().forEach(appFrameConfirm -> {
				ApprovalFrame approvalFrame = approvalPhaseState.getListApprovalFrame().stream()
						.filter(y -> y.getFrameOrder()==appFrameConfirm.getFrameOrder()).findAny().get();
				approvalFrame.setApprovalAtr(ApprovalBehaviorAtr.APPROVED);
				approvalFrame.setApprovalDate(appFrameConfirm.getApprovalDate());
				approvalFrame.setApproverID(appFrameConfirm.getApproverID().orElse(null));
				approvalFrame.setRepresenterID(appFrameConfirm.getRepresenterID().orElse(null));
			});
		});
		return approvalRootState;
	}

	@Override
	public List<ApproverToApprove> getApproverByPeriod(List<String> employeeIDLst, DatePeriod period, RecordRootType rootType) {
		String companyID = AppContexts.user().companyId();
		List<ApproverToApprove> approverToApproveLst = new ArrayList<>();
		// 対象者と期間から承認ルート中間データを取得する
		List<AppRootInstancePeriod> appRootInstancePeriodLst = this.getAppRootInstanceByEmpPeriod(employeeIDLst, period, rootType);
		// INPUT．対象者社員IDの先頭から最後へループ
		employeeIDLst.forEach(employeeIDLoop -> {
			// INPUT．期間の開始日から終了日へループ
			for(GeneralDate loopDate = period.start(); loopDate.beforeOrEquals(period.end()); loopDate = loopDate.addDays(1)){
				// 対象日の承認ルート中間データを取得する
				AppRootInstance appRootInstance = this.getAppRootInstanceByDate(loopDate, 
						appRootInstancePeriodLst.stream().filter(x -> x.getEmployeeID().equals(employeeIDLoop)).findAny().get().getAppRootInstanceLst());
				// 対象日の就業実績確認状態を取得する
				AppRootConfirm appRootConfirm = this.getAppRootConfirmByDate(companyID, employeeIDLoop, loopDate, rootType);
				// 中間データから承認ルートインスタンスに変換する
				ApprovalRootState approvalRootState = this.convertFromAppRootInstance(appRootInstance, appRootConfirm);
				// 承認ルート状況を取得する
				approverToApproveLst.add(this.getApproverToApprove(approvalRootState));
			}
		});
		return approverToApproveLst;
	}

	@Override
	public ApproverToApprove getApproverToApprove(ApprovalRootState approvalRootState) {
		String companyID = AppContexts.user().companyId();
		// output「ルート状況」を初期化する
		ApproverToApprove approverToApprove = new ApproverToApprove(
				approvalRootState.getApprovalRecordDate(),
				approvalRootState.getEmployeeID(),
				Collections.emptyList());
		List<String> approverIDLst = new ArrayList<>();
		// ドメインモデル「承認フェーズインスタンス」．順序5～1の順でループする
		List<ApprovalPhaseState> approvalPhaseStateLst = approvalRootState.getListApprovalPhaseState().stream()
				.sorted(Comparator.comparing(ApprovalPhaseState::getPhaseOrder).reversed()).collect(Collectors.toList());
		for(ApprovalPhaseState approvalPhaseState : approvalPhaseStateLst){
			// アルゴリズム「承認フェーズ毎の承認者を取得する」を実行する
			List<String> approverLst = judgmentApprovalStatusService.getApproverFromPhase(approvalPhaseState);
			if(CollectionUtil.isEmpty(approverLst)){
				continue;
			}
			// ループ中の承認フェーズをチェックする
			if(approvalPhaseState.getApprovalAtr()==ApprovalBehaviorAtr.APPROVED ||
					approvalPhaseState.getApprovalAtr()==ApprovalBehaviorAtr.DENIAL){
				break;
			}
			// ループ中の承認フェーズが承認中のフェーズかチェックする
			boolean checkPhase = judgmentApprovalStatusService.judgmentLoopApprovalPhase(approvalRootState, approvalPhaseState, false);
			if(!checkPhase){
				continue;
			}
			approvalPhaseState.getListApprovalFrame().forEach(frameState -> {
				if(frameState.getApprovalAtr()!=ApprovalBehaviorAtr.UNAPPROVED){
					return;
				}
				approverIDLst.addAll(frameState.getListApproverState().stream().map(x -> x.getApproverID()).collect(Collectors.toList()));
			});
		}
		// アルゴリズム「指定した社員が指定した承認者リストの代行承認者かの判断」を実行する
		ApprovalRepresenterOutput approvalRepresenterOutput = collectApprovalAgentInforService.getApprovalAgentInfor(companyID, approverIDLst);
		// 取得した承認代行者リストを、output「承認すべき承認者」に追加する
		approverIDLst.addAll(approvalRepresenterOutput.getListAgent());
		approverIDLst.forEach(approverID -> {
			// imported（ワークフロー）「社員」を取得する
			PersonImport personImport = employeeAdapter.getEmployeeInformation(approverID);
			approverToApprove.getAuthorList().add(new ApproverEmployee(
					personImport.getSID(), 
					personImport.getEmployeeCode(), 
					personImport.getEmployeeName()));
		});
		return approverToApprove;
	}

	@Override
	public ApprovalPersonInstance getApproverAndAgent(String approverID, DatePeriod period, RecordRootType rootType) {
		String companyID = AppContexts.user().companyId();
		ApprovalPersonInstance approvalPersonInstance = new ApprovalPersonInstance();
		// 承認者と期間から承認ルート中間データを取得する
		List<AppRootInstance> appRootInstanceLst = appRootInstanceRepository.findByEmpLstPeriod(Arrays.asList(approverID), period, rootType);
		// 取得した「承認ルート中間データ」をOUTPUTに追加する
		appRootInstanceLst.forEach(appRootInstance -> {
			ApprovalRouteDetails approvalRouteDetails = new ApprovalRouteDetails(appRootInstance, approverID, null, null, null);
			approvalPersonInstance.getApproverRoute().add(approvalRouteDetails);
		});
		// ドメインモデル「代行承認」を取得する
		List<AgentInfoOutput> agentInfoOutputLst = agentRepository.findAgentByPeriod(companyID, Arrays.asList(approverID), period.start(), period.end(), 1);
		// 取得した「代行承認」先頭から最後へループ
		agentInfoOutputLst.forEach(agentInfor -> {
			// 承認者と期間から承認ルート中間データを取得する
			List<AppRootInstance> appRootInstanceAgentLst = appRootInstanceRepository.findByEmpLstPeriod(
					Arrays.asList(agentInfor.getAgentID()), 
					new DatePeriod(agentInfor.getStartDate(), agentInfor.getEndDate()), 
					rootType);
			// 取得した「承認ルート中間データ」をOUTPUTに追加する
			appRootInstanceAgentLst.forEach(appRootInstanceAgent -> {
				ApprovalRouteDetails approvalRouteDetailAgent = new ApprovalRouteDetails(
						appRootInstanceAgent, 
						approverID, 
						Optional.of(agentInfor.getAgentID()), 
						Optional.of(agentInfor.getStartDate()), 
						Optional.of(agentInfor.getEndDate()));
				approvalPersonInstance.getAgentRoute().add(approvalRouteDetailAgent);
			});
		});
		return approvalPersonInstance;
	}

	@Override
	public boolean isDataExist(String approverID, DatePeriod period, RecordRootType rootType) {
		// 承認者(承認代行を含め)と期間から承認ルート中間データを取得する
		ApprovalPersonInstance approvalPersonInstance = this.getApproverAndAgent(approverID, period, rootType);
		// 取得した「承認者になる中間データ」．承認者としての承認ルートと代行者としての承認ルートの件数をチェックする
		if(CollectionUtil.isEmpty(approvalPersonInstance.getAgentRoute()) || 
				CollectionUtil.isEmpty(approvalPersonInstance.getApproverRoute())){
			return false;
		}
		// 承認者としての承認すべきデータがあるか
		if(this.isDataAgentExist(period, approvalPersonInstance.getAgentRoute())){
			return true;
		}
		// 代行者としての承認すべきデータがあるか
		if(this.isDataApproverExist(period, approvalPersonInstance.getApproverRoute())){
			return true;
		}
		return false;
	}

	@Override
	public boolean isDataApproverExist(DatePeriod period, List<ApprovalRouteDetails> approverRouteLst) {
		String companyID = AppContexts.user().companyId();
		// INPUT．「承認ルートの詳細」(List)の件数をチェックする
		if(CollectionUtil.isEmpty(approverRouteLst)){
			return false;
		}
		for(ApprovalRouteDetails approvalRouteDetails : approverRouteLst){
			DatePeriod loopPeriod = approvalRouteDetails.getAppRootInstance().getDatePeriod();
			for(GeneralDate loopDate = loopPeriod.start(); loopDate.beforeOrEquals(loopPeriod.end()); loopDate = loopDate.addDays(1)){
				// 対象日の承認ルート中間データを取得する
				AppRootInstance appRootInstance = this.getAppRootInstanceByDate(loopDate, Arrays.asList(approvalRouteDetails.getAppRootInstance()));
				// 対象日の就業実績確認状態を取得する
				AppRootConfirm appRootConfirm = this.getAppRootConfirmByDate(companyID, approvalRouteDetails.getEmployeeID(), loopDate, RecordRootType.CONFIRM_WORK_BY_DAY);
				// 中間データから承認ルートインスタンスに変換する
				ApprovalRootState approvalRootState = this.convertFromAppRootInstance(appRootInstance, appRootConfirm);
				// 指定した社員が承認できるかの判断(NoDBACCESS)
				ApproverPersonOutput approverPersonOutput = judgmentApprovalStatusService.judgmentTargetPerCanApproveNoDB(approvalRootState);
				// 実行結果をチェックする
				if(approverPersonOutput.getAuthorFlag()&&
						(approverPersonOutput.getApprovalAtr()==ApprovalBehaviorAtr.UNAPPROVED)&&
						(!approverPersonOutput.getExpirationAgentFlag())){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isDataAgentExist(DatePeriod period, List<ApprovalRouteDetails> agentRouteLst) {
		String companyID = AppContexts.user().companyId();
		// INPUT．「承認ルートの詳細」(List)の件数をチェックする
		if(CollectionUtil.isEmpty(agentRouteLst)){
			return false;
		}
		for(ApprovalRouteDetails approvalRouteDetails : agentRouteLst){
			DatePeriod loopPeriod = approvalRouteDetails.getAppRootInstance().getDatePeriod();
			for(GeneralDate loopDate = loopPeriod.start(); loopDate.beforeOrEquals(loopPeriod.end()); loopDate = loopDate.addDays(1)){
				// 対象日の承認ルート中間データを取得する
				AppRootInstance appRootInstance = this.getAppRootInstanceByDate(loopDate, Arrays.asList(approvalRouteDetails.getAppRootInstance()));
				if((approvalRouteDetails.getStartDate().isPresent()&&approvalRouteDetails.getStartDate().get().beforeOrEquals(loopDate)) ||
						(approvalRouteDetails.getEndDate().isPresent()&&approvalRouteDetails.getEndDate().get().afterOrEquals(loopDate))){
					// 対象日の就業実績確認状態を取得する
					AppRootConfirm appRootConfirm = this.getAppRootConfirmByDate(companyID, approvalRouteDetails.getEmployeeID(), loopDate, RecordRootType.CONFIRM_WORK_BY_DAY);
					// 中間データから承認ルートインスタンスに変換する
					ApprovalRootState approvalRootState = this.convertFromAppRootInstance(appRootInstance, appRootConfirm);
					// 指定した社員が承認できるかの判断(NoDBACCESS)
					ApproverPersonOutput approverPersonOutput = judgmentApprovalStatusService.judgmentTargetPerCanApproveNoDB(approvalRootState);
					// 実行結果をチェックする
					if(approverPersonOutput.getAuthorFlag()&&
							(approverPersonOutput.getApprovalAtr()==ApprovalBehaviorAtr.UNAPPROVED)&&
							(!approverPersonOutput.getExpirationAgentFlag())){
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public RouteSituation getRouteSituationByEmp(ApprovalRootState approvalRootState, String employeeID, List<String> agentLst) {
		String companyID = AppContexts.user().companyId();
		// output「ルート状況」を初期化する
		RouteSituation routeSituation = new RouteSituation(
				approvalRootState.getApprovalRecordDate(),
				approvalRootState.getEmployeeID(),
				null,
				Optional.of(new ApprovalStatus(
						ReleaseDivision.NOT_RELEASE,
						ApprovalActionByEmp.NOT_APPROVAL)));
		// 承認中のフェーズの承認者か = false
		boolean approverOfApprovedPhase = false;
		// 基準社員のフェーズ=0
		int employeePhase = 0;
		// 承認中のフェーズ=0
		int approvedPhase = 0;
		ApprovalBehaviorAtr approvalPhaseEnum = ApprovalBehaviorAtr.UNAPPROVED;
		// ドメインモデル「承認フェーズインスタンス」．順序5～1の順でループする
		List<ApprovalPhaseState> approvalPhaseStateLst = approvalRootState.getListApprovalPhaseState().stream()
				.sorted(Comparator.comparing(ApprovalPhaseState::getPhaseOrder).reversed()).collect(Collectors.toList());
		for(ApprovalPhaseState approvalPhaseState : approvalPhaseStateLst){
			// アルゴリズム「承認フェーズ毎の承認者を取得する」を実行する
			List<String> approverLst = judgmentApprovalStatusService.getApproverFromPhase(approvalPhaseState);
			if(CollectionUtil.isEmpty(approverLst)){
				continue;
			}
			if(approvalPhaseState.getApprovalAtr()!=ApprovalBehaviorAtr.APPROVED){
				// 承認中のフェーズ＝ループ中のフェーズ．順序
				approvedPhase = approvalPhaseState.getPhaseOrder();
				// フェーズ承認区分＝ループ中のフェーズ．承認区分
				approvalPhaseEnum = approvalPhaseState.getApprovalAtr();
			}
			// アルゴリズム「承認状況の判断」を実行する
			ApprovalStatusOutput approvalStatusOutput = judgmentApprovalStatusService.judmentApprovalStatusNodataDatabaseAcess(companyID, approvalPhaseState, employeeID, agentLst);
			if(approvalPhaseState.getPhaseOrder()==5&&(approvalPhaseState.getApprovalAtr()==ApprovalBehaviorAtr.APPROVED)){
				// output「ルート状況」をセットする
				ApprovalStatus approvalStatus = this.setStatusFrame(approvalStatusOutput);
				routeSituation.setApprovalStatus(Optional.of(approvalStatus));
				// 基準社員のフェーズ＝ループ中のフェーズ．順序
				employeePhase = approvalPhaseState.getPhaseOrder();
				break;
			}
			if(approverOfApprovedPhase&&(approvalPhaseState.getApprovalAtr()==ApprovalBehaviorAtr.APPROVED)){
				break;
			}
			if(approvalStatusOutput.getApprovalFlag()&&(approvalPhaseState.getApprovalAtr()==ApprovalBehaviorAtr.UNAPPROVED)){
				// 承認中のフェーズの承認者か=true
				approverOfApprovedPhase = true;
			}
			// output「ルート状況」をセットする
			ApprovalStatus approvalStatus = this.setStatusFrame(approvalStatusOutput);
			routeSituation.setApprovalStatus(Optional.of(approvalStatus));
			if(approvalStatusOutput.getApprovableFlag()){
				// 基準社員のフェーズ＝ループ中のフェーズ．順序
				employeePhase = approvalPhaseState.getPhaseOrder();
			}
			if(approvalPhaseState.getApprovalAtr()==ApprovalBehaviorAtr.APPROVED){
				break;
			}
			Optional<ApprovalFrame> frameApproved = approvalPhaseState.getListApprovalFrame().stream()
					.filter(x -> x.getApprovalAtr()==ApprovalBehaviorAtr.APPROVED).findAny();
			if(frameApproved.isPresent()){
				break;
			}
		}
		// output「ルート状況」をセットする
		if((approvedPhase==employeePhase)&&approvalPhaseEnum==ApprovalBehaviorAtr.UNAPPROVED){
			routeSituation.setApproverEmpState(ApproverEmpState.PHASE_DURING);
		} else if(approvedPhase==0){
			routeSituation.setApproverEmpState(ApproverEmpState.COMPLETE);
		} else if((approvedPhase==employeePhase)&&approvalPhaseEnum==ApprovalBehaviorAtr.UNAPPROVED){
			routeSituation.setApproverEmpState(ApproverEmpState.COMPLETE);
		} else if(approvedPhase < employeePhase){
			routeSituation.setApproverEmpState(ApproverEmpState.PHASE_LESS);
		} else {
			routeSituation.setApproverEmpState(ApproverEmpState.PHASE_PASS);
		}
		return routeSituation;
	}
	
	private ApprovalStatus setStatusFrame(ApprovalStatusOutput approvalStatusOutput){
		ApprovalStatus approvalStatus = new ApprovalStatus();
		// 承認状況．解除可否区分
		if((approvalStatusOutput.getApprovalAtr()==ApprovalBehaviorAtr.APPROVED)&&approvalStatusOutput.getApprovableFlag()){
			approvalStatus.setReleaseAtr(ReleaseDivision.RELEASE);
		} else {
			approvalStatus.setReleaseAtr(ReleaseDivision.NOT_RELEASE);
		}
		// 承認状況．基準社員の承認アクション
		if(approvalStatusOutput.getApprovableFlag()){
			approvalStatus.setApprovalAction(ApprovalActionByEmp.NOT_APPROVAL);
		} else if(approvalStatusOutput.getApprovalAtr()==ApprovalBehaviorAtr.UNAPPROVED){
			approvalStatus.setApprovalAction(ApprovalActionByEmp.APPROVAL_REQUIRE);
		} else {
			approvalStatus.setApprovalAction(ApprovalActionByEmp.APPROVALED);
		}
		return approvalStatus;
	}

	@Override
	public ApprovalEmpStatus getApprovalEmpStatus(String employeeID, DatePeriod period, RecordRootType rootType) {
		// 承認者(承認代行を含め)と期間から承認ルート中間データを取得する
		ApprovalPersonInstance approvalPersonInstance = getApproverAndAgent(employeeID, period, rootType);
		List<String> agentLst = approvalPersonInstance.getAgentRoute().stream().map(x -> x.getAgentID().orElse(null))
				.filter(x -> Strings.isNotBlank(x)).collect(Collectors.toList());
		// output「基準社員の承認対象者」を初期化する
		ApprovalEmpStatus approvalEmpStatus = new ApprovalEmpStatus(employeeID, Collections.emptyList());
		// 取得した「承認者としての承認ルート」．承認ルートの詳細の件数をチェックする
		List<RouteSituation> approverRouteLst = new ArrayList<>();
		if(!CollectionUtil.isEmpty(approvalPersonInstance.getApproverRoute())){
			// 承認者としてのルート状況を取得する
			approverRouteLst = this.getApproverRouteSituation(period, approvalPersonInstance.getApproverRoute(), agentLst);
		}
		// 取得した「代行者としての承認ルート」．承認ルートの詳細の件数をチェックする
		List<RouteSituation> agentRouteLst = new ArrayList<>();
		if(!CollectionUtil.isEmpty(approvalPersonInstance.getAgentRoute())){
			// 代行者としてのルート状況を取得する
			agentRouteLst = this.getAgentRouteSituation(period, approvalPersonInstance.getAgentRoute(), agentLst);
		}
		// outputの整合
		List<RouteSituation> mergeLst = this.mergeRouteSituationLst(approverRouteLst, agentRouteLst);
		// 「ルート状況」リスト整合処理後をoutput「基準社員の承認対象者」に追加する
		approvalEmpStatus.getRouteSituationLst().addAll(mergeLst);
		return approvalEmpStatus;
	}

	@Override
	public List<RouteSituation> getApproverRouteSituation(DatePeriod period, List<ApprovalRouteDetails> approverRouteLst, List<String> agentLst) {
		String companyID = AppContexts.user().companyId();
		List<RouteSituation> routeSituationLst = new ArrayList<>();
		// 取得した対象者(List)の先頭から最後へループ
		for(ApprovalRouteDetails approvalRouteDetails : approverRouteLst){
			DatePeriod loopPeriod = approvalRouteDetails.getAppRootInstance().getDatePeriod();
			// INPUT．期間の開始日から終了日へループ
			for(GeneralDate loopDate = loopPeriod.start(); loopDate.beforeOrEquals(loopPeriod.end()); loopDate = loopDate.addDays(1)){
				// 対象日の承認ルート中間データを取得する
				AppRootInstance appRootInstance = this.getAppRootInstanceByDate(loopDate, Arrays.asList(approvalRouteDetails.getAppRootInstance()));
				// 対象日の就業実績確認状態を取得する
				AppRootConfirm appRootConfirm = this.getAppRootConfirmByDate(companyID, approvalRouteDetails.getEmployeeID(), loopDate, RecordRootType.CONFIRM_WORK_BY_DAY);
				// 中間データから承認ルートインスタンスに変換する
				ApprovalRootState approvalRootState = this.convertFromAppRootInstance(appRootInstance, appRootConfirm);
				// 基準社員を元にルート状況を取得する
				RouteSituation routeSituation = this.getRouteSituationByEmp(approvalRootState, approvalRouteDetails.getEmployeeID(), agentLst);
				// 実行結果をoutput「ルート状況」に追加する
				routeSituationLst.add(routeSituation);
			}
		}
		return routeSituationLst;
	}

	@Override
	public List<RouteSituation> getAgentRouteSituation(DatePeriod period, List<ApprovalRouteDetails> agentRouteLst, List<String> agentLst) {
		String companyID = AppContexts.user().companyId();
		List<RouteSituation> routeSituationLst = new ArrayList<>();
		// 取得した対象者(List)の先頭から最後へループ
		for(ApprovalRouteDetails approvalRouteDetails : agentRouteLst){
			DatePeriod loopPeriod = approvalRouteDetails.getAppRootInstance().getDatePeriod();
			// INPUT．期間の開始日から終了日へループ
			for(GeneralDate loopDate = loopPeriod.start(); loopDate.beforeOrEquals(loopPeriod.end()); loopDate = loopDate.addDays(1)){
				// 対象日の承認ルート中間データを取得する
				AppRootInstance appRootInstance = this.getAppRootInstanceByDate(loopDate, Arrays.asList(approvalRouteDetails.getAppRootInstance()));
				// ループする日は代行期間内かチェックする
				if((approvalRouteDetails.getStartDate().isPresent()&&approvalRouteDetails.getStartDate().get().beforeOrEquals(loopDate)) ||
						(approvalRouteDetails.getEndDate().isPresent()&&approvalRouteDetails.getEndDate().get().afterOrEquals(loopDate))){
					// 対象日の就業実績確認状態を取得する
					AppRootConfirm appRootConfirm = this.getAppRootConfirmByDate(companyID, approvalRouteDetails.getEmployeeID(), loopDate, RecordRootType.CONFIRM_WORK_BY_DAY);
					// 中間データから承認ルートインスタンスに変換する
					ApprovalRootState approvalRootState = this.convertFromAppRootInstance(appRootInstance, appRootConfirm);
					// 基準社員を元にルート状況を取得する
					RouteSituation routeSituation = this.getRouteSituationByEmp(approvalRootState, approvalRouteDetails.getEmployeeID(), agentLst);
					// 実行結果をoutput「ルート状況」に追加する
					routeSituationLst.add(routeSituation);
				}
			}
		}
		return routeSituationLst;
	}

	@Override
	public List<RouteSituation> mergeRouteSituationLst(List<RouteSituation> approverRouteLst, List<RouteSituation> agentRouteLst) {
		// output「ルート状況」を空リストに初期化する
		List<RouteSituation> routeSituationLst = new ArrayList<>();
		// INPUT．「ルート状況」リスト①をoutput「ルート状況」を空リストに追加する
		routeSituationLst.addAll(approverRouteLst);
		// 「ルート状況」リスト②を先頭から最後へループする
		agentRouteLst.forEach(route -> {
			// output「ルート状況」に同じ日、同じ対象者は存在するかチェックする
			Optional<RouteSituation> opRouteSituation = routeSituationLst.stream().filter(x -> 
				(x.getEmployeeID()==route.getEmployeeID())&&(x.getDate().equals(route.getDate()))).findAny();
			if(opRouteSituation.isPresent()){
				RouteSituation routeSituation = opRouteSituation.get();
				// ループ中の「ルート状況」．基準社員の承認アクションをチェックする
				Optional<ApprovalStatus> opApprovalStatus = routeSituation.getApprovalStatus();
				if(opApprovalStatus.isPresent()){
					ApprovalStatus approvalStatus = opApprovalStatus.get();
					if(approvalStatus.getApprovalAction()==ApprovalActionByEmp.APPROVAL_REQUIRE){
						// 対象者と対象日がループ中の「ルート状況」が一致する項目を更新する
						route.setApprovalStatus(opApprovalStatus);
					}
				}
			} else {
				// ループ中の「ルート状況」をoutput「ルート状況」を空リストに追加する
				routeSituationLst.add(route);
			}
		});
		return routeSituationLst;
	}
}