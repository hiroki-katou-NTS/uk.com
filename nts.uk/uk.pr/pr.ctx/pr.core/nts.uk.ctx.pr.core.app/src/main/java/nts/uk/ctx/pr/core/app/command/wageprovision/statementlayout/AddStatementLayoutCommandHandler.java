package nts.uk.ctx.pr.core.app.command.wageprovision.statementlayout;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.service.StatementLayoutService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class AddStatementLayoutCommandHandler extends CommandHandler<StatementLayoutCommand> {

    @Inject
    private StatementLayoutService statementLayoutService;

    @Override
    protected void handle(CommandHandlerContext<StatementLayoutCommand> commandHandlerContext) {
        StatementLayoutCommand command = commandHandlerContext.getCommand();
        statementLayoutService.addStatementLayout(command.getIsClone(), command.getHistIdNew(), command.getHistIdClone(), command.getLayoutPatternClone(),
                command.getStatementCode(), command.getStatementName(), command.getStartDate(), command.getLayoutPattern(), command.getStatementCodeClone());
    }
}
