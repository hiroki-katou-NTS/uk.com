package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.history.edit;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.SelectionHistory;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.SelectionHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author tuannv
 *
 */
@Stateless
public class EditHistoryCommandHandler extends CommandHandler<EditHistoryCommand> {

	@Inject
	private SelectionHistoryRepository historySelecitonRepo;

	@Override
	protected void handle(CommandHandlerContext<EditHistoryCommand> context) {
		EditHistoryCommand command = context.getCommand();

		String companyId = AppContexts.user().companyId();

		SelectionHistory history = historySelecitonRepo.get(command.getSelectionItemId(), companyId).get();

		DateHistoryItem updateDateHistItem = history.getDateHistoryItems().stream()
				.filter(x -> x.identifier().equals(command.getSelectingHistId())).findFirst().get();

		history.changeSpan(updateDateHistItem, new DatePeriod(command.getNewStartDate(), GeneralDate.max()));

		this.historySelecitonRepo.update(history, updateDateHistItem);
	}

	private boolean isGroupManager() {
		String groupManageRoleId = AppContexts.user().roles().forGroupCompaniesAdmin();
		if (groupManageRoleId != null) {
			return true;
		}
		return false;
	}

}
