package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.RegisterMode;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisClassification;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisClassificationRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisClassificationService;

@Stateless
@Transactional
public class RegisterStateCorrelationHisClassificationCommandHandler extends CommandHandler<StateCorrelationHisClassificationCommand> {
    
    @Inject
    private StateCorrelationHisClassificationService stateCorrelationHisClassificationService;
    
    @Override
    protected void handle(CommandHandlerContext<StateCorrelationHisClassificationCommand> context) {
        StateCorrelationHisClassificationCommand command = context.getCommand();
        YearMonth start = new YearMonth(command.getStartYearMonth());
        YearMonth end = new YearMonth(command.getEndYearMonth());
        if(command.getMode() == RegisterMode.NEW.value) {
           // stateCorrelationHisClassificationService.addHistoryClassification(start, end, );
        } else {

        }
    }
}
