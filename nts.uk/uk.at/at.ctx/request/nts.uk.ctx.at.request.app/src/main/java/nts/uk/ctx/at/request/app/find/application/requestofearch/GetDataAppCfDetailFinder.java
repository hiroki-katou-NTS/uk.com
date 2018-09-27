package nts.uk.ctx.at.request.app.find.application.requestofearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationMetaDto;
import nts.uk.ctx.at.request.app.find.setting.request.application.ApplicationDeadlineDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SEmpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.daycalendar.ObtainDeadlineDateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.ReceptionRestrictionSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadline;
import nts.uk.ctx.at.request.dom.setting.request.application.DeadlineCriteria;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AllowAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.common.CheckMethod;
import nts.uk.ctx.at.request.dom.setting.request.application.common.RetrictPreTimeDay;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class GetDataAppCfDetailFinder {

	@Inject
	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepo;

	@Inject
	private nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm; 
	//closure ID
	@Inject
	private EmployeeRequestAdapter employeeAdaptor;
	
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;
	
	@Inject
	private nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadlineRepository applicationDeadlineRepository;
	
	@Inject
	private WorkplaceAdapter workplaceAdapter;
	
	@Inject
	private ObtainDeadlineDateAdapter obtainDeadlineDateAdapter;
	
	@Inject
	private RequestSettingRepository requestSettingRepository;
	
	public OutputMessageDeadline getDataConfigDetail(ApplicationMetaDto metaDto, int overtimeAtr) {
		String message = "";
		String deadline = "";
		boolean chkShow = false;
		String companyID = AppContexts.user().companyId();
		String sid = AppContexts.user().employeeId();
		String strMonth = "月";
		String strDay = "日";
		String strMessageFirst = "事前申請の受付は";
		String strMessageAfter = "事後申請の受付は";
		String strMessageDay = "当月の申請は";
		String strKara = "分から。";
		String strBunMade = "分まで。";
		String strMade = "まで。";
		ApprovalFunctionSetting approvalFunctionSetting = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(
				companyID, 
				sid, 
				1,
				EnumAdaptor.valueOf(metaDto.getAppType(), ApplicationType.class),
				GeneralDate.today()).approvalFunctionSetting;
		/*
		ドメインモデル「締め」を取得する(lấy thông tin domain「締め」)
		*/
		SEmpHistImport empHistImport = employeeAdaptor.getEmpHist(companyID, sid, metaDto.getAppDate());
		if(empHistImport==null || empHistImport.getEmploymentCode()==null){
			throw new BusinessException("Msg_426");
		}
		String employmentCD = empHistImport.getEmploymentCode();
		Optional<ClosureEmployment> closureEmployment = closureEmploymentRepository.findByEmploymentCD(companyID, employmentCD);
		if(!closureEmployment.isPresent()){
			throw new BusinessException("Msg_1134");
		}
		Optional<ApplicationDeadline> applicationDeadline = applicationDeadlineRepository.getDeadlineByClosureId(companyID, closureEmployment.get().getClosureId());
		ApplicationDeadline appDeadline = applicationDeadline.get();
		//申請種類別設定 - 受付制限設定
		Optional<AppTypeDiscreteSetting> appTypeDiscreteSettingOp = appTypeDiscreteSettingRepo
				.getAppTypeDiscreteSettingByAppType(companyID, metaDto.getAppType());
		AppTypeDiscreteSetting appTypeDiscreteSetting = appTypeDiscreteSettingOp.get();
		//「申請利用設定」．備考に内容なし &&  「申請締切設定」．利用区分が利用しない  &&  「事前の受付制限」．利用区分が利用しない  &&  「事後の受付制限」．未来日許可しないがfalse
		if(approvalFunctionSetting.getAppUseSetting().getMemo().v().isEmpty()
				&& appDeadline.getUserAtr() == UseAtr.NOTUSE
				&& appTypeDiscreteSetting.getRetrictPreUseFlg() == UseAtr.NOTUSE
				&& appTypeDiscreteSetting.getRetrictPostAllowFutureFlg() != AllowAtr.NOTALLOW) {
			return new OutputMessageDeadline("", "", false);
		}
		//「申請利用設定」．備考に内容あり  ||  「申請締切設定」．利用区分が利用  ||  「事前の受付制限」．利用区分が利用  ||  「事後の受付制限」．未来日許可しないがtrue
		if(!approvalFunctionSetting.getAppUseSetting().getMemo().v().isEmpty()){
			message = approvalFunctionSetting.getAppUseSetting().getMemo().v();
			chkShow = true;
		}
		GeneralDate toDateSystem = GeneralDate.today();
		//ドメインモデル「事前の受付制限」．利用区分が利用する
		if(appTypeDiscreteSetting.getRetrictPreUseFlg() == UseAtr.USE) {
			//事前受付日
			String strDate = "";
			//「事前の受付制限」．チェック方法が日数
			if(appTypeDiscreteSetting.getRetrictPreMethodFlg() == CheckMethod.DAYCHECK) {
				//　⇒事前受付制限日 = システム日付 + 「事前の受付制限」．日数
				toDateSystem = toDateSystem.addDays(appTypeDiscreteSetting.getRetrictPreDay().value);
				strDate = toDateSystem.month() + strMonth + toDateSystem.day() + strDay;
				deadline = strMessageFirst + strDate + strKara;
				chkShow = true;
			}
			//「事前の受付制限」．チェック方法が時刻
			if(appTypeDiscreteSetting.getRetrictPreMethodFlg() == CheckMethod.TIMECHECK) {		
				ApplicationType appType = EnumAdaptor.valueOf(metaDto.getAppType(), ApplicationType.class);
				if(appType!=ApplicationType.OVER_TIME_APPLICATION){
					RetrictPreTimeDay retrictPreTimeDay = appTypeDiscreteSetting.getRetrictPreTimeDay();
					int minuteData = 0;
					if(retrictPreTimeDay.v()==null){
						strDate = "[error data]";
					} else {
						if(appType==ApplicationType.ABSENCE_APPLICATION||
							appType==ApplicationType.WORK_CHANGE_APPLICATION||
							appType==ApplicationType.BREAK_TIME_APPLICATION||
							appType==ApplicationType.ANNUAL_HOLIDAY_APPLICATION){
							strDate = "[error data]";
						} else {
							minuteData = retrictPreTimeDay.v();
							//　⇒事前受付制限日時 = システム日付 +「事前の受付制限」．時分（分⇒時刻に変換）
							strDate = formatTime(minuteData).toString();
						}
					}
					deadline = TextResource.localize("KAF000_41",strDate);
					chkShow = true;
				} else {
					Optional<RequestSetting> requestSetting = this.requestSettingRepository.findByCompany(companyID);
					List<ReceptionRestrictionSetting> receptionRestrictionSetting = new ArrayList<>();
					if(requestSetting.isPresent()){
						receptionRestrictionSetting = requestSetting.get().getApplicationSetting().getListReceptionRestrictionSetting().stream().filter(x -> x.getAppType().equals(ApplicationType.OVER_TIME_APPLICATION)).collect(Collectors.toList());
					}
					if(!CollectionUtil.isEmpty(receptionRestrictionSetting)){
						if(overtimeAtr == OverTimeAtr.PREOVERTIME.value){
							int minuteData = receptionRestrictionSetting.get(0).getBeforehandRestriction().getPreOtTime().v();
							strDate = formatTime(minuteData).toString();
						} else if(overtimeAtr == OverTimeAtr.REGULAROVERTIME.value){
							int minuteData = receptionRestrictionSetting.get(0).getBeforehandRestriction().getNormalOtTime().v();
							strDate = formatTime(minuteData).toString();
						} else {
							int minuteData = receptionRestrictionSetting.get(0).getBeforehandRestriction().getTimeBeforehandRestriction().v();
							strDate = formatTime(minuteData).toString();
						}
						deadline = TextResource.localize("KAF000_41",strDate);
						chkShow = true;
					}
				}
			}
		}
		//ドメインモデル「事後の受付制限」．未来日許可しないがtrue
		toDateSystem = GeneralDate.today();
		if(appTypeDiscreteSetting.getRetrictPostAllowFutureFlg() == AllowAtr.ALLOW) {
			//事後受付日
			deadline +=  strMessageAfter + toDateSystem.month() + strMonth + toDateSystem.day() + strDay + strBunMade;
			chkShow = true;
		}
		
		//全部利用する
		if(appDeadline.getUserAtr() == UseAtr.USE) {		
			//締め切り期限日
			GeneralDate endDate =  otherCommonAlgorithm.employeePeriodCurrentMonthCalculate(companyID, sid, metaDto.getAppDate()).getEndDate();
			//「申請締切設定」．締切基準が暦日
			if(appDeadline.getDeadlineCriteria() == DeadlineCriteria.CALENDAR_DAY) {
				//　⇒締め切り期限日 = 申請締め切り日.AddDays(ドメインモデル「申請締切設定」．締切日数)
				endDate = endDate.addDays(appDeadline.getDeadline().v());
			}
			//「申請締切設定」．締切基準が稼働日
			if(appDeadline.getDeadlineCriteria() == DeadlineCriteria.WORKING_DAY) {
				//　⇒締め切り期限日 = 申請締め切り日.AddDays(ドメインモデル「申請締切設定」．締切日数（←稼働日）)
				// endDate = endDate.addDays(appDeadline.getDeadline().v());//TODO cần xác nhận lại
				// アルゴリズム「社員所属職場履歴を取得」を実行する
				WkpHistImport wkpHistImport = workplaceAdapter.findWkpBySid(sid, GeneralDate.today());
				// アルゴリズム「締切日を取得する」を実行する
				endDate = obtainDeadlineDateAdapter.obtainDeadlineDate(
						endDate, 
						appDeadline.getDeadline().v(), 
						wkpHistImport.getWorkplaceId(), 
						companyID);
			}
			deadline += strMessageDay + endDate.month() + strMonth + endDate.day() + strDay + strMade;
			chkShow = true;
		}
		//注意：ドメインモデル「事前の受付制限」．利用区分が利用しない && ドメインモデル「事後の受付制限」．未来日許可しないがfalse && ドメインモデル「申請締切設定」．利用区分が利用しない
		//          ⇒締め切りエリア全体に非表示
		if(appTypeDiscreteSetting.getRetrictPreUseFlg() == UseAtr.NOTUSE
				&& appTypeDiscreteSetting.getRetrictPostAllowFutureFlg() != AllowAtr.NOTALLOW
				&& appDeadline.getUserAtr() == UseAtr.NOTUSE) {
			deadline = "";
		}
		return new OutputMessageDeadline(message, deadline, chkShow);
		
	}
	
	private String formatTime(int time) {
		  return String.format("%02d:%02d", time / 60, time % 60);
		 }
	
	/**
	 * find Application Deadline By Closure Id
	 * @param closureId
	 * @return
	 * @author yennth
	 */
	public List<ApplicationDeadlineDto> findByClosureId(List<Integer> closureId){
		String companyId = AppContexts.user().companyId();
		List<ApplicationDeadlineDto> result = new ArrayList<>();
		for(Integer obj : closureId){
			ApplicationDeadlineDto appDead = this.applicationDeadlineRepository.getDeadlineByClosureId(companyId, obj)
					.map(c -> {
						return new ApplicationDeadlineDto(companyId, obj, 
															c.getUserAtr().value, c.getDeadline().v(), 
															c.getDeadlineCriteria().value);
					}).orElse(null);
			result.add(appDead);
		}
		return result;
	}
}
