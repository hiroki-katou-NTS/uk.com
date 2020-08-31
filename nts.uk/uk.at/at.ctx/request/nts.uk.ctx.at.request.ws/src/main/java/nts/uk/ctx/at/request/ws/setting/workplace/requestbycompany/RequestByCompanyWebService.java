package nts.uk.ctx.at.request.ws.setting.workplace.requestbycompany;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.setting.workplace.appuseset.ApplicationUseSetCommand;
import nts.uk.ctx.at.request.app.command.setting.workplace.requestbycompany.SaveRequestByCompanyCommandHandler;
import nts.uk.ctx.at.request.app.find.setting.workplace.appuseset.ApplicationUseSetDto;
import nts.uk.ctx.at.request.app.find.setting.workplace.requestbycompany.RequestByCompanyFinder;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApplicationUseSetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("at/request/setting/workplace/requestbycompany")
@Produces("application/json")
public class RequestByCompanyWebService extends WebService {
    @Inject
    private RequestByCompanyFinder finder;

    @Inject
    private SaveRequestByCompanyCommandHandler saveHandler;

    @POST
    @Path("getAll")
    public List<ApplicationUseSetDto> getAllByCompany() {
        return finder.findByCompany();
    }

    @POST
    @Path("save")
    public void save(List<ApplicationUseSetCommand> data) {
        this.saveHandler.handle(data);
    }
}
