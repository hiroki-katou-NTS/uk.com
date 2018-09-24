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
import nts.uk.ctx.at.request.app.find.setting.company.request.applicationsetting.apptypesetting.DisplayReasonDto;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.AllDayHalfDayLeaveAtr;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsenceRepository;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.AppForSpecLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.AppForSpecLeaveRepository;
import nts.uk.ctx.at.request.dom.application.appabsence.service.AbsenceServiceProcess;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.DisplayReasonRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.RequiredFlg;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;
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
	@Inject
	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;
	@Override
	protected ProcessResult handle(CommandHandlerContext<UpdateAppAbsenceCommand> context) {
		String companyID = AppContexts.user().companyId();
		UpdateAppAbsenceCommand command = context.getCommand();
		Optional<AppAbsence> opAppAbsence = repoAppAbsence.getAbsenceByAppId(companyID, command.getAppID());
		if(!opAppAbsence.isPresent()){
			throw new BusinessException("Msg_198");
		}
		AppTypeDiscreteSetting appTypeDiscreteSetting = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType(
				companyID, 
				ApplicationType.ABSENCE_APPLICATION.value).get();
		List<DisplayReasonDto> displayReasonDtoLst = 
				displayRep.findDisplayReason(companyID).stream().map(x -> DisplayReasonDto.fromDomain(x)).collect(Collectors.toList());
		DisplayReasonDto displayReasonSet = displayReasonDtoLst.stream().filter(x -> x.getTypeOfLeaveApp() == command.getHolidayAppType())
				.findAny().orElse(null);
		String appReason = "";
		if(displayReasonSet!=null){
			boolean displayFixedReason = displayReasonSet.getDisplayFixedReason() == 1 ? true : false;
			boolean displayAppReason = displayReasonSet.getDisplayAppReason() == 1 ? true : false;
			String typicalReason = Strings.EMPTY;
			String displayReason = Strings.EMPTY;
			if(displayFixedReason){
				if(Strings.isBlank(command.getAppReasonID())){
					typicalReason += "";
				} else {
					typicalReason += command.getAppReasonID();
				}
			}
			if(displayAppReason){
				if(Strings.isNotBlank(typicalReason)){
					displayReason += System.lineSeparator();
				}
				if(Strings.isBlank(command.getApplicationReason())){
					displayReason += "";
				} else {
					displayReason += command.getApplicationReason();
				}
			}
			Optional<ApplicationSetting> applicationSettingOp = applicationSettingRepository
					.getApplicationSettingByComID(companyID);
			ApplicationSetting applicationSetting = applicationSettingOp.get();
			if(appTypeDiscreteSetting.getTypicalReasonDisplayFlg().equals(AppDisplayAtr.DISPLAY)
				||appTypeDiscreteSetting.getDisplayReasonFlg().equals(AppDisplayAtr.DISPLAY)){
				if (applicationSetting.getRequireAppReasonFlg().equals(RequiredFlg.REQUIRED)
						&& Strings.isBlank(typicalReason+displayReason)) {
					throw new BusinessException("Msg_115");
				}
			}
			appReason = typicalReason + displayReason;
		}
		AppAbsence appAbsence = opAppAbsence.get();
		appAbsence.setAllDayHalfDayLeaveAtr(EnumAdaptor.valueOf(command.getAllDayHalfDayLeaveAtr(), AllDayHalfDayLeaveAtr.class));
		appAbsence.setChangeWorkHour(command.isChangeWorkHour());
		appAbsence.setStartTime1(command.getStartTime1() == null ? null : new TimeWithDayAttr(command.getStartTime1()));
		appAbsence.setEndTime1(command.getEndTime1() == null ? null : new TimeWithDayAttr(command.getEndTime1()));
		appAbsence.setStartTime2(command.getStartTime2() == null ? null : new TimeWithDayAttr(command.getStartTime2()));
		appAbsence.setEndTime2(command.getEndTime2() == null ? null : new TimeWithDayAttr(command.getEndTime2()));
		appAbsence.setWorkTypeCode(command.getWorkTypeCode() == null ? null : new WorkTypeCode(command.getWorkTypeCode()));
		appAbsence.setWorkTimeCode(command.getWorkTimeCode() == null ? null : new WorkTimeCode(command.getWorkTimeCode()));
		appAbsence.getApplication().setAppReason(new AppReason(appReason));
		appAbsence.setVersion(appAbsence.getVersion());
		appAbsence.getApplication().setVersion(command.getVersion());
		
		//6.休暇申請（詳細）登録
		// 4-1.詳細画面登録前の処理
		detailBeforeUpdate.processBeforeDetailScreenRegistration(
				companyID, 
				appAbsence.getApplication().getEmployeeID(), 
				appAbsence.getApplication().getAppDate(), 
				1, 
				appAbsence.getAppID(), 
				appAbsence.getApplication().getPrePostAtr(), command.getVersion());
		//check update
		insertAppAbsence.checkBeforeRegister(convert(command),
				opAppAbsence.get().getApplication().getAppDate(),
				opAppAbsence.get().getApplication().getEndDate().isPresent() ?opAppAbsence.get().getApplication().getEndDate().get() : opAppAbsence.get().getApplication().getAppDate(),false);
		//計画年休上限チェック(check giới han trên plan annual holiday)
		//hoatt-2018-07-05
		absenceServiceProcess.checkLimitAbsencePlan(companyID, command.getEmployeeID(), command.getWorkTypeCode(),
				GeneralDate.fromString(command.getStartDate(),"yyyy/MM/dd"), GeneralDate.fromString(command.getEndDate(),"yyyy/MM/dd"), EnumAdaptor.valueOf(command.getHolidayAppType(), HolidayAppType.class));
		//update appAbsence
		repoAppAbsence.updateAbsence(appAbsence);
		SpecHolidayCommand specHdCm = command.getSpecHd();
		if(command.getHolidayAppType() == HolidayAppType.SPECIAL_HOLIDAY.value && specHdCm != null){
			AppForSpecLeave specHd = AppForSpecLeave.createFromJavaType(command.getAppID(), specHdCm.getMournerCheck(), specHdCm.getRelationCD(), specHdCm.getRelaReason());
			repoSpecLeave.updateSpecHd(specHd);
		}
		//update application
		repoApplication.updateWithVersion(appAbsence.getApplication());
		// 暫定データの登録
		GeneralDate cmdStartDate = GeneralDate.fromString(command.getStartDate(), DATE_FORMAT);
		GeneralDate cmdEndDate = GeneralDate.fromString(command.getStartDate(), DATE_FORMAT);
		List<GeneralDate> listDate = new ArrayList<>();
		for(GeneralDate loopDate = cmdStartDate; loopDate.beforeOrEquals(cmdEndDate); loopDate = loopDate.addDays(1)){
			listDate.add(loopDate);
		}
		interimRemainDataMngRegisterDateChange.registerDateChange(
				command.getCompanyID(), 
				command.getEmployeeID(), 
				listDate);
		// 4-2.詳細画面登録後の処理
		return detailAfterUpdate.processAfterDetailScreenRegistration(appAbsence.getApplication());
	}
	private CreatAppAbsenceCommand convert(UpdateAppAbsenceCommand command){
		CreatAppAbsenceCommand creat = new CreatAppAbsenceCommand();
		creat.setEmployeeID(command.getEmployeeID());
		creat.setPrePostAtr(command.getPrePostAtr());
		creat.setHolidayAppType(command.getHolidayAppType());
		creat.setWorkTypeCode(command.getWorkTypeCode());
		return creat;
	}

}
