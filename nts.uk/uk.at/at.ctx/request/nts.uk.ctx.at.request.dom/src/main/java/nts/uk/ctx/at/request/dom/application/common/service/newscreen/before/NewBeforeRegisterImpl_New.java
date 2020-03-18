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
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.PesionInforImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SEmpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.actuallock.ActualLockAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.actuallock.ActualLockImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.daycalendar.ObtainDeadlineDateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.CollectApprovalRootPatternService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.StartupErrorCheckService;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.PeriodCurrentMonth;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service.ActualLockingCheck;
//import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service.DayActualConfirmDoneCheck;
//import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service.MonthActualConfirmDoneCheck;
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
	
	//@Inject
	//private DayActualConfirmDoneCheck dayActualConfirmDoneCheck;

	//@Inject
	//private MonthActualConfirmDoneCheck monthActualConfirmDoneCheck;

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
	
    public void processBeforeRegister(Application_New application, int overTimeAtr, boolean checkOver1Year, List<GeneralDate> lstDateHd){
		// 繧｢繝ｫ繧ｴ繝ｪ繧ｺ繝�縲梧悴蜈･遉ｾ蜑阪メ繧ｧ繝�繧ｯ縲阪ｒ螳滓命縺吶ｋ
		retirementCheckBeforeJoinCompany(application.getCompanyID(), application.getEmployeeID(), application.getAppDate());
		
		// 繧｢繝ｫ繧ｴ繝ｪ繧ｺ繝�縲檎､ｾ蜩｡縺ｮ蠖捺怦縺ｮ譛滄俣繧堤ｮ怜�ｺ縺吶ｋ縲阪ｒ螳溯｡後☆繧�
		PeriodCurrentMonth periodCurrentMonth = otherCommonAlgorithmService.employeePeriodCurrentMonthCalculate(application.getCompanyID(), application.getEmployeeID(), application.getAppDate());
		
		GeneralDate startDate = application.getAppDate();
		GeneralDate endDate = application.getAppDate();
		if (application.getStartDate().isPresent() && application.getEndDate().isPresent()) {
			startDate = application.getStartDate().get();
			endDate = application.getEndDate().get();
			
			// 逋ｻ骭ｲ縺吶ｋ譛滄俣縺ｮ繝√ぉ繝�繧ｯ
			//((TimeSpan)(逕ｳ隲九☆繧狗ｵゆｺ�譌･ - 逕ｳ隲九☆繧矩幕蟋区律)).Days > 31縺荊rue
			if((ChronoUnit.DAYS.between(startDate.localDate(), endDate.localDate()) + 1)  > 31){
				throw new BusinessException("Msg_277");
			}
			// 逋ｻ骭ｲ蜿ｯ閭ｽ譛滄俣縺ｮ繝√ぉ繝�繧ｯ(�ｼ大ｹｴ莉･蜀�)
			//EA菫ｮ豁｣螻･豁ｴ No.3210
			//hoatt 2019.03.22
			if(periodCurrentMonth.getStartDate().addYears(1).beforeOrEquals(endDate) && checkOver1Year) {
				//邱�繧∵悄髢難ｼ朱幕蟋句ｹｴ譛域律.AddYears(1) <= 逕ｳ隲九☆繧狗ｵゆｺ�譌･縺荊rue
				//遒ｺ隱阪Γ繝�繧ｻ繝ｼ繧ｸ�ｼ�Msg_1518�ｼ峨ｒ陦ｨ遉ｺ縺吶ｋ
				throw new BusinessException("Msg_1518", periodCurrentMonth.getStartDate().addYears(1).toString(DATE_FORMAT));
			}
			
			// 驕主悉譛医�ｮ繝√ぉ繝�繧ｯ
			if(startDate.before(periodCurrentMonth.getStartDate())) {
				throw new BusinessException("Msg_236");			
			}
		}		
		
		// 繧ｭ繝｣繝�繧ｷ繝･縺九ｉ謇ｿ隱阪Ν繝ｼ繝医ｒ蜿門ｾ励☆繧�(L蘯･y comfirm root t盻ｫ cache)	
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
		
		// 繧｢繝ｫ繧ｴ繝ｪ繧ｺ繝�縲檎筏隲九�ｮ邱�繧∝��繧頑悄髯舌ｒ繝√ぉ繝�繧ｯ縲阪ｒ螳滓命縺吶ｋ
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
		
		// 繧｢繝ｫ繧ｴ繝ｪ繧ｺ繝�縲檎筏隲九�ｮ蜿嶺ｻ伜宛髯舌ｒ繝√ぉ繝�繧ｯ縲阪ｒ螳滓命縺吶ｋ
		applicationAcceptanceRestrictionsCheck(application.getCompanyID(), application.getAppType(), application.getPrePostAtr(), startDate, endDate,overTimeAtr);
		// 逕ｳ隲九☆繧矩幕蟋区律�ｽ樒筏隲九☆繧狗ｵゆｺ�譌･縺ｾ縺ｧ繝ｫ繝ｼ繝励☆繧�
		for(GeneralDate loopDate = startDate; loopDate.beforeOrEquals(endDate); loopDate = loopDate.addDays(1)){
            //hoatt 2019/10/14 #109087繧貞ｯｾ蠢�
            if(lstDateHd != null && lstDateHd.contains(loopDate)){
                continue;
            }
			if(loopDate.equals(GeneralDate.today()) && application.getPrePostAtr().equals(PrePostAtr.PREDICT) && application.isAppOverTime()){
				confirmCheckOvertime(application.getCompanyID(), application.getEmployeeID(), loopDate);
			}else{
				// 繧｢繝ｫ繧ｴ繝ｪ繧ｺ繝�縲檎｢ｺ螳壹メ繧ｧ繝�繧ｯ縲阪ｒ螳滓命縺吶ｋ
				confirmationCheck(application.getCompanyID(), application.getEmployeeID(), loopDate);
			}
		}
	}
	
	// moi nguoi chi co the o mot cty vao mot thoi diem
	// check xem nguoi xin con trong cty k
	public void retirementCheckBeforeJoinCompany(String companyID, String employeeID, GeneralDate date){
		// Imported(蟆ｱ讌ｭ)縲檎､ｾ蜩｡縲阪ｒ蜿門ｾ励☆繧�
		PesionInforImport pesionInforImport = employeeAdaptor.getEmployeeInfor(employeeID);
		// 蜈･遉ｾ蜑阪ｒ繝√ぉ繝�繧ｯ縺吶ｋ
		// 繝�繝ｼ繧ｿ縺鯉ｼ台ｻｶ莉･荳雁叙蠕励〒縺阪◆ 荳斐▽ 逕ｳ隲句ｯｾ雎｡譌･ < Imported(蟆ｱ讌ｭ)縲檎､ｾ蜩｡縲搾ｼ主�･遉ｾ蟷ｴ譛域律
		if(pesionInforImport.getEntryDate() != null && date.before(pesionInforImport.getEntryDate())) {
			throw new BusinessException("Msg_235");
		}
		
		// 騾�閨ｷ蠕後ｒ繝√ぉ繝�繧ｯ縺吶ｋ
		// 繝�繝ｼ繧ｿ縺鯉ｼ台ｻｶ莉･荳雁叙蠕励〒縺阪◆ 荳斐▽ 逕ｳ隲句ｯｾ雎｡譌･ > Imported(蟆ｱ讌ｭ)縲檎､ｾ蜩｡縲搾ｼ朱��閨ｷ蟷ｴ譛域律
		if(pesionInforImport.getRetiredDate() != null && date.after(pesionInforImport.getRetiredDate())) {
			throw new BusinessException("Msg_391");
		}
	}
	
	public void deadlineApplicationCheck(String companyID, Integer closureID, String employeeID, 
			GeneralDate deadlineStartDate, GeneralDate deadlineEndDate, GeneralDate appStartDate, GeneralDate appEndDate){
		/*繝ｭ繧ｰ繧､繝ｳ閠�縺ｮ繝代せ繝ｯ繝ｼ繝峨Ξ繝吶Ν縺鯉ｼ舌�ｮ蝣ｴ蜷医�√メ繧ｧ繝�繧ｯ縺励↑縺�
		繝ｭ繝ｼ繝ｫ縺梧ｱｺ縺ｾ縺｣縺溘ｉ縲∬ｦ∬ｿｽ蜉�*/
		// if(passwordLevel!=0) return;
		
		// 繝峨Γ繧､繝ｳ繝｢繝�繝ｫ縲檎筏隲狗ｷ�蛻�險ｭ螳壹�搾ｼ主茜逕ｨ蛹ｺ蛻�繧偵メ繧ｧ繝�繧ｯ縺吶ｋ(check蛻ｩ逕ｨ蛹ｺ蛻�)
		Optional<ApplicationDeadline> appDeadlineOp = appDeadlineRepository.getDeadlineByClosureId(companyID, closureID);
		if(!appDeadlineOp.isPresent()) {
			throw new RuntimeException("Not found ApplicationDeadline in table KRQST_APP_DEADLINE, closureID =" + closureID);
		}
		ApplicationDeadline appDeadline = appDeadlineOp.get();
		
		GeneralDate systemDate = GeneralDate.today();
		// 繝峨Γ繧､繝ｳ繝｢繝�繝ｫ縲檎筏隲狗ｷ�蛻�險ｭ螳壹�搾ｼ主茜逕ｨ蛹ｺ蛻�繧偵メ繧ｧ繝�繧ｯ縺吶ｋ(check蛻ｩ逕ｨ蛹ｺ蛻�)
		if(appDeadline.getUserAtr().equals(UseAtr.NOTUSE)) { 
			return; 
		};
		
		// 逕ｳ隲九☆繧矩幕蟋区律(input)縺九ｉ逕ｳ隲九☆繧狗ｵゆｺ�譌･(input)縺ｾ縺ｧ繝ｫ繝ｼ繝励☆繧�
		for(int i = 0; appStartDate.compareTo(appEndDate) + i <= 0; i++){
			GeneralDate loopDate = appStartDate.addDays(i);
			if(loopDate.after(deadlineEndDate)){
				continue;
			}
			GeneralDate deadline = null;
			// 繝峨Γ繧､繝ｳ繝｢繝�繝ｫ縲檎筏隲狗ｷ�蛻�險ｭ螳壹�搾ｼ守ｷ�蛻�蝓ｺ貅悶ｒ繝√ぉ繝�繧ｯ縺吶ｋ
			if(appDeadline.getDeadlineCriteria().equals(DeadlineCriteria.WORKING_DAY)) {
				// 繧｢繝ｫ繧ｴ繝ｪ繧ｺ繝�縲檎､ｾ蜩｡謇�螻櫁�ｷ蝣ｴ螻･豁ｴ繧貞叙蠕励�阪ｒ螳溯｡後☆繧�
				WkpHistImport wkpHistImport = workplaceAdapter.findWkpBySid(employeeID, systemDate);
				// 繧｢繝ｫ繧ｴ繝ｪ繧ｺ繝�縲檎ｷ�蛻�譌･繧貞叙蠕励☆繧九�阪ｒ螳溯｡後☆繧�
				deadline = obtainDeadlineDateAdapter.obtainDeadlineDate(
						deadlineEndDate, 
						appDeadline.getDeadline().v(), 
						wkpHistImport.getWorkplaceId(), 
						companyID);
			} else {
				deadline = deadlineEndDate.addDays(appDeadline.getDeadline().v());
			}
			// 繧ｷ繧ｹ繝�繝�譌･莉倥→逕ｳ隲狗ｷ�繧∝��繧頑律繧呈ｯ碑ｼ�縺吶ｋ
			if(systemDate.after(deadline)) {
				throw new BusinessException("Msg_327", deadline.toString(DATE_FORMAT)); 
			}
		}	
	}
	
	public void applicationAcceptanceRestrictionsCheck(String companyID, ApplicationType appType, PrePostAtr postAtr, GeneralDate startDate, GeneralDate endDate,int overTimeAtr){
		/*繝ｭ繧ｰ繧､繝ｳ閠�縺ｮ繝代せ繝ｯ繝ｼ繝峨Ξ繝吶Ν縺鯉ｼ舌�ｮ蝣ｴ蜷医�√メ繧ｧ繝�繧ｯ縺励↑縺�
		繝ｭ繝ｼ繝ｫ縺梧ｱｺ縺ｾ縺｣縺溘ｉ縲∬ｦ∬ｿｽ蜉�*/
		// if(passwordLevel!=0) return;
		GeneralDate systemDate = GeneralDate.today();
		
		// 繧ｭ繝｣繝�繧ｷ繝･縺九ｉ蜿門ｾ�
		Optional<AppTypeDiscreteSetting> appTypeDiscreteSettingOp = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType(companyID, appType.value);
		if(!appTypeDiscreteSettingOp.isPresent()) {
			throw new RuntimeException("Not found AppTypeDiscreteSetting in table KRQST_APP_TYPE_DISCRETE, appType =" +  appType);
		}
		Optional<RequestSetting> requestSetting = this.requestSettingRepository.findByCompany(companyID);
		List<ReceptionRestrictionSetting> receptionRestrictionSetting = new ArrayList<>();
		if(requestSetting.isPresent()){
			receptionRestrictionSetting = requestSetting.get().getApplicationSetting().getListReceptionRestrictionSetting().stream().filter(x -> x.getAppType().equals(ApplicationType.OVER_TIME_APPLICATION)).collect(Collectors.toList());
		}
		AppTypeDiscreteSetting appTypeDiscreteSetting = appTypeDiscreteSettingOp.get();
		
		// 莠句燕莠句ｾ悟玄蛻�(input)繧偵メ繧ｧ繝�繧ｯ縺吶ｋ
		if(postAtr.equals(PrePostAtr.POSTERIOR)){
			// 繝峨Γ繧､繝ｳ繝｢繝�繝ｫ縲御ｺ句ｾ後�ｮ蜿嶺ｻ伜宛髯舌�搾ｼ取悴譚･譌･險ｱ蜿ｯ縺励↑縺�繧偵メ繧ｧ繝�繧ｯ縺吶ｋ
			if (!appTypeDiscreteSetting.getRetrictPostAllowFutureFlg().equals(AllowAtr.ALLOW)) {
				return;
			}
			// 譛ｪ譚･譌･縺ｮ莠句ｾ檎筏隲九°繝√ぉ繝�繧ｯ縺吶ｋ
			if (startDate.after(systemDate) || endDate.after(systemDate)) {
				throw new BusinessException("Msg_328");
			} 
		} else {
			// 繝峨Γ繧､繝ｳ繝｢繝�繝ｫ縲御ｺ句燕縺ｮ蜿嶺ｻ伜宛髯舌�搾ｼ主茜逕ｨ蛹ｺ蛻�繧偵メ繧ｧ繝�繧ｯ縺吶ｋ
			if(appTypeDiscreteSetting.getRetrictPreUseFlg().equals(UseAtr.NOTUSE)){
				return;
			}
			// 逕ｳ隲九☆繧矩幕蟋区律(input)縺九ｉ逕ｳ隲九☆繧狗ｵゆｺ�譌･(input)縺ｾ縺ｧ繝ｫ繝ｼ繝励☆繧�
			boolean hasError = false;
			for(int i = 0; startDate.compareTo(endDate) + i <= 0; i++){
				// 蟇ｾ雎｡譌･縺檎筏隲句庄閭ｽ縺九ｒ蛻､螳壹☆繧�
				hasError = applyPossibleCheck.check(appType, startDate, overTimeAtr, appTypeDiscreteSetting, i, receptionRestrictionSetting);
				if (hasError == true) {
					throw new BusinessException("Msg_327", startDate.addDays(i).toString(DATE_FORMAT));
				}
			}
		
		}
	}
	
	public void confirmationCheck(String companyID, String employeeID, GeneralDate appDate){
		// 繧｢繝ｫ繧ｴ繝ｪ繧ｺ繝�縲檎筏隲九�ｮ邱�繧∝��繧頑悄髯舌ｒ繝√ぉ繝�繧ｯ縲阪ｒ螳滓命縺吶ｋ
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
		// 繝峨Γ繧､繝ｳ繝｢繝�繝ｫ縲檎筏隲句宛髯占ｨｭ螳壹�搾ｼ取律蛻･螳溽ｸｾ縺檎｢ｺ隱肴ｸ医↑繧臥筏隲九〒縺阪↑縺�繧偵メ繧ｧ繝�繧ｯ縺吶ｋ(check domain 縲檎筏隲句宛髯占ｨｭ螳壹�搾ｼ取律蛻･螳溽ｸｾ縺檎｢ｺ隱肴ｸ医↑繧臥筏隲九〒縺阪↑縺�)
		boolean hasError = false;
		//hasError = dayActualConfirmDoneCheck.check(appLimitSetting, companyID, employeeID, appDate);
		if (hasError == true) {
			throw new BusinessException("Msg_448");
		}

		confirmCheck(appLimitSetting,actualLockImport,appDate,companyID,employeeID,closureEmployment);
	}
	public void confirmCheckOvertime(String companyID, String employeeID, GeneralDate appDate){
		// 繧｢繝ｫ繧ｴ繝ｪ繧ｺ繝�縲檎筏隲九�ｮ邱�繧∝��繧頑悄髯舌ｒ繝√ぉ繝�繧ｯ縲阪ｒ螳滓命縺吶ｋ
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
		// 繝峨Γ繧､繝ｳ繝｢繝�繝ｫ縲檎筏隲句宛髯占ｨｭ螳壹�搾ｼ取怦蛻･螳溽ｸｾ縺檎｢ｺ隱肴ｸ医↑繧臥筏隲九〒縺阪↑縺�繧偵メ繧ｧ繝�繧ｯ縺吶ｋ
		//hasError = monthActualConfirmDoneCheck.check(appLimitSetting, companyID, employeeID, appDate);
		if (hasError == true) {
			throw new BusinessException("Msg_449");
		}
		
		// 繝峨Γ繧､繝ｳ繝｢繝�繝ｫ縲檎筏隲句宛髯占ｨｭ螳壹�搾ｼ主ｰｱ讌ｭ遒ｺ螳壽ｸ医�ｮ蝣ｴ蜷育筏隲九〒縺阪↑縺�繧偵メ繧ｧ繝�繧ｯ縺吶ｋ
		hasError = workConfirmDoneCheck.check(appLimitSetting, companyID, employeeID, appDate, closureEmployment);
		if (hasError == true) {
			throw new BusinessException("Msg_450");
		}

		// 繝峨Γ繧､繝ｳ繝｢繝�繝ｫ縲檎筏隲句宛髯占ｨｭ螳壹�搾ｼ主ｮ溽ｸｾ菫ｮ豁｣縺後Ο繝�繧ｯ迥ｶ諷九↑繧臥筏隲九〒縺阪↑縺�繧偵メ繧ｧ繝�繧ｯ縺吶ｋ
		hasError = actualLockingCheck.check(appLimitSetting, companyID, employeeID, appDate, actualLockImport);
		if (hasError == true) {
			throw new BusinessException("Msg_451");
		}
	}
}
