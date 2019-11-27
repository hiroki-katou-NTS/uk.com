package nts.uk.ctx.pr.core.ws.wageprovision.processdatecls;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.RemoveEmpTiedProYearCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("ctx.pr.core.ws.wageprovision.processdatecls")
@Produces("application/json")
public class DeleteEmpTiedWebService extends WebService {


    @Inject
    RemoveEmpTiedProYearCommandHandler removeEmpTiedProYearCommandHandler;

    @POST
    @Path("removeEmpTied/{processCateNo}")
    public void removeEmpTied(@PathParam("processCateNo")int processCateNo){
        this.removeEmpTiedProYearCommandHandler.removeEmpTiedProYear(processCateNo);
    }
}
