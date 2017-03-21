package nts.uk.ctx.pr.formula.app.command.formulahistory;

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
 * @author nampt
 * K screen - update history
 * activity 35
 *
 */
@Stateless
public class UpdateFormulaHistoryCommandHandler extends CommandHandler<UpdateFormulaHistoryCommand>{


	@Inject
	private FormulaHistoryRepository formulaHistoryrepository;
	
	/**
	 * @ CCD = login company code
	 * @ FORMULA CD = [K _ LBL _ 002]
	 * History ID of the history selected with @HIST_ID = [A_LST_001]
	 */
	@Override
	protected void handle(CommandHandlerContext<UpdateFormulaHistoryCommand> context) {
		UpdateFormulaHistoryCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		
	}

}
