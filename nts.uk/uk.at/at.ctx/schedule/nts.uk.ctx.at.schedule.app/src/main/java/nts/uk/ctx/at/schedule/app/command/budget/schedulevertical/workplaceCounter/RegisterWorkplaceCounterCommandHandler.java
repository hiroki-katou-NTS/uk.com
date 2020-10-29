package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.workplaceCounter;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.*;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting.TimesNumberCounterType;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * スケジュール職場計情報を登録する
 */
@Transactional
@Stateless
public class RegisterWorkplaceCounterCommandHandler extends CommandHandler<List<RegisterWorkplaceCounterCommand>> {
	@Inject
	private WorkplaceCounterRepo repository;

	@Override
	protected void handle(CommandHandlerContext<List<RegisterWorkplaceCounterCommand>> context) {
		List<RegisterWorkplaceCounterCommand> commands = context.getCommand();
		WorkplaceCounter workplaceCounter = new WorkplaceCounter(
			commands.stream().map(x -> EnumAdaptor.valueOf(x.getWorkplaceCategory(), WorkplaceCounterCategory.class)).collect(Collectors.toList()));
		RequireImpl require = new RequireImpl(repository);
		WorkplaceCounterRegisterResult atomTask = WorkplaceCounterRegister.register(require,workplaceCounter);
		transaction.execute(() -> {
			atomTask.getAtomTask().run();
		});
	}

	@AllArgsConstructor
	private static class RequireImpl implements WorkplaceCounterRegister.Require {

		private WorkplaceCounterRepo workplaceCounterRepo;

		@Override
		public boolean existsLaborCostAndTime() {
			return false;
		}

		@Override
		public boolean existsTimeZonePeople() {
			return false;
		}

		@Override
		public boolean existsTimesCouting(TimesNumberCounterType type) {
			return false;
		}

		@Override
		public boolean existsWorkplaceCounter() {
			return false;
		}

		@Override
		public void updateWorkplaceCounter(WorkplaceCounter workplaceCounter) {
			workplaceCounterRepo.update(AppContexts.user().companyId(),workplaceCounter);
		}

		@Override
		public void insertWorkplaceCounter(WorkplaceCounter workplaceCounter) {
			workplaceCounterRepo.insert(AppContexts.user().companyId(),workplaceCounter);
		}
	}
}
