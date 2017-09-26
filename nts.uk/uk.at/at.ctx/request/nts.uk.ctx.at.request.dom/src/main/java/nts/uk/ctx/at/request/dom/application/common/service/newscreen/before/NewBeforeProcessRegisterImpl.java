package nts.uk.ctx.at.request.dom.application.common.service.newscreen.before;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.PeriodCurrentMonth;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadline;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadlineRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.DeadlineCriteria;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AllowAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.common.CheckMethod;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.InitValueAtr;
import nts.uk.ctx.at.request.dom.application.common.UseAtr;

@Stateless
public class NewBeforeProcessRegisterImpl implements NewBeforeProcessRegister {
	
	@Inject
	private EmployeeAdapter employeeAdaptor;
	
	@Inject
	private ApplicationDeadlineRepository appDeadlineRepository;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithmService;
	
	@Inject
	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;
	
	public void processBeforeRegister(String companyID, String employeeID, GeneralDate date, PrePostAtr postAtr, int routeAtr, int appType){
		retirementCheckBeforeJoinCompany(companyID, employeeID, date);
		PeriodCurrentMonth periodCurrentMonth = otherCommonAlgorithmService.employeePeriodCurrentMonthCalculate(companyID, employeeID, date);
		// if(endDate.after(startDate.addDays(31))) throw new BusinessException("Msg_277");
		// if(periodCurrentMonth.getStartDate().addYears(1).beforeOrEquals(periodCurrentMonth.getEndDate())) throw new BusinessException("Msg_276");
		// if(startDate.before(periodCurrentMonth.getStartDate())) throw new BusinessException("Msg_236");
		/*
		confirmRoot = from cache;
		ApproFrame.ApprovarSID
		if(confirmRoot.ApprovedRouteErrorFlag==undefined) throw new BusinessException("Msg_238");
		if(confirmRoot.ApprovedRouteErrorFlag.count==0) throw new BusinessException("Msg_324");
		if(confirmRoot.ApprovedRouteErrorFlag.count>=10) throw new BusinessException("Msg_237");
		if(passwordLevel!=0) deadlineApplicationCheck();
		if(passwordLevel!=0) applicationAcceptanceRestrictionsCheck();
		confirmationCheck();
		*/
	}
	
	public void retirementCheckBeforeJoinCompany(String companyID, String employeeID, GeneralDate date){
		/*
		Optional<Employee> e = Employee.find(employeeID); 
		if(e.isPresent()){
			if(date<e.entryDate) throw new BusinessException(Msg_235);
			if(date<e.retirementDate) throw new BusinessException(Msg_391); 
		}
		*/
	}
	
	public void deadlineApplicationCheck(String companyID, String appID, GeneralDate appStartDate, GeneralDate appEndDate, GeneralDate startDate, GeneralDate endDate){
		Optional<ApplicationDeadline> appDeadlineOp = appDeadlineRepository.getDeadlineByClosureId(companyID, Integer.parseInt(appID));
		if(!appDeadlineOp.isPresent()) throw new RuntimeException();
		ApplicationDeadline appDeadline = appDeadlineOp.get();
		if(appDeadline.getUserAtr().equals(UseAtr.NOTUSE)) return;
		for(GeneralDate loopDate = startDate; loopDate.beforeOrEquals(endDate); loopDate.addDays(1)){
			GeneralDate deadline = null;
			if(appDeadline.getDeadlineCriteria().equals(DeadlineCriteria.WORKING_DAY)) {
				// Waiting for algorithm
				// input: appEndDate, obj.deadline, 
			} else {
				deadline = appEndDate.addDays(appDeadline.getDeadline().v());
			}
			if(GeneralDate.today().afterOrEquals(deadline)) throw new BusinessException("Msg_327"); 
		}	
	}
	
