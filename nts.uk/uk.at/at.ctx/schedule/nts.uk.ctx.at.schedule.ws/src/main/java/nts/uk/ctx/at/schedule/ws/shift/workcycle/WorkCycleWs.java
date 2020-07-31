package nts.uk.ctx.at.schedule.ws.shift.workcycle;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.workcycle.AddWorkCycleCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.workcycle.DeleteWorkCycleCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.workcycle.UpdateWorkCycleCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.workcycle.command.AddWorkCycleCommand;
import nts.uk.ctx.at.schedule.app.command.shift.workcycle.command.DeleteWorkCycleCommand;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("ctx/at/schedule/pattern/work/cycle")
@Produces(MediaType.APPLICATION_JSON)
public class WorkCycleWs extends WebService {

    @Inject
    AddWorkCycleCommandHandler addWorkCycleCommandHandler;

    @Inject
    DeleteWorkCycleCommandHandler deleteWorkCycleCommandHandler;

    @Inject
    UpdateWorkCycleCommandHandler updateWorkCycleCommandHandler;

    @POST
    @Path("register")
    public void registerWorkCycle(AddWorkCycleCommand command) { this.addWorkCycleCommandHandler.handle(command); }

    @POST
    @Path("update")
    public void updateWorkCycle(AddWorkCycleCommand command) { this.updateWorkCycleCommandHandler.handle(command); }

    @POST
    @Path("delete")
    public void deleteWorkCycle(DeleteWorkCycleCommand command) { this.deleteWorkCycleCommandHandler.handle(command); }

}
