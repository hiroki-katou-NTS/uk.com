package nts.uk.ctx.pr.formula.app.command.formulaeasystandarditem;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyStandardItem;
import nts.uk.ctx.pr.formula.dom.primitive.EasyFormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.ReferenceItemCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyStandardItemRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author nampt
 *
 */
@Stateless
public class RemoveFormulaEasyStandardItemCommandHandler extends CommandHandler<RemoveFormulaEasyStandardItemCommand>{

	@Inject
	private FormulaEasyStandardItemRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<RemoveFormulaEasyStandardItemCommand> context) {
		
		RemoveFormulaEasyStandardItemCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		FormulaEasyStandardItem formulaEasyStandardItem = new FormulaEasyStandardItem(
				companyCode,
				new FormulaCode(command.getFormulaCode()),
				command.getHistoryId(),
				new EasyFormulaCode(command.getEasyFormulaCode()),
				new ReferenceItemCode(command.getReferenceItemCode()));
		
		repository.remove(companyCode, formulaEasyStandardItem.getFormulaCode(), formulaEasyStandardItem.getHistoryId());
	}

}
