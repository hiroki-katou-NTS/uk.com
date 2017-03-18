package nts.uk.ctx.pr.formula.app.command.formulahistory;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.formula.dom.formula.FormulaHistory;
import nts.uk.ctx.pr.formula.dom.formula.FormulaManual;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaHistoryDomainService;
import nts.uk.shr.com.context.AppContexts;

public class UpdateFormulaHistoryCommandHandler extends CommandHandler<UpdateFormulaHistoryCommand> {

	@Inject
	FormulaHistoryDomainService formulaHistoryDomainService;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateFormulaHistoryCommand> context) {

		UpdateFormulaHistoryCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();

		FormulaHistory formulaHistory = new FormulaHistory(companyCode, new FormulaCode(command.getFormulaCode()),
				command.getHistoryId());
		
		FormulaManual formulaManual = new FormulaManual(companyCode, new FormulaCode(command.getFormulaCode()),
				command.getHistoryId());
		
		formulaHistoryDomainService.update(command.getConditionAtr(),formulaHistory, formulaManual);
	}

}
