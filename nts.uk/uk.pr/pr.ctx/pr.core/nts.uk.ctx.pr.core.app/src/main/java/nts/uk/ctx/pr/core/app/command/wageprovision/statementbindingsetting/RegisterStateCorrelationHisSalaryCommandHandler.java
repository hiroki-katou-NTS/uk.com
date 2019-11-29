package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.RegisterMode;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisSalaService;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetMaster;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Transactional
public class RegisterStateCorrelationHisSalaryCommandHandler extends CommandHandler<StateCorrelationHisSalaryCommand> {
    
    @Inject
    private StateCorreHisSalaService stateCorreHisSalaService;
    
    @Override
    protected void handle(CommandHandlerContext<StateCorrelationHisSalaryCommand> context) {
        StateCorrelationHisSalaryCommand command = context.getCommand();
        YearMonth start = new YearMonth(command.getStartYearMonth());
        YearMonth end = new YearMonth(command.getEndYearMonth());
        if(command.getMode() == RegisterMode.NEW.value) {
            String hisId = IdentifierUtil.randomUniqueId();
            List<StateLinkSetMaster> listStateLinkSetMaster = command.getStateLinkSettingMaster().stream().map(i -> new StateLinkSetMaster(
                    hisId,
                    i.getMasterCode(),
                    i.getSalaryCode(),
                    i.getBonusCode())).collect(Collectors.toList());
            stateCorreHisSalaService.addHistorySalary(hisId, start, end, listStateLinkSetMaster);
        } else {
            String hisId = command.getHisId();
            List<StateLinkSetMaster> listStateLinkSetMaster = command.getStateLinkSettingMaster().stream().map(i -> new StateLinkSetMaster(
                    hisId,
                    i.getMasterCode(),
                    i.getSalaryCode(),
                    i.getBonusCode())).collect(Collectors.toList());
            stateCorreHisSalaService.updateHistorySalary(hisId, start, end, listStateLinkSetMaster);
        }
    
    }
}
