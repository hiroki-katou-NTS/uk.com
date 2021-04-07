package nts.uk.ctx.at.shared.ws.scherec.taskmanagement.task;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.scherec.taskmanagement.task.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("at/shared/scherec/taskmanagement/task/kmt009")
@Produces("application/json")
public class TaskMangementWebService extends WebService {

    @Inject
    private UpdateSubTaskRefinementInfoCommandHandler updateHandler;

    @Inject
    private DeleteSubTaskRefinementInfoCommandHandler deleteHandler;

    @Inject
    private CopySubTaskInformationCommandHandler copyHandler;

    @Path("update")
    @POST
    public void update(UpdateSubTaskRefinementInfoCommand command) {
        updateHandler.handle(command);
    }

    @Path("delete")
    @POST
    public void delete(DeleteSubTaskRefinementInfoCommand command) {
        deleteHandler.handle(command);
    }

    @Path("copy")
    @POST
    public void copy(CopySubTaskInformationCommand command) {
        copyHandler.handle(command);
    }
}
