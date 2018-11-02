package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.MasterCode;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisEmployeeService;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMaster;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StatementCode;
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
    private StateCorrelationHisEmployeeService stateCorrelationHisEmployeeService;

    @Override
    protected void handle(CommandHandlerContext<StateCorrelationHisEmployeeContainerCommand> context) {
        String cid = AppContexts.user().companyId();
        List<StateLinkSettingMasterCommand> listStateLinkSettingMasterCommand = context.getCommand().getListStateLinkSettingMasterCommand();
        //convert to domain
        List<StateLinkSettingMaster> stateLinkSettingMaster = new ArrayList<StateLinkSettingMaster>();
        if(listStateLinkSettingMasterCommand.size() > 0){
            stateLinkSettingMaster = listStateLinkSettingMasterCommand.stream().map(item ->{
                return new StateLinkSettingMaster(item.getHistoryID(),new MasterCode(item.getMasterCode()),
                        item.getSalaryCode() == null ? null : new StatementCode(item.getSalaryCode()),
                        item.getBonusCode() == null ? null : new StatementCode(item.getBonusCode()));
            }).collect(Collectors.toList());
        }
        StateCorrelationHisEmployeeCommand stateCorrelationHisEmployeeCommand = context.getCommand().getStateCorrelationHisEmployeeCommand();
        int mode = context.getCommand().getMode();
        String hisID = stateCorrelationHisEmployeeCommand.getHisId();
        YearMonth start = new YearMonth(stateCorrelationHisEmployeeCommand.getStartYearMonth());
        YearMonth end = new YearMonth(stateCorrelationHisEmployeeCommand.getEndYearMonth());

        stateCorrelationHisEmployeeService.addOrUpdate(cid,hisID,start,end,mode,stateLinkSettingMaster);
    }
}
