package nts.uk.ctx.at.request.app.find.application.stamp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppStampDto_Old;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppStampNewPreDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppStampOutputDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.StampCombinationDto;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonForReversion;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendanceitem.AttendanceResultImport;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.DetailAppCommonSetService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementDetail;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.StampRecordOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.stamp.AppCommonDomainService;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampCombinationAtr;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampCommonDomainService;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampNewDomainService;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp_Old;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode_Old;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampNewPreOutput;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampOutput;
import nts.uk.ctx.at.request.dom.application.stamp.output.ErrorStampInfo;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class AppStampFinder {
	
	@Inject
	private AppStampNewDomainService appStampNewDomainService;
	
	@Inject 
	private AppStampCommonDomainService appStampCommonDomainService;
	
	//refactor4
	public static final String PATTERN_DATE = "yyyy/MM/dd";
	
	@Inject
	private AppCommonDomainService appCommonStampDomainService;
	
	@Inject
	private DetailAppCommonSetService appCommonSetService;
	
	@Inject
	private CommonAlgorithm commonAlgorithm;
	
	public AppStampNewPreDto newAppStampPreProcess(String employeeID, String date) {
		String companyID = AppContexts.user().companyId();
		if(Strings.isBlank(employeeID)){
			employeeID = AppContexts.user().employeeId();
		}
		GeneralDate targetDate = Strings.isNotBlank(date) ? GeneralDate.fromString(date, "yyyy/MM/dd") : GeneralDate.today();
		AppStampNewPreOutput appStampNewPreOutput = this.appStampNewDomainService.appStampPreProcess(companyID, employeeID, targetDate);
		AppStampNewPreDto appStampNewPreDto = new AppStampNewPreDto();
//		appStampNewPreDto.appCommonSettingDto = new AppCommonSettingDto(
//				targetDate.toString("yyyy/MM/dd"), 
//				ApplicationSettingDto.convertToDto(appStampNewPreOutput.appCommonSettingOutput.applicationSetting), 
//				null, 
//				appStampNewPreOutput.appCommonSettingOutput.appTypeDiscreteSettings.stream().map(x -> AppTypeDiscreteSettingDto.convertToDto(x)).collect(Collectors.toList()), 
//				null);
//		appStampNewPreDto.appStampSetDto = new AppStampSetDto(
//				new StampRequestSettingDto(
//						companyID, 
//						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getTopComment().getComment().v(), 
//						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getTopComment().getFontColor(), 
//						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getTopComment().getFontWeight(), 
//						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getBottomComment().getComment().v(), 
//						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getBottomComment().getFontColor(), 
//						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getBottomComment().getFontWeight(), 
//						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getResultDisp().value, 
//						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getSupFrameDispNO().v(), 
//						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getStampPlaceDisp().value, 
//						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getStampDisplayControl().getStampAtrWorkDisp().value, 
//						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getStampDisplayControl().getStampAtrGoOutDisp().value, 
//						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getStampDisplayControl().getStampAtrCareDisp().value, 
//						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getStampDisplayControl().getStampAtrSupDisp().value,
//						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getStampDisplayControl().getStampAtrChildCareDisp().value,
//						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getGoOutTypeDisplayControl().getStampGoOutAtrPrivateDisp().value, 
//						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getGoOutTypeDisplayControl().getStampGoOutAtrPublicDisp().value, 
//						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getGoOutTypeDisplayControl().getStampGoOutAtrCompensationDisp().value, 
//						appStampNewPreOutput.appStampSetOutput.getStampRequestSetting().getGoOutTypeDisplayControl().getStampGoOutAtrUnionDisp().value),  
//				appStampNewPreOutput.appStampSetOutput.getApplicationReasons().stream()
//					.map(x -> new ApplicationReasonDto(
//							x.getCompanyId(),
//							x.getAppType().value,
//							x.getReasonID(),
//							x.getDispOrder(),
//							x.getReasonTemp().v(),
//							x.getDefaultFlg().value))
//					.collect(Collectors.toList()));
		appStampNewPreDto.companyID = companyID;
		appStampNewPreDto.employeeID = employeeID;
		appStampNewPreDto.employeeName = appStampNewPreOutput.employeeName;
		return appStampNewPreDto;
	}
	
	public List<StampCombinationDto> getStampCombinationAtr(){
		List<StampCombinationDto> stampCombinationDtos = new ArrayList<>();
		for(AppStampCombinationAtr a : AppStampCombinationAtr.values()){
			stampCombinationDtos.add(new StampCombinationDto(a.value, a.name));
		}
		return stampCombinationDtos;
	}
	
	public AppStampDto_Old getAppStampByID(String appID){
		String companyID = AppContexts.user().companyId();
		AppStamp_Old appStamp = appStampCommonDomainService.findByID(companyID, appID);
		String employeeName = appStampCommonDomainService.getEmployeeName(appStamp.getApplication().getEmployeeID());
		String inputEmpName = appStampCommonDomainService.getEmployeeName(appStamp.getApplication().getEnteredPersonID());
		return AppStampDto_Old.convertToDto(appStamp, employeeName, inputEmpName);
	}
	
	public List<AttendanceResultImport> getAttendanceItem(List<String> employeeIDLst, String date, Integer stampRequestMode){
		String companyID = AppContexts.user().companyId();
		if(CollectionUtil.isEmpty(employeeIDLst)){
			String employeeID = AppContexts.user().employeeId();
			employeeIDLst = Arrays.asList(employeeID);
		}
		return appStampCommonDomainService.getAttendanceResult(
				companyID, 
				employeeIDLst, 
				GeneralDate.fromString(date, "yyyy/MM/dd"), 
				EnumAdaptor.valueOf(stampRequestMode, StampRequestMode_Old.class));
	}
	
//	Refactor4	
	public AppStampOutputDto getDataCommon(StartAppStampParam startParam) {
		AppStampOutput appStampOutput = appCommonStampDomainService.getDataCommon(startParam.getCompanyId(),
				!StringUtils.isBlank(startParam.getDate())
						? Optional.of(GeneralDate.fromString(startParam.getDate(), PATTERN_DATE))
						: Optional.empty(),
				startParam.getAppDispInfoStartupDto().toDomain(), startParam.getRecoderFlag());
		return AppStampOutputDto.fromDomain(appStampOutput);
		
	}
	
	public List<ConfirmMsgOutput> checkBeforeRegister(BeforeRegisterOrUpdateParam beforeRegisterParam) {
		String pattern2 = "yyyy/MM/dd";
		ApplicationDto applicationDto = beforeRegisterParam.getApplicationDto();
		Application application = Application.createFromNew(
				EnumAdaptor.valueOf(applicationDto.getPrePostAtr(), PrePostAtr.class),
				applicationDto.getEmployeeID(),
				EnumAdaptor.valueOf(applicationDto.getAppType(), ApplicationType.class),
				new ApplicationDate(GeneralDate.fromString(applicationDto.getAppDate(), pattern2)),
				applicationDto.getEnteredPerson(),
				applicationDto.getOpStampRequestMode() == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(applicationDto.getOpStampRequestMode(), StampRequestMode.class)),
				applicationDto.getOpReversionReason() == null ? Optional.empty() : Optional.of(new ReasonForReversion(applicationDto.getOpReversionReason())),
				StringUtils.isBlank(applicationDto.getOpAppStartDate()) ? Optional.empty() : Optional.of(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppStartDate(), pattern2))),
				StringUtils.isBlank(applicationDto.getOpAppEndDate()) ? Optional.empty() : Optional.of(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppEndDate(), pattern2))),
				applicationDto.getOpAppReason() == null ? Optional.empty() : Optional.of(new AppReason(applicationDto.getOpAppReason())),
				applicationDto.getOpAppStandardReasonCD() == null ? Optional.empty() : Optional.of(new AppStandardReasonCode(applicationDto.getOpAppStandardReasonCD())));
		AppStampOutput as = beforeRegisterParam.getAppStampOutputDto().toDomain();
		as.getAppStampOptional().ifPresent(x -> {
			x.setPrePostAtr(application.getPrePostAtr());
		});
		return appCommonStampDomainService.checkBeforeRegister(
				beforeRegisterParam.getCompanyId(),
				beforeRegisterParam.getAgentAtr(),
				application,
				as);
	}
	
	public List<ConfirmMsgOutput> checkBeforeUpdate(BeforeRegisterOrUpdateParam beforeRegisterParam) {
		ApplicationDto applicationDto = beforeRegisterParam.getApplicationDto();
		Application application =applicationDto.toDomain();
		AppStampOutput as = beforeRegisterParam.getAppStampOutputDto().toDomain();
		as.getAppStampOptional().ifPresent(x -> {
			x.setPrePostAtr(application.getPrePostAtr());
		});
		
		 return appCommonStampDomainService.checkBeforeUpdate(
				beforeRegisterParam.getCompanyId(),
				beforeRegisterParam.getAgentAtr(),
				application,
				as);
	}
	
	public AppStampOutputDto getDataDetailCommon(DetailAppStampParam detailAppStampParam) {
		
//		14-1.詳細画面起動前申請共通設定を取得する
		//lay tu man 000
		 AppDispInfoStartupOutput appDispInfoStartupOutput = 
				 appCommonSetService.getCommonSetBeforeDetail(detailAppStampParam.getCompanyId(), detailAppStampParam.getAppId());
		 
		AppStampOutput appStampOutput = appCommonStampDomainService.getDataDetailCommon(
				detailAppStampParam.getCompanyId(),
				detailAppStampParam.getAppId(),
				appDispInfoStartupOutput,
//				detailAppStampParam.getAppDispInfoStartupDto().toDomain(),
				detailAppStampParam.getRecoderFlag());
		
		return AppStampOutputDto.fromDomain(appStampOutput);
	}
	public AppStampOutputDto changeDateAppStamp(ChangeDateParam changeDateParam) {
		AppStampOutput  appStampOutput = changeDateParam.getAppStampOutputDto().toDomain();
		List<GeneralDate> dates = Collections.emptyList();
		if (!CollectionUtil.isEmpty(changeDateParam.getDate())) {
			changeDateParam.getDate().stream().map(x -> GeneralDate.fromString(x, PATTERN_DATE)).collect(Collectors.toList());
		}
		// 申請日を変更する
		// lay o man 000
		AppDispInfoWithDateOutput appDispInfoWithDateOutput = commonAlgorithm.changeAppDateProcess(
				changeDateParam.getCompanyId(), dates, ApplicationType.STAMP_APPLICATION,
				appStampOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput(),
				appStampOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput(), Optional.empty());
		appStampOutput.getAppDispInfoStartupOutput().setAppDispInfoWithDateOutput(appDispInfoWithDateOutput);
		
//		実績の打刻のチェック
		StampRecordOutput stampRecordOutput = null;
		Optional<List<ActualContentDisplay>> listActualContentDisplay = appStampOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst();
		if (listActualContentDisplay.isPresent()) {
			if (!CollectionUtil.isEmpty(listActualContentDisplay.get())) {
				ActualContentDisplay actualContentDisplay = listActualContentDisplay.get().get(0);
				Optional<AchievementDetail> opAchievementDetail = actualContentDisplay.getOpAchievementDetail();
				if (opAchievementDetail.isPresent()) {
					stampRecordOutput = opAchievementDetail.get().getStampRecordOutput();
				}
			}
		}
		List<ErrorStampInfo> listErrorStampInfo = appCommonStampDomainService.getErrorStampList(stampRecordOutput);
		appStampOutput.setErrorListOptional(Optional.ofNullable(listErrorStampInfo));
		
		return AppStampOutputDto.fromDomain(appStampOutput);
	}
	
	
	
	
	
}
