package nts.uk.ctx.at.request.ws.setting.workplace.requestbyworkplace;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.setting.workplace.appuseset.ApplicationUseSetCommand;
import nts.uk.ctx.at.request.app.command.setting.workplace.requestbycompany.SaveRequestByCompanyCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.workplace.requestbyworkplace.*;
import nts.uk.ctx.at.request.app.find.setting.workplace.appuseset.ApplicationUseSetDto;
import nts.uk.ctx.at.request.app.find.setting.workplace.requestbycompany.RequestByCompanyFinder;
import nts.uk.ctx.at.request.app.find.setting.workplace.requestbyworkplace.RequestByWorkplaceDto;
import nts.uk.ctx.at.request.app.find.setting.workplace.requestbyworkplace.RequestByWorkplaceFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("at/request/setting/workplace/requestbyworkplace")
@Produces("application/json")
public class RequestByWorkplaceWebService extends WebService {
    @Inject
    private RequestByWorkplaceFinder finder;

    @Inject
    private SaveRequestByWorkplaceCommandHandler saveHandler;

    @Inject
    private DeleteRequestByWorkplaceCommandHandler deleteHandler;

    @Inject
    private CopyRequestByWorkplaceCommandHandler copyHandler;


    @POST
    @Path("getAll")
    public List<RequestByWorkplaceDto> getAllByCompany() {
        return finder.findAllByCompany();
    }

    @POST
    @Path("save")
    public void save(RequestByWorkplaceCommand data) {
        this.saveHandler.handle(data);
    }

    @POST
    @Path("delete")
    public void delete(RequestByWorkplaceCommand data) {
        this.deleteHandler.handle(data);
    }

    @POST
    @Path("copy")
    public void copy(CopyRequestByWorkplaceCommand data) {
        this.copyHandler.handle(data);
    }
}
