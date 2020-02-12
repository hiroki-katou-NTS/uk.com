package nts.uk.ctx.at.request.app.find.setting.request.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApproverRegisterSet;
import nts.uk.ctx.at.request.dom.setting.workplace.ApplicationUseSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachCompany;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachCompanyRepository;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachWorkplace;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachWorkplaceRepository;
import nts.uk.shr.com.context.AppContexts;
//HOATT - CMM018_2
@Stateless
public class ApplicationUseAtrFinder {

	@Inject
	private RequestOfEachWorkplaceRepository repoRequestWkp;
	@Inject
	private RequestOfEachCompanyRepository repoRequestCom;
	@Inject
	private ApplicationSettingRepository repoAppSet;
	@Inject
	private WorkplaceAdapter wkpAdapter;
	//利用している申請種類を取得
	public List<AppUseAtrDto> getAppUseAtr(String workplaceID){
		List<AppUseAtrDto> lstResult = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		if(Strings.isBlank(workplaceID)){
			WkpHistImport wkp = wkpAdapter.findWkpBySid(AppContexts.user().employeeId(), GeneralDate.today());
			if(wkp == null) return lstResult;
			workplaceID = wkp.getWorkplaceId();
		}
		//申請利用設定
		List<ApplicationUseSetting> lstAppUseSet = new ArrayList<>();
		Optional<RequestOfEachWorkplace> wkpS = repoRequestWkp.getRequestByWorkplace(companyId, workplaceID);
		if(wkpS.isPresent()){
			lstAppUseSet = wkpS.get().getListApprovalFunctionSetting().stream()
			.map(c -> c.getAppUseSetting())
			.collect(Collectors.toList());
		}else{
			Optional<RequestOfEachCompany> comS = repoRequestCom.getRequestByCompany(companyId);
			if(!comS.isPresent()) return lstResult;
			lstAppUseSet = comS.get().getListApprovalFunctionSetting().stream()
					.map(c -> c.getAppUseSetting())
					.collect(Collectors.toList());
		}
		lstResult = lstAppUseSet.stream().map(c -> new AppUseAtrDto(c.getAppType().value, c.getUserAtr().value)).collect(Collectors.toList());
		return lstResult;
	}
	
	public ApproverRegisterSetDto getAppSet(String companyId){
		Optional<ApplicationSetting> appSet = repoAppSet.getApplicationSettingByComID(companyId);
		if(!appSet.isPresent()) return new ApproverRegisterSetDto(1, 1, 1);
		ApproverRegisterSet c =  appSet.get().getApproverResSet();
		return new ApproverRegisterSetDto(c.getCompanyUnit().value, 
				c.getWorkplaceUnit().value, c.getEmployeeUnit().value);
	}
}
