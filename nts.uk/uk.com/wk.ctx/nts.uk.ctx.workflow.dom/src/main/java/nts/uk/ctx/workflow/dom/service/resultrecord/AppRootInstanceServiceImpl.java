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

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.PersonImport;
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
			throw new BusinessException("error on process: 対象日の承認ルート中間データを取得する");
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
}
