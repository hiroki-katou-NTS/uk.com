package nts.uk.ctx.at.request.ws.setting.company.appreasonstandard;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.setting.company.appreasonstandard.SaveReasonTypeItemCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.appreasonstandard.DeleteReasonTypeItemCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.appreasonstandard.ReasonTypeItemCommand;
import nts.uk.ctx.at.request.app.find.setting.company.appreasonstandard.AppReasonStandardDto;
import nts.uk.ctx.at.request.app.find.setting.company.appreasonstandard.AppReasonStandardFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("at/request/setting/company/appreasonstd")
@Produces("application/json")
public class AppReasonStandardWebService extends WebService {
    @Inject
    private AppReasonStandardFinder finder;

    @Inject
    private SaveReasonTypeItemCommandHandler saveHandler;

    @Inject
    private DeleteReasonTypeItemCommandHandler deleteHandler;

    @POST
    @Path("all")
    public List<AppReasonStandardDto> getAll() {
        return finder.getAllByCompany();
    }

    @POST
    @Path("item/save")
    public void saveReasonTypeItem(List<ReasonTypeItemCommand> data) {
        saveHandler.handle(data);
    }

    @POST
    @Path("item/delete")
    public void deleteReasonTypeItem(ReasonTypeItemCommand data) {
        deleteHandler.handle(data);
    }
}
