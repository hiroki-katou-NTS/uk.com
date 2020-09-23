package nts.uk.ctx.at.schedule.app.command.scheduleteam;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeamRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamCd;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice.DeleteScheduleTeamService;
import nts.uk.shr.com.context.AppContexts;

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
	
	@Inject
	private BelongScheduleTeamRepository belongScheduleTeamRepository ;

	@Override
	protected void handle(CommandHandlerContext<ScheduleTeamDeleteCommand> context) {
		ScheduleTeamDeleteCommand command = context.getCommand();
		
		/** 1: 削除する(Require, 職場グループID, スケジュールチームコード)  */
		ScheduleTeamDeleteImpl scheduleTeamDeleteImpl = new ScheduleTeamDeleteImpl(scheduleTeamRepository, belongScheduleTeamRepository);
		AtomTask delete = DeleteScheduleTeamService.delete(scheduleTeamDeleteImpl, command.getWorkplaceGroupId(), new ScheduleTeamCd(command.getCode()));
		transaction.execute(() ->{
			delete.run();
		});
	}
	
	@AllArgsConstructor
	private static class ScheduleTeamDeleteImpl implements DeleteScheduleTeamService.Require{		
		@Inject
		private ScheduleTeamRepository scheduleTeamRepository;
		
		@Inject
		private BelongScheduleTeamRepository belongScheduleTeamRepository ;

		@Override
		public void deleteScheduleTeam(String WKPGRPID, ScheduleTeamCd scheduleTeamCd) {
			String companyID = AppContexts.user().companyId();
			scheduleTeamRepository.delete(companyID, WKPGRPID, scheduleTeamCd.v());			
		}

		@Override
		public void deleteBelongScheduleTeam(String WKPGRPID, ScheduleTeamCd scheduleTeamCd) {
			String companyID = AppContexts.user().companyId();
			belongScheduleTeamRepository.delete(companyID, WKPGRPID, scheduleTeamCd.v());			
		}		
	}
}
