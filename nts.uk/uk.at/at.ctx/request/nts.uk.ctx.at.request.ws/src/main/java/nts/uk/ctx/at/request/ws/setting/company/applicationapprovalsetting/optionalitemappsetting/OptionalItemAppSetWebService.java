package nts.uk.ctx.at.request.ws.setting.company.applicationapprovalsetting.optionalitemappsetting;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.optionalitemappsetting.DeleteOptinalItemAppSetCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemAppSetCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.optionalitemappsetting.SaveOptinalItemAppSetCommandHandler;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemAppSetDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemAppSetFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("at/request/setting/company/applicationapproval/optionalitem")
@Produces("application/json")
public class OptionalItemAppSetWebService extends WebService {
    @Inject
    private OptionalItemAppSetFinder finder;

    @Inject
    private SaveOptinalItemAppSetCommandHandler saveHandler;

    @Inject
    private DeleteOptinalItemAppSetCommandHandler deleteHandler;

    @POST
    @Path("findall")
    public List<OptionalItemAppSetDto> findAll() {
        return finder.findAllByCompany();
    }

    @POST
    @Path("findone")
    public OptionalItemAppSetDto findOne(String code) {
        return finder.findByCode(code);
    }

    @POST
    @Path("save")
    public void save(OptionalItemAppSetCommand data) {
        saveHandler.handle(data);
    }

    @POST
    @Path("delete")
    public void save(String code) {
        deleteHandler.handle(code);
    }
}
