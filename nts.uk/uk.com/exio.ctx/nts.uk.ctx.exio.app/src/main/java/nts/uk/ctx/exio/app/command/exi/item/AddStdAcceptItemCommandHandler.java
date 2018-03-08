package nts.uk.ctx.exio.app.command.exi.item;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddStdAcceptItemCommandHandler extends CommandHandler<StdAcceptItemCommand> {

	@Inject
	private StdAcceptItemRepository repository;

	@Override
	protected void handle(CommandHandlerContext<StdAcceptItemCommand> context) {
		StdAcceptItemCommand addCommand = context.getCommand();
		String companyId = AppContexts.user().companyId();
		repository.add(addCommand.toDomain(companyId));
	}
}
