package nts.uk.ctx.at.request.dom.application.common.service.newscreen.before;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.ApplicationType_Old;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.PrePostAtr_Old;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.PesionInforImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SEmpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.actuallock.ActualLockAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.actuallock.ActualLockImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.daycalendar.ObtainDeadlineDateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.CollectApprovalRootPatternService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.StartupErrorCheckService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.PeriodCurrentMonth;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service.ActualLockingCheck;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service.DayActualConfirmDoneCheck;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service.MonthActualConfirmDoneCheck;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service.WorkConfirmDoneCheck;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service.ApplyPossibleCheck;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.applimitset.AppLimitSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.ReceptionRestrictionSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadline;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadlineRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.DeadlineCriteria;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AllowAtr;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;

@Stateless
public class NewBeforeRegisterImpl_New implements NewBeforeRegister_New {
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	
	@Inject
	private EmployeeRequestAdapter employeeAdaptor;
	
	@Inject
	private ApplicationDeadlineRepository appDeadlineRepository;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithmService;
	
	@Inject
	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;
	
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;
	
	@Inject
	private WorkplaceAdapter workplaceAdapter;
	
	@Inject
	private ObtainDeadlineDateAdapter obtainDeadlineDateAdapter;
	@Inject
	private RequestSettingRepository requestSettingRepository;
	@Inject
	private ActualLockAdapter actualLockAdapter;
	
	@Inject
	private DayActualConfirmDoneCheck dayActualConfirmDoneCheck;

	@Inject
	private MonthActualConfirmDoneCheck monthActualConfirmDoneCheck;

	@Inject
	private WorkConfirmDoneCheck workConfirmDoneCheck;

	@Inject
	private ActualLockingCheck actualLockingCheck;

	@Inject
	private ApplyPossibleCheck applyPossibleCheck;
	
	@Inject
	private CollectApprovalRootPatternService approvalRootPatternService;
	
	@Inject
	private StartupErrorCheckService startupErrorCheckService;
	
