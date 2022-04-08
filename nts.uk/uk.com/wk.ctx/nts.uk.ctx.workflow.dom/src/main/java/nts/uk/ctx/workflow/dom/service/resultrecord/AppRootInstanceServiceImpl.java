package nts.uk.ctx.workflow.dom.service.resultrecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.gul.collection.ListHashMap;
import nts.uk.ctx.workflow.dom.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.PersonImport;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.ResultRequest596Import;
import nts.uk.ctx.workflow.dom.agent.AgentRepository;
import nts.uk.ctx.workflow.dom.agent.output.AgentInfoOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmPerson;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalFrame;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApproverInfor;
import nts.uk.ctx.workflow.dom.approverstatemanagement.RootType;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootConfirm;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootConfirmQueryRepository;
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
import nts.uk.ctx.workflow.dom.service.output.Request113Output;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@RequestScoped
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
	
	@Inject
	private AppRootConfirmQueryRepository confirmQueryRepository;

	@Override
	public Request113Output getAppRootStatusByEmpsPeriod(List<String> employeeIDLst, DatePeriod period, RecordRootType rootType) {
		// Đối ứng SPR
		String companyID = "000000000004-0018";
		String loginCompanyID = AppContexts.user().companyId();
		if(Strings.isNotBlank(loginCompanyID)){
			companyID = loginCompanyID;
		}
		
		// レスポンス改善版
		val interms = this.confirmQueryRepository.queryInterm(companyID, employeeIDLst, period, rootType);
		val confirms = this.confirmQueryRepository.queryConfirm(companyID, employeeIDLst, period, rootType);
		
		List<ApprovalRootStateStatus> appRootStatusLst = new ArrayList<ApprovalRootStateStatus>();
		List<String> errorEmployeeIds = new ArrayList<String>();
		for (String employeeId : employeeIDLst) {
			val result = confirms.aggregate(period, employeeId, interms);
			
			if (result.isError()) {
				errorEmployeeIds.add(employeeId);
				continue;
			}
			
			appRootStatusLst.addAll(result.getResults());
		};
		
		return new Request113Output(
				appRootStatusLst,
				!errorEmployeeIds.isEmpty(),
				!errorEmployeeIds.isEmpty() ? "Msg_1430" : "",
				errorEmployeeIds);
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
	public List<ApprovalRootStateStatus> getAppRootStatusByEmpsPeriod(String compID, List<String> employeeIDLst, DatePeriod period, RecordRootType rootType) {
		
		// 対象者と期間から承認ルート中間データを取得する
		List<AppRootInstance> aris = appRootInstanceRepository.findByEmpLstPeriod(compID, employeeIDLst, period, rootType);
		List<AppRootConfirm> arcs = appRootConfirmRepository.findByEmpDate(compID, employeeIDLst, period, rootType);
		// INPUT．対象者社員IDの先頭から最後へループ
		List<ApprovalRootState> arses = period.datesBetween().stream().map(cd -> {
			// INPUT．期間の開始日から終了日へループ
			return employeeIDLst.stream().map(cei -> {
				// 対象日の承認ルート中間データを取得する
				AppRootInstance appRootInstance = aris.stream().filter(c -> c.getEmployeeID().equals(cei) 
												&& c.getDatePeriod().start().beforeOrEquals(cd)).findFirst().orElse(null);
				
				if(appRootInstance==null){
					throw new BusinessException("Msg_1430", "承認者");
				}
				// 対象日の就業実績確認状態を取得する
				AppRootConfirm appRootConfirm = arcs.stream().filter(c -> c.getEmployeeID().equals(cei) && c.getRecordDate().equals(cd)).findFirst()
						.orElseGet(() -> new AppRootConfirm(UUID.randomUUID().toString(), compID, cei, cd, rootType, new ArrayList<>(),
								Optional.empty(), Optional.empty(), Optional.empty()));
				// 中間データから承認ルートインスタンスに変換する
				// 承認ルート状況を取得する
				return this.convertFromAppRootInstance(appRootInstance, appRootConfirm);
			}).collect(Collectors.toList());
		}).flatMap(List::stream).collect(Collectors.toList());
		
		return approvalRootStateStatusService.getApprovalRootStateStatus(arses);
	}
	
	@Override
	public List<AppRootInstancePeriod> getAppRootInstanceByEmpPeriod(String compID, List<String> employeeIDLst, DatePeriod period,
			RecordRootType rootType) {
		List<AppRootInstance> appRootInstanceLst = appRootInstanceRepository.findByEmpLstPeriod(compID, employeeIDLst, period, rootType);
		
		return employeeIDLst.stream().map(x -> {
			List<AppRootInstance> opAppRootInstancePeriod = appRootInstanceLst.stream()
																.filter(y -> y.getEmployeeID().equals(x)).collect(Collectors.toList());
			
			return new AppRootInstancePeriod(x, opAppRootInstancePeriod);
		}).collect(Collectors.toList());
	}
	
	@Override
	public AppRootInstance getAppRootInstanceByDate(GeneralDate date, List<AppRootInstance> appRootInstanceLst) {
		// 承認ルートなしフラグ=true
		boolean noAppRootFlag = true;
		AppRootInstance result = null;
		// INPUT．承認ルート中間データ一覧の先頭から最後へループする
		for(AppRootInstance appRootInstance : appRootInstanceLst){
			// ループ中の「承認ルート中間データ」．履歴期間．開始日とINPUT．年月日を比較する
			if(appRootInstance.getDatePeriod().start().beforeOrEquals(date)&&appRootInstance.getDatePeriod().end().afterOrEquals(date)){
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
			return AppRootConfirm.dummy(companyID, employeeID, date, rootType);
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
		approvalRootState.setListApprovalPhaseState(new ArrayList<>());
		approvalRootState.setRootStateID(appRootConfirm.getRootID());
		// ドメインモデル「承認ルート中間データ」の値をoutput「承認ルートインスタンス」に入れる
		appRootInstance.getListAppPhase().forEach(appPhaseInstance -> {
			ApprovalPhaseState approvalPhaseState = new ApprovalPhaseState();
			approvalPhaseState.setApprovalAtr(ApprovalBehaviorAtr.UNAPPROVED);
			approvalPhaseState.setApprovalForm(appPhaseInstance.getApprovalForm());
			approvalPhaseState.setPhaseOrder(appPhaseInstance.getPhaseOrder());
			approvalPhaseState.setListApprovalFrame(new ArrayList<>());
			appPhaseInstance.getListAppFrame().forEach(appFrameInstance -> {
				ApprovalFrame approvalFrame = new ApprovalFrame();
				approvalFrame.setFrameOrder(appFrameInstance.getFrameOrder());
				approvalFrame.setConfirmAtr(appFrameInstance.isConfirmAtr() ? ConfirmPerson.CONFIRM : ConfirmPerson.NOT_CONFIRM);
				approvalFrame.setLstApproverInfo(new ArrayList<>());
				appFrameInstance.getListApprover().forEach(approver -> {
					ApproverInfor approverState = new ApproverInfor();
					approverState.setApprovalAtr(ApprovalBehaviorAtr.UNAPPROVED);
					approverState.setApproverID(approver);
					approvalFrame.getLstApproverInfo().add(approverState);
				});
				approvalPhaseState.getListApprovalFrame().add(approvalFrame);
			});
			approvalRootState.getListApprovalPhaseState().add(approvalPhaseState);
		});
		// ドメインモデル「就業実績確認状態」の値をoutput「承認ルートインスタンス」に入れる
		appRootConfirm.getListAppPhase().forEach(appPhaseConfirm -> {
			Optional<ApprovalPhaseState> opApprovalPhaseState = approvalRootState.getListApprovalPhaseState().stream()
					.filter(x -> x.getPhaseOrder()==appPhaseConfirm.getPhaseOrder()).findAny();
			if(opApprovalPhaseState.isPresent()){
				ApprovalPhaseState approvalPhaseState = opApprovalPhaseState.get();
				approvalPhaseState.setApprovalAtr(appPhaseConfirm.getAppPhaseAtr());
				appPhaseConfirm.getListAppFrame().forEach(appFrameConfirm -> {
					Optional<ApprovalFrame> opApprovalFrame = approvalPhaseState.getListApprovalFrame().stream()
							.filter(y -> y.getFrameOrder()==appFrameConfirm.getFrameOrder()).findAny();
					if(opApprovalFrame.isPresent()){
						ApprovalFrame approvalFrame = opApprovalFrame.get();
						if(!CollectionUtil.isEmpty(approvalFrame.getLstApproverInfo())) {
							approvalFrame.getLstApproverInfo().get(0).setApprovalAtr(ApprovalBehaviorAtr.APPROVED);
							approvalFrame.getLstApproverInfo().get(0).setApproverID(appFrameConfirm.getApproverID().orElse(""));
							approvalFrame.getLstApproverInfo().get(0).setAgentID(appFrameConfirm.getRepresenterID().orElse(""));
						}
						approvalFrame.setAppDate(appFrameConfirm.getApprovalDate());
					}
				});
			}
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
				if(appRootInstance==null){
					throw new BusinessException("Msg_1430", "承認者");
				}
				// 対象日の就業実績確認状態を取得する
				AppRootConfirm appRootConfirm = this.getAppRootConfirmByDate(companyID, employeeIDLoop, loopDate, rootType);
				// 中間データから承認ルートインスタンスに変換する
				ApprovalRootState approvalRootState = this.convertFromAppRootInstance(appRootInstance, appRootConfirm);
				// アルゴリズム「現在承認するべき未承認者を取得する」を実行する
				approverToApproveLst.add(this.getApproverCanApprove(approvalRootState));
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
				new ArrayList<>());
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
				if(frameState.isApproved(approvalPhaseState.getApprovalForm())){
					return;
				}
				approverIDLst.addAll(frameState.getLstApproverInfo().stream().map(x -> x.getApproverID()).collect(Collectors.toList()));
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
		ApprovalPersonInstance approvalPersonInstance = new ApprovalPersonInstance(new ArrayList<>(), new ArrayList<>());
		// 承認者と期間から承認ルート中間データを取得する
		List<AppRootInstance> appRootInstanceLst = appRootInstanceRepository.findByApproverPeriod(approverID, period, rootType);
		// [No.596]削除された社員を取り除く
		List<String> empInsLst = appRootInstanceLst.stream().map(x -> x.getEmployeeID()).collect(Collectors.toList());
		List<ResultRequest596Import> importResult = employeeAdapter.getEmpDeletedLstBySids(empInsLst);
		List<String> empDelLst = importResult.stream().map(x -> x.getSid()).collect(Collectors.toList());
		List<AppRootInstance> appRootInsNotDelLst = appRootInstanceLst.stream().filter(x -> !empDelLst.contains(x.getEmployeeID())).collect(Collectors.toList());
		
		// 取得した「承認ルート中間データ」をOUTPUTに追加する
		appRootInsNotDelLst.forEach(appRootInstance -> {
			ApprovalRouteDetails approvalRouteDetails = new ApprovalRouteDetails(appRootInstance, approverID, Optional.empty(), Optional.empty(), Optional.empty());
			approvalPersonInstance.getApproverRoute().add(approvalRouteDetails);
		});
		// ドメインモデル「代行承認」を取得する
		GeneralDate systemDate = GeneralDate.today();
		List<AgentInfoOutput> agentInfoOutputLst = agentRepository.findAgentByPeriod(companyID, Arrays.asList(approverID), systemDate, systemDate, 1);
		// 取得した「代行承認」先頭から最後へループ
		agentInfoOutputLst.forEach(agentInfor -> {
			// 承認者と期間から承認ルート中間データを取得する
			List<AppRootInstance> appRootInstanceAgentLst = appRootInstanceRepository.findByApproverPeriod(
					agentInfor.getAgentID(), 
					new DatePeriod(agentInfor.getStartDate(), agentInfor.getEndDate()), 
					rootType);
			// [No.596]削除された社員を取り除く
			List<String> empInsAgentLst = appRootInstanceAgentLst.stream().map(x -> x.getEmployeeID()).collect(Collectors.toList());
			List<ResultRequest596Import> importAgentResult = employeeAdapter.getEmpDeletedLstBySids(empInsAgentLst);
			List<String> empAgentDelLst = importAgentResult.stream().map(x -> x.getSid()).collect(Collectors.toList());
			List<AppRootInstance> appRootInsAgentNotDelLst = appRootInstanceAgentLst.stream().filter(x -> !empAgentDelLst.contains(x.getEmployeeID())).collect(Collectors.toList());
			
			// 取得した「承認ルート中間データ」をOUTPUTに追加する
			appRootInsAgentNotDelLst.forEach(appRootInstanceAgent -> {
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
		if(CollectionUtil.isEmpty(approvalPersonInstance.getAgentRoute()) && 
				CollectionUtil.isEmpty(approvalPersonInstance.getApproverRoute())){
			return false;
		}
		// 承認者としての承認すべきデータがあるか
		if(this.isDataApproverExist(period, approvalPersonInstance.getApproverRoute())){
			return true;
		}
		// 代行者としての承認すべきデータがあるか
		if(this.isDataAgentExist(period, approvalPersonInstance.getAgentRoute())){
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
			List<AppRootInstance> appRootInstanceLst = approverRouteLst.stream().map(x -> x.getAppRootInstance())
					.filter(x -> x.getEmployeeID().equals(approvalRouteDetails.getAppRootInstance().getEmployeeID())).collect(Collectors.toList());
			DatePeriod loopPeriod = period;
			for(GeneralDate loopDate = loopPeriod.start(); loopDate.beforeOrEquals(loopPeriod.end()); loopDate = loopDate.addDays(1)){
				// 対象日の承認ルート中間データを取得する
				AppRootInstance appRootInstance = this.getAppRootInstanceByDate(loopDate, appRootInstanceLst);
				if(appRootInstance==null){
					continue;
				}
				// 対象日の就業実績確認状態を取得する
				Optional<AppRootConfirm> opAppRootConfirm = appRootConfirmRepository.findByEmpDate(companyID, approvalRouteDetails.getAppRootInstance().getEmployeeID(), loopDate, RecordRootType.CONFIRM_WORK_BY_DAY);
				if(!opAppRootConfirm.isPresent()){
					continue;
				}
				AppRootConfirm appRootConfirm = opAppRootConfirm.get();
				// 中間データから承認ルートインスタンスに変換する
				ApprovalRootState approvalRootState = this.convertFromAppRootInstance(appRootInstance, appRootConfirm);
				// 指定した社員が承認できるかの判断(NoDBACCESS)
				ApproverPersonOutput approverPersonOutput = judgmentApprovalStatusService.judgmentTargetPerCanApproveNoDB(approvalRootState, approvalRouteDetails.getEmployeeID());
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
			List<AppRootInstance> appRootInstanceLst = agentRouteLst.stream().map(x -> x.getAppRootInstance())
					.filter(x -> x.getEmployeeID().equals(approvalRouteDetails.getAppRootInstance().getEmployeeID())).collect(Collectors.toList());
			DatePeriod loopPeriod = period;
			for(GeneralDate loopDate = loopPeriod.start(); loopDate.beforeOrEquals(loopPeriod.end()); loopDate = loopDate.addDays(1)){
				// 対象日の承認ルート中間データを取得する
				AppRootInstance appRootInstance = this.getAppRootInstanceByDate(loopDate, appRootInstanceLst);
				if(appRootInstance==null){
					continue;
				}
				if((approvalRouteDetails.getStartDate().isPresent()&&approvalRouteDetails.getStartDate().get().beforeOrEquals(loopDate)) ||
						(approvalRouteDetails.getEndDate().isPresent()&&approvalRouteDetails.getEndDate().get().afterOrEquals(loopDate))){
					// 対象日の就業実績確認状態を取得する
					Optional<AppRootConfirm> opAppRootConfirm = appRootConfirmRepository.findByEmpDate(companyID, approvalRouteDetails.getAppRootInstance().getEmployeeID(), loopDate, RecordRootType.CONFIRM_WORK_BY_DAY);
					if(!opAppRootConfirm.isPresent()){
						continue;
					}
					AppRootConfirm appRootConfirm = opAppRootConfirm.get();
					// 中間データから承認ルートインスタンスに変換する
					ApprovalRootState approvalRootState = this.convertFromAppRootInstance(appRootInstance, appRootConfirm);
					// 指定した社員が承認できるかの判断(NoDBACCESS)
					ApproverPersonOutput approverPersonOutput = judgmentApprovalStatusService.judgmentTargetPerCanApproveNoDB(approvalRootState, approvalRouteDetails.getEmployeeID());
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
		ApprovalBehaviorAtr approvalPhaseEnum = ApprovalBehaviorAtr.APPROVED;
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
					.filter(x -> x.isApproved(approvalPhaseState.getApprovalForm())).findAny();
			if(frameApproved.isPresent()){
				break;
			}
		}
		// output「ルート状況」をセットする
		if((approvedPhase==employeePhase)&&approvalPhaseEnum==ApprovalBehaviorAtr.UNAPPROVED){
			routeSituation.setApproverEmpState(ApproverEmpState.PHASE_DURING);
		} else if(approvedPhase==0){
			routeSituation.setApproverEmpState(ApproverEmpState.COMPLETE);
		} else if((approvedPhase==employeePhase)&&approvalPhaseEnum==ApprovalBehaviorAtr.APPROVED){
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
		if(!approvalStatusOutput.getApprovableFlag()){
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
		ApprovalEmpStatus approvalEmpStatus = new ApprovalEmpStatus(employeeID, new ArrayList<>());
		// 取得した「承認者としての承認ルート」．承認ルートの詳細の件数をチェックする
		List<RouteSituation> approverRouteLst = new ArrayList<>();
		if(!CollectionUtil.isEmpty(approvalPersonInstance.getApproverRoute())){
			// 承認者としてのルート状況を取得する
			approverRouteLst = this.getApproverRouteSituation(period, approvalPersonInstance.getApproverRoute(), agentLst, rootType, 
					false, null, null, null, null);
		}
		// 取得した「代行者としての承認ルート」．承認ルートの詳細の件数をチェックする
		List<RouteSituation> agentRouteLst = new ArrayList<>();
		if(!CollectionUtil.isEmpty(approvalPersonInstance.getAgentRoute())){
			// 代行者としてのルート状況を取得する
			agentRouteLst = this.getAgentRouteSituation(period, approvalPersonInstance.getAgentRoute(), agentLst, rootType,
					false, null, null, null, null);
		}
		// outputの整合
		List<RouteSituation> mergeLst = this.mergeRouteSituationLst(approverRouteLst, agentRouteLst);
		// 「ルート状況」リスト整合処理後をoutput「基準社員の承認対象者」に追加する
		approvalEmpStatus.getRouteSituationLst().addAll(mergeLst);
		return approvalEmpStatus;
	}
	
	@Override
	public ApprovalEmpStatus getDailyApprovalStatus(String companyId, String approverId, List<String> targetEmployeeIds, DatePeriod period) {
		
		val confirms = this.appRootConfirmRepository.findByEmpDate(
				companyId, targetEmployeeIds, period, RecordRootType.CONFIRM_WORK_BY_DAY);
		
		// システム日付時点で代行依頼があれば、承認できる
		val representRequests = this.agentRepository.findAgentByPeriod(
				companyId, Arrays.asList(approverId), GeneralDate.today(), GeneralDate.today(), 1);
		
		val instancesMap = appRootInstancesMap(companyId, approverId, representRequests, targetEmployeeIds, period);
		
		List<RouteSituation> routeSituations = new ArrayList<>();
		
		// 実績確認状態(confirms)は承認者関係なく取得するので、実際にはapproverIdが承認者ではないものも含まれている。
		// その場合、instancesMapには当該データが存在しないことになる。
		// そういったデータはルート状況リストに含めずに返す仕様。
		for (val confirm : confirms) {
			Optional<AppRootInstance> instance = instancesMap.apply(confirm.getEmployeeID(), confirm.getRecordDate());
			if (!instance.isPresent()) {
				continue;
			}
			
			List<String> approverLst = instance.get().getListAppPhase()
				.stream().map(x -> x.getListAppFrame())
				.flatMap(Collection::stream).collect(Collectors.toList())
				.stream().map(x -> x.getListApprover())
				.flatMap(Collection::stream).collect(Collectors.toList());
			
			List<AgentInfoOutput> representRequesterIds = this.agentRepository.find(companyId, approverLst, GeneralDate.today())
					.stream().map(x -> new AgentInfoOutput(
							x.getAgentSid1(), 
							x.getEmployeeId(), 
							x.getStartDate(), 
							x.getEndDate()))
					.collect(Collectors.toList());
			
			routeSituations.add(RouteSituation.create(confirm, instance.get(), approverId, representRequesterIds));
		}
		
		return new ApprovalEmpStatus(approverId, routeSituations);
	}
	
	/**
	 * getting AppRootInstance for getDailyApprovalStatus
	 */
	private BiFunction<String, GeneralDate, Optional<AppRootInstance>> appRootInstancesMap(
			String companyId,
			String approverId,
			List<AgentInfoOutput> representRequests,
			List<String> targetEmployeeIds,
			DatePeriod period) {
		
		val instancesApprover = this.appRootInstanceRepository.findByApproverEmployeePeriod(
				companyId, approverId, targetEmployeeIds, period, RecordRootType.CONFIRM_WORK_BY_DAY);
		val mapApprover = ListHashMap.create(instancesApprover, i -> i.getEmployeeID());
		
		// 代行依頼している承認者達の中間データ
		val instancesRepresent = representRequests.stream()
				.flatMap(request -> this.appRootInstanceRepository.findByApproverEmployeePeriod(
							companyId, request.getAgentID(), targetEmployeeIds, period, RecordRootType.CONFIRM_WORK_BY_DAY).stream())
				.collect(Collectors.toList());
		val mapRepresent = ListHashMap.create(instancesRepresent, i -> i.getEmployeeID());
		
		return (employeeId, date) -> {
			Optional<AppRootInstance> result = mapApprover.getOrDefault(employeeId, Collections.emptyList()).stream()
					.filter(i -> i.getDatePeriod().contains(date))
					.findFirst();
			
			// 承認者自身のものがあればそれを返す
			if (result.isPresent()) {
				return result;
			}
			
			// 無ければ代行依頼者のものを探す
			return mapRepresent.getOrDefault(employeeId, Collections.emptyList()).stream()
					.filter(i -> i.getDatePeriod().contains(date))
					.findFirst();
		};
	}
	
	@Override
	public List<RouteSituation> getApproverRouteSituation(DatePeriod period, List<ApprovalRouteDetails> approverRouteLst, List<String> agentLst, RecordRootType rootType,
			boolean useDayApproverConfirm, DatePeriod closurePeriod, YearMonth yearMonth, Integer closureID, ClosureDate closureDate) {
		String companyID = AppContexts.user().companyId();
		List<RouteSituation> routeSituationLst = new ArrayList<>();
		List<String> empLst = approverRouteLst.stream().map(x -> x.getAppRootInstance().getEmployeeID()).distinct().collect(Collectors.toList());
		
		/*if(useDayApproverConfirm == true && rootType == RecordRootType.CONFIRM_WORK_BY_MONTH){
			
			// 社員の指定期間中の所属期間を取得する
			statusOfEmpImportLst = employeeAdapter.getListAffComHistByListSidAndPeriod(empLst, closurePeriod);
			// [No.113](中間データ版)承認対象者と期間から承認状況を取得する
			request113Output = this.getAppRootStatusByEmpsPeriod(empLst, closurePeriod, rootType);
		}*/
		
		List<AppRootConfirm> appRootConfirmLst = new ArrayList<>();
		
		if(rootType==RecordRootType.CONFIRM_WORK_BY_DAY){
			// 対象日の就業実績確認状態を取得する
			appRootConfirmLst = appRootConfirmRepository.findByEmpDate(companyID, empLst, period, rootType);
		} else {
			// ドメインモデル「就業実績確認状態」を取得する
			appRootConfirmLst = appRootConfirmRepository.findByEmpLstMonth(companyID, empLst, yearMonth, closureID, closureDate, rootType);
		}
		// 取得した対象者(List)の先頭から最後へループ
		for(String empLoop : empLst){
			/*
			if(useDayApproverConfirm == true && rootType == RecordRootType.CONFIRM_WORK_BY_MONTH){
				// 「社員の会社所属状況」からループ中の社員の対象期間を取得する
				Optional<StatusOfEmpImport> opStatusOfEmpImport = statusOfEmpImportLst.stream().filter(x -> x.getEmployeeId().equals(empLoop)).findAny();
				if(opStatusOfEmpImport.isPresent()){
					opStatusOfEmpImport.get().getListPeriod().sort(Comparator.comparing(DatePeriod::start).reversed());
					DatePeriod currentPeriod = opStatusOfEmpImport.get().getListPeriod().get(0);
					// 対象者の承認ルート状況をチェックする
					Optional<ApprovalRootStateStatus> opApprovalRootStateStatus = request113Output.getAppRootStatusLst().stream().filter(x -> {
						return x.getEmployeeID().equals(empLoop) &&
								x.getDailyConfirmAtr()!=DailyConfirmAtr.ALREADY_APPROVED &&
								x.getDate().afterOrEquals(currentPeriod.start()) &&
								x.getDate().beforeOrEquals(currentPeriod.end());
					}).findAny();
					if(opApprovalRootStateStatus.isPresent()){
						// OUTPUT．ルート状況を追加する
						routeSituationLst.add(
								new RouteSituation(
										closurePeriod.end(), 
										empLoop, 
										ApproverEmpState.PHASE_LESS, 
										Optional.of(new ApprovalStatus(ReleaseDivision.NOT_RELEASE, ApprovalActionByEmp.NOT_APPROVAL))));
						continue;
					}
				}
			}
			*/
			List<AppRootInstance> appRootInstanceLst = approverRouteLst.stream().map(x -> x.getAppRootInstance())
					.filter(x -> x.getEmployeeID().equals(empLoop)).collect(Collectors.toList());
			DatePeriod loopPeriod = period;
			// INPUT．期間の開始日から終了日へループ
			for(GeneralDate loopDate = loopPeriod.start(); loopDate.beforeOrEquals(loopPeriod.end()); loopDate = loopDate.addDays(1)){
				// 対象日の承認ルート中間データを取得する
				AppRootInstance appRootInstance = this.getAppRootInstanceByDate(loopDate, appRootInstanceLst);
				if(appRootInstance==null){
					continue;
				}
				// 対象日の就業実績確認状態を取得する
				List<AppRootConfirm> appRootConfirmPeriod = appRootConfirmLst.stream()
						.filter(x -> x.getEmployeeID().equals(empLoop)).collect(Collectors.toList());
				AppRootConfirm appRootConfirm = null;
				if(rootType==RecordRootType.CONFIRM_WORK_BY_DAY){
					Optional<AppRootConfirm> opAppRootConfirm = Optional.empty();
					for(AppRootConfirm appRootConfirmParam : appRootConfirmPeriod){
						if(appRootConfirmParam.getRecordDate().compareTo(loopDate)==0){
							opAppRootConfirm = Optional.of(appRootConfirmParam);
							break;
						}
					}
					if(!opAppRootConfirm.isPresent()){
						continue;
					}
					appRootConfirm = opAppRootConfirm.get();
				} else {
					if(!CollectionUtil.isEmpty(appRootConfirmPeriod)){
						appRootConfirm = appRootConfirmPeriod.get(0);
					} else {
						continue;
					}
				}
				// 中間データから承認ルートインスタンスに変換する
				ApprovalRootState approvalRootState = this.convertFromAppRootInstance(appRootInstance, appRootConfirm);
				// 基準社員を元にルート状況を取得する
				RouteSituation routeSituation = this.getRouteSituationByEmp(approvalRootState, approverRouteLst.get(0).getEmployeeID(), agentLst);
				// 実行結果をoutput「ルート状況」に追加する
				routeSituationLst.add(routeSituation);
			}
		}
		return routeSituationLst;
	}

	@Override
	public List<RouteSituation> getAgentRouteSituation(DatePeriod period, List<ApprovalRouteDetails> agentRouteLst, List<String> agentLst, RecordRootType rootType,
			boolean useDayApproverConfirm, DatePeriod closurePeriod, YearMonth yearMonth, Integer closureID, ClosureDate closureDate) {
		String companyID = AppContexts.user().companyId();
		List<RouteSituation> routeSituationLst = new ArrayList<>();
		List<String> empLst = agentRouteLst.stream().map(x -> x.getAppRootInstance().getEmployeeID()).distinct().collect(Collectors.toList());
		if(useDayApproverConfirm == true && rootType == RecordRootType.CONFIRM_WORK_BY_MONTH){
			/*
			// 社員の指定期間中の所属期間を取得する
			statusOfEmpImportLst = employeeAdapter.getListAffComHistByListSidAndPeriod(empLst, closurePeriod);
			// [No.113](中間データ版)承認対象者と期間から承認状況を取得する
			request113Output = this.getAppRootStatusByEmpsPeriod(empLst, closurePeriod, rootType);
			*/
		}
		List<AppRootConfirm> appRootConfirmLst = new ArrayList<>();
		if(rootType==RecordRootType.CONFIRM_WORK_BY_DAY){
			// 対象日の就業実績確認状態を取得する
			appRootConfirmLst = appRootConfirmRepository.findByEmpDate(companyID, empLst, period, rootType);
		} else {
			// ドメインモデル「就業実績確認状態」を取得する
			appRootConfirmLst = appRootConfirmRepository.findByEmpLstMonth(companyID, empLst, yearMonth, closureID, closureDate, rootType);
		}
		// 取得した対象者(List)の先頭から最後へループ
		for(String empLoop : empLst){
			/*
			if(useDayApproverConfirm == true && rootType == RecordRootType.CONFIRM_WORK_BY_MONTH){
				// 「社員の会社所属状況」からループ中の社員の対象期間を取得する
				Optional<StatusOfEmpImport> opStatusOfEmpImport = statusOfEmpImportLst.stream().filter(x -> x.getEmployeeId().equals(empLoop)).findAny();
				if(opStatusOfEmpImport.isPresent()){
					opStatusOfEmpImport.get().getListPeriod().sort(Comparator.comparing(DatePeriod::start).reversed());
					DatePeriod currentPeriod = opStatusOfEmpImport.get().getListPeriod().get(0);
					// 対象者の承認ルート状況をチェックする
					Optional<ApprovalRootStateStatus> opApprovalRootStateStatus = request113Output.getAppRootStatusLst().stream().filter(x -> {
						return x.getEmployeeID().equals(empLoop) &&
								x.getDailyConfirmAtr()!=DailyConfirmAtr.ALREADY_APPROVED &&
								x.getDate().afterOrEquals(currentPeriod.start()) &&
								x.getDate().beforeOrEquals(currentPeriod.end());
					}).findAny();
					if(opApprovalRootStateStatus.isPresent()){
						// OUTPUT．ルート状況を追加する
						routeSituationLst.add(
								new RouteSituation(
										closurePeriod.end(), 
										empLoop, 
										ApproverEmpState.PHASE_LESS, 
										Optional.of(new ApprovalStatus(ReleaseDivision.NOT_RELEASE, ApprovalActionByEmp.NOT_APPROVAL))));
						continue;
					}
				}
			}
			*/
			List<AppRootInstance> appRootInstanceLst = agentRouteLst.stream().map(x -> x.getAppRootInstance())
					.filter(x -> x.getEmployeeID().equals(empLoop)).collect(Collectors.toList());
			DatePeriod loopPeriod = period;
			// INPUT．期間の開始日から終了日へループ
			for(GeneralDate loopDate = loopPeriod.start(); loopDate.beforeOrEquals(loopPeriod.end()); loopDate = loopDate.addDays(1)){
				// 対象日の承認ルート中間データを取得する
				AppRootInstance appRootInstance = this.getAppRootInstanceByDate(loopDate, appRootInstanceLst);
				if(appRootInstance==null){
					continue;
				}
				ApprovalRouteDetails approvalRouteDetails = agentRouteLst.stream().filter(x -> x.getAppRootInstance().getRootID().equals(appRootInstance.getRootID())).findAny().get();
				// ループする日は代行期間内かチェックする
				if((approvalRouteDetails.getStartDate().isPresent()&&approvalRouteDetails.getStartDate().get().beforeOrEquals(loopDate)) ||
						(approvalRouteDetails.getEndDate().isPresent()&&approvalRouteDetails.getEndDate().get().afterOrEquals(loopDate))){
					// 対象日の就業実績確認状態を取得する
					List<AppRootConfirm> appRootConfirmPeriod = appRootConfirmLst.stream()
							.filter(x -> x.getEmployeeID().equals(empLoop)).collect(Collectors.toList());
					AppRootConfirm appRootConfirm = null;
					if(rootType==RecordRootType.CONFIRM_WORK_BY_DAY){
						Optional<AppRootConfirm> opAppRootConfirm = Optional.empty();
						for(AppRootConfirm appRootConfirmParam : appRootConfirmPeriod){
							if(appRootConfirmParam.getRecordDate().compareTo(loopDate)==0){
								opAppRootConfirm = Optional.of(appRootConfirmParam);
								break;
							}
						}
						if(!opAppRootConfirm.isPresent()){
							continue;
						}
						appRootConfirm = opAppRootConfirm.get();
					} else {
						if(!CollectionUtil.isEmpty(appRootConfirmPeriod)){
							appRootConfirm = appRootConfirmPeriod.get(0);
						} else {
							continue;
						}
					}
					// 中間データから承認ルートインスタンスに変換する
					ApprovalRootState approvalRootState = this.convertFromAppRootInstance(appRootInstance, appRootConfirm);
					// 基準社員を元にルート状況を取得する
					RouteSituation routeSituation = this.getRouteSituationByEmp(approvalRootState, approvalRouteDetails.getAgentID().get(), agentLst);
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
				(x.getEmployeeID().equals(route.getEmployeeID()))&&(x.getDate().equals(route.getDate()))).findAny();
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

	@Override
	public AppRootConfirm getAppRootCFByMonth(String companyID, String employeeID, YearMonth yearMonth,
			Integer closureID, ClosureDate closureDate, RecordRootType rootType) {
		// ドメインモデル「就業実績確認状態」を取得する
		Optional<AppRootConfirm> opAppRootConfirm = appRootConfirmRepository.findByEmpMonth(companyID, employeeID, yearMonth, closureID, closureDate, rootType);
		if(!opAppRootConfirm.isPresent()){
			return new AppRootConfirm(UUID.randomUUID().toString(), companyID, employeeID, GeneralDate.today(), rootType, new ArrayList<>(),
					Optional.empty(), Optional.empty(), Optional.empty());
		}
		return opAppRootConfirm.get();
	}

	@Override
	public boolean isDataApproverExistMonth(YearMonth yearMonth, List<ApprovalRouteDetails> approverRouteLst) {
		String companyID = AppContexts.user().companyId();
		// INPUT．「承認ルートの詳細」(List)の件数をチェックする
		if(CollectionUtil.isEmpty(approverRouteLst)){
			return false;
		}
		System.out.println(approverRouteLst.stream().map(x -> x.getAppRootInstance()).map(x -> "'"+x.getRootID()+"'").collect(Collectors.toList()));
		System.out.println(approverRouteLst.stream().map(x -> x.getAppRootInstance()).map(x -> "'"+x.getEmployeeID()+"'").collect(Collectors.toList()));
		for(ApprovalRouteDetails approvalRouteDetails : approverRouteLst){
			List<AppRootInstance> appRootInstanceLst = approverRouteLst.stream().map(x -> x.getAppRootInstance())
					.filter(x -> x.getEmployeeID().equals(approvalRouteDetails.getAppRootInstance().getEmployeeID())).collect(Collectors.toList());
			// ドメインモデル「就業実績確認状態」を取得する
			List<AppRootConfirm> appRootConfirmLst = appRootConfirmRepository.findByEmpYearMonth(companyID, approvalRouteDetails.getAppRootInstance().getEmployeeID(), yearMonth);
			for(AppRootConfirm appRootConfirmLoop : appRootConfirmLst){
				// 対象日の承認ルート中間データを取得する
				AppRootInstance appRootInstance = this.getAppRootInstanceByDate(appRootConfirmLoop.getRecordDate(), appRootInstanceLst);
				if(appRootInstance==null){
					continue;
				}
				// 中間データから承認ルートインスタンスに変換する
				ApprovalRootState approvalRootState = this.convertFromAppRootInstance(appRootInstance, appRootConfirmLoop);
				// 指定した社員が承認できるかの判断(NoDBACCESS)
				ApproverPersonOutput approverPersonOutput = judgmentApprovalStatusService.judgmentTargetPerCanApproveNoDB(approvalRootState, approvalRouteDetails.getEmployeeID());
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
	public boolean isDataAgentExistMonth(YearMonth yearMonth, List<ApprovalRouteDetails> agentRouteLst) {
		String companyID = AppContexts.user().companyId();
		// INPUT．「承認ルートの詳細」(List)の件数をチェックする
		if(CollectionUtil.isEmpty(agentRouteLst)){
			return false;
		}
		for(ApprovalRouteDetails approvalRouteDetails : agentRouteLst){
			List<AppRootInstance> appRootInstanceLst = agentRouteLst.stream().map(x -> x.getAppRootInstance())
					.filter(x -> x.getEmployeeID().equals(approvalRouteDetails.getAppRootInstance().getEmployeeID())).collect(Collectors.toList());
			// ドメインモデル「就業実績確認状態」を取得する
			List<AppRootConfirm> appRootConfirmLst = appRootConfirmRepository.findByEmpYearMonth(companyID, approvalRouteDetails.getAppRootInstance().getEmployeeID(), yearMonth);
			for(AppRootConfirm appRootConfirmLoop : appRootConfirmLst){
				// 対象日の承認ルート中間データを取得する
				AppRootInstance appRootInstance = this.getAppRootInstanceByDate(appRootConfirmLoop.getRecordDate(), appRootInstanceLst);
				if(appRootInstance==null){
					continue;
				}
				if((approvalRouteDetails.getStartDate().isPresent()&&approvalRouteDetails.getStartDate().get().beforeOrEquals(appRootConfirmLoop.getRecordDate())) ||
						(approvalRouteDetails.getEndDate().isPresent()&&approvalRouteDetails.getEndDate().get().afterOrEquals(appRootConfirmLoop.getRecordDate()))){
					// 中間データから承認ルートインスタンスに変換する
					ApprovalRootState approvalRootState = this.convertFromAppRootInstance(appRootInstance, appRootConfirmLoop);
					// 指定した社員が承認できるかの判断(NoDBACCESS)
					ApproverPersonOutput approverPersonOutput = judgmentApprovalStatusService.judgmentTargetPerCanApproveNoDB(approvalRootState, approvalRouteDetails.getEmployeeID());
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
	public ApproverToApprove getApproverCanApprove(ApprovalRootState approvalRootState) {
		String companyID = AppContexts.user().companyId();
		// output「ルート状況」を初期化する
		ApproverToApprove approverToApprove = new ApproverToApprove(
				approvalRootState.getApprovalRecordDate(),
				approvalRootState.getEmployeeID(),
				new ArrayList<>());
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
			boolean checkPhase = judgmentApprovalStatusService.checkLoopApprovalPhase(approvalRootState, approvalPhaseState, false);
			if(!checkPhase){
				continue;
			}
			approvalPhaseState.getListApprovalFrame().forEach(frameState -> {
				if(frameState.isApproved(approvalPhaseState.getApprovalForm())){
					return;
				}
				approverIDLst.addAll(frameState.getLstApproverInfo().stream().map(x -> x.getApproverID()).collect(Collectors.toList()));
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
}
