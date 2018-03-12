package nts.uk.ctx.at.request.app.find.application.common;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationMetaDto;
import nts.uk.ctx.at.request.app.find.application.requestofearch.GetDataAppCfDetailFinder;
import nts.uk.ctx.at.request.app.find.application.requestofearch.OutputMessageDeadline;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalFrameImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.CollectApprovalRootPatternService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.StartupErrorCheckService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.output.ApprovalRootPattern;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
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
	private RecordWorkInfoAdapter recordWorkInfoAdapter;
	
	@Inject
	private StartupErrorCheckService startupErrorCheckService;
	
	@Inject
	private ApplicationRepository_New applicationRepository_New;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	
	private final String DATE_FORMAT = "yyyy/MM/dd";
	
	public AppDateDataDto getAppDataByDate(Integer appTypeValue, String appDate, Boolean isStartUp, String appID){
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		GeneralDate appGeneralDate = GeneralDate.fromString(appDate, DATE_FORMAT);
		OutputMessageDeadline outputMessageDeadline = getDataAppCfDetailFinder.getDataConfigDetail(new ApplicationMetaDto("", appTypeValue, appGeneralDate));
		ApprovalRootPattern approvalRootPattern = null;
		ApplicationDto_New applicationDto = null;
		PrePostAtr defaultPrePostAtr = otherCommonAlgorithm.preliminaryJudgmentProcessing(EnumAdaptor.valueOf(appTypeValue, ApplicationType.class), appGeneralDate);
		if(Strings.isNotBlank(appID)){
			approvalRootPattern = approvalRootPatternService.getApprovalRootPatternService(
					companyID, 
					employeeID, 
					EmploymentRootAtr.APPLICATION, 
					EnumAdaptor.valueOf(appTypeValue, ApplicationType.class), 
					appGeneralDate,
					appID,
					false);
			Application_New application = applicationRepository_New.findByID(companyID, appID).get();
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
			approvalRootPattern = approvalRootPatternService.getApprovalRootPatternService(
					companyID, 
					employeeID, 
					EmploymentRootAtr.APPLICATION, 
					EnumAdaptor.valueOf(appTypeValue, ApplicationType.class), 
					appGeneralDate,
					appID,
					true);
		}
		if(isStartUp.equals(Boolean.TRUE)){
			startupErrorCheckService.startupErrorCheck(appGeneralDate, appTypeValue, approvalRootPattern.getApprovalRootContentImport());
		}
		RecordWorkInfoImport recordWorkInfoImport = recordWorkInfoAdapter.getRecordWorkInfo(employeeID, appGeneralDate);
		return new AppDateDataDto(
				outputMessageDeadline, 
				approvalRootPattern.getApprovalRootContentImport().getApprovalRootState().getListApprovalPhaseState()
					.stream().map(x -> ApprovalPhaseStateDto.fromApprovalPhaseStateImport(x)).collect(Collectors.toList()), 
				new RecordWorkDto_New(
						recordWorkInfoImport.getWorkTypeCode(),
						recordWorkInfoImport.getWorkTimeCode(), 
						recordWorkInfoImport.getAttendanceStampTimeFirst(), 
						recordWorkInfoImport.getLeaveStampTimeFirst(), 
						recordWorkInfoImport.getAttendanceStampTimeSecond(), 
						recordWorkInfoImport.getLeaveStampTimeSecond()),
				applicationDto,
				approvalRootPattern.getApprovalRootContentImport().getErrorFlag().value,
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
