package nts.uk.ctx.at.request.app.find.application.workchange;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.request.application.workchange.service.IWorkChangeCommonService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AppWorkChangeCommonSetFinder {
	@Inject
    private IWorkChangeCommonService workChangeCommonService;
	
	public AppWorkChangeCommonSetDto getWorkChangeCommonSetting(){
		String sId = AppContexts.user().employeeId();
		String companyId = AppContexts.user().companyId();
		return AppWorkChangeCommonSetDto.fromDomain( workChangeCommonService.getSettingData(companyId, sId));
	}
}
