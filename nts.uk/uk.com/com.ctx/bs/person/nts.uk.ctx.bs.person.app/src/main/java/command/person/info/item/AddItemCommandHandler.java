package command.person.info.item;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.info.item.PerInfoItemDefRepositoty;

@Stateless
public class AddItemCommandHandler extends CommandHandler<AddItemCommand> {
	
	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;
	
	@Override
	protected void handle(CommandHandlerContext<AddItemCommand> context) {
		AddItemCommand addItemCommand = context.getCommand();
		
	}

	

}
