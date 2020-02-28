package nts.uk.ctx.pr.core.ws.wageprovision.empsalunitprice;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.empsalunitprice.PayrollInformationCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.empsalunitprice.PayrollInformationCommands;
import nts.uk.ctx.pr.core.app.find.wageprovision.unitpricename.SalaryPerUnitPriceFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.unitpricename.SalaryPerUnitPriceNameDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("ctx/pr/core/wageprovision/empsalunitprice")
@Produces(MediaType.APPLICATION_JSON)
public class UpdateUnitPriceWebService{

    @Inject
    PayrollInformationCommandHandler commandHandler;

    @POST
    @Path("/updateUnitPrice")
    public void updateUnitPrice(PayrollInformationCommands commands) {
        this.commandHandler.handle(commands);
    }

}
