package nts.uk.ctx.at.shared.app.command.worktime.language;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.worktime.language.WorkTimeLanguage;
import nts.uk.ctx.at.shared.dom.worktime.language.WorkTimeLanguageRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 他言語登録処理
 * 
 * @author sonnh1
 *
 */
@Stateless
public class InsertWorkTimeLanguageCommandHandler extends CommandHandler<InsertWorkTimeLanguageCommand> {

	@Inject
	private WorkTimeLanguageRepository workTimeLanguageRepo;
	
	@Override
	protected void handle(CommandHandlerContext<InsertWorkTimeLanguageCommand> context) {
		String companyId = AppContexts.user().companyId();
		InsertWorkTimeLanguageCommand command = context.getCommand();
		WorkTimeLanguage workTypeLanguage = command.toDomain(command.getWorkTimeCode(), command.getLangId(),
				command.getName(), command.getAbName());

		Optional<WorkTimeLanguage> optWorkTimeLang = workTimeLanguageRepo.findById(companyId, command.getLangId(),
				command.getWorkTimeCode());

		if (optWorkTimeLang.isPresent()) {
			// 他言語の既存の名称を登録する
			workTimeLanguageRepo.update(workTypeLanguage);
		} else {
			// 他言語の名称を登録する
			workTimeLanguageRepo.insert(workTypeLanguage);
		}
	}

}
