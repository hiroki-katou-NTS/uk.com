package nts.uk.ctx.pr.core.ws.employaverwage;


import nts.uk.ctx.pr.core.app.find.employaverwage.EmployeeAverWageFinder;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("pr/core/employeeaverwage")
@Produces("application/json")
public class EmployeeAverWageService {

    @Inject
    private EmployeeAverWageFinder employeeAverWageFinder;


    @Path("/start")
    public  String start() {
        return employeeAverWageFinder.init();
    }

    @Path("show")
    public void show() {

    }

}
