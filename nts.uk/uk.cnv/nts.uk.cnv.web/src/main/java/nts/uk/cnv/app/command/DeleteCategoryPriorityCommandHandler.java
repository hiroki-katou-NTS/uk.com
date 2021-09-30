package nts.uk.cnv.app.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.cnv.dom.categorypriority.CategoryPriorityRepository;
import nts.uk.cnv.dom.conversiontable.ConversionCategoryTableRepository;

@Stateless
public class DeleteCategoryPriorityCommandHandler  extends CommandHandler<DeleteCategoryPriorityCommand>{

	@Inject
	CategoryPriorityRepository repository;

	@Inject
	ConversionCategoryTableRepository catetoryTableRepo;

	@Override
	protected void handle(CommandHandlerContext<DeleteCategoryPriorityCommand> context) {
		DeleteCategoryPriorityCommand command = context.getCommand();
		repository.delete(command.getCategory());
		catetoryTableRepo.delete(command.getCategory());
	}

}
