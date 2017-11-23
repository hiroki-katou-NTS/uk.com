package nts.uk.ctx.at.request.app.find.application.workchange;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.request.application.workchange.service.IWorkChangeCommonService;

@Stateless
public class AppWorkChangeCommonSetFinder {
	@Inject
    private IWorkChangeCommonService workChangeCommonService;
	
	public AppWorkChangeCommonSetDto getWorkChangeCommonSetting(String sid){
		return AppWorkChangeCommonSetDto.fromDomain( workChangeCommonService.getSettingData(sid));
	}
}
