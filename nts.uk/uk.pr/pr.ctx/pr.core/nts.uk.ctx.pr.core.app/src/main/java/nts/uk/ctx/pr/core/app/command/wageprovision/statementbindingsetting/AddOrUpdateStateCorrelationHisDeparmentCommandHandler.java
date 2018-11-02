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
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.MasterCode;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisDeparmentService;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingDate;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMaster;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StatementCode;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddOrUpdateStateCorrelationHisDeparmentCommandHandler extends CommandHandler<ListStateLinkSettingMasterCommand> {
    
    @Inject
    private StateCorrelationHisDeparmentService stateCorrelationHisDeparmentService;
    
    @Override
    protected void handle(CommandHandlerContext<ListStateLinkSettingMasterCommand> context) {
        String cid = AppContexts.user().companyId();

        List<StateLinkSettingMasterCommand> listStateLinkSettingMasterCommand = context.getCommand().getListStateLinkSettingMasterCommand();
        //convert to domain
        List<StateLinkSettingMaster> stateLinkSettingMaster = new ArrayList<StateLinkSettingMaster>();
        if(listStateLinkSettingMasterCommand.size() > 0){
            stateLinkSettingMaster = listStateLinkSettingMasterCommand.stream().map(item ->{
                return new StateLinkSettingMaster(item.getHisId(),new MasterCode(item.getMasterCode()), new StatementCode(item.getSalaryCode()), new StatementCode(item.getBonusCode()));
            }).collect(Collectors.toList());
        }

        StateLinkSettingDateCommand stateLinkSettingDateCommand = context.getCommand().getStateLinkSettingDateCommand();

        String[] date = stateLinkSettingDateCommand.getDate().split("/");
        int y = Integer.valueOf(date[0]);
        int m = Integer.valueOf(date[1]);
        int d = Integer.valueOf(date[2]);

        StateLinkSettingDate stateLinkSettingDate = new StateLinkSettingDate(stateLinkSettingDateCommand.getHistoryID(), GeneralDate.ymd(y,m,d));
        StateCorrelationHisDeparmentCommand stateCorrelationHisDeparmentCommand = context.getCommand().getStateCorrelationHisDeparmentCommand();
        int mode = context.getCommand().getMode();
        String hisID = stateCorrelationHisDeparmentCommand.getHistoryID();
        YearMonth start = new YearMonth(stateCorrelationHisDeparmentCommand.getStartYearMonth());
        YearMonth end = new YearMonth(stateCorrelationHisDeparmentCommand.getEndYearMonth());

        stateCorrelationHisDeparmentService.addOrUpdate(cid,hisID,start,end,mode,stateLinkSettingDate,stateLinkSettingMaster);
    }
}
