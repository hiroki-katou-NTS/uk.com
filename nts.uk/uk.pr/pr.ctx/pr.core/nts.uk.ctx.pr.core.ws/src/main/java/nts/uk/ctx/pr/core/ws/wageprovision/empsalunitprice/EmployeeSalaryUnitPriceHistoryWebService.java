package nts.uk.ctx.pr.core.ws.wageprovision.empsalunitprice;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceHistoryCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.empsalunitprice.IndEmpSalUnitPriceHistoryDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceHistoryFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("ctx/pr/core/wageprovision/empsalunitprice")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeSalaryUnitPriceHistoryWebService extends WebService {

    @Inject
    private EmployeeSalaryUnitPriceHistoryFinder finder;

    @POST
    @Path("/getEmployeeSalaryUnitPriceHistory")
    public EmployeeSalaryUnitPriceDto getEmployeeSalaryUnitPriceHistory(EmployeeSalaryUnitPriceHistoryCommand command) {
        return finder.getEmployeeSalaryUnitPriceDto(command.getPersonalUnitPriceCode(), command.getEmployeeIds());
    }

    @POST
    @Path("/getAllIndEmpSalUnitPriceHistory")
    public List<IndEmpSalUnitPriceHistoryDto> getAllIndEmpSalUnitPriceHistory(IndEmpSalUnitPriceHistoryDto dto) {
        return finder.getAllIndividualEmpSalUnitPriceHistoryDto(dto);
    }
}
