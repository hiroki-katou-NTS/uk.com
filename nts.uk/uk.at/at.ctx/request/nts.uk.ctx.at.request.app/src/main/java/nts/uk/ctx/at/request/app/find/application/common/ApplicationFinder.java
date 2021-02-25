package nts.uk.ctx.at.request.app.find.application.common;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationMetaDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationPeriodDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationSendDto;
import nts.uk.ctx.at.request.app.find.application.overtime.AppOvertimeFinder_Old;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.application.IApplicationForRemandService;
import nts.uk.ctx.at.request.dom.application.common.service.application.IApplicationForSendService;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApplicationForRemandOutput;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApplicationForSendOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.DetailScreenBefore;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforePreBootMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.DetailAppCommonSetService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.other.CollectAchievement;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ApplicationFinder {

	@Inject
	private ApplicationRepository applicationRepository;

	@Inject
	private DetailAppCommonSetService detailAppCommonSetService;

	@Inject
	private IApplicationForSendService appForSendService;
	
	@Inject 
	private IApplicationForRemandService appForRemandService;
	
	@Inject
	private CollectAchievement collectAchievement;
	
	@Inject
	private DetailScreenBefore detailScreenBefore;
	
	@Inject
	private BeforePreBootMode beforePreBootMode;
	
	@Inject
	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
	
	@Inject
	private AppOvertimeFinder_Old appOvertimeFinder;

	public List<ApplicationMetaDto> getAppbyDate(ApplicationPeriodDto dto) {
		String companyID = AppContexts.user().companyId();
//		return this.applicationRepository.getApplicationIdByDate(companyID, dto.getStartDate(), dto.getEndDate())
//				.stream().map(c -> {
//					return new ApplicationMetaDto(c.getAppID(), c.getAppType().value, c.getAppDate());
//				}).collect(Collectors.toList());
		return null;
	}

	public ApplicationForRemandOutput getAppByIdForRemand(List<String> lstAppID) {
		return appForRemandService.getApplicationForRemand(lstAppID);
	}

	public ApplicationSendDto getAppByIdForSend(String appID){
		ApplicationForSendOutput appOutput = appForSendService.getApplicationForSend(appID);
		if (!Objects.isNull(appOutput)){
			/*
			 * return ApplicationSendDto.fromDomain(ApplicationDto_New.fromDomain(appOutput.
			 * getApplication()), appOutput.getMailTemplate(), appOutput.getApprovalRoot(),
			 * appOutput.getApplicantMail(), appOutput.getEmpName());
			 */
			return null;
		}
		return null;
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
	
	public DetailMobDto getDetailMob(String appID){
		DetailMobDto getDetailMob = new DetailMobDto();
		// error EA refactor 4
		/*String companyID = AppContexts.user().companyId();
		String loginEmpID = AppContexts.user().employeeId();
		Application_New application = null;
		// 14-1.詳細画面起動前申請共通設定を取得する
		// 共通アルゴリズム「詳細画面申請データを取得する」を実行する
		DetailScreenAppData detailScreenAppData = detailScreenBefore.getDetailScreenAppData(appID);
		application = detailScreenAppData.getApplication();
		getDetailMob.appStatus = application.getReflectionInformation().getStateReflectionReal().value;
		getDetailMob.version = application.getVersion().intValue();
		getDetailMob.reversionReason = application.getReversionReason().v();
		getDetailMob.listApprovalPhaseStateDto = detailScreenAppData.getDetailScreenApprovalData().getApprovalLst()
				.stream().map(x -> ApprovalPhaseStateForAppDto.fromApprovalPhaseStateImport(x)).collect(Collectors.toList());
		getDetailMob.authorComment = detailScreenAppData.getDetailScreenApprovalData().getAuthorComment();
		//1-1.新規画面起動前申請共通設定を取得する
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID, application.getEmployeeID(), 1, 
				EnumAdaptor.valueOf(ApplicationType_Old.OVER_TIME_APPLICATION.value, ApplicationType_Old.class), application.getAppDate());
		// 14-2.詳細画面起動前モードの判断
		DetailedScreenPreBootModeOutput detailedScreenPreBootModeOutput = 
				beforePreBootMode.judgmentDetailScreenMode(companyID, loginEmpID, appID, appCommonSettingOutput.getGeneralDate());
		getDetailMob.reflectStatus = detailedScreenPreBootModeOutput.getReflectPlanState().value;
		getDetailMob.authorizableFlags = detailedScreenPreBootModeOutput.isAuthorizableFlags();
		getDetailMob.approvalATR = detailedScreenPreBootModeOutput.getApprovalATR().value;
		getDetailMob.alternateExpiration = detailedScreenPreBootModeOutput.isAlternateExpiration();
		getDetailMob.loginApprovalAtr = detailScreenAppData.getDetailScreenApprovalData().getLoginApprovalAtr() == null ? null : detailScreenAppData.getDetailScreenApprovalData().getLoginApprovalAtr().value;
		switch (application.getAppType()) {
		case OVER_TIME_APPLICATION:
			getDetailMob.appOvertime = appOvertimeFinder.getDetailMob(appID, appCommonSettingOutput);
			break;

		default:
			break;
		}*/
		return getDetailMob;
	}
	
	public AppDispInfoStartupDto getDetailPC(String appID) {
		String companyID = AppContexts.user().companyId();
		AppDispInfoStartupOutput appDispInfoStartupOutput = detailAppCommonSetService.getCommonSetBeforeDetail(companyID, appID);
		return AppDispInfoStartupDto.fromDomain(appDispInfoStartupOutput);
	}
}
