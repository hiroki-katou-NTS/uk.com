package nts.uk.ctx.pr.shared.ws.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.shared.app.command.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsLossInfoCommand;
import nts.uk.ctx.pr.shared.app.command.empinsqualifiinfo.employmentinsqualifiinfo.RegisterEmpInsLossInfoCommandHandler;
import nts.uk.ctx.pr.shared.app.find.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsLossInfoDto;
import nts.uk.ctx.pr.shared.app.find.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsLossInfoFinder;
import nts.uk.ctx.pr.shared.app.find.empinsqualifiinfo.employmentinsqualifiinfo.RetirementReasonClsInfoDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("shared/empinsqualifiinfo/employmentinsqualifiinfo/empinslossinfo")
@Produces("application/json")
public class EmpInsLossInfoWebService extends WebService {
    @Inject
    private EmpInsLossInfoFinder finder;

    @Inject
    private RegisterEmpInsLossInfoCommandHandler registerEmpInsLossInfoCommandHandler;

    @POST
    @Path("/getRetirement")
    public List<RetirementReasonClsInfoDto> getAllRetirementReasonClsById(){
        return finder.getAllRetirementReasonClsInfoById();
    }

    @POST
    @Path("/start/{sId}")
    public EmpInsLossInfoDto getEmpInsLossInfoById(@PathParam("sId") String sId) {
        return finder.getEmpInsLossInfoById(sId);
    }

    @POST
    @Path("/register")
    public void registerEmpInsLossInfo(EmpInsLossInfoCommand command) {
        registerEmpInsLossInfoCommandHandler.handle(command);
    }
}
