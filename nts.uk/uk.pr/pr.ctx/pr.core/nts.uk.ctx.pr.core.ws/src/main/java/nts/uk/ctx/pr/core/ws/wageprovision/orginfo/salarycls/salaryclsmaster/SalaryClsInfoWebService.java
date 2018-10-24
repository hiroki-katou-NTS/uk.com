package nts.uk.ctx.pr.core.ws.wageprovision.orginfo.salarycls.salaryclsmaster;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.orginfo.salarycls.salaryclsmaster.*;
import nts.uk.ctx.pr.core.app.find.wageprovision.orginfo.salarycls.salaryclsmaster.SalaryClsInfoDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.orginfo.salarycls.salaryclsmaster.SalaryClsInfoFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("pr/core/wageprovision/orginfo/salarycls/salaryclsmaster")
@Produces("application/json")
public class SalaryClsInfoWebService extends WebService {

    @Inject
    private SalaryClsInfoFinder finder;

    @Inject
    private AddSalaryClsInfoCommandHandler addCommandHandler;

    @Inject
    private DeleteSalaryClsInfoCommandHandler deleteCommandHandler;

    @Inject
    private UpdateSalaryClsInfoCommandHandler updateCommandHandler;

    @POST
    @Path("findAll")
    public List<SalaryClsInfoDto> findAll() {
        return finder.findAll();
    }

    @POST
    @Path("get/{cd}")
    public SalaryClsInfoDto get(@PathParam("cd") String code) {
        return finder.get(code);
    }

    @POST
    @Path("add")
    public void add(AddSalaryClsInfoCommand addCommand) {
        addCommandHandler.handle(addCommand);
    }

    @POST
    @Path("delete")
    public void delete(DeleteSalaryClsInfoCommand deleteCommand) {
        deleteCommandHandler.handle(deleteCommand);
    }

    @POST
    @Path("update")
    public void update(UpdateSalaryClsInfoCommand updateCommand) {
        updateCommandHandler.handle(updateCommand);
    }


}
