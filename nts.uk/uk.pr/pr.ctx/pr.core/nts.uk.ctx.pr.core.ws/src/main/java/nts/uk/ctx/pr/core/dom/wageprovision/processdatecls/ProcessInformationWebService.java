package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.AddProcessInformationCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.ProcessInformationCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.ProcessInformationDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.ProcessInformationFinder;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("pr/core/wageprovision/processdatecls/processinformation")
@Produces("application/json")
public class ProcessInformationWebService extends WebService {
    @Inject
    ProcessInformationFinder processInformationFinder;

    @Inject
    AddProcessInformationCommandHandler addProcessInformationCommandHandler;

    @GET
    public ProcessInformationDto getProcessInformation(int processCateNo){
        return  processInformationFinder.getProcessInformation(processCateNo);
    }

    @POST
    public void saveProcessInformation(ProcessInformationCommand command){
        this.addProcessInformationCommandHandler.handle(command);
    }



//    public void saveJobTitle(SaveJobTitleCommand command)
//    {
//        this.saveJobTitleCommandHandler.handle(command);
//    }


}
