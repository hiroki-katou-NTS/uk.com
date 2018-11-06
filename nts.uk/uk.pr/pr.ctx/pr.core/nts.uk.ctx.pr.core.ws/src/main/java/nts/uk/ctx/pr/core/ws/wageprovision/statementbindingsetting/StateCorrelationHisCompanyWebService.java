package nts.uk.ctx.pr.core.ws.wageprovision.statementbindingsetting;

import nts.arc.layer.ws.WebService;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.StateCorrelationHisCompanyCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateCorrelationHisCompanyDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateCorrelationHisCompanyFinder;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHistoryService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;


@Path("core/wageprovision/statementbindingsetting")
@Produces("application/json")
public class StateCorrelationHisCompanyWebService extends WebService {

    @Inject
    StateCorrelationHisCompanyFinder stateCorrelationHisCompanyFinder;

    @Inject
    private StateCorrelationHistoryService mStateCorrelationHistoryService;

    @POST
    @Path("getStateCorrelationHisCompanyById")
    public List<StateCorrelationHisCompanyDto> getStateCorrelationHisCompanyById(){
        String cid = AppContexts.user().companyId();
        return stateCorrelationHisCompanyFinder.getStateCorrelationHisCompanyById(cid);
    }
    @POST
    @Path("deleteStateCorrelationHis")
    public void deleteStateCorrelationHis(StateCorrelationHisCompanyCommand command){
        mStateCorrelationHistoryService.editHistoryProcess(command.getType(),
                command.getModeEditHistory(),
                command.getHistoryID(),
                command.getMasterCode(),
                new YearMonthHistoryItem(
                        command.getHistoryID(),
                        new YearMonthPeriod(new YearMonth(command.getStartYearMonth()), new YearMonth(command.getEndYearMonth()))),
                command.isUpdate());
    }
}
