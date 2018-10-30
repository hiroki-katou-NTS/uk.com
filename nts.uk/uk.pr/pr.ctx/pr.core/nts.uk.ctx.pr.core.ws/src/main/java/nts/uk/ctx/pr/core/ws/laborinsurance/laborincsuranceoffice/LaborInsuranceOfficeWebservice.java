package nts.uk.ctx.pr.core.ws.laborinsurance.laborincsuranceoffice;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.laborinsurance.laborinsuranceoffice.AddLaborInsuranceOfficeCommandHandler;
import nts.uk.ctx.pr.core.app.command.laborinsurance.laborinsuranceoffice.LaborInsuranceOfficeCommand;
import nts.uk.ctx.pr.core.app.command.laborinsurance.laborinsuranceoffice.RemoveLaborInsuranceOfficeCommandHandler;
import nts.uk.ctx.pr.core.app.command.laborinsurance.laborinsuranceoffice.UpdateLaborInsuranceOfficeCommandHandler;
import nts.uk.ctx.pr.core.app.find.laborinsurance.EmpInsurPreRateDto;
import nts.uk.ctx.pr.core.app.find.laborinsurance.laborinsuranceoffice.LaborInsuranceOfficeDto;
import nts.uk.ctx.pr.core.app.find.laborinsurance.laborinsuranceoffice.LaborInsuranceOfficeFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("ctx/pr/core/laborinsurance/laborinsuranceoffice")
@Produces("application/json")
public class LaborInsuranceOfficeWebservice extends WebService {

    @Inject
    private LaborInsuranceOfficeFinder laborInsuranceOfficeFinder;

    @Inject
    private AddLaborInsuranceOfficeCommandHandler addLaborInsuranceOfficeCommandHandler;

    @Inject
    private UpdateLaborInsuranceOfficeCommandHandler updateLaborInsuranceOfficeCommandHandler;

    @Inject
    private RemoveLaborInsuranceOfficeCommandHandler removeLaborInsuranceOfficeCommandHandler;

    @POST
    @Path("getAll")
    public List<LaborInsuranceOfficeDto> getAllOffice() {
        return laborInsuranceOfficeFinder.getAllLaborInsuranceOffice();
    }

    @POST
    @Path("findLaborOfficeByCode/{laborOfficeCode}")
    public LaborInsuranceOfficeDto getLaborOfficeByCode(@PathParam("laborOfficeCode") String laborOfficeCode) {
        return laborInsuranceOfficeFinder.getLaborInsuranceOffice(laborOfficeCode);
    }

    @POST
    @Path("addLaborOffice")
    public void addLaborOffice(LaborInsuranceOfficeCommand command) {
        addLaborInsuranceOfficeCommandHandler.handle(command);
    }

    @POST
    @Path("updateLaborOffice")
    public void updateLaborOffice(LaborInsuranceOfficeCommand command) {
        updateLaborInsuranceOfficeCommandHandler.handle(command);
    }

    @POST
    @Path("removeLaborOffice")
    public void removeLaborOffice(LaborInsuranceOfficeCommand command) {
        removeLaborInsuranceOfficeCommandHandler.handle(command);
    }
}
