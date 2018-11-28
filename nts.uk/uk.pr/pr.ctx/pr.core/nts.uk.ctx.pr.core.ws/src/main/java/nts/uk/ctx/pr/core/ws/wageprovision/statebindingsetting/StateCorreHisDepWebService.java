package nts.uk.ctx.pr.core.ws.wageprovision.statebindingsetting;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.AddOrUpdateStateCorrelationHisDeparmentCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.ListStateLinkSettingMasterCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.StateCorreHisDeparDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.StateCorreHisDeparFinder;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("core/wageprovision/statementbindingsetting")
@Produces("application/json")
public class StateCorreHisDepWebService extends WebService {

    @Inject
    private StateCorreHisDeparFinder stateCorreHisDeparFinder;

    @Inject
    private AddOrUpdateStateCorrelationHisDeparmentCommandHandler addOrUpdateStateCorrelationHisDeparmentCommandHandler;

    @POST
    @Path("getStateCorrelationHisDeparmentById")
    public List<StateCorreHisDeparDto> getStateCorrelationHisDeparmentById(){
        String cid = AppContexts.user().companyId();
        return stateCorreHisDeparFinder.getStateCorrelationHisDeparmentById(cid);
    }

    @POST
    @Path("registerStateCorrelationHisDeparment")
    public void register(ListStateLinkSettingMasterCommand command) {
        addOrUpdateStateCorrelationHisDeparmentCommandHandler.handle(command);
    }

}
