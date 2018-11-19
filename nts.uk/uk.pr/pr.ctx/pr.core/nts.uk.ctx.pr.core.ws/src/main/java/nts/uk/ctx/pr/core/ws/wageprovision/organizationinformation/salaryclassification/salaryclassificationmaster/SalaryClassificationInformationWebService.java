package nts.uk.ctx.pr.core.ws.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.AddSalaryClassificationInformationCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.RemoveSalaryClassificationInformationCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.SalaryClassificationInformationCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.UpdateSalaryClassificationInformationCommandHandler;
import nts.uk.ctx.pr.core.app.find.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.SalaryClassificationInformationDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.SalaryClassificationInformationFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("core/wageprovision/organizationinformation/salaryclassification/salaryclassificationmaster")
@Produces("application/json")
public class SalaryClassificationInformationWebService extends WebService {

    @Inject
    private SalaryClassificationInformationFinder finder;

    @Inject
    private AddSalaryClassificationInformationCommandHandler addCommandHandler;

    @Inject
    private UpdateSalaryClassificationInformationCommandHandler updateCommandHandler;

    @Inject
    private RemoveSalaryClassificationInformationCommandHandler removeCommandHandler;

    @POST
    @Path("getAll")
    public List<SalaryClassificationInformationDto> getAll() {
        return finder.getAllSalaryClassificationInformation();
    }

    @POST
    @Path("get")
    public SalaryClassificationInformationDto get(String code) {
        return finder.getSalaryClassificationInformationById(code);
    }

    @POST
    @Path("add")
    public void add(SalaryClassificationInformationCommand command) {
        addCommandHandler.handle(command);
    }

    @POST
    @Path("remove")
    public void delete(String salaryClassificationCode) {
        removeCommandHandler.handle(salaryClassificationCode);
    }

    @POST
    @Path("update")
    public void update(SalaryClassificationInformationCommand command) {
        updateCommandHandler.handle(command);
    }
}

