package nts.uk.ctx.pr.formula.app.command.formulaeasyhead;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.formula.app.command.formulamaster.AddFormulaMasterCommand;
import nts.uk.ctx.pr.formula.dom.enums.ConditionAtr;
import nts.uk.ctx.pr.formula.dom.enums.ReferenceMasterNo;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyHead;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.HistoryId;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyHeaderRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddFormulaEasyHeadCommandHandler extends CommandHandler<AddFormulaEasyHeadCommand>{
	
	@Inject
	private FormulaEasyHeaderRepository formulaEasyHeaderRepository;

	@Override
	protected void handle(CommandHandlerContext<AddFormulaEasyHeadCommand> context) {

		AddFormulaEasyHeadCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		FormulaEasyHead formulaEasyHead = new FormulaEasyHead(
				companyCode,
				new FormulaCode(command.getFormulaCode()),
				new HistoryId(command.getHistoryId()),
				EnumAdaptor.valueOf(command.getConditionAtr(), ConditionAtr.class),
				EnumAdaptor.valueOf(command.getReferenceMasterNo(), ReferenceMasterNo.class));
		
		formulaEasyHeaderRepository.add(formulaEasyHead);
	}
}
