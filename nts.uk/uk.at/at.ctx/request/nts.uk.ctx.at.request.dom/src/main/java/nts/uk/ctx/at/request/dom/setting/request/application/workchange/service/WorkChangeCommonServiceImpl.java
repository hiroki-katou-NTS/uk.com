package nts.uk.ctx.at.request.dom.setting.request.application.workchange.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.datawork.DataWork;
import nts.uk.ctx.at.request.dom.application.common.datawork.IDataWorkService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.StartApprovalRootService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.StartCheckErrorService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.IAppWorkChangeSetRepository;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultipleRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorkChangeCommonServiceImpl implements IWorkChangeCommonService {
	@Inject
	IAppWorkChangeSetRepository workChangeRepository;
	@Inject
	ApplicationReasonRepository appFormRepo;
	@Inject
	EmployeeRequestAdapter employeeAdapter;
	@Inject
	BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
	@Inject
	StartCheckErrorService startCheckErrorService;
	@Inject
	private StartApprovalRootService startApprovalRootService;
	@Inject 
	WorkManagementMultipleRepository workManagerRepo;	
	@Inject
	IDataWorkService dataWorkService;
	@Override
	public WorkChangeBasicData getSettingData(String companyId, String sId) {		
		// 1-1.新規画面起動前申請共通設定を取得する
		AppCommonSettingOutput appCommonSetting = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyId,
				sId, 1, ApplicationType.WORK_CHANGE_APPLICATION, null);

		// アルゴリズム「1-4.新規画面起動時の承認ルート取得パターン」を実行する
		startApprovalRootService.getApprovalRootPattern(companyId, sId, 1,
				ApplicationType.WORK_CHANGE_APPLICATION.value, null);

		// アルゴリズム「1-5.新規画面起動時のエラーチェック」を実行する
		startCheckErrorService.checkError(ApplicationType.WORK_CHANGE_APPLICATION.value);

		// アルゴリズム「勤務変更申請基本データ（新規）」を実行する
		WorkChangeBasicData wcBasicData = getWorkChangeBasicData(companyId);
		// 申請共通設定
		wcBasicData.setAppCommonSettingOutput(appCommonSetting);

		// 共通設定.複数回勤務
		Optional<WorkManagementMultiple> workManagement = workManagerRepo.findByCode(companyId);
		if (workManagement.isPresent()) {
			wcBasicData.setMultipleTime(workManagement.get().getUseATR().value == 1 ? true : false);
		}
		
		//勤務就業ダイアログ用データ取得
		GeneralDate appDate = GeneralDate.today();//基準日
		DataWork workingData = dataWorkService.getDataWork(companyId, sId, appDate, appCommonSetting);
		wcBasicData.setWorkingData(workingData);
		
		// 勤務変更申請基本データ
		return wcBasicData;
	}

	private WorkChangeBasicData getWorkChangeBasicData(String cid) {
		// アルゴリズム「勤務変更申請基本データ（新規）」を実行する
		WorkChangeBasicData wcBasicData = new WorkChangeBasicData();

		// ドメインモデル「勤務変更申請設定」より取得する
		wcBasicData.setWorkChangeCommonSetting(workChangeRepository.findWorkChangeSetByID(cid));

		// アルゴリズム「社員IDから社員を取得する」を実行する
		String employeeName = employeeAdapter.getEmployeeName(AppContexts.user().employeeId());
		wcBasicData.setEmployeeName(employeeName);
		wcBasicData.setSID(AppContexts.user().employeeId());

		// ドメインモデル「申請定型理由」を取得
		List<ApplicationReason> listReason = appFormRepo.getReasonByAppType(cid,
				ApplicationType.WORK_CHANGE_APPLICATION.value);
		wcBasicData.setListAppReason(listReason);

		// 勤務変更申請基本データ
		return wcBasicData;
	}

}
