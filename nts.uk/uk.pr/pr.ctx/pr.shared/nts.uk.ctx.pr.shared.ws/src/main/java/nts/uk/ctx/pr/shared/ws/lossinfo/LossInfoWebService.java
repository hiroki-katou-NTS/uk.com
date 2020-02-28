package nts.uk.ctx.pr.shared.ws.lossinfo;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo.LossInfoCommand;
import nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo.LossInfoCommandHandler;
import nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empbenepenpeninfor.LossInfoDto;
import nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empbenepenpeninfor.LossInfoFinder;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


@Path("ctx/pr/shared/lossinfo")
@Produces("application/json")
public class LossInfoWebService extends WebService{

    @Inject
    private LossInfoFinder lossInfoFinder;

    @Inject
    private LossInfoCommandHandler commandHandler;

    @POST
    @Path("/getLossInfo/{empId}")
    public LossInfoDto getLossInfoById(@PathParam("empId") String empId){
        String companyId = AppContexts.user().companyId();
        return lossInfoFinder.getLossInfoById(companyId, empId);
    }

    @POST
    @Path("/registerLossInfo")
    public void registerLossInfo(LossInfoCommand command){
        commandHandler.handle(command);
    }
}
