package nts.uk.ctx.pr.formula.app.command.formulaeasycondition;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.formula.dom.enums.FixFormulaAtr;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyCondition;
import nts.uk.ctx.pr.formula.dom.primitive.EasyFormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.Money;
import nts.uk.ctx.pr.formula.dom.primitive.ReferenceMasterCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyConditionRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author nampt
 *
 */

@Stateless
public class RemoveFormulaEasyConditionCommandHandler extends CommandHandler<RemoveFormulaEasyConditionCommand>{
	
	@Inject
	private FormulaEasyConditionRepository repository; 

	@Override
	protected void handle(CommandHandlerContext<RemoveFormulaEasyConditionCommand> context) {
		
		RemoveFormulaEasyConditionCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		FormulaEasyCondition formulaEasyCondition = new FormulaEasyCondition(
				companyCode,
				new FormulaCode(command.getFormulaCode()),
				command.getHistoryId(),
				new EasyFormulaCode(command.getEasyFormulaCode()),
				EnumAdaptor.valueOf(command.getFixFormulaAtr(), FixFormulaAtr.class),
				new Money(command.getFixMoney()),
				new ReferenceMasterCode(command.getReferenceMasterCode()));
		
		repository.remove(formulaEasyCondition);
	}
	
}
