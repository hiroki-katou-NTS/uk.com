package nts.uk.ctx.at.request.dom.application.common.service.newscreen.before;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SEmpHistImport;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.BaseDateFlg;

@Stateless
public class BeforePrelaunchAppCommonSetImpl implements BeforePrelaunchAppCommonSet {
	
	//private static final String BASE_DATE_CACHE_KEY = "baseDate";
	
	@Inject
	private ApplicationSettingRepository appSettingRepository;
	
	@Inject
	private EmployeeRequestAdapter employeeAdaptor;
	
//	@Inject
//	private RequestOfEachWorkplaceRepository requestOfEachWorkplaceRepository;
//	
//	@Inject
//	private RequestOfEachCompanyRepository requestOfEachCompanyRepository;
	
//	@Inject
//	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;
	@Inject
	private AppEmploymentSettingRepository appEmploymentSetting;
	public AppCommonSettingOutput prelaunchAppCommonSetService(String companyID, String employeeID, int rootAtr, ApplicationType targetApp, GeneralDate appDate){
		AppCommonSettingOutput appCommonSettingOutput = new AppCommonSettingOutput();
		GeneralDate baseDate = null;
		// ドメインモデル「申請承認設定」を取得する ( Acquire the domain model "application approval setting" )
		Optional<ApplicationSetting> applicationSettingOp = appSettingRepository.getApplicationSettingByComID(companyID);
		if(!applicationSettingOp.isPresent()){
			throw new RuntimeException("Not found ApplicationSetting in table KRQST_APPLICATION_SETTING, companyID =" + companyID);
		}
		ApplicationSetting applicationSetting = applicationSettingOp.get();
		
		appCommonSettingOutput.applicationSetting = applicationSetting;
//		Optional<AppTypeDiscreteSetting> appTypeDiscreteSettingOp = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType(companyID, targetApp.value);
//		if(appTypeDiscreteSettingOp.isPresent()) {
//			AppTypeDiscreteSetting appTypeDiscreteSetting = appTypeDiscreteSettingOp.get();
//			appCommonSettingOutput.appTypeDiscreteSettings.add(appTypeDiscreteSetting);
//		}
		// ドメインモデル「申請設定」．承認ルートの基準日をチェックする ( Domain model "application setting". Check base date of approval route )
		if(applicationSetting.getBaseDateFlg().equals(BaseDateFlg.APP_DATE)){
			if(appDate==null){
			// 「申請設定」．承認ルートの基準日が申請対象日時点の場合 ( "Application setting". When the reference date of the approval route is the date of the application target date )
			// 申請対象日のパラメータがあるかチェックする ( Check if there is a parameter on the application target date )
				baseDate = GeneralDate.today();
			} else {
				baseDate = appDate;
			}
		} else {
			// 「申請設定」．承認ルートの基準日がシステム日付時点の場合 ( "Application setting". When the base date of the approval route is at the time of the system date )
			baseDate = GeneralDate.today();
		}
		appCommonSettingOutput.generalDate = baseDate;
		
		// 申請本人の所属職場を含める上位職場を取得する ( Acquire the upper workplace to include the workplace of the applicant himself / herself )
//		List<String> workPlaceIDs = employeeAdaptor.findWpkIdsBySid(companyID, employeeID, baseDate);
//		List<ApprovalFunctionSetting> loopResult = new ArrayList<>();
//		for(String workPlaceID : workPlaceIDs) {
//			// ドメインモデル「職場別申請承認設定」を取得する ( Acquire domain model "Application approval setting by workplace" )
//			Optional<ApprovalFunctionSetting> settingOfEarchWorkplaceOp = requestOfEachWorkplaceRepository.getFunctionSetting(companyID, workPlaceID, targetApp.value);
//			if(settingOfEarchWorkplaceOp.isPresent()) {
//				loopResult.add(settingOfEarchWorkplaceOp.get());
//				break;
//			}
//		}
//		// ドメインモデル「職場別申請承認設定」を取得できたかチェックする ( Check whether domain model "application approval setting by workplace" could be acquired )
//		if(loopResult.size() == 0) {
//			//ドメインモデル「会社別申請承認設定」を取得する ( Acquire the domain model "application approval setting by company" )
//			Optional<ApprovalFunctionSetting> rqOptional = requestOfEachCompanyRepository.getFunctionSetting(companyID, targetApp.value);
//			if(rqOptional.isPresent()){
//				appCommonSettingOutput.approvalFunctionSetting = rqOptional.get();
//			}
//		} else {
//				// ドメインモデル「会社別申請承認設定」を取得する ( Acquire the domain model "application approval setting by company" )
//				appCommonSettingOutput.approvalFunctionSetting = loopResult.get(0);
//		}
		
		// アルゴリズム「社員所属雇用履歴を取得」を実行する ( Execute the algorithm "Acquire employee affiliation employment history" )
		SEmpHistImport empHistImport = employeeAdaptor.getEmpHist(companyID, employeeID, baseDate);
		if(empHistImport==null || empHistImport.getEmploymentCode()==null){
			throw new BusinessException("Msg_426");
		}
		// ドメインモデル「雇用別申請承認設定」を取得する ( Acquire the domain model "application approval setting by employment" )
		// ApplicationCommonSetting obj1 = ApplicationApprovalSettingByEmployment.find(companyID, employeeCD);
		// return obj1
		Optional<AppEmploymentSetting> lstEmploymentWt = appEmploymentSetting.getEmploymentSetting(companyID, empHistImport.getEmploymentCode(), targetApp.value);
		appCommonSettingOutput.appEmploymentWorkType = lstEmploymentWt;
		return appCommonSettingOutput;
	}
	
}
