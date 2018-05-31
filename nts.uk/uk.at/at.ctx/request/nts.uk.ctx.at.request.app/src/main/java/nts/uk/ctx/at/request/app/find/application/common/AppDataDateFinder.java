package nts.uk.ctx.at.request.app.find.application.common;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationMetaDto;
import nts.uk.ctx.at.request.app.find.application.requestofearch.GetDataAppCfDetailFinder;
import nts.uk.ctx.at.request.app.find.application.requestofearch.OutputMessageDeadline;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SEmpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalFrameImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.CollectApprovalRootPatternService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.StartupErrorCheckService;
import nts.uk.ctx.at.request.dom.application.common.service.other.CollectAchievement;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.BaseDateFlg;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class AppDataDateFinder {
	
	@Inject
	private GetDataAppCfDetailFinder getDataAppCfDetailFinder;
	
	@Inject
	private CollectApprovalRootPatternService approvalRootPatternService;
	
	@Inject
	private StartupErrorCheckService startupErrorCheckService;
	
	@Inject
	private ApplicationRepository_New applicationRepository_New;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	
	@Inject
	private EmployeeRequestAdapter employeeAdaptor;
	
	@Inject
	private CollectAchievement collectAchievement;
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private ApplicationSettingRepository applicationSettingRepository;
	
	private final String DATE_FORMAT = "yyyy/MM/dd";
	
	public AppDateDataDto getAppDataByDate(Integer appTypeValue, String appDate, Boolean isStartUp, String appID){
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		GeneralDate appGeneralDate = GeneralDate.fromString(appDate, DATE_FORMAT);
		AchievementOutput achievementOutput = new AchievementOutput(appGeneralDate, null, null, null, null, null, null);
		ApprovalRootContentImport_New approvalRootContentImport = null;
		ApplicationDto_New applicationDto = null;
		PrePostAtr defaultPrePostAtr = otherCommonAlgorithm.preliminaryJudgmentProcessing(EnumAdaptor.valueOf(appTypeValue, ApplicationType.class), appGeneralDate,0);
		if(Strings.isNotBlank(appID)){
			Application_New application = applicationRepository_New.findByID(companyID, appID).get();
			SEmpHistImport empHistImport = employeeAdaptor.getEmpHist(
					companyID, 
					application.getEmployeeID(), 
					appGeneralDate);
			if(empHistImport==null || empHistImport.getEmploymentCode()==null){
				throw new BusinessException("Msg_426");
			}
			approvalRootContentImport = approvalRootPatternService.getApprovalRootPatternService(
					companyID, 
					employeeID, 
					EmploymentRootAtr.APPLICATION, 
					EnumAdaptor.valueOf(appTypeValue, ApplicationType.class), 
					appGeneralDate,
					appID,
					false).getApprovalRootContentImport();
			applicationDto = ApplicationDto_New.builder()
					.version(application.getVersion())
					.companyID(application.getCompanyID())
					.applicationID(application.getAppID())
					.prePostAtr(application.getPrePostAtr().value)
					.inputDate(application.getInputDate().toString(DATE_FORMAT))
					.enteredPersonSID(application.getEnteredPersonID())
					.reversionReason(application.getReversionReason().v())
					.applicationDate(application.getAppDate().toString(DATE_FORMAT))
					.applicationReason(application.getAppReason().v())
					.applicationType(application.getAppType().value)
					.applicantSID(application.getEmployeeID())
					.reflectPlanState(application.getReflectionInformation().getStateReflection().value)
					.reflectPerState(application.getReflectionInformation().getStateReflectionReal().value)
					.reflectPlanEnforce(application.getReflectionInformation().getForcedReflection().value)
					.reflectPerEnforce(application.getReflectionInformation().getForcedReflectionReal().value)
					.reflectPlanScheReason(application.getReflectionInformation().getNotReason().map(x -> x.value).orElse(null))
					.reflectPerScheReason(application.getReflectionInformation().getNotReasonReal().map(x -> x.value).orElse(null))
					.reflectPlanTime(application.getReflectionInformation().getDateTimeReflection().map(x -> x.toString(DATE_FORMAT)).orElse(null))
					.reflectPerTime(application.getReflectionInformation().getDateTimeReflectionReal().map(x -> x.toString(DATE_FORMAT)).orElse(null))
					.build();
		} else {
			achievementOutput = collectAchievement.getAchievement(companyID, employeeID, appGeneralDate);
			if(isStartUp.equals(Boolean.TRUE)){
				approvalRootContentImport = approvalRootPatternService.getApprovalRootPatternService(
						companyID, 
						employeeID, 
						EmploymentRootAtr.APPLICATION, 
						EnumAdaptor.valueOf(appTypeValue, ApplicationType.class), 
						appGeneralDate,
						appID,
						true).getApprovalRootContentImport();
				startupErrorCheckService.startupErrorCheck(appGeneralDate, appTypeValue, approvalRootContentImport);
			} else {
				BaseDateFlg baseDateFlg = applicationSettingRepository.getApplicationSettingByComID(companyID)
								.map(x -> x.getBaseDateFlg()).orElse(BaseDateFlg.SYSTEM_DATE);
				if(baseDateFlg.equals(BaseDateFlg.APP_DATE)){
					approvalRootContentImport = approvalRootStateAdapter.getApprovalRootContent(companyID, employeeID, appTypeValue, appGeneralDate, appID, true);
				} 
			}
		}
		OutputMessageDeadline outputMessageDeadline = getDataAppCfDetailFinder.getDataConfigDetail(new ApplicationMetaDto("", appTypeValue, appGeneralDate));
		return new AppDateDataDto(
				outputMessageDeadline, 
				approvalRootContentImport == null ? null : 
					approvalRootContentImport.getApprovalRootState().getListApprovalPhaseState()
					.stream().map(x -> ApprovalPhaseStateDto.fromApprovalPhaseStateImport(x)).collect(Collectors.toList()), 
				new AchievementOutput(
						achievementOutput.getDate(), 
						achievementOutput.getWorkType(), 
						achievementOutput.getWorkTime(), 
						achievementOutput.getStartTime1(), 
						achievementOutput.getEndTime1(), 
						achievementOutput.getStartTime2(), 
						achievementOutput.getEndTime2()),
				applicationDto,
				approvalRootContentImport == null ? null : approvalRootContentImport.getErrorFlag().value,
				defaultPrePostAtr.value);
	}
	
}
@Value
@AllArgsConstructor
class ApprovalPhaseStateDto{
	private Integer phaseOrder;
	
