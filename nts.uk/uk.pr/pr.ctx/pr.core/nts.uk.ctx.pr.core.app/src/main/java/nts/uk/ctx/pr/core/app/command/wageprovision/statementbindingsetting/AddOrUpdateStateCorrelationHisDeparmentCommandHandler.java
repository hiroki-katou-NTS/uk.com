package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.MasterCode;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisDeparService;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetDate;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetMaster;
import nts.uk.shr.com.context.AppContexts;


@Stateless
@Transactional
public class AddOrUpdateStateCorrelationHisDeparmentCommandHandler extends CommandHandler<ListStateLinkSettingMasterCommand> {
    
    @Inject
    private StateCorreHisDeparService stateCorreHisDeparService;
    
    @Override
    protected void handle(CommandHandlerContext<ListStateLinkSettingMasterCommand> context) {
        String cid = AppContexts.user().companyId();

        List<StateLinkSettingMasterCommand> listStateLinkSettingMasterCommand = context.getCommand().getListStateLinkSettingMasterCommand();
        //convert to domain
        List<StateLinkSetMaster> stateLinkSetMaster = new ArrayList<StateLinkSetMaster>();
        if(listStateLinkSettingMasterCommand.size() > 0){
            stateLinkSetMaster = listStateLinkSettingMasterCommand.stream().map(item -> new StateLinkSetMaster(item.getHisId(),item.getMasterCode(),
                    item.getSalaryCode(),
                    item.getBonusCode())).collect(Collectors.toList());
        }

        StateLinkSettingDateCommand stateLinkSettingDateCommand = context.getCommand().getStateLinkSettingDateCommand();

        GeneralDate date =  GeneralDate.fromString(stateLinkSettingDateCommand.getDate(),"yyyy/MM/dd");

        StateLinkSetDate stateLinkSetDate = new StateLinkSetDate(stateLinkSettingDateCommand.getHistoryID(), date);
        StateCorrelationHisDeparmentCommand stateCorrelationHisDeparmentCommand = context.getCommand().getStateCorrelationHisDeparmentCommand();
        int mode = context.getCommand().getMode();
        String hisID = stateCorrelationHisDeparmentCommand.getHistoryID();
        YearMonth start = new YearMonth(stateCorrelationHisDeparmentCommand.getStartYearMonth());
        YearMonth end = new YearMonth(stateCorrelationHisDeparmentCommand.getEndYearMonth());

        stateCorreHisDeparService.addOrUpdate(cid,hisID,start,end,mode, stateLinkSetDate, stateLinkSetMaster);
    }

}
