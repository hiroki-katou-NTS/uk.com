package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InitMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforeAppCommonSetting;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforePreBootMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.PrelaunchAppSetting;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenInitModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.adapter.WorkLocationAdapter;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
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
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	@Inject
	private GoBackDirectlyRepository goBackRepo;

	@Inject
	private WorkLocationAdapter workLocationAdapter;

	@Inject
	private WorkTimeSettingRepository workTimeRepo;

	@Inject
	private WorkTypeRepository workTypeRepo;

	@Inject
	private ApplicationRepository_New appRepo;

	@Inject 
	private BeforePreBootMode beforePreBootMode;
	
	@Inject 
	private BeforeAppCommonSetting beforeAppCommonSetting;
	
	@Inject 
	private InitMode initMode;
	@Inject
	private EmployeeRequestAdapter employeeAdapter;

	@Override
	public GoBackDirectAppSet getGoBackDirectAppSet(String appID) {
		String companyID = AppContexts.user().companyId();
		GoBackDirectAppSet data = new GoBackDirectAppSet();
		Optional<Application_New> applicationOpt = appRepo.findByID(companyID, appID);
		if (!applicationOpt.isPresent()) {
			return data;
		}
		Application_New application = applicationOpt.get();
		data.setEmployeeName(employeeAdapter.getEmployeeName(application.getEmployeeID()));
		//14-2.詳細画面起動前モードの判断
		DetailedScreenPreBootModeOutput preBootOuput = beforePreBootMode.judgmentDetailScreenMode(companyID, application.getEmployeeID(), appID, application.getAppDate());
		data.detailedScreenPreBootModeOutput = preBootOuput;
		//14-1.詳細画面起動前申請共通設定を取得する
		PrelaunchAppSetting prelaunchAppSetting = beforeAppCommonSetting.getPrelaunchAppSetting(appID);
		data.prelaunchAppSetting = prelaunchAppSetting;
		data.prePostAtr = application.getPrePostAtr().value;			
		data.appReason = application.getAppReason().v();
		data.appDate = application.getAppDate().toString(DATE_FORMAT);
		//アルゴリズム「直行直帰基本データ」を実行する
		GoBackDirectly goBackDirect = goBackRepo.findByApplicationID(companyID, appID).get();
		data.goBackDirectly = goBackDirect;
		data.goBackDirectly.setVersion(application.getVersion());
		if(goBackDirect.getWorkLocationCD1().isPresent()) {
			data.workLocationName1 = workLocationAdapter.getByWorkLocationCD(companyID, goBackDirect.getWorkLocationCD1().get())
					.getWorkLocationName();
			
		}
		if(goBackDirect.getWorkLocationCD2().isPresent()) {
			data.workLocationName2 = workLocationAdapter.getByWorkLocationCD(companyID, goBackDirect.getWorkLocationCD2().get())
					.getWorkLocationName();
		}
		if (goBackDirect.getWorkTypeCD().isPresent()) {
			Optional<WorkType> workType = workTypeRepo.findByPK(companyID, goBackDirect.getWorkTypeCD().get().v());
			if(workType.isPresent()) {
				data.workTypeName = workType.get().getName().v();
			}
		}
		if (goBackDirect.getSiftCD().isPresent()) {
			Optional<WorkTimeSetting> workTime = workTimeRepo.findByCode(companyID, goBackDirect.getSiftCD().get().v());
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
