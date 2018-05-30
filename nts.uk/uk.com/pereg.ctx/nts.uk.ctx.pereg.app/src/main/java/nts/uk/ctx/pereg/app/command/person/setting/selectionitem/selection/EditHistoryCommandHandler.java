package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selection;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.PerInfoHistorySelection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.PerInfoHistorySelectionRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author tuannv
 *
 */
@Stateless
public class EditHistoryCommandHandler extends CommandHandler<EditHistoryCommand> {

	@Inject
	private PerInfoHistorySelectionRepository repoHistSel;

	@Override
	protected void handle(CommandHandlerContext<EditHistoryCommand> context) {
		EditHistoryCommand command = context.getCommand();

		GeneralDate newStartDate = command.getNewStartDate();

		String companyId = isGroupManager() ? AppContexts.user().zeroCompanyIdInContract()
				: AppContexts.user().companyId();

		Optional<PerInfoHistorySelection> previousHistory = getPreviousHistory(command.getSelectionItemId(), companyId);

		if (previousHistory.isPresent()) {
			// validate startDate
			validateHistory(previousHistory.get(), newStartDate);
			updatePreviouHistory(previousHistory.get(), newStartDate);
		}

		// get history current
		PerInfoHistorySelection histCur = this.repoHistSel.getHistSelByHistId(command.getSelectingHistId()).get();
		histCur.updateDate(new DatePeriod(newStartDate, GeneralDate.max()));
		this.repoHistSel.update(histCur);
	}

	private boolean isGroupManager() {
		String groupManageRoleId = AppContexts.user().roles().forGroupCompaniesAdmin();
		if (groupManageRoleId != null) {
			return true;
		}
		return false;
	}

	private Optional<PerInfoHistorySelection> getPreviousHistory(String selectionItemId, String companyId) {
		List<PerInfoHistorySelection> historyList = this.repoHistSel.getAllBySelecItemIdAndCompanyId(selectionItemId,
				companyId);
		if (historyList.size() > 1) {
			return Optional.of(historyList.get(1));
		}
		return Optional.empty();
	}

	private void validateHistory(PerInfoHistorySelection previouHistory, GeneralDate newStartDate) {
		if (newStartDate.beforeOrEquals(previouHistory.getPeriod().start())) {
			throw new BusinessException("Msg_127");
		}
	}

	private void updatePreviouHistory(PerInfoHistorySelection previouHistory, GeneralDate newStartDate) {
		GeneralDate previousEndDate = newStartDate.addDays(-1);
		DatePeriod newDatePeriod = new DatePeriod(previouHistory.getPeriod().start(), previousEndDate);
		previouHistory.updateDate(newDatePeriod);
		this.repoHistSel.update(previouHistory);
	}

}
