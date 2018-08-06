package nts.uk.ctx.exio.app.command.exo.category;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.category.ExOutLinkTable;
import nts.uk.ctx.exio.dom.exo.category.ExOutLinkTableRepository;

@Stateless
@Transactional
public class UpdateExCndOutputCommandHandler extends CommandHandler<ExCndOutputCommand> {

	@Inject
	private ExOutLinkTableRepository repository;

	@Override
	protected void handle(CommandHandlerContext<ExCndOutputCommand> context) {
		ExCndOutputCommand updateCommand = context.getCommand();
		repository.update(new ExOutLinkTable(updateCommand.getCategoryId(), updateCommand.getMainTable(),
				updateCommand.getForm1(), updateCommand.getForm2(), updateCommand.getConditions(),
				updateCommand.getOutCondItemName1(), updateCommand.getOutCondItemName2(),
				updateCommand.getOutCondItemName3(), updateCommand.getOutCondItemName4(),
				updateCommand.getOutCondItemName5(), updateCommand.getOutCondItemName6(),
				updateCommand.getOutCondItemName7(), updateCommand.getOutCondItemName8(),
				updateCommand.getOutCondItemName9(), updateCommand.getOutCondItemName10(),
				updateCommand.getOutCondAssociation1(), updateCommand.getOutCondAssociation2(),
				updateCommand.getOutCondAssociation3(), updateCommand.getOutCondAssociation4(),
				updateCommand.getOutCondAssociation5(), updateCommand.getOutCondAssociation6(),
				updateCommand.getOutCondAssociation7(), updateCommand.getOutCondAssociation8(),
				updateCommand.getOutCondAssociation9(), updateCommand.getOutCondAssociation10()));
	}
}
