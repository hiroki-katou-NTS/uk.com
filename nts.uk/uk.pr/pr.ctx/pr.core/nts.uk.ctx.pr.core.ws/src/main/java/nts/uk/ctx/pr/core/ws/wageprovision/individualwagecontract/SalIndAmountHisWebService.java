package nts.uk.ctx.pr.core.ws.wageprovision.individualwagecontract;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract.SalIndAmountByPerValCodeCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract.SalIndAmountByPerValCode;
import nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract.SalIndAmountHissAndSalIndAmountFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract.SalIndAmountHissDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("ctx.pr.core.ws.wageprovision.individualwagecontract")
@Produces("application/json")
public class SalIndAmountHisWebService  extends WebService {

    @Inject
    SalIndAmountHissAndSalIndAmountFinder finder;

    @POST
    @Path("salIndAmountHisByPeValCode")
    public SalIndAmountByPerValCode salIndAmountHisByPeValCode(SalIndAmountByPerValCodeCommand command){
        return null;
    }
}
