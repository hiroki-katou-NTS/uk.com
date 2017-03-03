package nts.uk.ctx.pr.formula.app.command.formulahistory;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.formula.dom.formula.FormulaHistory;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaHistoryRepository;
import nts.uk.shr.com.context.AppContexts;;

public class AddFormulaHistoryCommandHandler extends CommandHandler<AddFormulaHistoryCommand>{

	@Inject
	private FormulaHistoryRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<AddFormulaHistoryCommand> context) {
		
		AddFormulaHistoryCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		FormulaHistory formulaHistory = new FormulaHistory(
				companyCode,
				new FormulaCode(command.getFormulaCode()),
				command.getHistoryId(),
				new YearMonth(command.getStartDate()),
				new YearMonth(command.getEndDate()));
		
		repository.add(formulaHistory);		
	}
	
}
