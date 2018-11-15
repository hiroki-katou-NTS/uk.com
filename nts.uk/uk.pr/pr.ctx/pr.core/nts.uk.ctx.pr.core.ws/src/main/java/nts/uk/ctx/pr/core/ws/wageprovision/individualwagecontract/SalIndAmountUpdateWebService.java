package nts.uk.ctx.pr.core.ws.wageprovision.individualwagecontract;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract.SalIndAmountUpdateAllCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract.SalIndAmountUpdateCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx.pr.core.ws.wageprovision.individualwagecontract")
@Produces("application/json")
public class SalIndAmountUpdateWebService extends WebService {

    @Inject
    private SalIndAmountUpdateCommandHandler salIndAmountUpdateCommandHandler;

    @POST
    @Path("salIndAmountUpdateAll")
    public void salIndAmountHisByPeValCode(SalIndAmountUpdateAllCommand command) {
        this.salIndAmountUpdateCommandHandler.handle(command);
    }
}
