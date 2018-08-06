package nts.uk.ctx.workflow.dom.service.resultrecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalForm;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmPerson;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalFrame;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApproverState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.RootType;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootDynamic;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootDynamicRepository;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootInstance;
import nts.uk.ctx.workflow.dom.resultrecord.AppRootInstanceRepository;
import nts.uk.ctx.workflow.dom.resultrecord.RecordRootType;
import nts.uk.ctx.workflow.dom.service.ApprovalRootStateStatusService;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRootStateStatus;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AppRootInstanceServiceImpl implements AppRootInstanceService {
	
	@Inject
	private AppRootInstanceRepository appRootInstanceRepository;
	
	@Inject
	private ApprovalRootStateStatusService approvalRootStateStatusService;
	
	@Inject
	private AppRootDynamicRepository appRootDynamicRepository; 

	@Override
	public List<ApprovalRootStateStatus> getAppRootStatusByEmpsPeriod(List<String> employeeIDLst, DatePeriod period, RecordRootType rootType) {
		String companyID = AppContexts.user().companyId();
		List<ApprovalRootStateStatus> appRootStatusLst = new ArrayList<>();
		// 対象者と期間から承認ルート中間データを取得する
		List<AppRootDynamicPeriod> appRootDynamicPeriodLst = this.getAppRootDynamicByEmpPeriod(employeeIDLst, period, rootType);
		// INPUT．対象者社員IDの先頭から最後へループ
		employeeIDLst.forEach(employeeIDLoop -> {
			// INPUT．期間の開始日から終了日へループ
			for(GeneralDate loopDate = period.start(); loopDate.beforeOrEquals(period.end()); loopDate = loopDate.addDays(1)){
				// 対象日の承認ルート中間データを取得する
				AppRootDynamic appRootDynamic = this.getAppRootDynamicByDate(loopDate, 
						appRootDynamicPeriodLst.stream().filter(x -> x.getEmployeeID().equals(employeeIDLoop)).findAny().get().getAppRootDynamicLst());
				// 対象日の就業実績確認状態を取得する
				AppRootInstance appRootInstance = this.getAppRootInstanceByDate(companyID, employeeIDLoop, loopDate, rootType);
				// 中間データから承認ルートインスタンスに変換する
				ApprovalRootState approvalRootState = this.convertFromAppRootDynamic(appRootDynamic, appRootInstance);
				// 承認ルート状況を取得する
				appRootStatusLst.addAll(approvalRootStateStatusService.getApprovalRootStateStatus(Arrays.asList(approvalRootState)));
			}
		});
		return appRootStatusLst;
	}

	@Override
	public List<AppRootDynamicPeriod> getAppRootDynamicByEmpPeriod(List<String> employeeIDLst, DatePeriod period,
			RecordRootType rootType) {
		List<AppRootDynamic> appRootDynamicLst = appRootDynamicRepository.findByEmpLstPeriod(employeeIDLst, period, rootType);
		List<AppRootDynamicPeriod> dbList = appRootDynamicLst.stream().collect(Collectors.groupingBy(AppRootDynamic::getEmployeeID)).entrySet().stream()
				.map(x -> new AppRootDynamicPeriod(x.getKey(), x.getValue())).collect(Collectors.toList());
		List<AppRootDynamicPeriod> result = new ArrayList<>();
		employeeIDLst.forEach(x -> {
			Optional<AppRootDynamicPeriod> opAppRootDynamicPeriod = dbList.stream().filter(y -> y.getEmployeeID().equals(x)).findAny();
			if(opAppRootDynamicPeriod.isPresent()){
				result.add(new AppRootDynamicPeriod(x, opAppRootDynamicPeriod.get().getAppRootDynamicLst()));
			} else {
				result.add(new AppRootDynamicPeriod(x, Collections.emptyList()));
			}
		});
		return result;
	}

	@Override
	public AppRootDynamic getAppRootDynamicByDate(GeneralDate date, List<AppRootDynamic> appRootDynamicLst) {
		// 承認ルートなしフラグ=true
		boolean noAppRootFlag = true;
		AppRootDynamic result = null;
		// INPUT．承認ルート中間データ一覧の先頭から最後へループする
		for(AppRootDynamic appRootDynamic : appRootDynamicLst){
			// ループ中の「承認ルート中間データ」．履歴期間．開始日とINPUT．年月日を比較する
			if(appRootDynamic.getDatePeriod().start().beforeOrEquals(date)){
				// 承認ルートなしフラグ=false
				noAppRootFlag = false;
				result = appRootDynamic;
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
	public AppRootInstance getAppRootInstanceByDate(String companyID, String employeeID, GeneralDate date,
			RecordRootType rootType) {
		// ドメインモデル「就業実績確認状態」を取得する
		Optional<AppRootInstance> opAppRootInstance = appRootInstanceRepository.findByEmpDate(companyID, employeeID, date, rootType);
		if(!opAppRootInstance.isPresent()){
			throw new BusinessException("error on process: 対象日の就業実績確認状態を取得する");
		}
		return opAppRootInstance.get();
	}
	
	@Override
	public ApprovalRootState convertFromAppRootDynamic(AppRootDynamic appRootDynamic, AppRootInstance appRootInstance) {
		// output「承認ルートインスタンス」を初期化
		ApprovalRootState approvalRootState = new ApprovalRootState();
		approvalRootState.setRootType(EnumAdaptor.valueOf(appRootDynamic.getRootType().value, RootType.class));
		approvalRootState.setEmployeeID(appRootDynamic.getEmployeeID());
		approvalRootState.setApprovalRecordDate(appRootInstance.getRecordDate());
		// ドメインモデル「承認ルート中間データ」の値をoutput「承認ルートインスタンス」に入れる
		appRootDynamic.getListAppPhase().forEach(appPhaseDynamic -> {
			ApprovalPhaseState approvalPhaseState = new ApprovalPhaseState();
			approvalPhaseState.setApprovalAtr(ApprovalBehaviorAtr.UNAPPROVED);
			approvalPhaseState.setApprovalForm(appPhaseDynamic.getApprovalForm());
			approvalPhaseState.setPhaseOrder(appPhaseDynamic.getPhaseOrder());
			approvalPhaseState.setApprovalForm(ApprovalForm.EVERYONE_APPROVED);
			appPhaseDynamic.getListAppFrame().forEach(appFrameDynamic -> {
				ApprovalFrame approvalFrame = new ApprovalFrame();
				approvalFrame.setFrameOrder(appFrameDynamic.getFrameOrder());
				approvalFrame.setConfirmAtr(appFrameDynamic.isConfirmAtr() ? ConfirmPerson.CONFIRM : ConfirmPerson.NOT_CONFIRM);
				approvalFrame.setApprovalAtr(ApprovalBehaviorAtr.UNAPPROVED);
				appFrameDynamic.getListApprover().forEach(approver -> {
					ApproverState approverState = new ApproverState();
					approverState.setCompanyID(appRootDynamic.getCompanyID());
					approverState.setDate(appRootInstance.getRecordDate());
					approverState.setApproverID(approver);
					approvalFrame.getListApproverState().add(approverState);
				});
				approvalPhaseState.getListApprovalFrame().add(approvalFrame);
			});
			approvalRootState.getListApprovalPhaseState().add(approvalPhaseState);
		});
		// ドメインモデル「就業実績確認状態」の値をoutput「承認ルートインスタンス」に入れる
		appRootInstance.getListAppPhase().forEach(appPhaseInstance -> {
			ApprovalPhaseState approvalPhaseState = approvalRootState.getListApprovalPhaseState().stream()
					.filter(x -> x.getPhaseOrder()==appPhaseInstance.getPhaseOrder()).findAny().get();
			approvalPhaseState.setApprovalAtr(appPhaseInstance.getAppPhaseAtr());
			appPhaseInstance.getListAppFrame().forEach(appFrameInstance -> {
				ApprovalFrame approvalFrame = approvalPhaseState.getListApprovalFrame().stream()
						.filter(y -> y.getFrameOrder()==appFrameInstance.getFrameOrder()).findAny().get();
				approvalFrame.setApprovalAtr(ApprovalBehaviorAtr.APPROVED);
				approvalFrame.setApprovalDate(appFrameInstance.getApprovalDate());
				approvalFrame.setApproverID(appFrameInstance.getApproverID().orElse(null));
				approvalFrame.setRepresenterID(appFrameInstance.getRepresenterID().orElse(null));
			});
		});
		return approvalRootState;
	}
}
