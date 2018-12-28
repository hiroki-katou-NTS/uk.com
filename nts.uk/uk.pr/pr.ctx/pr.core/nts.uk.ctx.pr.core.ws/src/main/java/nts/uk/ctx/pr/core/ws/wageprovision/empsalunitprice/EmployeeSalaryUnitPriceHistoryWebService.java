package nts.uk.ctx.pr.core.ws.wageprovision.empsalunitprice;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.empsalunitprice.*;
import nts.uk.ctx.pr.core.app.find.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.empsalunitprice.IndEmpSalUnitPriceHistoryDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceHistoryFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.empsalunitprice.IndividualUnitPriceDto;

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

    @Inject
    private UpdateAmountIndEmpSalUnitPriceHistoryCommandHandler updateAmountIndEmpSalUnitPriceHistoryCommandHandler;

    @Inject
    private UpdateIndEmpSalUnitPriceHistoryCommandHandler updateIndEmpSalUnitPriceHistoryCommandHandler;

    @Inject
    private AddIndEmpSalUnitPriceHistoryCommandHandler addIndEmpSalUnitPriceHistoryCommandHandler;

    @Inject
    private DeleteIndEmpSalUnitPriceHistoryCommandHandler deleteIndEmpSalUnitPriceHistoryCommandHandler;

    @POST
    @Path("/getEmployeeSalaryUnitPriceHistory")
    public EmployeeSalaryUnitPriceDto getEmployeeSalaryUnitPriceHistory(EmployeeSalaryUnitPriceHistoryCommand command) {
        return finder.getEmployeeSalaryUnitPriceDto(command.getPersonalUnitPriceCode(), command.getEmployeeIds(), command.getYearMonthFilter());
    }

    @POST
    @Path("getIndEmpSalUnitPriceHistories")
    public List<IndEmpSalUnitPriceHistoryDto> getIndEmpSalUnitPriceHistories(IndEmpSalUnitPriceHistoryDto dto) {
        return finder.getAllIndividualEmpSalUnitPriceHistoryDto(dto);
    }

    @POST
    @Path("updateAmount")
    public void updateUnitPrice(UpdateAmountIndEmpSalUnitPriceHistoryCommand command) {
        updateAmountIndEmpSalUnitPriceHistoryCommandHandler.handle(command);
    }

    @POST
    @Path("addHistory")
    public void addHistory(AddIndEmpSalUnitPriceHistoryCommand command) {
        addIndEmpSalUnitPriceHistoryCommandHandler.handle(command);
    }

    @POST
    @Path("updateHistory")
    public void updateHistory(UpdateIndEmpSalUnitPriceHistoryCommand command) {
        updateIndEmpSalUnitPriceHistoryCommandHandler.handle(command);
    }

    @POST
    @Path("deleteHistory")
    public void deleteHistory(DeleteIndEmpSalUnitPriceHistoryCommand command) {
        deleteIndEmpSalUnitPriceHistoryCommandHandler.handle(command);
    }

    @POST
    @Path("individualUnitPriceDisplay")
    public List<IndividualUnitPriceDto> individualUnitPriceDisplay(IndividualUnitPriceDto dto) {
        return finder.getIndividualUnitPriceList(dto.getEmployeeId(), dto.getBaseYearMonth());
    }
}
