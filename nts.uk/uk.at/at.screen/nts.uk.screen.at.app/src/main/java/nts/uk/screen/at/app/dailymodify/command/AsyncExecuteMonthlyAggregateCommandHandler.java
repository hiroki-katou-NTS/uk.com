package nts.uk.screen.at.app.dailymodify.command;

import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemParent;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AsyncExecuteMonthlyAggregateCommandHandler extends AsyncCommandHandler<DPItemParent> {
    @Inject
    DailyModifyRCommandFacade dailyModifyRCommandFacade;

    @Override
    protected void handle(CommandHandlerContext<DPItemParent> command) {
        dailyModifyRCommandFacade.executeMonthlyAggregate(command.getCommand());
    }
}
