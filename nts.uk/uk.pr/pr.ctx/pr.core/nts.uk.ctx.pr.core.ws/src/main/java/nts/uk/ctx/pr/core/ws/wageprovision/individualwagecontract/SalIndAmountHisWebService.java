package nts.uk.ctx.pr.core.ws.wageprovision.individualwagecontract;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract.AddSalIndAmountHisCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract.RemoveSalIndAmountHisCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract.SalIndAmountHisCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract.UpdateSalIndAmountHisCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx/pr/core/ws/wageprovision/individualwagecontract")
@Produces("application/json")
public class SalIndAmountHisWebService {
    @Inject
    private UpdateSalIndAmountHisCommandHandler updateSalIndAmountHisCommandHandler;
    @Inject
    private RemoveSalIndAmountHisCommandHandler removeSalIndAmountHisCommandHandler;

    @POST
    @Path("/editHistory")
    public void editSalIndividualAmountHistory(SalIndAmountHisCommand command) {
        updateSalIndAmountHisCommandHandler.handle(command);
    }

    @POST
    @Path("/deleteHistory")
    public void deleteSalIndividualAmountHistory(SalIndAmountHisCommand command) {
        removeSalIndAmountHisCommandHandler.handle(command);
    }
}
