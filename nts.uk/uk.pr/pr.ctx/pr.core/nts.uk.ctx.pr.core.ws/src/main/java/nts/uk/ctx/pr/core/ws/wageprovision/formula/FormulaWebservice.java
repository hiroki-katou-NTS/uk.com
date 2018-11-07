package nts.uk.ctx.pr.core.ws.wageprovision.formula;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.laborinsurance.laborinsuranceoffice.AddLaborInsuranceOfficeCommandHandler;
import nts.uk.ctx.pr.core.app.command.laborinsurance.laborinsuranceoffice.LaborInsuranceOfficeCommand;
import nts.uk.ctx.pr.core.app.command.laborinsurance.laborinsuranceoffice.RemoveLaborInsuranceOfficeCommandHandler;
import nts.uk.ctx.pr.core.app.command.laborinsurance.laborinsuranceoffice.UpdateLaborInsuranceOfficeCommandHandler;
import nts.uk.ctx.pr.core.app.command.socialinsurance.healthinsurance.command.HealthInsuranceCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.formula.FormulaCommand;
import nts.uk.ctx.pr.core.app.find.laborinsurance.laborinsuranceoffice.LaborInsuranceOfficeDto;
import nts.uk.ctx.pr.core.app.find.laborinsurance.laborinsuranceoffice.LaborInsuranceOfficeFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("ctx/pr/core/wageprovision/formula")
@Produces("application/json")
public class FormulaWebservice extends WebService {

    @POST
    @Path("editHistory")
    public void editFormulaHistory(FormulaCommand command) {

    }

    @POST
    @Path("deleteHistory")
    public void deleteFormulaHistory(FormulaCommand command) {

    }
}
