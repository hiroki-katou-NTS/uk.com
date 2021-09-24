package nts.uk.cnv.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.cnv.dom.categorypriority.CategoryPriorityRepository;

@Stateless
public class RegistCategoryPriorityCommandHandler  extends CommandHandler<RegistCategoryPriorityCommand>{

	@Inject
	CategoryPriorityRepository repository;

	@Override
	protected void handle(CommandHandlerContext<RegistCategoryPriorityCommand> context) {
		RegistCategoryPriorityCommand command = context.getCommand();
		repository.register(command.getCategory());
	}

}
