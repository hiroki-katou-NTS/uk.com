package nts.uk.ctx.at.request.dom.application.common.service.newscreen.before;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.JobEntryHistoryImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.PesionInforImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.PeriodCurrentMonth;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadline;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadlineRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.DeadlineCriteria;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AllowAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.common.CheckMethod;

@Stateless
public class NewBeforeRegisterImpl implements NewBeforeRegister {
	
	@Inject
	private EmployeeAdapter employeeAdaptor;
	
	@Inject
	private ApplicationDeadlineRepository appDeadlineRepository;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithmService;
	
	@Inject
	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;
	
	@Inject
	private ApprovalRootAdapter approvalRootService;
	
	public void processBeforeRegister(String companyID, String employeeID, GeneralDate date, PrePostAtr postAtr, int routeAtr, int appType){
		// アルゴリズム「未入社前チェック」を実施する(Check trước khi vào cty)
		retirementCheckBeforeJoinCompany(companyID, employeeID, date);
		
		// アルゴリズム「社員の当月の期間を算出する」を実行する(Tính toán thời gian của tháng hiện tại cưa nhân viên)
		PeriodCurrentMonth periodCurrentMonth = otherCommonAlgorithmService.employeePeriodCurrentMonthCalculate(companyID, employeeID, date);
		
		// 登録する期間のチェック(Check thời gian đăng ký)
		// if(endDate.after(startDate.addDays(31))) throw new BusinessException("Msg_277");
		
		// 登録可能期間のチェック(１年以内)(check thời gian có thế đăng ký (trong vong 1 năm)
		if(periodCurrentMonth.getStartDate().addYears(1).beforeOrEquals(date)) throw new BusinessException("Msg_276");
		
		// 過去月のチェック(check tháng quá khứ)
		if(date.before(periodCurrentMonth.getStartDate())) throw new BusinessException("Msg_236");
		
		// キャッシュから承認ルートを取得する(Lấy comfirm root từ cache)
		List<ApprovalRootImport> approvalRootOutputs = approvalRootService.getApprovalRootOfSubjectRequest(companyID, employeeID, routeAtr, appType, date);
		ApprovalRootImport approvalRootOutput = approvalRootOutputs.get(0);
		if(approvalRootOutput.getErrorFlag().equals(ErrorFlagImport.NO_CONFIRM_PERSON)) throw new BusinessException("Msg_238");
		if(approvalRootOutput.getErrorFlag().equals(ErrorFlagImport.APPROVER_UP_10)) throw new BusinessException("Msg_237");
		if(approvalRootOutput.getErrorFlag().equals(ErrorFlagImport.NO_APPROVER)) throw new BusinessException("Msg_324");
		
		// アルゴリズム「申請の締め切り期限をチェック」を実施する(Check thời gian hết hạn xin)
		//deadlineApplicationCheck(companyID, appID, appStartDate, appEndDate, date, date);
		
		// アルゴリズム「申請の受付制限をチェック」を実施する(Check giới hạn chấp nhân xin)
		applicationAcceptanceRestrictionsCheck(postAtr, date, date);
		
		// アルゴリズム「確定チェック」を実施する(check xác thực)
		confirmationCheck(companyID, employeeID, date);
	}
	
	// moi nguoi chi co the o mot cty vao mot thoi diem
	// check xem nguoi xin con trong cty k
	public void retirementCheckBeforeJoinCompany(String companyID, String employeeID, GeneralDate date){
		// Imported(就業)「社員」を取得する (Lấy employee)
		PesionInforImport pesionInforImport = employeeAdaptor.getEmployeeInfor(employeeID);
		
		// lấy lịch sử làm việc mới nhất
		if(pesionInforImport.getListJobEntryHist().isEmpty()){
			throw new RuntimeException("Khong ton tai lich su cua nhan vien");
		}
		JobEntryHistoryImport jobEntryHistoryImport = pesionInforImport.getListJobEntryHist().get(0);
		
		// 入社前をチェックする(Check trước khi vào cty)
		// データが１件以上取得できた 且つ 申請対象日 < Imported(就業)「社員」．入社年月日
		if(date.before(jobEntryHistoryImport.getJoinDate())) {
			throw new BusinessException("Msg_235");
		}
		
		// 退職後をチェックする(Check sau khi làm việc)
		// データが１件以上取得できた 且つ 申請対象日 > Imported(就業)「社員」．退職年月日
		if(date.after(jobEntryHistoryImport.getRetirementDate())) {
			throw new BusinessException("Msg_391");
		}
	}
	
