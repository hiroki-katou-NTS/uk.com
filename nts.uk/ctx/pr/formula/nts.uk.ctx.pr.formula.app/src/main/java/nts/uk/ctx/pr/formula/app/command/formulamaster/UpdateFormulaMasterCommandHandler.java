package nts.uk.ctx.pr.formula.app.command.formulamaster;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.formula.dom.enums.DifficultyAtr;
import nts.uk.ctx.pr.formula.dom.formula.FormulaMaster;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaName;
import nts.uk.ctx.pr.formula.dom.repository.FormulaMasterRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author nampt
 *
 */
@Stateless
public class UpdateFormulaMasterCommandHandler extends CommandHandler<UpdateFormulaMasterCommand>{
	
	@Inject
	private FormulaMasterRepository formulaMasterRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateFormulaMasterCommand> context) {
		UpdateFormulaMasterCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		FormulaMaster formulaMaster = new FormulaMaster(companyCode, 
				new FormulaCode(command.getFormulaCode()),
				EnumAdaptor.valueOf(command.getDifficultyAtr().intValue(), DifficultyAtr.class),
				new FormulaName(command.getFormulaName()));
		
		formulaMasterRepository.update(formulaMaster);
	}
	
}
