package nts.uk.ctx.pr.core.ws.wageprovision.statementbindingsetting;

import nts.arc.layer.ws.WebService;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.AddOrUpdateStateCorrelationHisCompanyCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.StateCorrelationHisCompanyCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.StateCorrelationHisCompanySettingCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateCorrelationHisCompanyDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateCorrelationHisCompanyFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateLinkSettingCompanyFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StatementLayoutDto;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHistoryService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.Optional;


@Path("core/wageprovision/statementbindingsetting")
@Produces("application/json")
public class StateCorrelationHisCompanyWebService extends WebService {

    @Inject
    private StateCorrelationHisCompanyFinder stateCorrelationHisCompanyFinder;

    @Inject
    private StateCorrelationHistoryService mStateCorrelationHistoryService;

    @Inject
    private AddOrUpdateStateCorrelationHisCompanyCommandHandler addOrUpdateStateCorrelationHisCompanyCommandHandler;

    @POST
    @Path("getStateCorrelationHisCompanyById")
    public List<StateCorrelationHisCompanyDto> getStateCorrelationHisCompanyById(){
        String cid = AppContexts.user().companyId();
        return stateCorrelationHisCompanyFinder.getStateCorrelationHisCompanyById(cid);
    }
    @POST
    @Path("editHistoryProcess")
    public void editHistoryProcess(StateCorrelationHisCompanyCommand command){
        mStateCorrelationHistoryService.editHistoryProcess(command.getType(),
                command.getModeEditHistory(),
                command.getHistoryID(),
                command.getMasterCode(),
                command.getEmployeeId(),
                new YearMonthHistoryItem(
                        command.getHistoryID(),
                        new YearMonthPeriod(new YearMonth(command.getStartYearMonth()), new YearMonth(command.getEndYearMonth()))),
                command.isUpdate());
    }

    @POST
    @Path("getStateLinkSettingCompanyById/{hisId}/{startYearMonth}")
    public StatementLayoutDto getStateLinkSettingCompanyById(@PathParam("hisId") String hisId, @PathParam("startYearMonth") int startYearMonth){
        String cid = AppContexts.user().companyId();
        Optional<StatementLayoutDto> statementLayoutDto = stateCorrelationHisCompanyFinder.getStateLinkSettingCompanyById(cid,hisId,startYearMonth);
        if(statementLayoutDto.isPresent()){
            return statementLayoutDto.get();
        }
        return null;
    }

    @POST
    @Path("registerStateLinkSettingCompany")
    public void registerStateLinkSettingCompany(StateCorrelationHisCompanySettingCommand command){
        addOrUpdateStateCorrelationHisCompanyCommandHandler.handle(command);
    }
}
