package nts.uk.ctx.at.request.dom.application.common.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.ClosureAdaptor;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeAdaptor;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.CheckMethod;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.primitive.UseAtr;

@Stateless
public class OtherCommonAlgorithmDefault implements OtherCommonAlgorithmService {
	
	@Inject
	private EmployeeAdaptor employeeAdaptor;
	
	@Inject
	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepo;
	
	@Inject
	private ApplicationSettingRepository appSettingRepo;
	
	/*@Inject
	private ClosureAdaptor closureAdaptor;*/
	
	public List<GeneralDate> employeePeriodCurrentMonthCalculate(String companyID, String employeeID, GeneralDate date){
		/*
		アルゴリズム「社員所属雇用履歴を取得」を実行する(thực hiện xử lý 「社員所属雇用履歴を取得」)
		String employeeCD = EmployeeEmploymentHistory.find(employeeID, date); // emloyeeCD <=> 雇用コード
		*/
		String employeeCD = employeeAdaptor.getEmploymentCode(companyID, employeeID, date);
		
		/*
		ドメインモデル「締め」を取得する(lấy thông tin domain「締め」)
		Object<String: tightenID, String: currentMonth> obj1 = Tighten.find(companyID, employeeCD); // obj1 <=> (締めID,当月)
		*/
		//String closure = closureAdaptor.findById(companyID, employeeCD);
		
		/*
		当月の期間を算出する(tính period của tháng hiện tại)
		Object<String: startDate, String: endDate> obj2 = Period.find(obj1.tightenID, obj1.currentMonth); // obj2 <=> 締め期間(開始年月日,終了年月日) 
		*/
		return new ArrayList<GeneralDate>();
	}

	@Override
	public void getWorkingHoursByWorkplace(String companyID, String employeeID, GeneralDate referenceDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PrePostAtr preliminaryJudgmentProcessing(ApplicationType appType, GeneralDate appDate) {
		GeneralDate systemDate = GeneralDate.today();
		BigDecimal systemTime = BigDecimal.valueOf(GeneralDateTime.now().localDateTime().getHour()*60 
				+ GeneralDateTime.now().localDateTime().getMinute());
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

	@Override
	public void judgmentPrePostAtr(ApplicationType appType, GeneralDate appDate) {
		String companyID = AppContexts.user().companyId();
		Optional<ApplicationSetting> appSetting = appSettingRepo.getApplicationSettingByComID(companyID);
		if(appSetting.get().getDisplayPrePostFlg() == AppDisplayAtr.DISPLAY) { // AppDisplayAtr displayPrePostFlg
			
		}else {
			
		}
	}
}
