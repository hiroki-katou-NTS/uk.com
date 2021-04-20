package nts.uk.ctx.at.shared.ws.scherec.workmanagement;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.scherec.workregistration.command.DeleteWorkInformationCommandHandler;
import nts.uk.ctx.at.shared.app.command.scherec.workregistration.command.RegisterWorkInformationCommandHandler;
import nts.uk.ctx.at.shared.app.command.scherec.workregistration.command.UpdateWorkInformationCommandHandler;
import nts.uk.ctx.at.shared.app.command.scherec.workregistration.command.WorkInformationCommand;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("at/shared/scherec/workmanagement/work/kmt001")
@Produces("application/json")
public class WorkManagementWebService extends WebService {

//    @Inject
//    private WorkInformationFinder finder;

    @Inject
    private UpdateWorkInformationCommandHandler updateHandler;

    @Inject
    private DeleteWorkInformationCommandHandler deleteHandler;

    @Inject
    private RegisterWorkInformationCommandHandler registerHandler;

//    @Path("find")
//    @POST
//    public List<WorkInformationDto> get(TaskFrameNo taskFrameNo, TaskCode taskCode) {
//
//        return finder.get(taskFrameNo, taskCode);
//    }

    @Path("update")
    @POST
    public void update(WorkInformationCommand command) { updateHandler.handle(command); }

    @Path("delete")
    @POST
    public void delete(WorkInformationCommand command) {
        deleteHandler.handle(command);
    }

    @Path("register")
    @POST
    public void register(WorkInformationCommand command) {
        registerHandler.handle(command);
    }
}
