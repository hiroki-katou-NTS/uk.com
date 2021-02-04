package nts.uk.ctx.at.aggregation.app.command.schedulecounter.workplacecounter;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.WorkplaceCounter;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.WorkplaceCounterCategory;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.WorkplaceCounterRegister;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.WorkplaceCounterRegisterResult;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.WorkplaceCounterRepo;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.laborcostandtime.WorkplaceCounterLaborCostAndTimeRepo;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.timescounting.TimesNumberCounterSelectionRepo;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.timescounting.TimesNumberCounterType;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.timezonepeople.WorkplaceCounterTimeZonePeopleNumberRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * スケジュール職場計情報を登録する
 */
@Transactional
@Stateless
public class RegisterWorkplaceCounterCommandHandler extends CommandHandlerWithResult<RegisterWorkplaceCounterCommand, List<WorkplaceCounterCategory>> {

	@Inject
	private WorkplaceCounterRepo repository;

	@Inject
	private TimesNumberCounterSelectionRepo numberCounterSelectionRepo;

	@Inject
	private WorkplaceCounterTimeZonePeopleNumberRepo timeZonePeopleNumberRepo;

	@Inject
	private WorkplaceCounterLaborCostAndTimeRepo laborCostAndTimeRepo;

	@Override
	protected List<WorkplaceCounterCategory> handle(CommandHandlerContext<RegisterWorkplaceCounterCommand> context) {
		RegisterWorkplaceCounterCommand command = context.getCommand();
		WorkplaceCounter workplaceCounter = WorkplaceCounter.create(
			command.getWorkplaceCategory().stream().map(x -> EnumAdaptor.valueOf(x, WorkplaceCounterCategory.class)).collect(Collectors.toList()));
		RequireImpl require = new RequireImpl(repository,numberCounterSelectionRepo,timeZonePeopleNumberRepo,laborCostAndTimeRepo);

		//1 : 登録する(Require, 職場計) : 職場計の登録結果
		WorkplaceCounterRegisterResult result = WorkplaceCounterRegister.register(require,workplaceCounter);
		transaction.execute(() -> {
			result.getAtomTask().run();
		});

		return result.getNotDetailSettingList();
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
