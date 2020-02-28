package nts.uk.ctx.pr.report.ws.printconfig.socinsurnoticreset;

import nts.uk.ctx.pr.report.app.command.printconfig.socialinsurnoticreset.EmpAddChangeInfoCommand;
import nts.uk.ctx.pr.report.app.command.printconfig.socialinsurnoticreset.EmpAddChangeInfoCommandHandle;
import nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset.EmpAddChangeInfoDto;
import nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset.EmpAddChangeInfoFinder;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.stream.Collectors;

@Path("ctx/pr/report/printconfig/socinsurnoticreset")
@Produces("application/json")
public class AddChangeSettingWebService {
    @Inject
    EmpAddChangeInfoFinder empAddChangeInfoFinder;

    @Inject
    EmpAddChangeInfoCommandHandle empAddChangeInfoCommandHandle;

    @POST
    @Path("/start/{empId}")
    public EmpAddChangeInfoDto startScreen(@PathParam("empId") String empId) {
        return empAddChangeInfoFinder.getEmpAddChangeInfoRepository(empId);
    }

    @POST
    @Path("/register")
    public void registerEmpAddChangeInfo(EmpAddChangeInfoCommand empAddChangeInfoCommand) {
        empAddChangeInfoCommandHandle.handle(empAddChangeInfoCommand);
    }


}
