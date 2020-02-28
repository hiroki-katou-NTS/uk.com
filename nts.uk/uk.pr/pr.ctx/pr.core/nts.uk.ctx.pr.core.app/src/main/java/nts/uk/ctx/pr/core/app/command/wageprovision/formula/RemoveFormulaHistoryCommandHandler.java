package nts.uk.ctx.pr.core.app.command.wageprovision.formula;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.FormulaService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class RemoveFormulaHistoryCommandHandler extends CommandHandler<FormulaCommand> {

    @Inject
    private FormulaService formulaService;

    @Override
    protected void handle(CommandHandlerContext<FormulaCommand> context) {
        formulaService.removeFormulaHistory(context.getCommand().getFormulaCode(), context.getCommand().getFormulaSettingCommand().getYearMonth().fromCommandToDomain());
    }
}
