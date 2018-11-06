package nts.uk.ctx.pr.core.ws.wageprovision.statementbindingsetting;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.AddOrUpdateStateCorrelationHisDeparmentCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.ListStateLinkSettingMasterCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateCorrelationHisDeparmentDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateCorrelationHisDeparmentFinder;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("core/wageprovision/statementbindingsetting")
@Produces("application/json")
public class StateCorrelationHisDeparmentWebService extends WebService {

    @Inject
    private StateCorrelationHisDeparmentFinder stateCorrelationHisDeparmentFinder;

    @Inject
    private AddOrUpdateStateCorrelationHisDeparmentCommandHandler addOrUpdateStateCorrelationHisDeparmentCommandHandler;

    @POST
    @Path("getStateCorrelationHisDeparmentById")
    public List<StateCorrelationHisDeparmentDto> getStateCorrelationHisDeparmentById(){
        String cid = AppContexts.user().companyId();
        return stateCorrelationHisDeparmentFinder.getStateCorrelationHisDeparmentById(cid);
    }

    @POST
    @Path("registerStateCorrelationHisDeparment")
    public void register(ListStateLinkSettingMasterCommand command) {
        addOrUpdateStateCorrelationHisDeparmentCommandHandler.handle(command);

    }

}
