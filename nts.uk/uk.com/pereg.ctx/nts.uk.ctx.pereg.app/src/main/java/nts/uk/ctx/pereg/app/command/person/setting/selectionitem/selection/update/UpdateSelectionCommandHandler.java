package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selection.update;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionRepository;

/**
 * 
 * @author tuannv
 *
 */
@Stateless
public class UpdateSelectionCommandHandler extends CommandHandler<UpdateSelectionCommand> {

	@Inject
	private SelectionRepository selectionRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateSelectionCommand> context) {
		UpdateSelectionCommand command = context.getCommand();

		Selection domain = Selection.createFromSelection(command.getSelectionID(), command.getHistId(),
				command.getSelectionCD(), command.getSelectionName(), command.getExternalCD(),
				command.getMemoSelection());

		this.selectionRepo.update(domain);
	}

}
