package nts.uk.ctx.pr.shared.ws.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.shared.app.command.empinsqualifiinfo.employmentinsqualifiinfo.AddEmpInsGetInfoComandHandler;
import nts.uk.ctx.pr.shared.app.command.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsGetInfoCommand;
import nts.uk.ctx.pr.shared.app.find.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsGetInfoDto;
import nts.uk.ctx.pr.shared.app.find.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsGetInfoFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("shared/empinsqualifiinfo/employmentinsqualifiinfo")
@Produces("application/json")
public class EmpInsGetInfoWebService extends WebService {
    @Inject
    private EmpInsGetInfoFinder finder;

    @Inject
    private AddEmpInsGetInfoComandHandler addCommandHandler;

    @POST
    @Path("/start/{sid}")
    public EmpInsGetInfoDto getEmpInsGetInfoById(@PathParam("sid") String sid){
        return finder.getEmpInsGetInfoById(sid);
    }

    @POST
    @Path("/register")
    public void registerEmpInsGetInfo (EmpInsGetInfoCommand empInsGetInfoCommand){
        addCommandHandler.handle(empInsGetInfoCommand);
    }
}
