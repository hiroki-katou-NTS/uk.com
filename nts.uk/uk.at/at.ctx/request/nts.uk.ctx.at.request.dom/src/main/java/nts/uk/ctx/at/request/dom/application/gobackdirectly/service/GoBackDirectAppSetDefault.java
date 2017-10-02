package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.adapter.WorkLocationAdapter;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author ducpm
 *
 */
@Stateless
public class GoBackDirectAppSetDefault implements GoBackDirectAppSetService {
	@Inject
	private GoBackDirectlyRepository goBackRepo;

	@Inject
	private ApplicationSettingRepository appSetRepo;

	@Inject
	private WorkLocationAdapter workLocationAdapter;

	@Inject
	private WorkTimeRepository workTimeRepo;

	@Inject
	private WorkTypeRepository workTypeRepo;

	@Inject
	private ApplicationRepository appRepo;

	@Inject
	private BeforePrelaunchAppCommonSet preLaunch;

	@Override
	public GoBackDirectAppSet getGoBackDirectAppSet(String appID) {
		String companyID = AppContexts.user().companyId();
		int prePostAtr = 0;
		String workTypeName = "";
		String workTimeName = "";
		String appReasonId = "";
		String appReason = "";
		String appDate = "";
		String workLocationName1 = "";
		String workLocationName2 = "";
		AppCommonSettingOutput commonSettting = new AppCommonSettingOutput();
		GoBackDirectAppSet data = new GoBackDirectAppSet();
		GoBackDirectly goBackDirect = goBackRepo.findByApplicationID(companyID, appID).get();
		Application app = appRepo.getAppById(companyID, appID).get();
		if (app != null) {
			prePostAtr = app.getPrePostAtr().value;
			if (!app.getApplicationReason().v().equals(":")) {
				appReasonId = app.getApplicationReason().v().split(":")[0];
				appReason = app.getApplicationReason().v().split(":")[1];
			}
			appDate = app.getApplicationDate().toString("yyyy/MM/dd");
			/**
			 * Get 1.1
			 */
			commonSettting = preLaunch.prelaunchAppCommonSetService(companyID, app.getApplicantSID(), 1,
					ApplicationType.GO_RETURN_DIRECTLY_APPLICATION, app.getApplicationDate());

		}
		if (goBackDirect != null) {
			workLocationName1 = workLocationAdapter.getByWorkLocationCD(companyID, goBackDirect.getWorkLocationCD1())
					.getWorkLocationName();
			workLocationName2 = workLocationAdapter.getByWorkLocationCD(companyID, goBackDirect.getWorkLocationCD2())
					.getWorkLocationName();

			if (!StringUtils.isEmpty(goBackDirect.getWorkTypeCD().v())) {
				workTypeName = workTypeRepo.findByPK(companyID, goBackDirect.getWorkTypeCD().v()).get().getName().v();
			}

			if (!StringUtils.isEmpty(goBackDirect.getSiftCD().v())) {
				workTimeName = workTimeRepo.findByCode(companyID, goBackDirect.getSiftCD().v()).get()
						.getWorkTimeDisplayName().getWorkTimeName().v();
			}
		}

		/**
		 * 
		 */
		data.goBackDirectly = goBackDirect;
		data.workLocationName1 = workLocationName1;
		data.workLocationName2 = workLocationName2;
		data.workTypeName = workTypeName;
		data.workTimeName = workTimeName;
		data.appReasonId = appReasonId;
		data.appReason = appReason;
		data.prePostAtr = prePostAtr;
		data.appDate = appDate;
		data.appCommonSetOut = commonSettting;
		return data;
	}
}
