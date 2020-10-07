package nts.uk.ctx.at.schedule.ws.schedule.alarm.consecutivework.consecutiveattendance;

import nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.consecutiveattendance.DeleteConsecutiveAttendanceOrgCommandHandler;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.consecutiveattendance.DeleteConsecutiveAttendanceOrgDto;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.consecutiveattendance.RegisterConsecutiveAttendanceOrgCommandHandler;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.consecutiveattendance.RegisterConsecutiveAttendanceOrgDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("ctx/at/schedule/alarm/consecutivework/consecutiveattendance")
@Produces(MediaType.APPLICATION_JSON)
public class ConsecutiveAttendanceOrgWS {
    @Inject
    private RegisterConsecutiveAttendanceOrgCommandHandler registerConsecutiveAttendanceOrgHandler;

    @Inject
    private DeleteConsecutiveAttendanceOrgCommandHandler deleteConsecutiveAttendanceOrgHandler;

    @POST
    @Path("org/register")
    public void RegisterConsecutiveAttendanceOrg (RegisterConsecutiveAttendanceOrgDto command) {
        registerConsecutiveAttendanceOrgHandler.handle(command);
    }

    @POST
    @Path("org/delete")
    public void DeleteConsecutiveAttendanceOrg (DeleteConsecutiveAttendanceOrgDto command) {
        deleteConsecutiveAttendanceOrgHandler.handle(command);
    }
}
