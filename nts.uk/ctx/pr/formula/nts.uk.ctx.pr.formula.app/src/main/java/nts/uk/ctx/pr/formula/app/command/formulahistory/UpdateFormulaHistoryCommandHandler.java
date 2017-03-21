package nts.uk.ctx.pr.formula.app.command.formulahistory;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.formula.dom.formula.FormulaHistory;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaHistoryRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author nampt K screen - update history activity 35
 *
 */
@Stateless
public class UpdateFormulaHistoryCommandHandler extends CommandHandler<UpdateFormulaHistoryCommand> {

	@Inject
	private FormulaHistoryRepository formulaHistoryrepository;

	/**
	 * @ CCD = login company code @ FORMULA CD = [K _ LBL _ 002]
	 *  History ID of the history selected with @HIST_ID = [A_LST_001]
	 */
	@Override
	protected void handle(CommandHandlerContext<UpdateFormulaHistoryCommand> context) {
		UpdateFormulaHistoryCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();

		FormulaHistory formulaHistoryUpdate = new FormulaHistory(companyCode, new FormulaCode(command.getFormulaCode()),
				command.getHistoryId());
		this.formulaHistoryrepository.update(formulaHistoryUpdate);
		
		if (this.formulaHistoryrepository.isNewestHistory(companyCode, new FormulaCode(command.getFormulaCode()),
				new YearMonth(command.getEndDate())) == true) {

			// select last history
			Optional<FormulaHistory> lastFormulaHistory = this.formulaHistoryrepository.findLastHistory(companyCode,
					new FormulaCode(command.getFormulaCode()));
			// update history by historyId of last history, startDate =
			// startDate input
			FormulaHistory lastFormulaHistoryUpdate = new FormulaHistory(companyCode, new FormulaCode(command.getFormulaCode()),
					lastFormulaHistory.get().getHistoryId(), new YearMonth(command.getEndDate()),
					new YearMonth(lastFormulaHistory.get().getEndDate().v()));

			// select previous history with startDate
			Optional<FormulaHistory> previousFormulaHistory = this.formulaHistoryrepository
					.findPreviousHistory(companyCode, new FormulaCode(command.getFormulaCode()), new YearMonth(command.getEndDate()));
			// update previous history with endDate = startDate of last History
			FormulaHistory previousFormulaHistoryUpdate = new FormulaHistory(companyCode, new FormulaCode(command.getFormulaCode()),
					previousFormulaHistory.get().getHistoryId(), new YearMonth(previousFormulaHistory.get().getStartDate().v()),
					new YearMonth(lastFormulaHistory.get().getStartDate().v()));

			this.formulaHistoryrepository.update(lastFormulaHistoryUpdate);
			this.formulaHistoryrepository.update(previousFormulaHistoryUpdate);
		}
	}

}
