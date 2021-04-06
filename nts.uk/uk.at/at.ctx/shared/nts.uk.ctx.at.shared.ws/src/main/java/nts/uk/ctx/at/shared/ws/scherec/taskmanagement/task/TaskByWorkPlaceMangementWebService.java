package nts.uk.ctx.at.shared.ws.scherec.taskmanagement.task;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.scherec.taskmanagement.task.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("at/shared/scherec/taskmanagement/task/kmt010")
@Produces("application/json")
public class TaskByWorkPlaceMangementWebService extends WebService {

    @Inject
    private ChangeWorkInfoByWorkplaceCommandHandler changeHandler;

    @Inject
    private CopyTaskInfoByWorkplaceCommandHandler copyHandler;

    @Inject
    private DeleteWorkInfoByWorkplaceCommandHandler deleteHandler;


    @Path("change")
    @POST
    public void change(ChangeWorkInfoByWorkplaceCommand command) {
        changeHandler.handle(command);
    }

    @Path("delete")
    @POST
    public void delete(DeleteWorkInfoByWorkplaceCommand command) {
        deleteHandler.handle(command);
    }

    @Path("copy")
    @POST
    public void copy(CopyTaskInfoByWorkplaceCommand command) {
        copyHandler.handle(command);
    }

}
