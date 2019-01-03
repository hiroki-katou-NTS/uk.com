package nts.uk.ctx.pr.core.app.command.wageprovision.formula;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.detailcalculationformula.DetailFormulaCalculationService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class DetailFormulaCalculationCommandHandler extends CommandHandlerWithResult<DetailFormulaCommand, String> {

    private static final String
            OPEN_CURLY_BRACKET = "{", CLOSE_CURLY_BRACKET = "}";

    @Inject
    private DetailFormulaCalculationService detailFormulaCalculationService;

    @Override
    protected String handle(CommandHandlerContext<DetailFormulaCommand> context) {
        Map<String, String> replaceValues = context.getCommand().getReplaceValues().stream().collect(Collectors.toMap(item -> item.formulaItem, item -> item.trialCalculationValue));
        return detailFormulaCalculationService.calculateDisplayCalculationFormula(2, context.getCommand().getFormulaContent(), replaceValues, context.getCommand().getRoundingMethod(), context.getCommand().getRoundingPosition());
    }
}