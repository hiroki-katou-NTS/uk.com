package nts.uk.ctx.pr.shared.ws.socialinsurance.employeesociainsur;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empcomworkstlinfor.CorEmpWorkHisDto;
import nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empcomworkstlinfor.CorEmpWorkHisFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;


@Path("shared/employeesociainsur")
@Produces("application/json")
public class CorEmpWorkHisWebService extends WebService {

    @Inject
    CorEmpWorkHisFinder finder;

    @POST
    @Path("getCorEmpWorkHisByEmpId/{empID}")
    public List<CorEmpWorkHisDto> getAllCorEmpWorkHisByEmpId(@PathParam("empID") String empID){
        return finder.getAllCorEmpWorkHisByEmpId(empID);
    }
}
