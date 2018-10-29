package nts.uk.ctx.pr.core.ws.wageprovision.statementbindingsetting;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateCorrelationHisCompanyDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateCorrelationHisCompanyFinder;
import nts.uk.shr.com.context.AppContexts;

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

    @POST
    @Path("getStateCorrelationHisCompanyById")
    public List<StateCorrelationHisCompanyDto> getStateCorrelationHisCompanyById(){
        String cid = AppContexts.user().companyId();
        return stateCorrelationHisCompanyFinder.getStateCorrelationHisCompanyById(cid);
    }
}
