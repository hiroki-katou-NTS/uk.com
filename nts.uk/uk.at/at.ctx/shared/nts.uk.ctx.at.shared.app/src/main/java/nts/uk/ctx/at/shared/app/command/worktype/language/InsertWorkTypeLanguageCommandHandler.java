package nts.uk.ctx.at.shared.app.command.worktype.language;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.worktype.language.WorkTypeLanguage;
import nts.uk.ctx.at.shared.dom.worktype.language.WorkTypeLanguageRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * Insert workTypeLanguage to DB
 * 
 * @author sonnh1
 *
 */
@Stateless
public class InsertWorkTypeLanguageCommandHandler extends CommandHandler<InsertWorkTypeLanguageCommand> {

	@Inject
	private WorkTypeLanguageRepository workTypeLanguageRepo;

	@Override
	protected void handle(CommandHandlerContext<InsertWorkTypeLanguageCommand> context) {
		String companyId = AppContexts.user().companyId();
		InsertWorkTypeLanguageCommand command = context.getCommand();
		WorkTypeLanguage workTypeLanguage = command.toDomain(command.getWorkTypeCode(), command.getLangId(),
				command.getName(), command.getAbName());

		Optional<WorkTypeLanguage> workTypeLang = workTypeLanguageRepo.findById(companyId, command.getWorkTypeCode(),
				command.getLangId());

		if (workTypeLang.isPresent()) {
			workTypeLanguageRepo.update(workTypeLanguage);
		} else {
			workTypeLanguageRepo.add(workTypeLanguage);
		}
	}
}
