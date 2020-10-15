package nts.uk.ctx.at.schedule.app.command.scheduleteam;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeamRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamCd;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamName;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamRemarks;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice.SwapEmpOnScheduleTeamService;
import nts.uk.shr.com.context.AppContexts;

/**
 * <<Command>> チームを更新する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.社員情報.スケジュールチーム.App.チームを更新する
 * @author quytb
 *
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class UpdateScheduleTeamCommandHandler extends CommandHandler<ScheduleTeamSaveCommand> {
	@Inject
	private ScheduleTeamRepository scheduleTeamRepository;
	
	@Inject
	private BelongScheduleTeamRepository belongScheduleTeamRepository;

	@Override
	protected void handle(CommandHandlerContext<ScheduleTeamSaveCommand> context) {
		ScheduleTeamSaveCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		/** 1: get(職場グループID、コード) */
		Optional<ScheduleTeam> optionalSt = scheduleTeamRepository.getScheduleTeam(companyId,
				command.getWorkplaceGroupId(), command.getCode());
		
		if (!optionalSt.isPresent()) {
			return;
		}
		
		/** 2: set(名称、備考) */		
		ScheduleTeam scheduleTeam = new ScheduleTeam(command.getWorkplaceGroupId(),
				new ScheduleTeamCd(command.getCode()),
				new ScheduleTeamName(command.getName()),
				Optional.of(new ScheduleTeamRemarks(command.getNote())));
		
		/** 3: 入れ替える(Require, スケジュールチーム, List<社員ID>)*/		
		SwapEmpOnScheduleTeamImpl scheduleTeamImpl = new SwapEmpOnScheduleTeamImpl(belongScheduleTeamRepository);
		AtomTask update = SwapEmpOnScheduleTeamService.replace(scheduleTeamImpl, scheduleTeam, command.getEmployeeIds());
		transaction.execute(() -> {
			update.run();
		});
		
		/** 4.1: persist() */
		scheduleTeamRepository.update(scheduleTeam);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@AllArgsConstructor
	private static class SwapEmpOnScheduleTeamImpl implements SwapEmpOnScheduleTeamService.Require {
		@Inject
		private BelongScheduleTeamRepository belongScheduleTeamRepository;

		@Override
		public void deleteSpecifyTeamAndScheduleTeam(String WKPGRPID, String scheduleTeamCd) {
			String companyID = AppContexts.user().companyId();
			belongScheduleTeamRepository.delete(companyID, WKPGRPID, scheduleTeamCd);
		}

		@Override
		public boolean empBelongTeam(String empID) {
			String companyID = AppContexts.user().companyId();
			return belongScheduleTeamRepository.exists(companyID, empID);
		}

		@Override
		public void delete(String empID) {
			String companyID = AppContexts.user().companyId();
			belongScheduleTeamRepository.delete(companyID, empID);
		}

		@Override
		public void insert(BelongScheduleTeam belongScheduleTeam) {
			belongScheduleTeamRepository.insert(belongScheduleTeam);
		}
	}
}
