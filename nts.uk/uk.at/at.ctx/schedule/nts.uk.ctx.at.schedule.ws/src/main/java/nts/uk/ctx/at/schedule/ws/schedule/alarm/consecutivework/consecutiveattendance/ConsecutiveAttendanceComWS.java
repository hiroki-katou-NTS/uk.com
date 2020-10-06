package nts.uk.ctx.at.schedule.ws.schedule.alarm.consecutivework.consecutiveattendance;

import nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.consecutiveattendance.DeleteConsecutiveAttendanceComCommandHandler;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.consecutiveattendance.DeleteConsecutiveAttendanceComDto;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.consecutiveattendance.RegisterConsecutiveAttendanceComDto;
import nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.consecutiveattendance.RegisterConsecutiveAttendanceComCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("ctx/at/schedule/alarm/consecutivework/consecutiveattendance")
@Produces(MediaType.APPLICATION_JSON)
public class ConsecutiveAttendanceComWS {

    @Inject
    private RegisterConsecutiveAttendanceComCommandHandler registerConsecutiveAttendanceComHandler;

    @Inject
    private DeleteConsecutiveAttendanceComCommandHandler deleteConsecutiveAttendanceComHandler;

    @POST
    @Path("register")
    public void registerConsecutiveAttendanceCom(RegisterConsecutiveAttendanceComDto command){
        registerConsecutiveAttendanceComHandler.handle(command);
    }

    @POST
    @Path("delete")
    public void deleteConsecutiveAttendanceCom(DeleteConsecutiveAttendanceComDto command){

        deleteConsecutiveAttendanceComHandler.handle(command);
    }
}
