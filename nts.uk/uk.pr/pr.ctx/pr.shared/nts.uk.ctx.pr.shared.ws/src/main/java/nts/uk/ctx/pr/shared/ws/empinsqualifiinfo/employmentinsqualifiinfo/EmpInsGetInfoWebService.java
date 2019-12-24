package nts.uk.ctx.pr.shared.ws.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.shared.app.command.empinsqualifiinfo.employmentinsqualifiinfo.RegisterEmpInsGetInfoCommandHandler;
import nts.uk.ctx.pr.shared.app.command.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsGetInfoCommand;
import nts.uk.ctx.pr.shared.app.find.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsGetInfoDto;
import nts.uk.ctx.pr.shared.app.find.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsGetInfoFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("shared/empinsqualifiinfo/employmentinsqualifiinfo/empinsgetinfo")
@Produces("application/json")
public class EmpInsGetInfoWebService extends WebService {
    @Inject
    private EmpInsGetInfoFinder finder;

    @Inject
    private RegisterEmpInsGetInfoCommandHandler addCommandHandler;

    @POST
    @Path("/start/{sId}")
    public EmpInsGetInfoDto getEmpInsGetInfoById(@PathParam("sId") String sId) {
        return finder.getEmpInsGetInfoById(sId);
    }

    @POST
    @Path("/register")
    public void registerEmpInsGetInfo(EmpInsGetInfoCommand empInsGetInfoCommand) {
        addCommandHandler.handle(empInsGetInfoCommand);
    }
}
