package nts.uk.ctx.at.request.app.command.application.appabsence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.app.find.setting.company.request.applicationsetting.apptypesetting.DisplayReasonDto;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.appabsence.AllDayHalfDayLeaveAtr;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsenceRepository;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.AppForSpecLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.AppForSpecLeaveRepository;
import nts.uk.ctx.at.request.dom.application.appabsence.service.AbsenceServiceProcess;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AppAbsenceStartInfoOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InitMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenInitModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.OutputMode;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.DisplayReasonRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class UpdateAppAbsenceCommandHandler extends CommandHandlerWithResult<UpdateAppAbsenceCommand, ProcessResult>{
	final static String DATE_FORMAT = "yyyy/MM/dd";
	@Inject
	private AppAbsenceRepository repoAppAbsence;
	@Inject
	private DetailBeforeUpdate detailBeforeUpdate;
	@Inject
	private ApplicationRepository_New repoApplication;
	@Inject
	private DetailAfterUpdate detailAfterUpdate;
	@Inject
	private CreatAppAbsenceCommandHandler insertAppAbsence;
	@Inject 
	private AbsenceServiceProcess absenceServiceProcess;
	@Inject
	private AppForSpecLeaveRepository repoSpecLeave;
	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;
	@Inject
	private DisplayReasonRepository displayRep;
	@Inject
	ApplicationSettingRepository applicationSettingRepository;
//	@Inject
//	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;
	@Inject
	private OtherCommonAlgorithm otherCommonAlg;	
	@Inject
	private InitMode initMode;
	@Override
	protected ProcessResult handle(CommandHandlerContext<UpdateAppAbsenceCommand> context) {
		String companyID = AppContexts.user().companyId();
		UpdateAppAbsenceCommand command = context.getCommand();
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = command.getAppAbsenceStartInfoDto().toDomain();
		Optional<AppAbsence> opAppAbsence = repoAppAbsence.getAbsenceByAppId(companyID, command.getApplicationCommand().getApplicationID());
		if(!opAppAbsence.isPresent()){
			throw new BusinessException("Msg_198");
		}
//		AppTypeDiscreteSetting appTypeDiscreteSetting = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType(
//				companyID, 
//				ApplicationType.ABSENCE_APPLICATION.value).get();
		List<DisplayReasonDto> displayReasonDtoLst = 
				displayRep.findDisplayReason(companyID).stream().map(x -> DisplayReasonDto.fromDomain(x)).collect(Collectors.toList());
		DisplayReasonDto displayReasonSet = displayReasonDtoLst.stream().filter(x -> x.getTypeOfLeaveApp() == command.getAppAbsenceCommand().getHolidayAppType())
				.findAny().orElse(null);
		DetailScreenInitModeOutput output = initMode.getDetailScreenInitMode(
				appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDetailScreenInfo().get().getUser(), 
				appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDetailScreenInfo().get().getReflectPlanState().value);
		String appReason = opAppAbsence.get().getApplication().getAppReason().v();
		if(output.getOutputMode()==OutputMode.EDITMODE){
			if(displayReasonSet!=null){
				boolean displayFixedReason = displayReasonSet.getDisplayFixedReason() == 1 ? true : false;
				boolean displayAppReason = displayReasonSet.getDisplayAppReason() == 1 ? true : false;
				String typicalReason = Strings.EMPTY;
				String displayReason = Strings.EMPTY;
				if(displayFixedReason){
					if(Strings.isBlank(command.getApplicationCommand().getAppReasonID())){
						typicalReason += "";
					} else {
						typicalReason += command.getApplicationCommand().getAppReasonID();
					}
				}
				if(displayAppReason){
					if(Strings.isNotBlank(typicalReason)){
						displayReason += System.lineSeparator();
					}
					if(Strings.isBlank(command.getApplicationCommand().getApplicationReason())){
						displayReason += "";
					} else {
						displayReason += command.getApplicationCommand().getApplicationReason();
					}
				}else{
					if (Strings.isBlank(typicalReason)) {
						displayReason = opAppAbsence.get().getApplication().getAppReason().v();
					}
				}
				ApplicationSetting applicationSetting = appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput()
						.getRequestSetting().getApplicationSetting();
				if(displayFixedReason||displayAppReason){
					if (applicationSetting.getAppLimitSetting().getRequiredAppReason()
							&& Strings.isBlank(typicalReason+displayReason)) {
						throw new BusinessException("Msg_115");
					}
					appReason = typicalReason + displayReason;
				}
			}
		}
		AppAbsence appAbsence = opAppAbsence.get();
		appAbsence.setAllDayHalfDayLeaveAtr(EnumAdaptor.valueOf(command.getAppAbsenceCommand().getAllDayHalfDayLeaveAtr(), AllDayHalfDayLeaveAtr.class));
		appAbsence.setChangeWorkHour(command.getAppAbsenceCommand().isChangeWorkHour());
		appAbsence.setStartTime1(command.getAppAbsenceCommand().getStartTime1() == null ? null : new TimeWithDayAttr(command.getAppAbsenceCommand().getStartTime1()));
		appAbsence.setEndTime1(command.getAppAbsenceCommand().getEndTime1() == null ? null : new TimeWithDayAttr(command.getAppAbsenceCommand().getEndTime1()));
		appAbsence.setStartTime2(command.getAppAbsenceCommand().getStartTime2() == null ? null : new TimeWithDayAttr(command.getAppAbsenceCommand().getStartTime2()));
		appAbsence.setEndTime2(command.getAppAbsenceCommand().getEndTime2() == null ? null : new TimeWithDayAttr(command.getAppAbsenceCommand().getEndTime2()));
		appAbsence.setWorkTypeCode(command.getAppAbsenceCommand().getWorkTypeCode() == null ? null : new WorkTypeCode(command.getAppAbsenceCommand().getWorkTypeCode()));
		appAbsence.setWorkTimeCode(command.getAppAbsenceCommand().getWorkTimeCode() == null ? null : new WorkTimeCode(command.getAppAbsenceCommand().getWorkTimeCode()));
		appAbsence.getApplication().setAppReason(new AppReason(appReason));
		appAbsence.setVersion(appAbsence.getVersion());
		appAbsence.getApplication().setVersion(command.getApplicationCommand().getVersion());
		
		//6.休暇申請（詳細）登録
		// 4-1.詳細画面登録前の処理
		/*detailBeforeUpdate.processBeforeDetailScreenRegistration(
				companyID, 
				appAbsence.getApplication().getEmployeeID(), 
				appAbsence.getApplication().getAppDate(), 
				1, 
				appAbsence.getAppID(), 
				appAbsence.getApplication().getPrePostAtr(), 
				command.getVersion(),
				appAbsence.getWorkTypeCode() == null ? null : appAbsence.getWorkTypeCode().v(),
				appAbsence.getWorkTimeCode() == null ? null : appAbsence.getWorkTimeCode().v());*/
		/*GeneralDate startDate = opAppAbsence.get().getApplication().getAppDate();
		GeneralDate endDate = opAppAbsence.get().getApplication().getEndDate().isPresent() ? opAppAbsence.get().getApplication().getEndDate().get() : opAppAbsence.get().getApplication().getAppDate();
		
		//休日申請日
		List<GeneralDate> lstDateIsHoliday = otherCommonAlg.lstDateIsHoliday(companyID, command.getEmployeeID(), new DatePeriod(startDate, endDate));
		//check update 7.登録時のエラーチェック
		insertAppAbsence.checkBeforeRegister(convert(command),
				startDate,
				endDate,
				false,
				lstDateIsHoliday);
		//計画年休上限チェック(check giới han trên plan annual holiday)
		//hoatt-2018-07-05
		absenceServiceProcess.checkLimitAbsencePlan(companyID, command.getEmployeeID(), command.getWorkTypeCode(),
				GeneralDate.fromString(command.getStartDate(),"yyyy/MM/dd"),
				GeneralDate.fromString(command.getEndDate(),"yyyy/MM/dd"),
				lstDateIsHoliday);*/
		//update appAbsence
		repoAppAbsence.updateAbsence(appAbsence);
		AppForSpecLeaveCmd appForSpecLeaveCmd = command.getAppAbsenceCommand().getAppForSpecLeave();
		//TH don nghi ngay dac biet
		if(command.getAppAbsenceCommand().getHolidayAppType() == HolidayAppType.SPECIAL_HOLIDAY.value && appForSpecLeaveCmd != null){//TH update
			AppForSpecLeave specHd = AppForSpecLeave.createFromJavaType(
					command.getApplicationCommand().getApplicationID(), 
					appForSpecLeaveCmd.isMournerFlag(), 
					appForSpecLeaveCmd.getRelationshipCD(), 
					appForSpecLeaveCmd.getRelationshipReason());
			repoSpecLeave.updateSpecHd(specHd);
		}else{//TH delete or nothing
			//get 特別休暇申請
			Optional<AppForSpecLeave> appSpec = repoSpecLeave.getAppForSpecLeaveById(companyID, command.getApplicationCommand().getApplicationID());
			if(appSpec.isPresent()){//TH co specHd old
				AppForSpecLeave specLeave = appSpec.get();
				repoSpecLeave.deleteSpecHd(specLeave);
			}
		}
		//update application
		repoApplication.updateWithVersion(appAbsence.getApplication());
		// 暫定データの登録
		GeneralDate cmdStartDate = GeneralDate.fromString(command.getApplicationCommand().getStartDate(), DATE_FORMAT);
		GeneralDate cmdEndDate = GeneralDate.fromString(command.getApplicationCommand().getEndDate(), DATE_FORMAT);
		List<GeneralDate> listDate = new ArrayList<>();
		List<GeneralDate> lstHoliday = otherCommonAlg.lstDateIsHoliday(companyID, command.getApplicationCommand().getApplicantSID(), new DatePeriod(cmdStartDate, cmdEndDate));
		for(GeneralDate loopDate = cmdStartDate; loopDate.beforeOrEquals(cmdEndDate); loopDate = loopDate.addDays(1)){
			if(!lstHoliday.contains(loopDate)) {
				listDate.add(loopDate);	
			}			
		}		
		if (!listDate.isEmpty()) {
			interimRemainDataMngRegisterDateChange.registerDateChange(
					companyID, 
					command.getApplicationCommand().getApplicantSID(), 
					listDate);	
		}
		
		// 4-2.詳細画面登録後の処理
		return detailAfterUpdate.processAfterDetailScreenRegistration(appAbsence.getApplication());
	}
	/*private CreatAppAbsenceCommand convert(UpdateAppAbsenceCommand command){
		CreatAppAbsenceCommand creat = new CreatAppAbsenceCommand();
		creat.setEmployeeID(command.getApplicationCommand().getApplicantSID());
		creat.setPrePostAtr(command. getPrePostAtr());
		creat.setHolidayAppType(command.getHolidayAppType());
		creat.setWorkTypeCode(command.getWorkTypeCode());
		creat.setSpecHd(command.getSpecHd());
		return creat;
	}*/

}
