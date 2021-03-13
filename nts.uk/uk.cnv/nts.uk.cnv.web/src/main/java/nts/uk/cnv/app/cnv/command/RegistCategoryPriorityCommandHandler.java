package nts.uk.cnv.app.cnv.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.cnv.dom.cnv.categorypriority.CategoryPriorityRepository;

@Stateless
public class RegistCategoryPriorityCommandHandler  extends CommandHandler<RegistCategoryPriorityCommand>{

	@Inject
	CategoryPriorityRepository repository;

	@Override
	protected void handle(CommandHandlerContext<RegistCategoryPriorityCommand> context) {
		RegistCategoryPriorityCommand command = context.getCommand();

		repository.deleteAll();

		int seq = 0;
		for(String category : command.getCategories()) {
			repository.register(seq, category);
			seq++;
		}
	}

}
