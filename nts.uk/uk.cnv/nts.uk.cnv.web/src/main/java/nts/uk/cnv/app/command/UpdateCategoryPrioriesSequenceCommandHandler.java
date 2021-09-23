package nts.uk.cnv.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.cnv.dom.categorypriority.CategoryPriorityRepository;

@Stateless
public class UpdateCategoryPrioriesSequenceCommandHandler extends CommandHandler<UpdateCategoryPrioriesSequenceCommand>{

	@Inject
	CategoryPriorityRepository repository;

	@Override
	protected void handle(CommandHandlerContext<UpdateCategoryPrioriesSequenceCommand> context) {
		UpdateCategoryPrioriesSequenceCommand command = context.getCommand();
		repository.update(command.getCategories());
	}
}
