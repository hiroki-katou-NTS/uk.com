package nts.uk.ctx.at.request.pubimp.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.closure.PresentClosingPeriodImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.closure.RqClosureAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.daycalendar.ObtainDeadlineDateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispNameRepository;
import nts.uk.ctx.at.request.dom.setting.company.displayname.HdAppDispNameRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadline;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadlineRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.DeadlineCriteria;
import nts.uk.ctx.at.request.pub.screen.ApplicationDeadlineExport;
import nts.uk.ctx.at.request.pub.screen.ApplicationExport;
import nts.uk.ctx.at.request.pub.screen.ApplicationPub;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class ApplicationPubImpl implements ApplicationPub {
	@Inject
	private ApplicationRepository_New applicationRepository_New;
	@Inject
	private AppDispNameRepository appDispNameRepository;
	@Inject
	private HdAppDispNameRepository hdAppDispNameRepository;
	@Inject
	private ApplicationDeadlineRepository appDeadlineRepository;
	@Inject
	private RqClosureAdapter rqClosureAdapter;
	@Inject
	private WorkplaceAdapter workplaceAdapter;
	@Inject
	private ObtainDeadlineDateAdapter obtainDeadlineDateAdapter;
	
	@Override
	public List<ApplicationExport> getApplicationBySID(List<String> employeeID, GeneralDate startDate,
			GeneralDate endDate) {
		List<ApplicationExport> applicationExports = new ArrayList<>();
		List<Application_New> application = this.applicationRepository_New.getApplicationBySIDs(employeeID, startDate, endDate);
		if(CollectionUtil.isEmpty(application)){
			return applicationExports;
		}
		List<Application_New> applicationExcessHoliday = application.stream().filter(x -> x.getAppType().value != ApplicationType.ABSENCE_APPLICATION.value).collect(Collectors.toList());
		for(Application_New app : applicationExcessHoliday){
			ApplicationExport applicationExport = new ApplicationExport();
			applicationExport.setAppDate(app.getAppDate());
			applicationExport.setAppType(app.getAppType().value);
			applicationExport.setEmployeeID(app.getEmployeeID());
			applicationExport.setAppTypeName(appDispNameRepository.getDisplay(app.getAppType().value).isPresent() ? appDispNameRepository.getDisplay(app.getAppType().value).get().getDispName().toString() : "" );
			applicationExports.add(applicationExport);
		}
		List<Application_New> applicationHoliday = application.stream().filter(x -> x.getAppType().value == ApplicationType.ABSENCE_APPLICATION.value).collect(Collectors.toList());
		for(Application_New app : applicationHoliday){
			ApplicationExport applicationExport = new ApplicationExport();
			applicationExport.setAppDate(app.getAppDate());
			applicationExport.setAppType(app.getAppType().value);
			applicationExport.setEmployeeID(app.getEmployeeID());
			applicationExport.setAppTypeName(hdAppDispNameRepository.getHdApp(app.getAppType().value).isPresent() ? hdAppDispNameRepository.getHdApp(app.getAppType().value).get().getDispName().toString() : "" );
			applicationExports.add(applicationExport);
		}
		return applicationExports;
	}
	@Override
	public ApplicationDeadlineExport getApplicationDeadline(String companyID, Integer closureID) {
		String employeeId = AppContexts.user().employeeId();
		ApplicationDeadlineExport result = new ApplicationDeadlineExport();
		// ドメインモデル「申請締切設定」．利用区分をチェックする(check利用区分)
		Optional<ApplicationDeadline> appDeadlineOp = appDeadlineRepository.getDeadlineByClosureId(companyID,
				closureID);
		if (!appDeadlineOp.isPresent()) {
			throw new RuntimeException(
					"Not found ApplicationDeadline in table KRQST_APP_DEADLINE, closureID =" + closureID);
		}
		ApplicationDeadline appDeadline = appDeadlineOp.get();

		GeneralDate systemDate = GeneralDate.today();
		// ドメインモデル「申請締切設定」．利用区分をチェックする(check利用区分)
		if (appDeadline.getUserAtr().equals(UseAtr.NOTUSE)) {
			result.setUseApplicationDeadline(false);
			return result;
		}
		Optional<PresentClosingPeriodImport> presentClosingPeriodImport = this.rqClosureAdapter.getClosureById(companyID, closureID);
		if(presentClosingPeriodImport.isPresent()){
				GeneralDate deadline = null;
				// ドメインモデル「申請締切設定」．締切基準をチェックする
				if(appDeadline.getDeadlineCriteria().equals(DeadlineCriteria.WORKING_DAY)) {
					// アルゴリズム「社員所属職場履歴を取得」を実行する
					WkpHistImport wkpHistImport = workplaceAdapter.findWkpBySid(employeeId, systemDate);
					// アルゴリズム「締切日を取得する」を実行する
					deadline = obtainDeadlineDateAdapter.obtainDeadlineDate(
							presentClosingPeriodImport.get().getClosureEndDate(), 
							appDeadline.getDeadline().v(), 
							wkpHistImport.getWorkplaceId(), 
							companyID);
				} else {
					// 「申請締切設定」．締切基準が暦日
					deadline = presentClosingPeriodImport.get().getClosureEndDate().addDays(appDeadline.getDeadline().v());
				}
				result.setDateDeadline(deadline);
		}
		result.setUseApplicationDeadline(true);
		return result;
	}

}
