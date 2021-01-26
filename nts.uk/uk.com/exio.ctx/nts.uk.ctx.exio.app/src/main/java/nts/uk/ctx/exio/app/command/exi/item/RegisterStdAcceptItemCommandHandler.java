package nts.uk.ctx.exio.app.command.exi.item;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItemService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class RegisterStdAcceptItemCommandHandler extends CommandHandler<Cmf001DCommand> {

	@Inject
	private StdAcceptItemService itemService;

	@Override
	protected void handle(CommandHandlerContext<Cmf001DCommand> context) {
		Cmf001DCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		this.itemService.register(
				command.getListItem().stream().map(item -> item.toDomain(companyId)).collect(Collectors.toList()),
				StdAcceptCondSet.createFromMemento(companyId, command.getConditionSetting()));
	}
}
