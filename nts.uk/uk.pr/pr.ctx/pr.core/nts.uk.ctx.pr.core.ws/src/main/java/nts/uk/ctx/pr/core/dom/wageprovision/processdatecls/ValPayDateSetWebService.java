package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.AddValPayDateSetCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.ValPayDateSetCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.ValPayDateSetDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.ValPayDateSetFinder;


import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("pr/core/wageprovision/processdatecls/valpaydateset")
@Produces("application/json")
public class ValPayDateSetWebService extends WebService {
    @Inject
    private ValPayDateSetFinder valPayDateSetFinder;

    @Inject
    private AddValPayDateSetCommandHandler addValPayDateSetCommandHandler;

    @GET
    public ValPayDateSetDto getValPayDateSet(int processCateNo){
        return valPayDateSetFinder.getValPayDateSet(processCateNo);
    }

    @POST
    public void saveValPayDateSet(ValPayDateSetCommand command){
        this.addValPayDateSetCommandHandler.handle(command);
    }



}
