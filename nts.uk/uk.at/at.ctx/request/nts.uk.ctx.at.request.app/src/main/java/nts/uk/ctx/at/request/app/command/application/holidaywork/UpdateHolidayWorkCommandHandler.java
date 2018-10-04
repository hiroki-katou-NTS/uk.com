package nts.uk.ctx.at.request.app.command.application.holidaywork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkClock;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class UpdateHolidayWorkCommandHandler extends CommandHandlerWithResult<UpdateHolidayWorkCommand, ProcessResult> {
	@Inject
	private AppHolidayWorkRepository appHolidayWorkRepository;
	@Inject
	private ApplicationRepository_New applicationRepository;
	@Inject
	private DetailAfterUpdate detailAfterUpdate;
	@Inject
	private DetailBeforeUpdate detailBeforeUpdate;
	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;
	
	@Override
	protected ProcessResult handle(CommandHandlerContext<UpdateHolidayWorkCommand> context) {
		String companyID = AppContexts.user().companyId();
		UpdateHolidayWorkCommand updateHolidayWorkCommand = context.getCommand();
		Optional<AppHolidayWork> opAppHolidayWork = appHolidayWorkRepository.getFullAppHolidayWork(companyID, updateHolidayWorkCommand.getAppID());
		if(!opAppHolidayWork.isPresent()){
			throw new RuntimeException("khong tim dc doi tuong");
		}
		AppHolidayWork appHolidayWork = opAppHolidayWork.get();
		List<HolidayWorkInput> holidayWorkInputs = new ArrayList<>();
		holidayWorkInputs.addAll(updateHolidayWorkCommand.getRestTime().stream().filter(x -> x.getStartTime()!=null||x.getEndTime()!=null).map(x -> x.convertToDomain()).collect(Collectors.toList()));
		holidayWorkInputs.addAll(updateHolidayWorkCommand.getOvertimeHours().stream().filter(x -> x.getApplicationTime()!=null).map(x -> x.convertToDomain()).collect(Collectors.toList()));
		holidayWorkInputs.addAll(updateHolidayWorkCommand.getBreakTimes().stream().filter(x -> x.getApplicationTime()!=null).map(x -> x.convertToDomain()).collect(Collectors.toList()));
		holidayWorkInputs.addAll(updateHolidayWorkCommand.getBonusTimes().stream().filter(x -> x.getApplicationTime()!=null).map(x -> x.convertToDomain()).collect(Collectors.toList()));
		Optional<AppOvertimeDetail> appOvertimeDetailOtp = updateHolidayWorkCommand.getAppOvertimeDetail() == null ? Optional.empty()
				: Optional.ofNullable(updateHolidayWorkCommand.getAppOvertimeDetail().toDomain(companyID, appHolidayWork.getAppID()));
		String divergenceReason = updateHolidayWorkCommand.getDivergenceReasonContent().replaceFirst(":", System.lineSeparator());
		String applicationReason = updateHolidayWorkCommand.getApplicationReason().replaceFirst(":", System.lineSeparator());
		appHolidayWork.setDivergenceReason(divergenceReason);
		appHolidayWork.setHolidayWorkInputs(holidayWorkInputs);
		appHolidayWork.setAppOvertimeDetail(appOvertimeDetailOtp);
		appHolidayWork.setHolidayShiftNight(updateHolidayWorkCommand.getHolidayWorkShiftNight());
		appHolidayWork.setWorkTimeCode(new WorkTimeCode(updateHolidayWorkCommand.getSiftTypeCode()));
		appHolidayWork.setWorkClock1(HolidayWorkClock.validateTime(updateHolidayWorkCommand.getWorkClockStart1(), updateHolidayWorkCommand.getWorkClockEnd1(), updateHolidayWorkCommand.getGoAtr1(), updateHolidayWorkCommand.getBackAtr1()));
		appHolidayWork.setWorkClock2(HolidayWorkClock.validateTime(updateHolidayWorkCommand.getWorkClockStart2(), updateHolidayWorkCommand.getWorkClockEnd2(), updateHolidayWorkCommand.getGoAtr2(), updateHolidayWorkCommand.getBackAtr2()));
		appHolidayWork.setWorkTypeCode(new WorkTypeCode(updateHolidayWorkCommand.getWorkTypeCode()));
		appHolidayWork.getApplication().setAppReason(new AppReason(applicationReason));
		appHolidayWork.setVersion(appHolidayWork.getVersion());
		appHolidayWork.getApplication().setVersion(updateHolidayWorkCommand.getVersion());
		detailBeforeUpdate.processBeforeDetailScreenRegistration(
				companyID, 
				appHolidayWork.getApplication().getEmployeeID(), 
				appHolidayWork.getApplication().getAppDate(), 
				1, 
				appHolidayWork.getAppID(), 
				appHolidayWork.getApplication().getPrePostAtr(), updateHolidayWorkCommand.getVersion());
		appHolidayWorkRepository.update(appHolidayWork);
		applicationRepository.updateWithVersion(appHolidayWork.getApplication());
		
		// 暫定データの登録
		interimRemainDataMngRegisterDateChange.registerDateChange(
				companyID, 
				updateHolidayWorkCommand.getApplicantSID(), 
				Arrays.asList(updateHolidayWorkCommand.getApplicationDate()));
		
		return detailAfterUpdate.processAfterDetailScreenRegistration(appHolidayWork.getApplication());
	}

}
