package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.personalcounter;

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
import java.util.stream.Collectors;

/**
 * スケジュール個人計情報を登録する
 */
@Transactional
@Stateless
public class RegisterPersonalCounterCommandHandler extends CommandHandler<RegisterPersonalCounterCommand> {
	@Inject
	private PersonalCounterRepo repository;

	@Override
	protected void handle(CommandHandlerContext<RegisterPersonalCounterCommand> context) {
		RegisterPersonalCounterCommand command = context.getCommand();
		PersonalCounter personalCounter = new PersonalCounter(
			command.getPersonalCategory().stream().map(x -> EnumAdaptor.valueOf(x, PersonalCounterCategory.class)).collect(Collectors.toList()));
		RequireImpl require = new RequireImpl(repository);

		//1 : 登録する(Require, 個人計) : 個人計の登録結果
		PersonalCounterRegisterResult atomTask = PersonalCounterRegister.register(require,personalCounter);
		transaction.execute(() -> {
			atomTask.getAtomTask().run();
		});
	}

	@AllArgsConstructor
	private static class RequireImpl implements PersonalCounterRegister.Require {

		private PersonalCounterRepo personalCounterRepo;

		@Override
		public boolean existsTimesCouting(TimesNumberCounterType type) {
			return false;
		}

		@Override
		public boolean existsOnePersonCounter() {
			return false;
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
