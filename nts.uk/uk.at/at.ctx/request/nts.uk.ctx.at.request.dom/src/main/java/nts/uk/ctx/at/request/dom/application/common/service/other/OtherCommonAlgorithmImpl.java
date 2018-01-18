package nts.uk.ctx.at.request.dom.application.common.service.other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SEmpHistImport;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.PeriodCurrentMonth;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.CheckMethod;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.InitValueAtr;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplaceRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class OtherCommonAlgorithmImpl implements OtherCommonAlgorithm {
	
	@Inject
	private EmployeeRequestAdapter employeeAdaptor;
	@Inject
	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepo;
	
	@Inject
	private ApplicationSettingRepository appSettingRepo;
	
	@Inject
	private WorkTimeWorkplaceRepository workTimeWorkplaceRepo;
	
		@Inject
		private ClosureRepository closureRepository;
	
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;
	
	@Inject
	private ClosureService closureService;
	
	public PeriodCurrentMonth employeePeriodCurrentMonthCalculate(String companyID, String employeeID, GeneralDate date){
		/*
		アルゴリズム「社員所属雇用履歴を取得」を実行する(thực hiện xử lý 「社員所属雇用履歴を取得」)
		*/
		SEmpHistImport empHistImport = employeeAdaptor.getEmpHist(companyID, employeeID, date);
		if(empHistImport==null || empHistImport.getEmploymentCode()==null){
			throw new BusinessException("Msg_426");
		}
		String employmentCD = empHistImport.getEmploymentCode();
		/*
		ドメインモデル「締め」を取得する(lấy thông tin domain「締め」)
		Object<String: tightenID, String: currentMonth> obj1 = Tighten.find(companyID, employeeCD); // obj1 <=> (締めID,当月)
		*/
		Optional<ClosureEmployment> closureEmployment = closureEmploymentRepository.findByEmploymentCD(companyID, employmentCD);
		if(!closureEmployment.isPresent()){
			throw new RuntimeException("khong co closure employement");
		}
		Optional<Closure> closure = closureRepository.findById(companyID, closureEmployment.get().getClosureId());
		if(!closure.isPresent()){
			throw new RuntimeException("khong co closure");
		}
		/*
		当月の期間を算出する(tính period của tháng hiện tại)
		Object<String: startDate, String: endDate> obj2 = Period.find(obj1.tightenID, obj1.currentMonth); // obj2 <=> 締め期間(開始年月日,終了年月日) 
		*/
		DatePeriod datePeriod = closureService.getClosurePeriod(closure.get().getClosureId().value,
				closure.get().getClosureMonth().getProcessingYm());
		return new PeriodCurrentMonth(datePeriod.start(), datePeriod.end());
	}
	/**
	 * 1.職場別就業時間帯を取得
	 */
	@Override
	public List<String> getWorkingHoursByWorkplace(String companyID, String employeeID, GeneralDate referenceDate) {
		List<String> listEmployeeAdaptor = employeeAdaptor.findWpkIdsBySid(companyID, employeeID, referenceDate);
		//取得した所属職場ID＋その上位職場IDを先頭から最後までループする
		List<String> listWorkTimeCodes = new ArrayList<>();
		for(String employeeAdaptor : listEmployeeAdaptor) {
			listWorkTimeCodes = workTimeWorkplaceRepo
					.getWorkTimeWorkplaceById(companyID, employeeAdaptor);
			if(listWorkTimeCodes.size()>0) {
				Collections.sort(listWorkTimeCodes);
				break;
			}
			
		}
		return listWorkTimeCodes;
	}

	@Override
	public PrePostAtr preliminaryJudgmentProcessing(ApplicationType appType, GeneralDate appDate) {
		GeneralDate systemDate = GeneralDate.today();
		Integer systemTime = GeneralDateTime.now().localDateTime().getHour()*60 
				+ GeneralDateTime.now().localDateTime().getMinute();
		String companyID = AppContexts.user().companyId();
		PrePostAtr prePostAtr = null;
		Optional<AppTypeDiscreteSetting> appTypeDisc = appTypeDiscreteSettingRepo.getAppTypeDiscreteSettingByAppType(companyID, appType.value);
		//if appdate > systemDate 
		if(appDate.after(systemDate) ) {
			//xin truoc 事前事後区分= 事前
			prePostAtr = PrePostAtr.PREDICT;
			
		}else if(appDate.before(systemDate)) { // if appDate < systemDate
			//xin sau 事前事後区分= 事後
			prePostAtr = PrePostAtr.POSTERIOR;
		}else{ // if appDate = systemDate
			// if RetrictPreUseFlg = notuse ->prePostAtr = POSTERIOR
			if(appTypeDisc.get().getRetrictPreUseFlg() == UseAtr.NOTUSE) {
				prePostAtr = PrePostAtr.POSTERIOR;
			}else {
				//「事前の受付制限」．チェック方法が日数でチェック
				if(appTypeDisc.get().getRetrictPreMethodFlg() == CheckMethod.DAYCHECK) {
					prePostAtr = PrePostAtr.POSTERIOR;
				}else {//システム日時と受付制限日時と比較する
					if(systemTime.compareTo(appTypeDisc.get().getRetrictPreTimeDay().v())==1) {
						
						prePostAtr = PrePostAtr.POSTERIOR;
					}else { // if systemDateTime <=  RetrictPreTimeDay - > xin truoc
						prePostAtr = PrePostAtr.PREDICT;
					}
				}
			}
		}
			
		return prePostAtr;
	}
	/**
	 * 5.事前事後区分の判断
	 */
	@Override
	public InitValueAtr judgmentPrePostAtr(ApplicationType appType, GeneralDate appDate,boolean checkCaller) {
		InitValueAtr outputInitValueAtr = null;
		String companyID = AppContexts.user().companyId();
		Optional<AppTypeDiscreteSetting> appTypeDisc = appTypeDiscreteSettingRepo.getAppTypeDiscreteSettingByAppType(companyID, appType.value);
		Optional<ApplicationSetting> appSetting = appSettingRepo.getApplicationSettingByComID(companyID);
		if(appSetting.get().getDisplayPrePostFlg() == AppDisplayAtr.DISPLAY) { // AppDisplayAtr displayPrePostFlg
			//メニューから起動(Boot from menu) : checkCaller == true
			if(checkCaller) {
				outputInitValueAtr = appTypeDisc.get().getPrePostInitFlg();
			}else {// その他のPG（日別修正、トップページアラーム、残業指示）から起動(Start from other PG (daily correction, top page alarm, overtime work instruction)): checkCaller == false
				outputInitValueAtr = InitValueAtr.POST;
			}
		}else { //if not display
			outputInitValueAtr = InitValueAtr.NOCHOOSE;
		}
		return outputInitValueAtr;
	}
}
