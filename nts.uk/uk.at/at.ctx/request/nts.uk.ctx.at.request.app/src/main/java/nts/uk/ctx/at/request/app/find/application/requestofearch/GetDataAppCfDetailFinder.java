package nts.uk.ctx.at.request.app.find.application.requestofearch;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationMetaDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadline;
import nts.uk.ctx.at.request.dom.setting.request.application.DeadlineCriteria;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AllowAtr;
import nts.uk.ctx.at.request.dom.setting.request.application.common.CheckMethod;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestOfEachCommon;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.shr.com.context.AppContexts;

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
	

	public OutputMessageDeadline getDataConfigDetail(ApplicationMetaDto metaDto) {
		String message = "";
		String deadline = "";
		GeneralDate date1 = GeneralDate.today();
		GeneralDate date2 = GeneralDate.today();
		GeneralDate date3 = GeneralDate.today();
		String companyID = AppContexts.user().companyId();
		String sid = AppContexts.user().employeeId();
		
		GeneralDate endDate =  otherCommonAlgorithm.employeePeriodCurrentMonthCalculate(companyID, sid, metaDto.getAppDate()).getEndDate();
		
		String employmentCD = employeeAdaptor.getEmploymentCode(companyID, sid, metaDto.getAppDate());
		
		
		
		/*
		ドメインモデル「締め」を取得する(lấy thông tin domain「締め」)
		Object<String: tightenID, String: currentMonth> obj1 = Tighten.find(companyID, employeeCD); // obj1 <=> (締めID,当月)
		*/
		Optional<ClosureEmployment> closureEmployment = closureEmploymentRepository.findByEmploymentCD(companyID, employmentCD);
		if(!closureEmployment.isPresent()){
			throw new RuntimeException("締めがない。");
		}
		
		//closureEmployment.get().getClosureId()
		Optional<ApplicationDeadline> applicationDeadline = applicationDeadlineRepository.getDeadlineByClosureId(companyID, closureEmployment.get().getClosureId());
		
		if(applicationDeadline.get().getDeadlineCriteria() == DeadlineCriteria.CALENDAR_DAY) {
			endDate.addDays(applicationDeadline.get().getDeadline().v());
		}
		
		if(applicationDeadline.get().getDeadlineCriteria() == DeadlineCriteria.WORKING_DAY) {
			endDate.addDays(applicationDeadline.get().getDeadline().v());
		}
		
		
		Optional<AppTypeDiscreteSetting> appTypeDiscreteSetting = appTypeDiscreteSettingRepo
				.getAppTypeDiscreteSettingByAppType(companyID, metaDto.getAppType());
		// 事後申請の受付は7月27日分まで。
		// 「事後の受付制限」．未来日許可しないがtrue、その他は利用しない
		// if : RetrictPostAllowFutureFlg = true(allow)s và RetrictPreUseFlg = false(not
		// use)
		if (!appTypeDiscreteSetting.isPresent()) {
			return new OutputMessageDeadline("", "");
		}

		if (appTypeDiscreteSetting.get().getRetrictPostAllowFutureFlg() == AllowAtr.ALLOW
				&& appTypeDiscreteSetting.get().getRetrictPreUseFlg() == UseAtr.NOTUSE) {
			deadline = "事後申請の受付は" + date2 + "分まで";
			// deadline ="事前申請の受付は"+date+"分から。事後申請の受付は"+date+"分まで。当月の申請は"+date+"まで";
		}

		// 1.1(Hung)
		//GeneralDate generalDate = GeneralDate.fromString(inputMessageDeadline.getAppDate(), "yyyy/MM/dd");
		//rootAtr = 1
		RequestOfEachCommon RequestOfEachCommon = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(
				companyID, sid, 1,
				EnumAdaptor.valueOf(metaDto.getAppType(), ApplicationType.class),
				GeneralDate.today()).requestOfEachCommon;
		
		AppConfigDetailDto appConfigDetail = AppConfigDetailDto
				.fromDomain(RequestOfEachCommon.getRequestAppDetailSettings().get(0));

		// this is company
//		Optional<AppConfigDetailDto> appConfigDetailCom = detailCompanyRepo
//				.getRequestDetail(inputMessageDeadline.getCompanyID(), inputMessageDeadline.getAppType())
//				.map(c -> AppConfigDetailDto.fromDomain(c));
		if (appConfigDetail != null) { // ton tai
			if (!appConfigDetail.getMemo().isEmpty()) {
				message = appConfigDetail.getMemo();
			}
		}
		// 条件：「申請利用設定」．備考に内容なし && 「申請締切設定」．利用区分が利用しない && 「事前の受付制限」．利用区分が利用しない
		// && 「事後の受付制限」．未来日許可しないがfalse
		if (appConfigDetail.getMemo().isEmpty() == true
				// && appConfigDetailCom.get().getUserAtr() ==0 // use Atr = 0
				&& appTypeDiscreteSetting.get().getRetrictPreUseFlg() == UseAtr.NOTUSE
				&& appTypeDiscreteSetting.get().getRetrictPostAllowFutureFlg() == AllowAtr.ALLOW) {
			message = "";
			deadline = "";
		}

		if (appConfigDetail.getMemo().isEmpty() == false
				// && appConfigDetailCom.get().getUserAtr() ==0 // use Atr = 1
				&& appTypeDiscreteSetting.get().getRetrictPreUseFlg() == UseAtr.USE
				&& appTypeDiscreteSetting.get().getRetrictPostAllowFutureFlg() == AllowAtr.ALLOW) {
			// if retrictPreMethodFlg is timecheck
			if (appTypeDiscreteSetting.get().getRetrictPreMethodFlg() == CheckMethod.TIMECHECK) {
				int minuteData = Integer.parseInt(appTypeDiscreteSetting.get().getRetrictPreTimeDay().v().toString());

				deadline = "事前申請の受付は" + date1.month() + "月" + date1.day() + "日 " +formatTime(minuteData) +" 分から。"
						+ "事後申請の受付は" + date2.month() + "月" + date2.day() + "日 分まで。 当月の申請は" + endDate.month() + "月"
						+ endDate.day() + "日 まで";
			}
			// if retrictPreMethodFlg is daycheck
			if (appTypeDiscreteSetting.get().getRetrictPreMethodFlg() == CheckMethod.DAYCHECK) {
				date1 = date1.addDays(appTypeDiscreteSetting.get().getRetrictPreDay().value);

				deadline = "事前申請の受付は" + date1.month() + "月" + date1.day() + "日  分から。 事後申請の受付は" + date2.month() + "月"
						+ date2.day() + "日 分まで。 当月の申請は" + endDate.month() + "月" + endDate.day() + "日 まで";
			}

		}

		return new OutputMessageDeadline(message, deadline);
	}
	
	private String formatTime(int time) {
		  return String.format("%02d:%02d", time / 60, time % 60);
		 }
}
