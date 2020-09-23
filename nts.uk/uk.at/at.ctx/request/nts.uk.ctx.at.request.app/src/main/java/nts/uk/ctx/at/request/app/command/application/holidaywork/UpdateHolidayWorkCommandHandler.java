package nts.uk.ctx.at.request.app.command.application.holidaywork;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
@Stateless
public class UpdateHolidayWorkCommandHandler extends CommandHandlerWithResult<UpdateHolidayWorkCommand, ProcessResult> {
	@Inject
	private AppHolidayWorkRepository appHolidayWorkRepository;
	@Inject
	private ApplicationRepository applicationRepository;
	@Inject
	private DetailAfterUpdate detailAfterUpdate;
	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;
	
	@Inject
	ApplicationSettingRepository applicationSettingRepository;
	
	/**
	 * 8.休出申請（詳細）登録処理
	 */
	@Override
	protected ProcessResult handle(CommandHandlerContext<UpdateHolidayWorkCommand> context) {
//		String companyID = AppContexts.user().companyId();
//		UpdateHolidayWorkCommand updateHolidayWorkCommand = context.getCommand();
//		Optional<AppHolidayWork> opAppHolidayWork = appHolidayWorkRepository.getFullAppHolidayWork(companyID, updateHolidayWorkCommand.getAppID());
//		if(!opAppHolidayWork.isPresent()){
//			throw new RuntimeException("khong tim dc doi tuong");
//		}
//		
//		AppHdWorkDispInfoOutput appHdWorkDispInfoOutput = updateHolidayWorkCommand.getAppHdWorkDispInfoCmd().toDomain();
//		
//		AppTypeSetting appTypeSetting = appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput()
//				.getRequestSetting().getApplicationSetting().getListAppTypeSetting().stream()
//				.filter(x -> x.getAppType() == ApplicationType.HOLIDAY_WORK_APPLICATION).findFirst().get();
//		String appReason = Strings.EMPTY;	
//		String typicalReason = Strings.EMPTY;
//		String displayReason = Strings.EMPTY;
//		if(appTypeSetting.getDisplayFixedReason() == DisplayAtr.DISPLAY){
//			typicalReason += updateHolidayWorkCommand.getAppReasonID();
//		}
//		if(appTypeSetting.getDisplayAppReason() == DisplayAtr.DISPLAY){
//			if(Strings.isNotBlank(typicalReason)){
//				displayReason += System.lineSeparator();
//			}
//			displayReason += updateHolidayWorkCommand.getApplicationReason();
//		} else {
//			if(Strings.isBlank(typicalReason)){
//				displayReason = applicationRepository.findByID(companyID, updateHolidayWorkCommand.getAppID()).get().getOpAppReason().get().v();
//			}
//		} 
//		ApplicationSetting applicationSetting = appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput()
//				.getRequestSetting().getApplicationSetting();
//		if(appTypeSetting.getDisplayFixedReason() == DisplayAtr.DISPLAY
//			||appTypeSetting.getDisplayAppReason() == DisplayAtr.DISPLAY){
//			if (applicationSetting.getAppLimitSetting().getRequiredAppReason()
//					&& Strings.isBlank(typicalReason+displayReason)) {
//				throw new BusinessException("Msg_115");
//			}
//		}
//		appReason = typicalReason + displayReason;
//		
//		AppHolidayWork appHolidayWork = opAppHolidayWork.get();
//		List<HolidayWorkInput> holidayWorkInputs = new ArrayList<>();
//		holidayWorkInputs.addAll(updateHolidayWorkCommand.getRestTime().stream().filter(x -> x.getStartTime()!=null||x.getEndTime()!=null).map(x -> x.convertToDomain()).collect(Collectors.toList()));
//		holidayWorkInputs.addAll(updateHolidayWorkCommand.getOvertimeHours().stream().filter(x -> x.getApplicationTime()!=null).map(x -> x.convertToDomain()).collect(Collectors.toList()));
//		holidayWorkInputs.addAll(updateHolidayWorkCommand.getBreakTimes().stream().filter(x -> x.getApplicationTime()!=null).map(x -> x.convertToDomain()).collect(Collectors.toList()));
//		holidayWorkInputs.addAll(updateHolidayWorkCommand.getBonusTimes().stream().filter(x -> x.getApplicationTime()!=null).map(x -> x.convertToDomain()).collect(Collectors.toList()));
//		Optional<AppOvertimeDetail> appOvertimeDetailOtp = updateHolidayWorkCommand.getAppOvertimeDetail() == null ? Optional.empty()
//				: Optional.ofNullable(updateHolidayWorkCommand.getAppOvertimeDetail().toDomain(companyID, appHolidayWork.getAppID()));
//		String divergenceReason = updateHolidayWorkCommand.getDivergenceReasonContent().replaceFirst(":", System.lineSeparator());
//		String applicationReason = appReason;
//		appHolidayWork.setDivergenceReason(divergenceReason);
//		appHolidayWork.setHolidayWorkInputs(holidayWorkInputs);
//		appHolidayWork.setAppOvertimeDetail(appOvertimeDetailOtp);
//		appHolidayWork.setHolidayShiftNight(updateHolidayWorkCommand.getHolidayWorkShiftNight());
//		appHolidayWork.setWorkTimeCode(Strings.isBlank(updateHolidayWorkCommand.getSiftTypeCode()) ? null : new WorkTimeCode(updateHolidayWorkCommand.getSiftTypeCode()));
//		appHolidayWork.setWorkClock1(HolidayWorkClock.validateTime(updateHolidayWorkCommand.getWorkClockStart1(), updateHolidayWorkCommand.getWorkClockEnd1(), updateHolidayWorkCommand.getGoAtr1(), updateHolidayWorkCommand.getBackAtr1()));
//		appHolidayWork.setWorkClock2(HolidayWorkClock.validateTime(updateHolidayWorkCommand.getWorkClockStart2(), updateHolidayWorkCommand.getWorkClockEnd2(), updateHolidayWorkCommand.getGoAtr2(), updateHolidayWorkCommand.getBackAtr2()));
//		appHolidayWork.setWorkTypeCode(Strings.isBlank(updateHolidayWorkCommand.getWorkTypeCode()) ? null : new WorkTypeCode(updateHolidayWorkCommand.getWorkTypeCode()));
//		appHolidayWork.getApplication().setAppReason(new AppReason(applicationReason));
//		appHolidayWork.setVersion(appHolidayWork.getVersion());
//		appHolidayWork.getApplication().setVersion(updateHolidayWorkCommand.getVersion());
//		appHolidayWorkRepository.update(appHolidayWork);
//		applicationRepository.updateWithVersion(appHolidayWork.getApplication());
//		
//		// 暫定データの登録
//		interimRemainDataMngRegisterDateChange.registerDateChange(
//				companyID, 
//				updateHolidayWorkCommand.getApplicantSID(), 
//				Arrays.asList(updateHolidayWorkCommand.getApplicationDate()));
//		
//		// 4-2.詳細画面登録後の処理
//		// error EA refactor 4
//		/*return detailAfterUpdate.processAfterDetailScreenRegistration(appHolidayWork.getApplication());*/
		return null;
	}

}
