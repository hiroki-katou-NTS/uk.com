package nts.uk.ctx.at.schedule.app.command.scheduleteam;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamRepository;

/**
 * <<Command>> チームを削除する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.社員情報.スケジュールチーム.App.チームを削除する
 * @author quytb
 *
 */
@Stateless
public class DeleteScheduleTeamCommandHandler extends CommandHandler<ScheduleTeamDeleteCommand> {
	@Inject
	private ScheduleTeamRepository scheduleTeamRepository;

	@Override
	protected void handle(CommandHandlerContext<ScheduleTeamDeleteCommand> context) {
		ScheduleTeamDeleteCommand command = context.getCommand();
		/** 1: 削除する(Require, 職場グループID, スケジュールチームコード)*/
		
	}

}