    public void processBeforeRegister(Application_New application, OverTimeAtr overTimeAtr, boolean checkOver1Year, List<GeneralDate> lstDateHd){
		// アルゴリズム「未入社前チェック」を実施する
		retirementCheckBeforeJoinCompany(application.getCompanyID(), application.getEmployeeID(), application.getAppDate());
		
		// アルゴリズム「社員の当月の期間を算出する」を実行する
		PeriodCurrentMonth periodCurrentMonth = otherCommonAlgorithmService.employeePeriodCurrentMonthCalculate(application.getCompanyID(), application.getEmployeeID(), application.getAppDate());
		
		GeneralDate startDate = application.getAppDate();
		GeneralDate endDate = application.getAppDate();
		if (application.getStartDate().isPresent() && application.getEndDate().isPresent()) {
			startDate = application.getStartDate().get();
			endDate = application.getEndDate().get();
			
			// 登録する期間のチェック
			//((TimeSpan)(申請する終了日 - 申請する開始日)).Days > 31がtrue
			if((ChronoUnit.DAYS.between(startDate.localDate(), endDate.localDate()) + 1)  > 31){
				throw new BusinessException("Msg_277");
			}
			// 登録可能期間のチェック(１年以内)
			//EA修正履歴 No.3210
			//hoatt 2019.03.22
			if(periodCurrentMonth.getStartDate().addYears(1).beforeOrEquals(endDate) && checkOver1Year) {
				//締め期間．開始年月日.AddYears(1) <= 申請する終了日がtrue
				//確認メッセージ（Msg_1518）を表示する
				throw new BusinessException("Msg_1518", periodCurrentMonth.getStartDate().addYears(1).toString(DATE_FORMAT));
			}
			
			// 過去月のチェック
			if(startDate.before(periodCurrentMonth.getStartDate())) {
				throw new BusinessException("Msg_236");			
			}
		}		
		
		// キャッシュから承認ルートを取得する(Lấy comfirm root từ cache)	
		ApprovalRootContentImport_New approvalRootContentImport = approvalRootPatternService.getApprovalRootPatternService(
				application.getCompanyID(), 
				application.getEmployeeID(), 
				EmploymentRootAtr.APPLICATION, 
				application.getAppType(), 
				application.getAppDate(),
				application.getAppID(),
				true).getApprovalRootContentImport();
		startupErrorCheckService.startupErrorCheck(application.getAppDate(), application.getAppType().value, approvalRootContentImport);
		switch (approvalRootContentImport.getErrorFlag()) {
		case NO_CONFIRM_PERSON:
			throw new BusinessException("Msg_238");
		case APPROVER_UP_10:
			throw new BusinessException("Msg_237");
		case NO_APPROVER:
			throw new BusinessException("Msg_324");
		default:
			break;
		}
		
		// アルゴリズム「申請の締め切り期限をチェック」を実施する
		SEmpHistImport empHistImport = employeeAdaptor.getEmpHist(application.getCompanyID(), application.getEmployeeID(), application.getAppDate());
		if(empHistImport==null || empHistImport.getEmploymentCode()==null){
			throw new BusinessException("Msg_426");
		}
		String employmentCD = empHistImport.getEmploymentCode();
		Optional<ClosureEmployment> closureEmployment = closureEmploymentRepository.findByEmploymentCD(application.getCompanyID(), employmentCD);
		if(!closureEmployment.isPresent()){
			throw new RuntimeException("Not found ClosureEmployment in table KCLMT_CLOSURE_EMPLOYMENT, employment =" + employmentCD);
		}
		deadlineApplicationCheck(application.getCompanyID(), closureEmployment.get().getClosureId(), application.getEmployeeID(),
				periodCurrentMonth.getStartDate(), periodCurrentMonth.getEndDate(), startDate, endDate);
		
		// アルゴリズム「申請の受付制限をチェック」を実施する
		// applicationAcceptanceRestrictionsCheck(application.getCompanyID(), application.getAppType(), application.getPrePostAtr(), startDate, endDate, overTimeAtr);
		// 申請する開始日～申請する終了日までループする
		for(GeneralDate loopDate = startDate; loopDate.beforeOrEquals(endDate); loopDate = loopDate.addDays(1)){
            //hoatt 2019/10/14 #109087を対応
            if(lstDateHd != null && lstDateHd.contains(loopDate)){
                continue;
            }
			if(loopDate.equals(GeneralDate.today()) && application.getPrePostAtr().equals(PrePostAtr_Old.PREDICT) && application.isAppOverTime()){
				confirmCheckOvertime(application.getCompanyID(), application.getEmployeeID(), loopDate);
			}else{
				// アルゴリズム「確定チェック」を実施する
				confirmationCheck(application.getCompanyID(), application.getEmployeeID(), loopDate);
			}
		}
	}
	
	// moi nguoi chi co the o mot cty vao mot thoi diem
	// check xem nguoi xin con trong cty k
	public void retirementCheckBeforeJoinCompany(String companyID, String employeeID, GeneralDate date){
		// Imported(就業)「社員」を取得する
		PesionInforImport pesionInforImport = employeeAdaptor.getEmployeeInfor(employeeID);
		// 入社前をチェックする
		// データが１件以上取得できた 且つ 申請対象日 < Imported(就業)「社員」．入社年月日
		if(pesionInforImport.getEntryDate() != null && date.before(pesionInforImport.getEntryDate())) {
			throw new BusinessException("Msg_235");
		}
		
		// 退職後をチェックする
		// データが１件以上取得できた 且つ 申請対象日 > Imported(就業)「社員」．退職年月日
		if(pesionInforImport.getRetiredDate() != null && date.after(pesionInforImport.getRetiredDate())) {
			throw new BusinessException("Msg_391");
		}
	}
	
