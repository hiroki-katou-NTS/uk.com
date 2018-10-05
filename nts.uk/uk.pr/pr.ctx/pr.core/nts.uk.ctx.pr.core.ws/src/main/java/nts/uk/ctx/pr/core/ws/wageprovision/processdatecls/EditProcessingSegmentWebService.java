package nts.uk.ctx.pr.core.ws.wageprovision.processdatecls;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.AddRegisterProcessCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.AddRegisterProcessCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.DeleteProcessInformation;
import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.DeleteValPayDateSetCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.ProcessingSegmentCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.UpdateProcessingCommandHandler;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.ValPayDateSetDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.ValPayDateSetFinder;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ValPayDateSet;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ValPayDateSetRepository;

import javax.inject.Inject;
import javax.ws.rs.*;

@Path("ctx.pr.core.ws.wageprovision.processdatecls")
@Produces("application/json")
public class EditProcessingSegmentWebService extends WebService {
    @Inject
    private ValPayDateSetFinder finder;

    @Inject
    private UpdateProcessingCommandHandler updateProcessingCommandHandler;
    

    @POST
    @Path("findfindRegistedProcessing/{processCateNo}")
    public ValPayDateSetDto valPayDateSetDtoRegisted(@PathParam("processCateNo")int processCateNo){
        return finder.getValPayDateSet(processCateNo);
    }

    
    
    @POST
    @Path("updateRegistedProcessing")
    public void valPayDateSetUpdate(ProcessingSegmentCommand command) {
    	this.updateProcessingCommandHandler.handle(command);
    }
    
    
}
