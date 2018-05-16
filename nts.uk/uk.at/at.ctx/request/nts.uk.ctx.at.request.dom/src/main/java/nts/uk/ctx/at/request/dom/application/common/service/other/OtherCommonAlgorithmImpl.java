package nts.uk.ctx.at.request.dom.application.common.service.other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SEmpHistImport;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AppCompltLeaveSyncOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.PeriodCurrentMonth;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.CompltLeaveSimMng;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.CompltLeaveSimMngRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.SyncState;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.ReceptionRestrictionSetting;
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
	@Inject
	private AbsenceLeaveAppRepository absRepo;
	@Inject
	private CompltLeaveSimMngRepository compLeaveRepo;
	@Inject
	private RequestSettingRepository requestSettingRepository;
	
	public PeriodCurrentMonth employeePeriodCurrentMonthCalculate(String companyID, String employeeID, GeneralDate date){
		/*
		アルゴリズム「社員所属雇用履歴を取得」を実行する(thực hiện xử lý 「社員所属雇用履歴を取得」)
		*/
		SEmpHistImport empHistImport = employeeAdaptor.getEmpHist(companyID, employeeID, date);
		if(empHistImport==null || empHistImport.getEmploymentCode()==null){
			throw new BusinessException("Msg_426");
		}
		String employmentCD = empHistImport.getEmploymentCode();
		// ドメインモデル「雇用に紐づく就業締め」を取得する
		Optional<ClosureEmployment> closureEmployment = closureEmploymentRepository.findByEmploymentCD(companyID, employmentCD);
		if(!closureEmployment.isPresent()){
			throw new BusinessException("Msg_1134");
		}
		/*
		ドメインモデル「締め」を取得する(lấy thông tin domain「締め」)
		Object<String: tightenID, String: currentMonth> obj1 = Tighten.find(companyID, employeeCD); // obj1 <=> (締めID,当月)
		*/
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
	public PrePostAtr preliminaryJudgmentProcessing(ApplicationType appType, GeneralDate appDate,int overTimeAtr) {
		GeneralDate systemDate = GeneralDate.today();
		Integer systemTime = GeneralDateTime.now().localDateTime().getHour()*60 
				+ GeneralDateTime.now().localDateTime().getMinute();
		String companyID = AppContexts.user().companyId();
		PrePostAtr prePostAtr = null;
		Optional<AppTypeDiscreteSetting> appTypeDisc = appTypeDiscreteSettingRepo.getAppTypeDiscreteSettingByAppType(companyID, appType.value);
		Optional<RequestSetting> requestSetting = this.requestSettingRepository.findByCompany(companyID);
		List<ReceptionRestrictionSetting> receptionRestrictionSetting = new ArrayList<>();
		if(requestSetting.isPresent()){
			receptionRestrictionSetting = requestSetting.get().getApplicationSetting().getListReceptionRestrictionSetting().stream().filter(x -> x.getAppType().equals(ApplicationType.OVER_TIME_APPLICATION)).collect(Collectors.toList());
		}
		//if appdate > systemDate 
		if(appDate.after(systemDate) ) {
			//xin truoc 事前事後区分= 事前
			prePostAtr = PrePostAtr.PREDICT;
			
		}else if(appDate.before(systemDate)) { // if appDate < systemDate
			//xin sau 事前事後区分= 事後
			prePostAtr = PrePostAtr.POSTERIOR;
		}else{ // if appDate = systemDate
//			// if RetrictPreUseFlg = notuse ->prePostAtr = POSTERIOR
//			if(appTypeDisc.get().getRetrictPreUseFlg() == UseAtr.NOTUSE) {
//				prePostAtr = PrePostAtr.POSTERIOR;
//			}else {
//				//「事前の受付制限」．チェック方法が日数でチェック
//				if(appTypeDisc.get().getRetrictPreMethodFlg() == CheckMethod.DAYCHECK) {
//					prePostAtr = PrePostAtr.POSTERIOR;
//				}else {//システム日時と受付制限日時と比較する
//					if(systemTime.compareTo(appTypeDisc.get().getRetrictPreTimeDay().v())==1) {
//						
//						prePostAtr = PrePostAtr.POSTERIOR;
//					}else { // if systemDateTime <=  RetrictPreTimeDay - > xin truoc
//						prePostAtr = PrePostAtr.PREDICT;
//					}
//				}
//			}
			if(appType.equals(ApplicationType.OVER_TIME_APPLICATION)){
				if(appTypeDisc.get().getRetrictPreMethodFlg() == CheckMethod.DAYCHECK) {
					prePostAtr = PrePostAtr.POSTERIOR;
				}else{
					int resultCompare = 0;
					if(overTimeAtr == 0 && receptionRestrictionSetting.get(0).getBeforehandRestriction().getPreOtTime() != null){
						resultCompare = systemTime.compareTo(receptionRestrictionSetting.get(0).getBeforehandRestriction().getPreOtTime().v());
					}else if(overTimeAtr == 1 && receptionRestrictionSetting.get(0).getBeforehandRestriction().getNormalOtTime() !=  null){
						resultCompare = systemTime.compareTo(receptionRestrictionSetting.get(0).getBeforehandRestriction().getNormalOtTime().v());
					}else if(overTimeAtr == 2 && receptionRestrictionSetting.get(0).getBeforehandRestriction().getTimeBeforehandRestriction() !=  null){
						resultCompare = systemTime.compareTo(receptionRestrictionSetting.get(0).getBeforehandRestriction().getTimeBeforehandRestriction().v());
					}
					if(resultCompare == 1) {
						prePostAtr = PrePostAtr.POSTERIOR;
					}else { // if systemDateTime <=  RetrictPreTimeDay - > xin truoc
						prePostAtr = PrePostAtr.PREDICT;
					}
				}
			}else{
				prePostAtr = PrePostAtr.POSTERIOR;
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
	/**
	 * 9.同時申請された振休振出申請を取得する
	 * @author hoatt
	 * @param companyId
	 * @param appId
	 * @return
	 */
	@Override
	public AppCompltLeaveSyncOutput getAppComplementLeaveSync(String companyId, String appId) {
		// TODO Auto-generated method stub
		Optional<AbsenceLeaveApp> abs = absRepo.findByAppId(appId);
		Optional<CompltLeaveSimMng> sync = null;
		String absId = "";
		String recId = "";
		boolean synced = false;
		int type = 0;
		if(abs.isPresent()){//don xin nghi
			absId = appId;
			//tim lien ket theo abs
			sync = compLeaveRepo.findByAbsID(appId);
			if(sync.isPresent() && sync.get().getSyncing().equals(SyncState.SYNCHRONIZING)){
				recId = sync.get().getRecAppID();
				synced = true;
			}
		}else{//don lam bu
			type = 1;
			recId = appId;
			sync = compLeaveRepo.findByRecID(appId);
			if(sync.isPresent() && sync.get().getSyncing().equals(SyncState.SYNCHRONIZING)){
				absId = sync.get().getAbsenceLeaveAppID();
				synced = true;
			}
		}
		return new AppCompltLeaveSyncOutput(absId, recId, synced, type);
	}
}
