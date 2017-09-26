package nts.uk.ctx.at.request.dom.application.common.service.newscreen.before;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.eclipse.persistence.config.TunerType;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.BaseDateFlg;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestOfEachCompany;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestOfEachCompanyRepository;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestOfEachWorkplace;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestOfEachWorkplaceRepository;

@Stateless
public class BeforePrelaunchAppCommonSetImpl implements BeforePrelaunchAppCommonSet {
	
	@Inject
	private ApplicationSettingRepository appSettingRepository;
	
	@Inject
	private EmployeeAdapter employeeAdaptor;
	
	@Inject
	private RequestOfEachWorkplaceRepository requestOfEachWorkplaceRepository;
	
	@Inject
	private RequestOfEachCompanyRepository requestOfEachCompanyRepository;
	
	public AppCommonSettingOutput prelaunchAppCommonSetService(String companyID, String employeeID, int rootAtr, ApplicationType targetApp, GeneralDate appDate){
		AppCommonSettingOutput appCommonSettingOutput = new AppCommonSettingOutput();
		GeneralDate baseDate = null;
		Optional<ApplicationSetting> applicationSettingOp = appSettingRepository.getApplicationSettingByComID(companyID);
		if(!applicationSettingOp.isPresent()) throw new RuntimeException();
		ApplicationSetting applicationSetting = applicationSettingOp.get();
		appCommonSettingOutput.applicationSetting = applicationSetting;
		if(applicationSetting.getBaseDateFlg().equals(BaseDateFlg.APP_DATE)){
			if(appDate!=null){
				baseDate = GeneralDate.today();
			} else {
				baseDate = appDate;
			}
		} else {
			baseDate = GeneralDate.today();
		}
		appCommonSettingOutput.generalDate = baseDate;
		
		// 申請本人の所属職場を含める上位職場を取得する ( Acquire the upper workplace to include the workplace of the applicant himself / herself )
		List<String> workPlaceIDs = employeeAdaptor.findWpkIdsBySid(companyID, employeeID, baseDate);
		List<RequestOfEachWorkplace> loopResult = new ArrayList<>();
		for(String workPlaceID : workPlaceIDs) {
			Optional<RequestOfEachWorkplace> requestOfEarchWorkplaceOp = requestOfEachWorkplaceRepository.getRequest(companyID, workPlaceID);
			if(requestOfEarchWorkplaceOp.isPresent()) {
				loopResult.add(requestOfEarchWorkplaceOp.get());
				break;
			}
		}
		if(loopResult.size() == 0) {
			Optional<RequestOfEachCompany> rqOptional = requestOfEachCompanyRepository.getRequestByCompany(companyID);
			if(rqOptional.isPresent()) {
				appCommonSettingOutput.requestOfEachCommon = rqOptional.get();
			} 
		} else {
				appCommonSettingOutput.requestOfEachCommon = loopResult.get(0);
		}
		// アルゴリズム「社員所属雇用履歴を取得」を実行する ( Execute the algorithm "Acquire employee affiliation employment history" )
		/*String employeeCD = employeeAdaptor.getEmploymentCode(companyID, employeeID, baseDate);
		if(employeeCD!=null) {
			throw new BusinessException("Msg_426");
		}*/
		// ドメインモデル「雇用別申請承認設定」を取得する ( Acquire the domain model "application approval setting by employment" )
		// ApplicationCommonSetting obj1 = ApplicationApprovalSettingByEmployment.find(companyID, employeeCD);
		// return obj1
		
		return appCommonSettingOutput;
	}
	
}
