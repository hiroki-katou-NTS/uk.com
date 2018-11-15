package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.*;

@Stateless
@Transactional
public class RegisterHisIndividualCommandHandler extends CommandHandler<StateLinkSettingIndividualCommand> {
    
    @Inject
    private StateCorrelationHisIndividualService stateCorrelationHisIndividualService;
    
    @Override
    protected void handle(CommandHandlerContext<StateLinkSettingIndividualCommand> context) {
        StateLinkSettingIndividualCommand command = context.getCommand();
        YearMonth start = new YearMonth(command.getStart());
        YearMonth end = new YearMonth(command.getEnd());
        StateLinkSettingIndividual stateLinkSettingIndividual;
        if(command.getMode() == RegisterMode.NEW.value) {
            String hisId = IdentifierUtil.randomUniqueId();
            stateLinkSettingIndividual = new StateLinkSettingIndividual(
                    hisId,
                    command.getSalary() != null ? new StatementCode(command.getSalary()) : null,
                    command.getBonus() != null ? new StatementCode(command.getBonus()) : null
            );
            stateCorrelationHisIndividualService.addHistoryIndividual(hisId, start, end, stateLinkSettingIndividual, command.getEmpId());
        } else {
            String hisId = command.getHisId();
            stateLinkSettingIndividual = new StateLinkSettingIndividual(
                    hisId,
                    command.getSalary() != null ? new StatementCode(command.getSalary()) : null,
                    command.getBonus() != null ? new StatementCode(command.getBonus()) : null
            );
            stateCorrelationHisIndividualService.updateHistoryIndividual(stateLinkSettingIndividual);
        }
    }
}
