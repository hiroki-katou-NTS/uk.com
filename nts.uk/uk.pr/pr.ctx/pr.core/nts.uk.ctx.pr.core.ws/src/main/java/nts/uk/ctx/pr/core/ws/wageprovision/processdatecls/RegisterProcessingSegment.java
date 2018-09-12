package nts.uk.ctx.pr.core.ws.wageprovision.processdatecls;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx.pr.core.ws.wageprovision.registerprocessingsegment")
@Produces("application/json")
public class RegisterProcessingSegment extends WebService {
    @Inject
    private AddValPayDateSetCommandHandler addValPayDateSetCommandHandler;

    @Inject
    private AddProcessInformationCommandHandler addProcessInformationCommandHandler;

    @Inject
    private AddSpecPrintYmSetCommandHandler addSpecPrintYmSetCommandHandler;

    @Inject
    private AddSetDaySupportCommandHandler addSetDaySupportCommandHandler;

    @Inject
    private AddCurrProcessDateCommandHandler addCurrProcessDateCommandHandler;




    @POST
    public void registerProcessingSegment(
            ValPayDateSetCommand valPayDateSetCommand,
            ProcessInformationCommand processInformationCommand,
            SpecPrintYmSetCommand specPrintYmSetCommand,
            SetDaySupportCommand setDaySupportCommand,
            CurrProcessDateCommand currProcessDateCommand
    ) {
        this.addValPayDateSetCommandHandler.handle(valPayDateSetCommand);
        this.addProcessInformationCommandHandler.handle(processInformationCommand);
        this.addSpecPrintYmSetCommandHandler.handle(specPrintYmSetCommand);
        this.addSetDaySupportCommandHandler.handle(setDaySupportCommand);
        this.addCurrProcessDateCommandHandler.handle(currProcessDateCommand);

    }

}
