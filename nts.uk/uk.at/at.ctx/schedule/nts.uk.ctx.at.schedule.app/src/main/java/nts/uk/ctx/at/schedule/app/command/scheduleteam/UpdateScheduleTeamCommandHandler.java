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
import nts.uk.shr.com.context.AppContexts;

/**
 * <<Command>> チームを更新する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.社員情報.スケジュールチーム.App.チームを更新する
 * @author quytb
 *
 */
@Stateless
public class UpdateScheduleTeamCommandHandler extends CommandHandler<ScheduleTeamSaveCommand> {
	@Inject
	private ScheduleTeamRepository scheduleTeamRepository;

	@Override
	protected void handle(CommandHandlerContext<ScheduleTeamSaveCommand> context) {
		ScheduleTeamSaveCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		/** 1: get(職場グループID、コード) */
		Optional<ScheduleTeam> optionalSt = scheduleTeamRepository.getScheduleTeam(companyId,
				command.getWorkplaceGroupId(), command.getScheduleTeamCd());
		
		if (!optionalSt.isPresent()) {
			return;
		}
		
		/** 2: set(名称、備考) */		
		ScheduleTeam scheduleTeam = new ScheduleTeam(command.getWorkplaceGroupId(),
				new ScheduleTeamCd(command.getScheduleTeamCd()),
				new ScheduleTeamName(command.getScheduleTeamName()),
				Optional.of(new ScheduleTeamRemarks(command.getRemarks())));
		
		/** 3: 入れ替える(Require, スケジュールチーム, List<社員ID>)*/		
		/** 3.1: <call>()*/
		
		/** 4: <call>()*/		
		/** 4.1: persist() */
		scheduleTeamRepository.update(scheduleTeam);
	}
}