	public void deadlineApplicationCheck(String companyID, Integer closureID, String employeeID, 
			GeneralDate deadlineStartDate, GeneralDate deadlineEndDate, GeneralDate appStartDate, GeneralDate appEndDate){
		/*ログイン者のパスワードレベルが０の場合、チェックしない
		ロールが決まったら、要追加*/
		// if(passwordLevel!=0) return;
		
		// ドメインモデル「申請締切設定」．利用区分をチェックする(check利用区分)
		Optional<ApplicationDeadline> appDeadlineOp = appDeadlineRepository.getDeadlineByClosureId(companyID, closureID);
		if(!appDeadlineOp.isPresent()) {
			throw new RuntimeException("Not found ApplicationDeadline in table KRQST_APP_DEADLINE, closureID =" + closureID);
		}
		ApplicationDeadline appDeadline = appDeadlineOp.get();
		
		GeneralDate systemDate = GeneralDate.today();
		// ドメインモデル「申請締切設定」．利用区分をチェックする(check利用区分)
		if(appDeadline.getUserAtr().equals(UseAtr.NOTUSE)) { 
			return; 
		};
		
		// 申請する開始日(input)から申請する終了日(input)までループする
		for(int i = 0; appStartDate.compareTo(appEndDate) + i <= 0; i++){
			GeneralDate loopDate = appStartDate.addDays(i);
			if(loopDate.after(deadlineEndDate)){
				continue;
			}
			GeneralDate deadline = null;
			// ドメインモデル「申請締切設定」．締切基準をチェックする
			if(appDeadline.getDeadlineCriteria().equals(DeadlineCriteria.WORKING_DAY)) {
				// アルゴリズム「社員所属職場履歴を取得」を実行する
				WkpHistImport wkpHistImport = workplaceAdapter.findWkpBySid(employeeID, systemDate);
				// アルゴリズム「締切日を取得する」を実行する
				deadline = obtainDeadlineDateAdapter.obtainDeadlineDate(
						deadlineEndDate, 
						appDeadline.getDeadline().v(), 
						wkpHistImport.getWorkplaceId(), 
						companyID);
			} else {
				deadline = deadlineEndDate.addDays(appDeadline.getDeadline().v());
			}
			// システム日付と申請締め切り日を比較する
			if(systemDate.after(deadline)) {
				throw new BusinessException("Msg_327", deadline.toString(DATE_FORMAT)); 
			}
		}	
	}
	
	public void applicationAcceptanceRestrictionsCheck(String companyID, ApplicationType appType, PrePostAtr postAtr, GeneralDate startDate, GeneralDate endDate, OvertimeAppAtr overtimeAppAtr){
		/*ログイン者のパスワードレベルが０の場合、チェックしない
		ロールが決まったら、要追加*/
		// if(passwordLevel!=0) return;
		GeneralDate systemDate = GeneralDate.today();
		
		// キャッシュから取得
		Optional<AppTypeDiscreteSetting> appTypeDiscreteSettingOp = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType(companyID, appType.value);
		if(!appTypeDiscreteSettingOp.isPresent()) {
			throw new RuntimeException("Not found AppTypeDiscreteSetting in table KRQST_APP_TYPE_DISCRETE, appType =" +  appType);
		}
		Optional<RequestSetting> requestSetting = this.requestSettingRepository.findByCompany(companyID);
		List<ReceptionRestrictionSetting> receptionRestrictionSetting = new ArrayList<>();
		if(requestSetting.isPresent()){
			receptionRestrictionSetting = requestSetting.get().getApplicationSetting().getListReceptionRestrictionSetting().stream().filter(x -> x.getAppType().equals(ApplicationType_Old.OVER_TIME_APPLICATION)).collect(Collectors.toList());
		}
		AppTypeDiscreteSetting appTypeDiscreteSetting = appTypeDiscreteSettingOp.get();
		
		// 事前事後区分(input)をチェックする
		if(postAtr.equals(PrePostAtr_Old.POSTERIOR)){
			// ドメインモデル「事後の受付制限」．未来日許可しないをチェックする
			if (!appTypeDiscreteSetting.getRetrictPostAllowFutureFlg().equals(AllowAtr.ALLOW)) {
				return;
			}
			// 未来日の事後申請かチェックする
			if (startDate.after(systemDate) || endDate.after(systemDate)) {
				throw new BusinessException("Msg_328");
			} 
		} else {
			// ドメインモデル「事前の受付制限」．利用区分をチェックする
			if(appTypeDiscreteSetting.getRetrictPreUseFlg().equals(UseAtr.NOTUSE)){
				return;
			}
			// 申請する開始日(input)から申請する終了日(input)までループする
			boolean hasError = false;
			for(int i = 0; startDate.compareTo(endDate) + i <= 0; i++){
				// 対象日が申請可能かを判定する
				// error EA refactor 4
				// hasError = applyPossibleCheck.check(appType, startDate, overtimeAppAtr, appTypeDiscreteSetting, i, receptionRestrictionSetting);
				if (hasError == true) {
					throw new BusinessException("Msg_327", startDate.addDays(i).toString(DATE_FORMAT));
				}
			}
		
		}
	}
	
