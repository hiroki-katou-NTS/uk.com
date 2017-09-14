package nts.uk.ctx.at.request.dom.application.common.service.newscreen.before;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.scoped.session.SessionContextProvider;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.BaseDateFlg;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestOfEarchCompany;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestOfEarchCompanyRepository;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestOfEarchWorkplace;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestOfEarchWorkplaceRepository;

@Stateless
public class BeforePrelaunchAppCommonSetImpl implements BeforePrelaunchAppCommonSet {
	
	@Inject
	private ApplicationSettingRepository applicationSettingRepository;
	
	@Inject
	private EmployeeAdapter employeeAdaptor;
	
	@Inject
	private RequestOfEarchWorkplaceRepository requestOfEarchWorkplaceRepository;
	
	@Inject
	private RequestOfEarchCompanyRepository requestOfEarchCompanyRepository;
	
	public void prelaunchAppCommonSetService(String companyID, String employeeID, int rootAtr, int targetApp, GeneralDate appDate){
		GeneralDate baseDate = null;
		Optional<ApplicationSetting> applicationSettingOp = applicationSettingRepository.getApplicationSettingByComID(companyID);
		ApplicationSetting applicationSetting = applicationSettingOp.get();
		if(applicationSetting.getBaseDateFlg().equals(BaseDateFlg.APP_DATE)){
			if(appDate!=null){
				baseDate = GeneralDate.today();
			} else {
				baseDate = appDate;
			}
		} else {
			baseDate = GeneralDate.today();
		}
		SessionContextProvider.get().put("baseDate", baseDate);
		
		// 申請本人の所属職場を含める上位職場を取得する ( Acquire the upper workplace to include the workplace of the applicant himself / herself )
		List<String> workPlaceIDs = employeeAdaptor.findWpkIdsBySid(companyID, employeeID, baseDate);
		List<RequestOfEarchWorkplace> loopResult = new ArrayList<>();
		for(String workPlaceID : workPlaceIDs) {
			Optional<RequestOfEarchWorkplace> requestOfEarchWorkplaceOp = requestOfEarchWorkplaceRepository.getRequest(companyID, workPlaceID);
			if(requestOfEarchWorkplaceOp.isPresent()) {
				loopResult.add(requestOfEarchWorkplaceOp.get());
				break;
			}
		}
		if(loopResult.size() == 0) {
			Optional<RequestOfEarchCompany> rqOptional = requestOfEarchCompanyRepository.getRequestByCompany(companyID);
			if(rqOptional.isPresent()) SessionContextProvider.get().put("appSet", rqOptional.get());
		} else {
			SessionContextProvider.get().put("appSet", loopResult.get(0));
		}
		
		// アルゴリズム「社員所属雇用履歴を取得」を実行する ( Execute the algorithm "Acquire employee affiliation employment history" )
		String employeeCD = employeeAdaptor.getEmploymentCode(companyID, employeeID, baseDate);
		if(employeeCD!=null) {
			throw new BusinessException("Msg_426");
		}
		// ドメインモデル「雇用別申請承認設定」を取得する ( Acquire the domain model "application approval setting by employment" )
		// ApplicationCommonSetting obj1 = ApplicationApprovalSettingByEmployment.find(companyID, employeeCD);
		// return obj1
		
	}
	
}
