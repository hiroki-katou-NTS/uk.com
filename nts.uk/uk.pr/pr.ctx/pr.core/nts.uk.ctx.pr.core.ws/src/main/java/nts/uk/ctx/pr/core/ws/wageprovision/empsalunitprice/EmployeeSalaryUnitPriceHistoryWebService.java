package nts.uk.ctx.pr.core.ws.wageprovision.empsalunitprice;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceHistoryDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceHistoryFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("ctx/pr/core/wageprovision/empsalunitprice")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeSalaryUnitPriceHistoryWebService  extends WebService{

    @Inject
    EmployeeSalaryUnitPriceHistoryFinder employeeSalaryUnitPriceHistoryFinder;

    @POST
    @Path("/getEmployeeSalaryUnitPriceHistory")
    public EmployeeSalaryUnitPriceHistoryDto getEmployeeSalaryUnitPriceHistory() {
        return null;
    }

}
