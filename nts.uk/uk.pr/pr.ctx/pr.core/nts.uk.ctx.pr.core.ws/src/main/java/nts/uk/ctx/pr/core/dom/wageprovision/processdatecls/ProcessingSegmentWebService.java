package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.AddProcessInformationCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.ProcessInformationCommand;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("pr/core/wageprovision/processdatecls/processingsegment")
@Produces("application/json")
public class ProcessingSegmentWebService extends WebService {

    @Inject
    AddProcessInformationCommandHandler addProcessInformationCommandHandler;

    @POST
    public void saveProcessInformation(ProcessInformationCommand command) {
        this.addProcessInformationCommandHandler.handle(command);
    }
}
