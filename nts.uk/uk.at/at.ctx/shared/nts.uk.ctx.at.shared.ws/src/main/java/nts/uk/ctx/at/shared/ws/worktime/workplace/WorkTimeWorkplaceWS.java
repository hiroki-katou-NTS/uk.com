package nts.uk.ctx.at.shared.ws.worktime.workplace;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.worktime.workplace.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("at/shared/workTimeWorkplace")
@Produces(MediaType.APPLICATION_JSON)
public class WorkTimeWorkplaceWS extends WebService {

    @Inject
    private RegisterWorkTimeWorkplaceCommandHandler register;

    @Inject
    private UpdateWorkTimeWorkplaceCommandHandler update;

    @Inject
    private RemoveWorkTimeWorkplaceCommandHandler remove;

    @POST
    @Path("register")
    public void registerWorktime(RegisterWorkTimeWorkplaceCommand command) {
        this.register.handle(command);
    }

    @POST
    @Path("update")
    public void updateWorktime(RegisterWorkTimeWorkplaceCommand command) {
        this.update.handle(command);
    }

    @POST
    @Path("remove")
    public void removeWorktime(RemoveWorkTimeWorkplaceCommand command) {
        this.remove.handle(command);
    }

}
