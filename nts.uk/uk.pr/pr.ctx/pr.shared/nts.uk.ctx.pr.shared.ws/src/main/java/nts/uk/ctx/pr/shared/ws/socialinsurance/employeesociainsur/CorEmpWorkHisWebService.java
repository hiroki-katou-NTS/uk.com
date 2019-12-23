package nts.uk.ctx.pr.shared.ws.socialinsurance.employeesociainsur;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empcomofficehis.AddEmpCorpHealthOffHisCommand;
import nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empcomofficehis.AddEmpCorpHealthOffHisCommandHandler;
import nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empcomworkstlinfor.CorEmpWorkHisDto;
import nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empcomworkstlinfor.CorEmpWorkHisFinder;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

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
    private CorEmpWorkHisFinder finder;

//    @Inject
//    private AddEmpCorpHealthOffHisCommandHandler addEmpCorpHealthOffHisCommandHandler;

    @POST
    @Path("getCorEmpWorkHisByEmpId/{empID}")
    public List<CorEmpWorkHisDto> getAllCorEmpWorkHisByEmpId(@PathParam("empID") String empID){
        return finder.getAllCorEmpWorkHisByEmpId(empID);
    }

//    @POST
//    @Path("EmpCorpHealth/add")
//    public PeregAddCommandResult addCorpEmpWork(AddEmpCorpHealthOffHisCommand command){
//        return this.addEmpCorpHealthOffHisCommandHandler.handle(command);
//    }
}
