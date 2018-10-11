package nts.uk.ctx.pr.core.ws.emprsdttaxinfo.amountinfo;

import nts.uk.ctx.pr.core.app.command.emprsdttaxinfo.amountinfo.DeleteEmployeeResidentTaxPayAmountInfoCommand;
import nts.uk.ctx.pr.core.app.command.emprsdttaxinfo.amountinfo.DeleteEmployeeResidentTaxPayAmountInfoHandler;
import nts.uk.ctx.pr.core.app.command.emprsdttaxinfo.amountinfo.RegisterEmployeeResidentTaxPayAmountInfoCommand;
import nts.uk.ctx.pr.core.app.command.emprsdttaxinfo.amountinfo.RegisterEmployeeResidentTaxPayAmountInfoHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("core/emprsdttaxinfo/amountinfo")
@Produces("application/json")
public class EmployeeResidentTaxPayAmountInfWebService {

    @Inject
    private RegisterEmployeeResidentTaxPayAmountInfoHandler registerEmpRsdtTaxPayAmountInfoHandler;

    @Inject
    private DeleteEmployeeResidentTaxPayAmountInfoHandler deleteEmpRsdtTaxPayAmountInfoHandler;

    @POST
    @Path("registerTaxPayAmount")
    public void registerTaxPayAmount(RegisterEmployeeResidentTaxPayAmountInfoCommand command) {
        registerEmpRsdtTaxPayAmountInfoHandler.handle(command);
    }

    @POST
    @Path("deleteTaxPayAmount")
    public void registerTaxPayAmount(DeleteEmployeeResidentTaxPayAmountInfoCommand command) {
        deleteEmpRsdtTaxPayAmountInfoHandler.handle(command);
    }
}
