package nts.uk.ctx.pr.core.ws.wageprovision.empsalunitprice;

import nts.uk.ctx.pr.core.app.command.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceHistoryCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.unitpricename.SalaryPerUnitPriceFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.unitpricename.SalaryPerUnitPriceNameDto;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("ctx/pr/core/wageprovision/empsalunitprice")
@Produces(MediaType.APPLICATION_JSON)
public class SalaryPerUnitPriceNameWebService {

    @Inject
    SalaryPerUnitPriceFinder salaryPerUnitPriceFinder;

    @POST
    @Path("/getSalaryPerUnitPriceName")
    public List<SalaryPerUnitPriceNameDto> getSalaryPerUnitPriceNameDto() {
        return salaryPerUnitPriceFinder.getAllSalaryPerUnitPriceName();
    }
}
