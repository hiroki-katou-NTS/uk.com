package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.history.remove;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.domainservice.SelectionHistoryService;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tuannv
 *
 */
@Stateless
@Transactional
public class RemoveHistoryCommandHandler extends CommandHandler<RemoveHistoryCommand> {

	@Inject
	private SelectionHistoryService selectionHistoryService;

	@Override
	protected void handle(CommandHandlerContext<RemoveHistoryCommand> context) {
		RemoveHistoryCommand command = context.getCommand();

		String selectionItemId = command.getSelectionItemId();
		String historyId = command.getHistId();

		String companyId = AppContexts.user().companyId();

		selectionHistoryService.deleteSelectionHistory(selectionItemId, companyId, historyId);

	}

}
