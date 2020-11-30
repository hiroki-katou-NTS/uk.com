package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.personalcounter;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.*;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting.TimesNumberCounterSelectionRepo;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting.TimesNumberCounterType;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * スケジュール個人計情報を登録する
 */
@Transactional
@Stateless
public class RegisterPersonalCounterCommandHandler extends CommandHandlerWithResult<RegisterPersonalCounterCommand, List<PersonalCounterCategory>> {

	@Inject
	private PersonalCounterRepo repository;

	@Inject
	private TimesNumberCounterSelectionRepo numberCounterSelectionRepo;

	@Override
	protected List<PersonalCounterCategory> handle(CommandHandlerContext<RegisterPersonalCounterCommand> context) {
		RegisterPersonalCounterCommand command = context.getCommand();
		PersonalCounter personalCounter = PersonalCounter.create(
			command.getPersonalCategory().stream().map(x -> EnumAdaptor.valueOf(x, PersonalCounterCategory.class)).collect(Collectors.toList()));
		RequireImpl require = new RequireImpl(repository,numberCounterSelectionRepo);

		//1 : 登録する(Require, 個人計) : 個人計の登録結果
		PersonalCounterRegisterResult result = PersonalCounterRegister.register(require,personalCounter);
		transaction.execute(() -> {
            result.getAtomTask().run();
		});

		return result.getNotDetailSettingList();
	}

	@AllArgsConstructor
	private static class RequireImpl implements PersonalCounterRegister.Require {

		private PersonalCounterRepo personalCounterRepo;

		private TimesNumberCounterSelectionRepo numberCounterSelectionRepo;

		@Override
		public boolean existsTimesCouting(TimesNumberCounterType type) {
			return numberCounterSelectionRepo.exists(AppContexts.user().companyId(),type);
		}

		@Override
		public boolean existsOnePersonCounter() {
			return personalCounterRepo.exists(AppContexts.user().companyId());
		}

		@Override
		public void updateOnePersonCounter(PersonalCounter onePersonCounter) {
			personalCounterRepo.update(AppContexts.user().companyId(),onePersonCounter);
		}

		@Override
		public void insertOnePersonCounter(PersonalCounter onePersonCounter) {
			personalCounterRepo.insert(AppContexts.user().companyId(),onePersonCounter);
		}
	}
}