	public void confirmationCheck(String companyID, String employeeID, GeneralDate appDate){
		// アルゴリズム「申請の締め切り期限をチェック」を実施する
		SEmpHistImport empHistImport = employeeAdaptor.getEmpHist(companyID,
				employeeID, appDate);
		if (empHistImport == null || empHistImport.getEmploymentCode() == null) {
			throw new BusinessException("Msg_426");
		}
		String employmentCD = empHistImport.getEmploymentCode();
		Optional<ClosureEmployment> closureEmployment = closureEmploymentRepository
				.findByEmploymentCD(companyID, employmentCD);
		if (!closureEmployment.isPresent()) {
			throw new RuntimeException(
					"Not found ClosureEmployment in table KCLMT_CLOSURE_EMPLOYMENT, employment =" + employmentCD);
		}
		
		Optional<ActualLockImport> actualLockImport = this.actualLockAdapter.findByID(companyID,
				closureEmployment.get().getClosureId());
		
		Optional<RequestSetting> requestSetting = this.requestSettingRepository.findByCompany(companyID);
		if (!requestSetting.isPresent()) {
			return;
		}
		ApplicationSetting applicationSetting = requestSetting.get().getApplicationSetting();
		AppLimitSetting appLimitSetting = applicationSetting.getAppLimitSetting();
		// ドメインモデル「申請制限設定」．日別実績が確認済なら申請できないをチェックする(check domain 「申請制限設定」．日別実績が確認済なら申請できない)
		boolean hasError = false;
		hasError = dayActualConfirmDoneCheck.check(appLimitSetting, companyID, employeeID, appDate);
		if (hasError == true) {
			throw new BusinessException("Msg_448");
		}

		confirmCheck(appLimitSetting,actualLockImport,appDate,companyID,employeeID,closureEmployment);
	}
	public void confirmCheckOvertime(String companyID, String employeeID, GeneralDate appDate){
		// アルゴリズム「申請の締め切り期限をチェック」を実施する
		SEmpHistImport empHistImport = employeeAdaptor.getEmpHist(companyID,
				employeeID, appDate);
		if (empHistImport == null || empHistImport.getEmploymentCode() == null) {
			throw new BusinessException("Msg_426");
		}
		String employmentCD = empHistImport.getEmploymentCode();
		Optional<ClosureEmployment> closureEmployment = closureEmploymentRepository
				.findByEmploymentCD(companyID, employmentCD);
		if (!closureEmployment.isPresent()) {
			throw new RuntimeException(
					"Not found ClosureEmployment in table KCLMT_CLOSURE_EMPLOYMENT, employment =" + employmentCD);
		}
		Optional<ActualLockImport> actualLockImport = this.actualLockAdapter.findByID(companyID,
				closureEmployment.get().getClosureId());
		
		
		Optional<RequestSetting> requestSetting = this.requestSettingRepository.findByCompany(companyID);
		if (!requestSetting.isPresent()) {
			return;
		}
		ApplicationSetting applicationSetting = requestSetting.get().getApplicationSetting();
		AppLimitSetting appLimitSetting = applicationSetting.getAppLimitSetting();
		confirmCheck(appLimitSetting,actualLockImport,appDate,companyID,employeeID,closureEmployment);
	}
	private void confirmCheck(AppLimitSetting appLimitSetting, Optional<ActualLockImport> actualLockImport,
			GeneralDate appDate, String companyID, String employeeID,Optional<ClosureEmployment> closureEmployment) {
		boolean hasError = false;
		// ドメインモデル「申請制限設定」．月別実績が確認済なら申請できないをチェックする
		hasError = monthActualConfirmDoneCheck.check(appLimitSetting, companyID, employeeID, appDate);
		if (hasError == true) {
			throw new BusinessException("Msg_449");
		}
		
		// ドメインモデル「申請制限設定」．就業確定済の場合申請できないをチェックする
		hasError = workConfirmDoneCheck.check(appLimitSetting, companyID, employeeID, appDate, closureEmployment);
		if (hasError == true) {
			throw new BusinessException("Msg_450");
		}

		// ドメインモデル「申請制限設定」．実績修正がロック状態なら申請できないをチェックする
		hasError = actualLockingCheck.check(appLimitSetting, companyID, employeeID, appDate, actualLockImport);
		if (hasError == true) {
			throw new BusinessException("Msg_451");
		}
	}