	public void deadlineApplicationCheck(String companyID, String appID, GeneralDate appStartDate, GeneralDate appEndDate, GeneralDate startDate, GeneralDate endDate){
		/*ログイン者のパスワードレベルが０の場合、チェックしない
		ロールが決まったら、要追加*/
		// if(passwordLevel!=0) return;
		
		// ドメインモデル「申請締切設定」．利用区分をチェックする(check利用区分)
		Optional<ApplicationDeadline> appDeadlineOp = appDeadlineRepository.getDeadlineByClosureId(companyID, Integer.parseInt(appID));
		if(!appDeadlineOp.isPresent()) {
			throw new RuntimeException();
		}
		ApplicationDeadline appDeadline = appDeadlineOp.get();
		
		// ドメインモデル「申請締切設定」．利用区分をチェックする(check利用区分)
		if(appDeadline.getUserAtr().equals(UseAtr.NOTUSE)) { 
			return; 
		};
		
		// 申請する開始日(input)から申請する終了日(input)までループする(loop từ  startdate  đến  end date)
		for(GeneralDate loopDate = startDate; loopDate.beforeOrEquals(endDate); loopDate.addDays(1)){
			GeneralDate deadline = null;
			// ドメインモデル「申請締切設定」．締切基準をチェックする(Check ngày chốt chuẩn)
			if(appDeadline.getDeadlineCriteria().equals(DeadlineCriteria.WORKING_DAY)) {
				// アルゴリズム待ち
				// Waiting for algorithm
				// input: appEndDate, obj.deadline, 
			} else {
				deadline = appEndDate.addDays(appDeadline.getDeadline().v());
			}
			// システム日付と申請締め切り日を比較する(So sánh ngày hệ thống với ngày chốt)
			if(GeneralDate.today().afterOrEquals(deadline)) {
				throw new BusinessException("Msg_327"); 
			}
		}	
	}
	
	public void applicationAcceptanceRestrictionsCheck(PrePostAtr postAtr, GeneralDate startDate, GeneralDate endDate){
		/*ログイン者のパスワードレベルが０の場合、チェックしない
		ロールが決まったら、要追加*/
		// if(passwordLevel!=0) return;
		GeneralDateTime systemDateTime = GeneralDateTime.now();
		GeneralDate systemDate = GeneralDate.today();
		
		// キャッシュから取得(lấy từ cache) (Obtained from cache )
		Optional<AppTypeDiscreteSetting> appTypeDiscreteSettingOp = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType("", ApplicationType.STAMP_APPLICATION.value);
		if(!appTypeDiscreteSettingOp.isPresent()) {
			throw new RuntimeException();
		}
		AppTypeDiscreteSetting appTypeDiscreteSetting = appTypeDiscreteSettingOp.get();
		
		// 事前事後区分(input)をチェックする(Check input là xin trước hay sau)
		if(postAtr.equals(PrePostAtr.POSTERIOR)){
			// ドメインモデル「事後の受付制限」．未来日許可しないをチェックする(Check [không cho phép ngày tương lại])
			if(!appTypeDiscreteSetting.getRetrictPostAllowFutureFlg().equals(AllowAtr.NOTALLOW)){
				return;
			} else {
				// 未来日の事後申請かチェックする(Check xin sau )
				if(startDate.after(systemDate)||endDate.after(systemDate)){
					throw new BusinessException("Msg_328");
				} else {
					return;
				}
			}
		} else {
			// ドメインモデル「事前の受付制限」．利用区分をチェックする(check [phần sử dụng])
			if(appTypeDiscreteSetting.getRetrictPreUseFlg().equals(UseAtr.NOTUSE)){
				return;
			} else {
				// 申請する開始日(input)から申請する終了日(input)までループする(Loop từ startDate -> enđate)
				for(GeneralDate loopDay = startDate;loopDay.beforeOrEquals(endDate);loopDay.addDays(1)){
					// ドメインモデル「事前の受付制限」．チェック方法をチェックする(Check phương phám check)
					if(appTypeDiscreteSetting.getRetrictPreMethodFlg().equals(CheckMethod.DAYCHECK)){
						// ループする日と受付制限日と比較する(So sánh ngày loop với ngày giới hạn chấp nhận)
						GeneralDate limitDay = systemDate.addDays(appTypeDiscreteSetting.getRetrictPreDay().value);
						if(loopDay.before(limitDay)) {
							throw new BusinessException("Msg_327");
						}
					} else {
						// ループする日とシステム日付を比較する(So sánh ngày loop và ngày hệ thống)
						if(loopDay.before(systemDate)){
							throw new BusinessException("Msg_327");
						} else if(loopDay.equals(systemDate)){
							Integer limitDay = appTypeDiscreteSetting.getRetrictPreTimeDay().v();
							Integer systemTime = systemDateTime.hours() * 60 + systemDateTime.minutes();
							// システム日時と受付制限日時と比較する(So sánh ngày hệ thống với ngày giới hạn chấp nhận)
							if(systemTime > limitDay) {
								throw new BusinessException("Msg_327");
							}
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
