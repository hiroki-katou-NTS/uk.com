package nts.uk.ctx.pr.core.app.command.wageprovision.formula;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaService;

@Stateless
@Transactional
public class AddFormulaCommandHandler extends CommandHandler<FormulaCommand> {

    @Inject
    private FormulaService formulaService;

    @Override
    protected void handle(CommandHandlerContext<FormulaCommand> context) {
        FormulaCommand command = context.getCommand();
        formulaService.addFormula(command.fromCommandToDomain(), command.getFormulaSettingCommand().getBasicFormulaSettingCommand().fromCommandToDomain(), command.getFormulaSettingCommand().getYearMonth().fromCommandToDomain());
    }
}
