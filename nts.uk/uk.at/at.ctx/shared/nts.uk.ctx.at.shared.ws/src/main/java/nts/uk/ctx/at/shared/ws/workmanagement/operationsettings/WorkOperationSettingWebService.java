package nts.uk.ctx.at.shared.ws.workmanagement.operationsettings;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.query.workmanagement.operationsettings.WorkOperationSettingDto;
import nts.uk.ctx.at.shared.app.query.workmanagement.operationsettings.WorkOperationSettingQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("at/shared/workmanagement/operationsettings")
@Produces(MediaType.APPLICATION_JSON)
public class WorkOperationSettingWebService extends WebService {
    @Inject
    private WorkOperationSettingQuery query;

    @Path("find")
    @POST
    public WorkOperationSettingDto get() {
        return query.get();
    }
}
