package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InitMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforeAppCommonSetting;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforePreBootMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.PrelaunchAppSetting;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenInitModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.other.DataAppPhaseFrame;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.OutputAllDataApp;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.adapter.WorkLocationAdapter;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
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
	
	@Inject 
	private BeforePreBootMode beforePreBootMode;
	
	@Inject 
	private BeforeAppCommonSetting beforeAppCommonSetting;
	
	@Inject
	private DataAppPhaseFrame dataAppPhaseFrame;
	
	@Inject 
	private InitMode initMode;
	

	@Override
	public GoBackDirectAppSet getGoBackDirectAppSet(String appID) {
		String companyID = AppContexts.user().companyId();
		GoBackDirectAppSet data = new GoBackDirectAppSet();
		Optional<Application> application = appRepo.getAppById(companyID, appID);

		//14-2.詳細画面起動前モードの判断
		DetailedScreenPreBootModeOutput preBootOuput = beforePreBootMode.judgmentDetailScreenMode(application.get(), application.get().getApplicationDate());
		data.detailedScreenPreBootModeOutput = preBootOuput;
		//14-1.詳細画面起動前申請共通設定を取得する
		PrelaunchAppSetting prelaunchAppSetting = beforeAppCommonSetting.getPrelaunchAppSetting(appID);
		data.prelaunchAppSetting = prelaunchAppSetting;
		
		if (application.isPresent()) {
			data.prePostAtr = application.get().getPrePostAtr().value;
			if (application.get().getApplicationReason().v().equals(":")) {
				data.appReasonId = application.get().getApplicationReason().v().split(":")[0];
				data.appReason = application.get().getApplicationReason().v().split(":")[1];
			}
			data.appDate = application.get().getApplicationDate().toString("yyyy/MM/dd");
		}
		//アルゴリズム「直行直帰基本データ」を実行する
		GoBackDirectly goBackDirect = goBackRepo.findByApplicationID(companyID, appID).get();
		data.goBackDirectly = goBackDirect;
		if(goBackDirect.getWorkLocationCD1() != null) {
			data.workLocationName1 = workLocationAdapter.getByWorkLocationCD(companyID, goBackDirect.getWorkLocationCD1())
					.getWorkLocationName();
			
		}
		if(goBackDirect.getWorkLocationCD2() != null) {
			data.workLocationName2 = workLocationAdapter.getByWorkLocationCD(companyID, goBackDirect.getWorkLocationCD2())
					.getWorkLocationName();
		}
		if (!StringUtils.isEmpty(goBackDirect.getWorkTypeCD().v())) {
			Optional<WorkType> workType = workTypeRepo.findByPK(companyID, goBackDirect.getWorkTypeCD().v());
			if(workType.isPresent()) {
				data.workTypeName = workType.get().getName().v();
			}
		}
		if (!StringUtils.isEmpty(goBackDirect.getSiftCD().v())) {
			Optional<WorkTime> workTime = workTimeRepo.findByCode(companyID, goBackDirect.getSiftCD().v());
			if(workTime.isPresent()) {
				data.workTimeName = workTime.get().getWorkTimeDisplayName().getWorkTimeName().v();
			}
		}
		//アルゴリズム「直行直帰画面初期モード」を実行する
		//Get 14-3
		DetailScreenInitModeOutput outMode = initMode.getDetailScreenInitMode(preBootOuput.getUser(), preBootOuput.getReflectPlanState().value);
		data.detailScreenInitModeOutput = outMode;
		return data;
	}
}
