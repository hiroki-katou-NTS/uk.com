package nts.uk.ctx.pr.core.ws.employaverwage;


import nts.uk.ctx.pr.core.app.command.employaverwage.EmployeeAverWageComand;
import nts.uk.ctx.pr.core.app.command.employaverwage.EmployeeAverWageHandler;
import nts.uk.ctx.pr.core.app.command.employaverwage.EmployeeComand;
import nts.uk.ctx.pr.core.app.find.employaverwage.EmployeeAverWageFinder;
import nts.uk.ctx.pr.core.app.find.employaverwage.EmployeeInfoDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("pr/core/employeeaverwage")
@Produces("application/json")
public class EmployeeAverWageService {

    @Inject
    private EmployeeAverWageFinder employeeAverWageFinder;

    @Inject
    private EmployeeAverWageHandler employeeAverWageHandler;



    @POST
    @Path("/start")
    public List<String> start() {
        return employeeAverWageFinder.init();
    }

    @POST
    @Path("/findemployee")
    public List<EmployeeInfoDto> detailEmployee(EmployeeComand employeeCommand) {
        if(employeeCommand.getEmployeeIds() != null) {
            return employeeAverWageFinder.getEmpInfoDept(employeeCommand);
        } else {
            return null;
        }

    }

    @POST
    @Path("/update")
    public List<String> update(EmployeeAverWageComand employeeCommand) {
        return employeeAverWageHandler.handle(employeeCommand);
    }

}
