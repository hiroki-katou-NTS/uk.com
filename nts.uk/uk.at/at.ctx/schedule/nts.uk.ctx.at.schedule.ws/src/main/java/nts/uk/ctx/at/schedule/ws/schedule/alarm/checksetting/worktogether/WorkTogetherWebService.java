package nts.uk.ctx.at.schedule.ws.schedule.alarm.checksetting.worktogether;

import nts.uk.ctx.at.schedule.app.command.schedule.alarm.checksetting.worktogether.*;
import nts.uk.ctx.at.schedule.app.query.schedule.alarm.checksetting.worktogether.CheckDisplayEmployeeQuery;
import nts.uk.ctx.at.schedule.app.query.schedule.alarm.checksetting.worktogether.Dto.CheckDisplayEmployeeDto;
import nts.uk.ctx.at.schedule.app.query.schedule.alarm.checksetting.worktogether.Dto.ParamCheckDisplayEmployee;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("at/schedule/alarm/worktogether")
@Produces("application/json")
public class WorkTogetherWebService {

    @Inject
    private CheckDisplayEmployeeQuery checkDisplayEmployeeQuery;

    @Inject
    private AddWorkTogetherCommandHandler addSimultaneousCmd;

    @Inject
    private UpdateWorkTogetherCommandHandler updateSimultaneousCmd;

    @Inject
    private DeleteWorkTogetherCommandHandler deleteSimultaneousCmd;

    @POST
    @Path("checkDisplay")
    public List<CheckDisplayEmployeeDto> checkEmpWorkTogether(ParamCheckDisplayEmployee param) {
        List<String> sids = param.getSids();
        return checkDisplayEmployeeQuery.getEmployeeIsDisplay(sids);
    }

    @POST
    @Path("register")
    public void register(AddWorkTogetherCommand cmd) {
        addSimultaneousCmd.handle(cmd);
    }

    @POST
    @Path("update")
    public void update(AddWorkTogetherCommand cmd) {
        updateSimultaneousCmd.handle(cmd);
    }

    @POST
    @Path("delete")
    public void delete(DeleteWorkTogetherCommand cmd) {
        deleteSimultaneousCmd.handle(cmd);
    }

}
