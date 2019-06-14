package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.RegisterMode;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisClsService;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetMaster;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Stateless
@Transactional
public class RegisterStateCorrelationHisClassificationCommandHandler extends CommandHandler<StateCorrelationHisClassificationCommand> {
    
    @Inject
    private StateCorreHisClsService stateCorreHisClsService;
    
    @Override
    protected void handle(CommandHandlerContext<StateCorrelationHisClassificationCommand> context) {
        StateCorrelationHisClassificationCommand command = context.getCommand();
        YearMonth start = new YearMonth(command.getStartYearMonth());
        YearMonth end = new YearMonth(command.getEndYearMonth());
        List<StateLinkSetMaster> listStateLinkSetMaster = new ArrayList<StateLinkSetMaster>();
        if(command.getMode() == RegisterMode.NEW.value) {
            String hisId = IdentifierUtil.randomUniqueId();
            if(command.getStateLinkSettingMaster() != null) {
                listStateLinkSetMaster = command.getStateLinkSettingMaster().stream().map(i -> {
                    return new StateLinkSetMaster(
                            hisId,
                            i.getMasterCode(),
                            i.getSalaryCode(),
                            i.getBonusCode());
                            }).collect(Collectors.toList());
            }
            stateCorreHisClsService.addHistoryClassification(hisId, start, end, listStateLinkSetMaster);
        } else {
            String hisId = command.getHisId();
            if(command.getStateLinkSettingMaster() != null) {
                listStateLinkSetMaster = command.getStateLinkSettingMaster().stream().map(i -> {
                    return new StateLinkSetMaster(
                            hisId,
                            i.getMasterCode(),
                            i.getSalaryCode(),
                            i.getBonusCode());
                            }).collect(Collectors.toList());
            }
            stateCorreHisClsService.updateHistoryClassification(hisId, listStateLinkSetMaster,start,end);
        }
    }
}
