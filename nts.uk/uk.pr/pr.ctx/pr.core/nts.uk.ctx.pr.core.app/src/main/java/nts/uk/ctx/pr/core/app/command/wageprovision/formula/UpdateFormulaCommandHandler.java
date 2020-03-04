package nts.uk.ctx.pr.core.app.command.wageprovision.formula;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class UpdateFormulaCommandHandler extends CommandHandler<FormulaCommand> {

    @Inject
    private FormulaService formulaService;

    @Override
    protected void handle(CommandHandlerContext<FormulaCommand> context) {
        FormulaCommand command = context.getCommand();
        formulaService.updateFormula(command.fromCommandToDomain());
    }
}
