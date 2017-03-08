package nts.uk.ctx.pr.formula.app.command.formulaeasyheader;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.formula.dom.enums.ConditionAtr;
import nts.uk.ctx.pr.formula.dom.enums.ReferenceMasterNo;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyHeader;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyHeaderRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author nampt
 *
 */
@Stateless
public class RemoveFormulaEasyHeadCommandHandler extends CommandHandler<RemoveFormulaEasyHeadCommand>{

	@Inject
	private FormulaEasyHeaderRepository formulaEasyHeaderRepository;
	
	@Override
	protected void handle(CommandHandlerContext<RemoveFormulaEasyHeadCommand> context) {
		RemoveFormulaEasyHeadCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		FormulaEasyHeader formulaEasyHead = new FormulaEasyHeader(
				companyCode,
				new FormulaCode(command.getFormulaCode()),
				command.getHistoryId(),
				EnumAdaptor.valueOf(command.getConditionAtr(), ConditionAtr.class),
				EnumAdaptor.valueOf(command.getReferenceMasterNo(), ReferenceMasterNo.class));
		
		formulaEasyHeaderRepository.remove(formulaEasyHead);
	}

}
