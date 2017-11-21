package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.shared.dom.worktime_old.SiftCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.context.AppContexts;

public class UpdateOvertimeCommandHandler extends CommandHandler<UpdateOvertimeCommand>{

	@Inject
	private OvertimeRepository overtimeRepository;
	
	@Inject
	private DetailBeforeUpdate detailBeforeUpdate;
	
	@Inject
	private DetailAfterUpdate detailAfterUpdate;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateOvertimeCommand> context) {
		String companyID = AppContexts.user().companyId();
		UpdateOvertimeCommand command = context.getCommand();
		Optional<AppOverTime> opAppOverTime = overtimeRepository.getFullAppOvertime(companyID, command.getAppID());
		if(!opAppOverTime.isPresent()){
			throw new RuntimeException("khong ton tai doi tuong de update");
		}
		AppOverTime appOverTime = opAppOverTime.get();
		List<OverTimeInput> overTimeInputs = new ArrayList<>();
		overTimeInputs.addAll(command.getRestTime().stream().map(x -> x.convertToDomain()).collect(Collectors.toList()));
		overTimeInputs.addAll(command.getOvertimeHours().stream().map(x -> x.convertToDomain()).collect(Collectors.toList()));
		overTimeInputs.addAll(command.getBreakTimes().stream().map(x -> x.convertToDomain()).collect(Collectors.toList()));
		overTimeInputs.addAll(command.getBonusTimes().stream().map(x -> x.convertToDomain()).collect(Collectors.toList()));
		
		appOverTime.setDivergenceReason(command.getDivergenceReasonContent());
		appOverTime.setFlexExessTime(command.getFlexExessTime());
		appOverTime.setOverTimeAtr(EnumAdaptor.valueOf(command.getOvertimeAtr(), OverTimeAtr.class));
		appOverTime.setOverTimeInput(overTimeInputs);
		appOverTime.setOverTimeShiftNight(command.getOverTimeShiftNight());
		appOverTime.setSiftCode(new SiftCode(command.getSiftTypeCode()));
		appOverTime.setWorkClockFrom1(command.getWorkClockFrom1());
		appOverTime.setWorkClockFrom2(command.getWorkClockFrom2());
		appOverTime.setWorkClockTo1(command.getWorkClockTo1());
		appOverTime.setWorkClockTo2(command.getWorkClockTo2());
		appOverTime.setWorkTypeCode(new WorkTypeCode(command.getWorkTypeCode()));
		appOverTime.getApplication().setApplicationReason(new AppReason(command.getApplicationReason()));
		appOverTime.setVersion(command.getVersion());
		appOverTime.getApplication().setVersion(appOverTime.getVersion());
		
		detailBeforeUpdate.processBeforeDetailScreenRegistration(
				companyID, 
				appOverTime.getApplication().getApplicantSID(), 
				appOverTime.getApplication().getApplicationDate(), 
				1, 
				appOverTime.getAppID(), 
				appOverTime.getApplication().getPrePostAtr());
		overtimeRepository.update(appOverTime);
		applicationRepository.updateApplication(appOverTime.getApplication());
		detailAfterUpdate.processAfterDetailScreenRegistration(appOverTime.getApplication());
	}

}