	private Integer approvalAtrValue;
	
	private String approvalAtrName;
	
	private List<ApprovalFrameDto> listApprovalFrame;
	
	public static ApprovalPhaseStateDto fromApprovalPhaseStateImport(ApprovalPhaseStateImport_New approvalPhaseStateImport){
		return new ApprovalPhaseStateDto(
				approvalPhaseStateImport.getPhaseOrder(), 
				approvalPhaseStateImport.getApprovalAtr().value,
				approvalPhaseStateImport.getApprovalAtr().name,
				approvalPhaseStateImport.getListApprovalFrame().stream().map(x -> ApprovalFrameDto.fromApprovalFrameImport(x)).collect(Collectors.toList()));
	}
}

@Value
@AllArgsConstructor
class ApprovalFrameDto {
	private Integer phaseOrder;
	
	private Integer frameOrder;
	
	private Integer approvalAtrValue;
	
	private String approvalAtrName;
	
	private List<ApproverStateDto> listApprover;
	
	private String approverID;
	
	private String approverName;
	
	private String representerID;
	
	private String representerName;
	
	private String approvalReason;
	
	public static ApprovalFrameDto fromApprovalFrameImport(ApprovalFrameImport_New approvalFrameImport){
		return new ApprovalFrameDto(
				approvalFrameImport.getPhaseOrder(), 
				approvalFrameImport.getFrameOrder(), 
				approvalFrameImport.getApprovalAtr().value,
				approvalFrameImport.getApprovalAtr().name, 
				approvalFrameImport.getListApprover().stream()
					.map(x -> new ApproverStateDto(
							x.getApproverID(), 
							x.getApproverName(),
							x.getRepresenterID(),
							x.getRepresenterName()))
					.collect(Collectors.toList()), 
				approvalFrameImport.getApproverID(),
				approvalFrameImport.getApproverName(),
				approvalFrameImport.getRepresenterID(),
				approvalFrameImport.getRepresenterName(),
				approvalFrameImport.getApprovalReason());
	}
}

@Value
@AllArgsConstructor
class ApproverStateDto {
	private String approverID;
	
	private String approverName;
	
	private String representerID;
	
	private String representerName;
}

@Value
@AllArgsConstructor
class RecordWorkDto_New {
	// 勤務種類コード
	private String workTypeCode;
	
	// 就業時間帯コード
	private String workTimeCode;
	
	// 開始時刻1
	private Integer attendanceStampTimeFirst;
	
	// 終了時刻1
	private Integer leaveStampTimeFirst;
	
	// 開始時刻2
	private Integer attendanceStampTimeSecond;
	
	// 終了時刻2
	private Integer leaveStampTimeSecond;
}
