package nts.uk.ctx.at.schedule.app.command.scheduleteam;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamCd;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamName;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamRemarks;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamRepository;

/**
 * <<Command>> チームを登録する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.社員情報.スケジュールチーム.App.チームを登録する
 * @author quytb
 *
 */
@Stateless
public class RegisterScheduleTeamCommandHandler extends CommandHandler<ScheduleTeamSaveCommand> {
	
	@Inject
	private ScheduleTeamRepository scheduleTeamRepository; 

	@Override
	protected void handle(CommandHandlerContext<ScheduleTeamSaveCommand> context) {
		ScheduleTeamSaveCommand command = context.getCommand();
		ScheduleTeam scheduleTeam = new ScheduleTeam(command.getWorkplaceGroupId(),
														new ScheduleTeamCd(command.getCode()),
														new ScheduleTeamName(command.getName()),
														Optional.of(new ScheduleTeamRemarks(command.getNote()))
														);
		scheduleTeamRepository.insert(scheduleTeam);
	}

}