	public void applicationAcceptanceRestrictionsCheck(PrePostAtr postAtr, GeneralDate startDate, GeneralDate endDate){
		GeneralDate systemDate = GeneralDate.today();
		Optional<AppTypeDiscreteSetting> appTypeDiscreteSettingOp = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType("", ApplicationType.STAMP_APPLICATION.value);
		if(!appTypeDiscreteSettingOp.isPresent()) throw new RuntimeException();
		AppTypeDiscreteSetting appTypeDiscreteSetting = appTypeDiscreteSettingOp.get();
		if(postAtr.equals(PrePostAtr.POSTERIOR)){
			if(appTypeDiscreteSetting.getRetrictPostAllowFutureFlg().equals(AllowAtr.NOTALLOW)){
				return;
			} else {
				if(startDate.after(systemDate)||endDate.after(systemDate)){
					throw new BusinessException("Msg_328");
				} else {
					return;
				}
			}
		} else {
			if(appTypeDiscreteSetting.getRetrictPreUseFlg().equals(UseAtr.NOTUSE)){
				return;
			} else {
				for(GeneralDate loopDay = startDate;loopDay.beforeOrEquals(endDate);loopDay.addDays(1)){
					if(appTypeDiscreteSetting.getRetrictPreMethodFlg().equals(CheckMethod.DAYCHECK)){
						GeneralDate limitDay = systemDate.addDays(appTypeDiscreteSetting.getRetrictPreDay().value);
						if(loopDay.before(limitDay)) throw new BusinessException("Msg_327");
					} else {
						if(loopDay.before(systemDate)){
							throw new BusinessException("Msg_327");
						} else if(loopDay.equals(systemDate)){
							/*limitDay = AdvanceAcceptanceRestriction.hourAndMinutes 
									
							// 受付制限日時 = 「事前の受付制限」．時分
								システム日時 = System.DateTime.Nowのhour * 60 + System.DateTime.Nowのminute;
								
								例）「事前の受付制限」．時分 = 1080(18:00)
								
								システム日時 = 1079(17:59)今日の事前申請を登録。。。ＯＫ
								システム日時 = 1080(18:00)今日の事前申請を登録。。。ＯＫ
								システム日時 = 1081(18:01)今日の事前申請を登録。。。ＮＧ 
								
							if(systemDate > limitDay) throw new BusinessException("Msg_327");*/
						}
					}
				}
			}
		}
	}
	
	public void confirmationCheck(String companyID, String employeeID, GeneralDate appDate){
		
		/*
		obj = "Imported(ApplicationApproval)[ActualResultConfirmedState]  // 「Imported(申請承認)「実績確定状態」 
		if(obj.isPresent()){
			applicationRestrictionSetting = from cache; // 申請制限設定
			if(applicationRestrictionSetting.canNotApplyIfTheResultsByDayAreConfirmed == true){
				obj1 = Imported(ApplicationApproval)[ActualResultConfirmedState].dailyPerformanceConfirmed; // 「Imported(申請承認)「実績確定状態」．日別実績が確認済
				if(obj1 = true) throw new BusinessException(Msg_448);
			}
			if(applicationRestrictionSetting.canNotApplyIfTheResultsByMonthAreConfirmed == true){
				obj2 = Imported(ApplicationApproval)[ActualResultConfirmedState].monthlyPerformanceConfirmed; // 「Imported(申請承認)「実績確定状態」．月別実績が確認済
				if(obj2 = true) throw new BusinessException(Msg_449);
			}
			if(applicationRestrictionSetting.canNotApplyIfWorkHasBeenFixed == true){
				obj3 = Imported(ApplicationApproval)[ActualResultConfirmedState].classificationOfEmploymentOfBelongingWorkplace; // 「Imported(申請承認)「実績確定状態」．所属職場の就業確定区分
				if(obj3 = true) throw new BusinessException(Msg_450);
			}
			if(applicationRestrictionSetting.canNotApplyIfActualResultIsLockedState == true){
				date = from cache;
				アルゴリズム「社員の当月の期間を算出する」を実行する(thực hiện xử lý 「社員の当月の期間を算出する」)
				obj4 = OtherCommonAlgorithm.employeePeriodCurrentMonthCalculate(companyID, employeeID, date); // obj4 <=> 締め期間(開始年月日,終了年月日)
				if(obj4.startDate <= appDate <= obj4.endDate){ 
					obj5 = Imported(ApplicationApproval)[ActualResultConfirmedState].lockOfDailyPerformance; // 「Imported(申請承認)「実績確定状態」．日別実績のロック
					obj6 = Imported(ApplicationApproval)[ActualResultConfirmedState].lockOfMonthlyPerformance; // 「Imported(申請承認)「実績確定状態」．月別実績のロック
					if((obj5==true)||(obj6==true)) throw new BusinessException(Msg_451);
				} 
			}
		}
		 */
	}
}
