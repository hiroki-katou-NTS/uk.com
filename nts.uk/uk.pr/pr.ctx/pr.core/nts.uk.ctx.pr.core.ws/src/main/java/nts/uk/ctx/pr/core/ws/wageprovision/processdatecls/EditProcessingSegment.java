package nts.uk.ctx.pr.core.ws.wageprovision.processdatecls;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.ProcessInformationCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.UpdateProcessInformationCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.UpdateValPayDateSetCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.ValPayDateSetCommand;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx.pr.core.ws.wageprovision.registerprocessingsegment")
@Produces("application/json")
public class EditProcessingSegment extends WebService {

    @Inject
    UpdateProcessInformationCommandHandler updateProcessInformationCommandHandler;

    @Inject
    UpdateValPayDateSetCommandHandler updateValPayDateSetCommandHandler;

    @POST
    public void editProcessingSegment(
            ProcessInformationCommand processInformationCommand,
            ValPayDateSetCommand valPayDateSetCommand
    ){
        this.updateProcessInformationCommandHandler.handle(processInformationCommand);
        this.updateValPayDateSetCommandHandler.handle(valPayDateSetCommand);
    }


}
