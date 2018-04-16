package nts.uk.ctx.at.function.app.command.annualworkschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.annualworkschedule.repostory.ItemOutTblBookRepository;

@Stateless
@Transactional
public class RemoveItemOutTblBookCommandHandler extends CommandHandler<ItemOutTblBookCommand> {
	@Inject
	private ItemOutTblBookRepository repository;

	@Override
	protected void handle(CommandHandlerContext<ItemOutTblBookCommand> context) {
		String cid = context.getCommand().getCid();
		int code = context.getCommand().getCode();
		int sortBy = context.getCommand().getSortBy();
		repository.remove(cid, code, sortBy);
	}
}
