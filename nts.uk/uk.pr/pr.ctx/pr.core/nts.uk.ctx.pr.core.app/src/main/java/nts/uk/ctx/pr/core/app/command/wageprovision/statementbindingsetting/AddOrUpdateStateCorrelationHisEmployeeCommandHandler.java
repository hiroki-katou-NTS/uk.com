package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.MasterCode;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisEmService;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetMaster;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.arc.time.YearMonth;

@Stateless
@Transactional
public class AddOrUpdateStateCorrelationHisEmployeeCommandHandler extends CommandHandler<StateCorrelationHisEmployeeContainerCommand> {

    @Inject
    private StateCorreHisEmService stateCorreHisEmService;

    @Override
    protected void handle(CommandHandlerContext<StateCorrelationHisEmployeeContainerCommand> context) {
        String cid = AppContexts.user().companyId();
        List<StateLinkSettingMasterCommand> listStateLinkSettingMasterCommand = context.getCommand().getListStateLinkSettingMasterCommand();
        //convert to domain
        List<StateLinkSetMaster> stateLinkSetMaster = new ArrayList<StateLinkSetMaster>();
        if(listStateLinkSettingMasterCommand.size() > 0){
            stateLinkSetMaster = listStateLinkSettingMasterCommand.stream().map(item -> new StateLinkSetMaster(item.getHisId(),item.getMasterCode(),
                    item.getSalaryCode(),
                    item.getBonusCode())).collect(Collectors.toList());
        }
        StateCorrelationHisEmployeeCommand stateCorrelationHisEmployeeCommand = context.getCommand().getStateCorrelationHisEmployeeCommand();
        int mode = context.getCommand().getMode();
        String hisID = stateCorrelationHisEmployeeCommand.getHisId();
        YearMonth start = new YearMonth(stateCorrelationHisEmployeeCommand.getStartYearMonth());
        YearMonth end = new YearMonth(stateCorrelationHisEmployeeCommand.getEndYearMonth());

        stateCorreHisEmService.addOrUpdate(cid,hisID,start,end,mode, stateLinkSetMaster);
    }
}
