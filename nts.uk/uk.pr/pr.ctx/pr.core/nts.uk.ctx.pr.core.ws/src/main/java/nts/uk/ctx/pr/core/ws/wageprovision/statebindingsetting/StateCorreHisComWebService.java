package nts.uk.ctx.pr.core.ws.wageprovision.statebindingsetting;

import nts.arc.layer.ws.WebService;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.AddOrUpdateStateCorrelationHisCompanyCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.StateCorrelationHisCompanyCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.StateCorrelationHisCompanySettingCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.StateCorreHisComDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.StateCorreHisComFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.StatementLayoutDto;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.Optional;


@Path("core/wageprovision/statementbindingsetting")
@Produces("application/json")
public class StateCorreHisComWebService extends WebService {

    @Inject
    private StateCorreHisComFinder stateCorreHisComFinder;

    @Inject
    private StateCorreHisService mStateCorreHisService;

    @Inject
    private AddOrUpdateStateCorrelationHisCompanyCommandHandler addOrUpdateStateCorrelationHisCompanyCommandHandler;

    @POST
    @Path("getStateCorrelationHisCompanyById")
    public List<StateCorreHisComDto> getStateCorrelationHisCompanyById(){
        String cid = AppContexts.user().companyId();
        return stateCorreHisComFinder.getStateCorrelationHisCompanyById(cid);
    }
    @POST
    @Path("editHistoryProcess")
    public void editHistoryProcess(StateCorrelationHisCompanyCommand command){
        mStateCorreHisService.editHistoryProcess(command.getType(),
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
        Optional<StatementLayoutDto> statementLayoutDto = stateCorreHisComFinder.getStateLinkSettingCompanyById(cid,hisId,startYearMonth);
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
