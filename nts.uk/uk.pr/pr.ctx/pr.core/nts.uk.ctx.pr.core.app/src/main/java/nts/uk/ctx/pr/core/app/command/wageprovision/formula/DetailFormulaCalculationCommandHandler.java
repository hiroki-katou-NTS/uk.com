package nts.uk.ctx.pr.core.app.command.wageprovision.formula;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.detailcalculationformula.DetailFormulaCalculationService;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class DetailFormulaCalculationCommandHandler extends CommandHandlerWithResult<DetailFormulaCommand, String> {

    @Inject
    private DetailFormulaCalculationService detailFormulaCalculationService;

    @Override
    protected String handle(CommandHandlerContext<DetailFormulaCommand> context) {
        return detailFormulaCalculationService.calculateDisplayCalculationFormula(2, context.getCommand().getFormulaContent(), context.getCommand().getReplaceValues());
    }
}