package nts.uk.ctx.pr.core.ws.wageprovision.organizationinfo.salarycls.salaryclsmaster;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.organizationinfo.salarycls.salaryclsmaster.AddSalaryClsInfoCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.organizationinfo.salarycls.salaryclsmaster.RemoveSalaryClsInfoCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.organizationinfo.salarycls.salaryclsmaster.SalaryClsInfoCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.organizationinfo.salarycls.salaryclsmaster.UpdateSalaryClsInfoCommandHandler;
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
    private AddSalaryClsInfoCommandHandler addCommandHandler;

    @Inject
    private UpdateSalaryClsInfoCommandHandler updateCommandHandler;

    @Inject
    private RemoveSalaryClsInfoCommandHandler removeCommandHandler;

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
    public void add(SalaryClsInfoCommand command) {
        addCommandHandler.handle(command);
    }

    @POST
    @Path("remove")
    public void delete(String salaryClassificationCode) {
        removeCommandHandler.handle(salaryClassificationCode);
    }

    @POST
    @Path("update")
    public void update(SalaryClsInfoCommand command) {
        updateCommandHandler.handle(command);
    }
}

