package nts.uk.ctx.exio.app.command.exo.category;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.category.ExOutLinkTable;
import nts.uk.ctx.exio.dom.exo.category.ExOutLinkTableRepository;;

@Stateless
@Transactional
public class AddExCndOutputCommandHandler extends CommandHandler<ExCndOutputCommand> {

	@Inject
	private ExOutLinkTableRepository repository;

	@Override
	protected void handle(CommandHandlerContext<ExCndOutputCommand> context) {
		ExCndOutputCommand addCommand = context.getCommand();
		repository.add(new ExOutLinkTable(addCommand.getCategoryId(), addCommand.getMainTable(), addCommand.getForm1(),
				addCommand.getForm2(), addCommand.getConditions(), addCommand.getOutCondItemName1(),
				addCommand.getOutCondItemName2(), addCommand.getOutCondItemName3(), addCommand.getOutCondItemName4(),
				addCommand.getOutCondItemName5(), addCommand.getOutCondItemName6(), addCommand.getOutCondItemName7(),
				addCommand.getOutCondItemName8(), addCommand.getOutCondItemName9(), addCommand.getOutCondItemName10(),
				addCommand.getOutCondAssociation1(), addCommand.getOutCondAssociation2(),
				addCommand.getOutCondAssociation3(), addCommand.getOutCondAssociation4(),
				addCommand.getOutCondAssociation5(), addCommand.getOutCondAssociation6(),
				addCommand.getOutCondAssociation7(), addCommand.getOutCondAssociation8(),
				addCommand.getOutCondAssociation9(), addCommand.getOutCondAssociation10()));
	}
}
