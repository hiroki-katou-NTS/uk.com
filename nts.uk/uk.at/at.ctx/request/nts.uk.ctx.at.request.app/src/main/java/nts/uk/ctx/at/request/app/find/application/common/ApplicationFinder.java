package nts.uk.ctx.at.request.app.find.application.common;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationMetaDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationSendDto;
import nts.uk.ctx.at.request.dom.application.common.service.application.IApplicationForRemandService;
import nts.uk.ctx.at.request.dom.application.common.service.application.IApplicationForSendService;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApplicationForRemandOutput;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApplicationForSendOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.DetailAppCommonSetService;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ApplicationFinder {


	@Inject
	private DetailAppCommonSetService detailAppCommonSetService;

	@Inject
	private IApplicationForSendService appForSendService;
	
	@Inject 
	private IApplicationForRemandService appForRemandService;
	


	



	public ApplicationForRemandOutput getAppByIdForRemand(List<String> lstAppID) {
		return appForRemandService.getApplicationForRemand(lstAppID);
	}

	public ApplicationSendDto getAppByIdForSend(List<String> appIDLst){
		ApplicationForSendOutput appOutput = appForSendService.getApplicationForSend(appIDLst);
		return ApplicationSendDto.fromDomain(appOutput);
	}

	public ApplicationMetaDto getAppByID(String appID){
		String companyID = AppContexts.user().companyId();
		return ApplicationMetaDto.fromDomain(detailAppCommonSetService.getDetailAppCommonSet(companyID, appID));
	}
	public List<ApplicationMetaDto> getListAppInfo(List<String> listAppID){
		String companyID = AppContexts.user().companyId();
		return detailAppCommonSetService.getListDetailAppCommonSet(companyID, listAppID)
				.stream().map(x -> ApplicationMetaDto.fromDomain(x)).collect(Collectors.toList());
	}
	
	public AchievementOutput getDetailRealData(String appID){
		/*String companyID = AppContexts.user().companyId();
		Application_New app = applicationRepository.findByID(companyID, appID).get();
		return collectAchievement.getAchievement(companyID, app.getEmployeeID(), app.getAppDate());*/
		return null;
	}
	
	
	public AppDispInfoStartupDto getDetailPC(String appID) {
		String companyID = AppContexts.user().companyId();
		AppDispInfoStartupOutput appDispInfoStartupOutput = detailAppCommonSetService.getCommonSetBeforeDetail(companyID, appID);
		return AppDispInfoStartupDto.fromDomain(appDispInfoStartupOutput);
	}
}
