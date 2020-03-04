package nts.uk.ctx.pr.core.ws.wageprovision.processdatecls;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx/pr/core/ws/wageprovision")
@Produces("application/json")
public class RegisterProcessingSegment extends WebService {
    @Inject
    private AddProcessingSegmentCommandHandler addProcessingSegmentCommandHandler;


    @POST
    @Path("registerprocessingsegment")
    public void registerProcessingSegment(ProcessingSegmentCommand command) {
        this.addProcessingSegmentCommandHandler.handle(command);
    }

}
