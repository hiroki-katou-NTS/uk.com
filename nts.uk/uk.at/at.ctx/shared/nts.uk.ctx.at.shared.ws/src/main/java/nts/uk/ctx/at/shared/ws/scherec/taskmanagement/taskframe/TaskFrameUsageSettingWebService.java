package nts.uk.ctx.at.shared.ws.scherec.taskmanagement.taskframe;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.scherec.taskmanagement.taskframe.RegisterTaskFrameUsageSettingCommandHandler;
import nts.uk.ctx.at.shared.app.command.scherec.taskmanagement.taskframe.TaskFrameSettingCommand;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("at/shared/scherec/taskmanagement/taskframe")
@Produces(MediaType.APPLICATION_JSON)
public class TaskFrameUsageSettingWebService extends WebService {
    @Inject
    private RegisterTaskFrameUsageSettingCommandHandler commandHandler;

    @Path("register")
    @POST
    public void register(List<TaskFrameSettingCommand> command) {
        commandHandler.handle(command);
    }
}
