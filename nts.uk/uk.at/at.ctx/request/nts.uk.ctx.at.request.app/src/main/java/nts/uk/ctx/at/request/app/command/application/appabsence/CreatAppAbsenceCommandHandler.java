package nts.uk.ctx.at.request.app.command.application.appabsence;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.app.find.setting.company.request.applicationsetting.apptypesetting.DisplayReasonDto;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.IFactoryApplication;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.AppForSpecLeave_Old;
import nts.uk.ctx.at.request.dom.application.appabsence.service.AbsenceServiceProcess;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AppAbsenceStartInfoOutput;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.DisplayReasonRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CreatAppAbsenceCommandHandler extends CommandHandlerWithResult<CreatAppAbsenceCommand, ProcessResult>{
	
	final static String DATE_FORMAT = "yyyy/MM/dd";
	@Inject
	private IFactoryApplication iFactoryApplication;
	@Inject 
	private AbsenceServiceProcess absenceServiceProcess;
	@Inject
	private NewAfterRegister newAfterRegister;
	@Inject
	private RegisterAtApproveReflectionInfoService registerService;
	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;
	@Inject
	private DisplayReasonRepository displayRep;
	@Inject
	private OtherCommonAlgorithm otherCommonAlg;	
	
	/**
	 * 休暇申請（新規）登録処理
	 */
	@Override
	protected ProcessResult handle(CommandHandlerContext<CreatAppAbsenceCommand> context) {
//		CreatAppAbsenceCommand command = context.getCommand();
//		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = command.getAppAbsenceStartInfoDto().toDomain();
//		// 会社ID
//		String companyID = AppContexts.user().companyId();
//		// 申請ID
//		String appID = IdentifierUtil.randomUniqueId();
//		// Create Application
//		GeneralDate startDate = command.getApplicationCommand().getStartDate() == null ? null : GeneralDate.fromString(command.getApplicationCommand().getStartDate(), DATE_FORMAT);
//		GeneralDate endDate = command.getApplicationCommand().getEndDate() == null ? null : GeneralDate.fromString(command.getApplicationCommand().getEndDate(), DATE_FORMAT);
//		List<DisplayReasonDto> displayReasonDtoLst = 
//				displayRep.findDisplayReason(companyID).stream().map(x -> DisplayReasonDto.fromDomain(x)).collect(Collectors.toList());
//		DisplayReasonDto displayReasonSet = displayReasonDtoLst.stream().filter(x -> x.getTypeOfLeaveApp() == command.getAppAbsenceCommand().getHolidayAppType())
//				.findAny().orElse(null);
//		String appReason = "";
//		if(displayReasonSet!=null){
//			boolean displayFixedReason = displayReasonSet.getDisplayFixedReason() == 1 ? true : false;
//			boolean displayAppReason = displayReasonSet.getDisplayAppReason() == 1 ? true : false;
//			String typicalReason = Strings.EMPTY;
//			String displayReason = Strings.EMPTY;
//			if(displayFixedReason){
//				if(Strings.isBlank(command.getApplicationCommand().getAppReasonID())){
//					typicalReason += "";
//				} else {
//					typicalReason += command.getApplicationCommand().getAppReasonID();
//				}
//			}
//			if(displayAppReason){
//				if(Strings.isNotBlank(typicalReason)){
//					displayReason += System.lineSeparator();
//				}
//				if(Strings.isBlank(command.getApplicationCommand().getApplicationReason())){
//					displayReason += "";
//				} else {
//					displayReason += command.getApplicationCommand().getApplicationReason();
//				}
//			}
//			ApplicationSetting applicationSetting = appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput()
//					.getRequestSetting().getApplicationSetting();
//			if(displayFixedReason||displayAppReason){
//				if (applicationSetting.getAppLimitSetting().getRequiredAppReason()
//						&& Strings.isBlank(typicalReason+displayReason)) {
//					throw new BusinessException("Msg_115");
//				}
//			}
//			appReason = typicalReason + displayReason;
//		}
////		Application_New appRoot = iFactoryApplication.buildApplication(appID, startDate,
////				command.getApplicationCommand().getPrePostAtr(), appReason, appReason,
////				ApplicationType.ABSENCE_APPLICATION, startDate, endDate, command.getApplicationCommand().getApplicantSID());
//		Application appRoot = null;
//		AppForSpecLeave specHd = null;
//		AppForSpecLeaveCmd appForSpecLeaveCmd = command.getAppAbsenceCommand().getAppForSpecLeave();
//		if(command.getAppAbsenceCommand().getHolidayAppType() == HolidayAppType.SPECIAL_HOLIDAY.value && appForSpecLeaveCmd != null){
//			specHd = AppForSpecLeave.createFromJavaType(appID, appForSpecLeaveCmd.isMournerFlag(), appForSpecLeaveCmd.getRelationshipCD(), appForSpecLeaveCmd.getRelationshipReason());
//		}
//		AppAbsence appAbsence = new AppAbsence(companyID,
//				appID,
//				command.getAppAbsenceCommand().getHolidayAppType(),
//				command.getAppAbsenceCommand().getWorkTypeCode(),
//				command.getAppAbsenceCommand().getWorkTimeCode(),
//				command.getAppAbsenceCommand().isHalfDayFlg(), 
//				command.getAppAbsenceCommand().isChangeWorkHour(),
//				command.getAppAbsenceCommand().getAllDayHalfDayLeaveAtr(), 
//				command.getAppAbsenceCommand().getStartTime1(),
//				command.getAppAbsenceCommand().getEndTime1(),
//				command.getAppAbsenceCommand().getStartTime2(),
//				command.getAppAbsenceCommand().getEndTime2(),
//				specHd);
//		
//		// insert
//		absenceServiceProcess.createAbsence(appAbsence, appRoot, appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getApprovalRootState());
//		// 2-2.新規画面登録時承認反映情報の整理
//		// error EA refactor 4
//		/*registerService.newScreenRegisterAtApproveInfoReflect(appRoot.getEmployeeID(), appRoot);*/
//		// 暫定データの登録
//		GeneralDate cmdStartDate = GeneralDate.fromString(command.getApplicationCommand().getStartDate(), DATE_FORMAT);
//		GeneralDate cmdEndDate = GeneralDate.fromString(command.getApplicationCommand().getEndDate(), DATE_FORMAT);
//		List<GeneralDate> listDate = new ArrayList<>();
//		List<GeneralDate> lstHoliday = otherCommonAlg.lstDateIsHoliday(companyID, command.getApplicationCommand().getApplicantSID(), new DatePeriod(cmdStartDate, cmdEndDate));
//		for(GeneralDate loopDate = cmdStartDate; loopDate.beforeOrEquals(cmdEndDate); loopDate = loopDate.addDays(1)){
//			if(!lstHoliday.contains(loopDate)) {
//				listDate.add(loopDate);	
//			}			
//		}	
//		if(!listDate.isEmpty()) {
//			interimRemainDataMngRegisterDateChange.registerDateChange(
//					companyID, 
//					command.getApplicationCommand().getApplicantSID(), 
//					listDate);	
//		}
		
		// // error EA refactor 4
		// 2-3.新規画面登録後の処理を実行
		/*return newAfterRegister.processAfterRegister(appRoot);*/
		return null;
	}
}
