package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.workplacecounter;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.*;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.WorkplaceCounterLaborCostAndTimeRepo;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting.TimesNumberCounterSelectionRepo;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting.TimesNumberCounterType;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timezonepeople.WorkplaceCounterTimeZonePeopleNumberRepo;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.stream.Collectors;

/**
 * スケジュール職場計情報を登録する
 */
@Transactional
@Stateless
public class RegisterWorkplaceCounterCommandHandler extends CommandHandler<RegisterWorkplaceCounterCommand> {

	@Inject
	private WorkplaceCounterRepo repository;

	@Inject
	private TimesNumberCounterSelectionRepo numberCounterSelectionRepo;

	@Inject
	private WorkplaceCounterTimeZonePeopleNumberRepo timeZonePeopleNumberRepo;

	@Inject
	private WorkplaceCounterLaborCostAndTimeRepo laborCostAndTimeRepo;

	@Override
	protected void handle(CommandHandlerContext<RegisterWorkplaceCounterCommand> context) {
		RegisterWorkplaceCounterCommand command = context.getCommand();
		WorkplaceCounter workplaceCounter = new WorkplaceCounter(
			command.getWorkplaceCategory().stream().map(x -> EnumAdaptor.valueOf(x, WorkplaceCounterCategory.class)).collect(Collectors.toList()));
		RequireImpl require = new RequireImpl(repository,numberCounterSelectionRepo,timeZonePeopleNumberRepo,laborCostAndTimeRepo);

		//1 : 登録する(Require, 職場計) : 職場計の登録結果
		WorkplaceCounterRegisterResult atomTask = WorkplaceCounterRegister.register(require,workplaceCounter);
		transaction.execute(() -> {
			atomTask.getAtomTask().run();
		});
	}

	@AllArgsConstructor
	private static class RequireImpl implements WorkplaceCounterRegister.Require {

		private WorkplaceCounterRepo workplaceCounterRepo;

		private TimesNumberCounterSelectionRepo numberCounterSelectionRepo;

		private WorkplaceCounterTimeZonePeopleNumberRepo timeZonePeopleNumberRepo;

		private WorkplaceCounterLaborCostAndTimeRepo laborCostAndTimeRepo;

		@Override
		public boolean existsLaborCostAndTime() {
			return laborCostAndTimeRepo.exists(AppContexts.user().companyId());
		}

		@Override
		public boolean existsTimeZonePeople() {
			return timeZonePeopleNumberRepo.exists(AppContexts.user().companyId());
		}

		@Override
		public boolean existsTimesCouting(TimesNumberCounterType type) {
			return numberCounterSelectionRepo.exists(AppContexts.user().companyId(),type);
		}

		@Override
		public boolean existsWorkplaceCounter() {
			return workplaceCounterRepo.exists(AppContexts.user().companyId());
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
