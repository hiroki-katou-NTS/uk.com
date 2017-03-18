package nts.uk.ctx.pr.formula.app.command.formulamanual;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.formula.dom.enums.ReferenceMonthAtr;
import nts.uk.ctx.pr.formula.dom.enums.RoundDigit;
import nts.uk.ctx.pr.formula.dom.enums.RoundMethod;
import nts.uk.ctx.pr.formula.dom.formula.FormulaManual;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaContent;
import nts.uk.ctx.pr.formula.dom.repository.FormulaManualRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author nampt
 *
 */
@Stateless
public class RemoveFormulaManualCommandHandler extends CommandHandler<RemoveFormulaManualCommand>{

	@Inject
	private FormulaManualRepository repository;
	
	/**
	 * @ CCD = login company code
	 * @ FORMULA CD = [K _ LBL _ 002]
	 * History ID of the history selected with @HIST_ID = [A_LST_001]
	 * @param formulaManual
	 */
	@Override
	protected void handle(CommandHandlerContext<RemoveFormulaManualCommand> context) {
//		RemoveFormulaManualCommand command = context.getCommand();
//		String companyCode = AppContexts.user().companyCode();
//
//		FormulaManual formulaManual = new FormulaManual(companyCode, new FormulaCode(command.getFormulaCode()),
//				command.getHistoryId(), new FormulaContent(command.getFormulaContent()),
//				EnumAdaptor.valueOf(command.getReferenceMonthAtr().intValue(), ReferenceMonthAtr.class),
//				EnumAdaptor.valueOf(command.getRoundAtr().intValue(), RoundMethod.class),
//				EnumAdaptor.valueOf(command.getRoundDigit().intValue(), RoundDigit.class));
//
//		repository.remove(formulaManual);
	}

}
