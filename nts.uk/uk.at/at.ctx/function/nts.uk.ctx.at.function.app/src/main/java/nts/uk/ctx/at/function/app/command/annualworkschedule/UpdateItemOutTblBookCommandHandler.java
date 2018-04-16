package nts.uk.ctx.at.function.app.command.annualworkschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.annualworkschedule.repostory.ItemOutTblBookRepository;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemOutTblBook;

@Stateless
@Transactional
public class UpdateItemOutTblBookCommandHandler extends CommandHandler<ItemOutTblBookCommand> {
	@Inject
	private ItemOutTblBookRepository repository;

	@Override
	protected void handle(CommandHandlerContext<ItemOutTblBookCommand> context) {
		ItemOutTblBookCommand updateCommand = context.getCommand();
		repository.update(new ItemOutTblBook(updateCommand.getCid(), updateCommand.getCode(), updateCommand.getSortBy(), updateCommand.getUseClass(), updateCommand.getValOutFormat(), updateCommand.getHeadingName()));
	}
}
