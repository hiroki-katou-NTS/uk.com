package nts.uk.ctx.pr.core.ws.wageprovision.organizationinfo.salarycls.salaryclsmaster;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.AddSalaryClassificationInformationCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.RemoveSalaryClassificationInformationCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.SalaryClassificationInformationCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.UpdateSalaryClassificationInformationCommandHandler;
import nts.uk.ctx.pr.core.app.find.wageprovision.organizationinfor.salarycls.salaryclsmaster.SalaryClsInforDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.organizationinfor.salarycls.salaryclsmaster.SalaryClsInforFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("core/wageprovision/organizationinfor/salarycls/salaryclsmaster")
@Produces("application/json")
public class SalaryClsInfoWebService extends WebService {

    @Inject
    private SalaryClsInforFinder finder;

    @Inject
    private AddSalaryClassificationInformationCommandHandler addCommandHandler;

    @Inject
    private UpdateSalaryClassificationInformationCommandHandler updateCommandHandler;

    @Inject
    private RemoveSalaryClassificationInformationCommandHandler removeCommandHandler;

    @POST
    @Path("getAll")
    public List<SalaryClsInforDto> getAll() {
        return finder.getAllSalaryClassificationInformation();
    }

    @POST
    @Path("get")
    public SalaryClsInforDto get(String code) {
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

