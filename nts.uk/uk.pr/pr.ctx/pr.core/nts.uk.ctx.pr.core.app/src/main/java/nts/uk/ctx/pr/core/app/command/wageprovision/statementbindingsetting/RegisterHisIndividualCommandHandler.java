package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.RegisterMode;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisIndiviService;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetIndivi;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class RegisterHisIndividualCommandHandler extends CommandHandler<StateLinkSettingIndividualCommand> {
    
    @Inject
    private StateCorreHisIndiviService stateCorreHisIndiviService;
    
    @Override
    protected void handle(CommandHandlerContext<StateLinkSettingIndividualCommand> context) {
        StateLinkSettingIndividualCommand command = context.getCommand();
        YearMonth start = new YearMonth(command.getStart());
        YearMonth end = new YearMonth(command.getEnd());
        StateLinkSetIndivi stateLinkSetIndivi;
        if(command.getMode() == RegisterMode.NEW.value) {
            String hisId = IdentifierUtil.randomUniqueId();
            stateLinkSetIndivi = new StateLinkSetIndivi(hisId, command.getSalary(), command.getBonus());
            stateCorreHisIndiviService.addHistoryIndividual(hisId, start, end, stateLinkSetIndivi, command.getEmpId());
        } else {
            String hisId = command.getHisId();
            YearMonthHistoryItem history = new YearMonthHistoryItem(hisId,new YearMonthPeriod(start,end));
            stateLinkSetIndivi = new StateLinkSetIndivi(hisId, command.getSalary(), command.getBonus());
            stateCorreHisIndiviService.updateHistoryIndividual(history, stateLinkSetIndivi, command.getEmpId());
        }
    }
}