	@Override
	public List<ConfirmMsgOutput> processBeforeRegister_New(String companyID, EmploymentRootAtr employmentRootAtr, boolean agentAtr,
			Application application, OvertimeAppAtr overtimeAppAtr, ErrorFlagImport errorFlg,
			List<GeneralDate> lstDateHd) {
		List<ConfirmMsgOutput> result = new ArrayList<>();
		// アルゴリズム「未入社前チェック」を実施する
		retirementCheckBeforeJoinCompany(companyID, application.getEmployeeID(), application.getAppDate().getApplicationDate());
		
		// アルゴリズム「社員の当月の期間を算出する」を実行する
		PeriodCurrentMonth periodCurrentMonth = otherCommonAlgorithmService.employeePeriodCurrentMonthCalculate(
				companyID, application.getEmployeeID(), application.getAppDate().getApplicationDate());
		
		Optional<GeneralDate> opStartDate = application.getOpAppStartDate().map(x -> x.getApplicationDate());
		Optional<GeneralDate> opEndDate = application.getOpAppEndDate().map(x -> x.getApplicationDate());
		if (opStartDate.isPresent() && opEndDate.isPresent()) {
			// 登録する期間のチェック
			//((TimeSpan)(申請する終了日 - 申請する開始日)).Days > 31がtrue
			if((ChronoUnit.DAYS.between(opStartDate.get().localDate(), opEndDate.get().localDate()) + 1)  > 31){
				throw new BusinessException("Msg_277");
			}
			// 登録可能期間のチェック(１年以内)
			//EA修正履歴 No.3210
			//hoatt 2019.03.22
			if(periodCurrentMonth.getStartDate().addYears(1).beforeOrEquals(opEndDate.get())) {
				//締め期間．開始年月日.AddYears(1) <= 申請する終了日がtrue
				// エラーメッセージメッセージ（Msg_1518）
				throw new BusinessException("Msg_1518", periodCurrentMonth.getStartDate().addYears(1).toString(DATE_FORMAT));
			}
			
			// 過去月のチェック
			if(opStartDate.get().before(periodCurrentMonth.getStartDate())) {
				throw new BusinessException("Msg_236");			
			}
		}		
		
		// キャッシュから承認ルートを取得する(Lấy comfirm root từ cache)	
		switch (errorFlg) {
		case NO_CONFIRM_PERSON:
			throw new BusinessException("Msg_238");
		case APPROVER_UP_10:
			throw new BusinessException("Msg_237");
		case NO_APPROVER:
			throw new BusinessException("Msg_324");
		default:
			break;
		}

		// アルゴリズム「申請の締め切り期限をチェック」を実施する
		deadlineApplicationCheck(companyID, periodCurrentMonth.getClosureId().value, application.getEmployeeID(),
				periodCurrentMonth.getStartDate(), periodCurrentMonth.getEndDate(), opStartDate.get(), opEndDate.get());
		
		// アルゴリズム「申請の受付制限をチェック」を実施する
		applicationAcceptanceRestrictionsCheck(companyID, application.getAppType(), application.getPrePostAtr(), opStartDate.get(), opEndDate.get(), overtimeAppAtr);
		
		// 申請する開始日～申請する終了日までループする
		for(GeneralDate loopDate = opStartDate.get(); loopDate.beforeOrEquals(opEndDate.get()); loopDate = loopDate.addDays(1)){
            //hoatt 2019/10/14 #109087を対応
            if(lstDateHd != null && lstDateHd.contains(loopDate)){
                continue;
            }
			if(loopDate.equals(GeneralDate.today()) && application.getPrePostAtr().equals(PrePostAtr_Old.PREDICT) && 
					application.getAppType() == ApplicationType.OVER_TIME_APPLICATION){
				// アルゴリズム「6.確定チェック（事前残業申請用）」を実施する
				confirmCheckOvertime(companyID, application.getEmployeeID(), loopDate);
			}else{
				// アルゴリズム「確定チェック」を実施する
				confirmationCheck(companyID, application.getEmployeeID(), loopDate);
			}
		}
		return result;
	}
}
