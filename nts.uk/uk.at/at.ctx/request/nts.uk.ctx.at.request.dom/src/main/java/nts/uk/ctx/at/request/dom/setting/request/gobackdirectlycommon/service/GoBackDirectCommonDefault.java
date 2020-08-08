package nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.datawork.DataWork;
import nts.uk.ctx.at.request.dom.application.common.datawork.IDataWorkService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSettingRepository;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultipleRepository;

@Stateless
public class GoBackDirectCommonDefault implements GoBackDirectCommonService {
	
	@Inject
	GoBackDirectlyCommonSettingRepository goBackRepo;

	@Inject
	ApplicationReasonRepository appFormRepo;
	
	@Inject 
	WorkManagementMultipleRepository workManagerRepo;
	
	@Inject 
	EmployeeRequestAdapter employeeAdapter;
	
	@Inject 
	BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
	
	@Inject
	IDataWorkService dataWorkService;
	
	//定型理由「#KAF009_200(選択してください)」が選択されている場合は未選択とする
	private static final String DEFAULT_REASON_RESOURCE = "KAF009_200";
	
	@Override	
	public GoBackDirectBasicData getSettingData(String companyID, String SID, GeneralDate appDate) {
		
		//1-1.新規画面起動前申請共通設定を取得する
		AppCommonSettingOutput appCommonSetting = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(
				companyID, 
				SID, 
				1, 
				ApplicationType.GO_RETURN_DIRECTLY_APPLICATION, 
				appDate);
		//アルゴリズム「直行直帰基本データ」を実行する 
		GoBackDirectBasicData dataSetting = new GoBackDirectBasicData();
		//dataSetting.sID(AppContexts.user().employeeId());
		
		// ドメインモデル「直行直帰申請共通設定」より取得する
		dataSetting.setGoBackDirectSet(goBackRepo.findByCompanyID(companyID));
		// アルゴリズム「社員IDから社員を取得する」を実行する
		String employeeName = employeeAdapter.getEmployeeName(SID);
		dataSetting.setEmployeeName(employeeName);
		dataSetting.setSID(SID);
		// ドメインモデル「申請定型理由」を取得
		List<ApplicationReason> listReason = appFormRepo.getReasonByAppType(companyID, ApplicationType.GO_RETURN_DIRECTLY_APPLICATION.value, DEFAULT_REASON_RESOURCE);
		dataSetting.setListAppReason(listReason);
		dataSetting.setAppCommonSettingOutput(appCommonSetting);
		/*// アルゴリズム「1-4.新規画面起動時の承認ルート取得パターン」を実行する
		ApprovalRootPattern approvalRootPattern = collectApprovalRootPatternService.getApprovalRootPatternService(
				companyID, 
				SID, 
				EmploymentRootAtr.APPLICATION, 
				ApplicationType.WORK_CHANGE_APPLICATION, 
				appCommonSetting.generalDate, 
				"", 
				true);

		// アルゴリズム「1-5.新規画面起動時のエラーチェック」を実行する
		startupErrorCheckService.startupErrorCheck(
				appCommonSetting.generalDate, 
				ApplicationType.WORK_CHANGE_APPLICATION.value, 
				approvalRootPattern.getApprovalRootContentImport());*/
		//共通設定.複数回勤務
		Optional<WorkManagementMultiple> workManagement = workManagerRepo.findByCode(companyID);
		if(workManagement.isPresent()) {
			dataSetting.setDutiesMulti(workManagement.get().getUseATR().value == 1 ? true : false);
		}
		
		DataWork workingData = dataWorkService.getDataWork(companyID, SID, appDate, appCommonSetting,ApplicationType.GO_RETURN_DIRECTLY_APPLICATION.value);
		dataSetting.setWorkingData(workingData);
		
		return dataSetting;
	}

}
