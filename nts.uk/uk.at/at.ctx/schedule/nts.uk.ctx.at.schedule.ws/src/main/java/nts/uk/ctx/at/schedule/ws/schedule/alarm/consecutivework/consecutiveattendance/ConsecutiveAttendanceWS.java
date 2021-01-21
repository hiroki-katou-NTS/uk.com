package nts.uk.ctx.at.schedule.ws.schedule.alarm.consecutivework.consecutiveattendance;

import nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.consecutiveattendance.*;
import nts.uk.ctx.at.schedule.app.query.schedule.alarm.consecutivework.consecutiveattendance.ConsecutiveAttendanceOrgQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("consecutivework/consecutiveattendance")
@Produces(MediaType.APPLICATION_JSON)
public class ConsecutiveAttendanceWS {

    @Inject
    private RegisterConsecutiveAttendanceComCommandHandler registerConsecutiveAttendanceComHandler;

    @Inject
    private DeleteConsecutiveAttendanceComCommandHandler deleteConsecutiveAttendanceComHandler;

    @Inject
    private ConsecutiveAttendanceOrgQuery consecutiveAttendanceOrgQuery;

    @Inject
    private RegisterConsecutiveAttendanceOrgCommandHandler registerConsecutiveAttendanceOrgHandler;

    @Inject
    private DeleteConsecutiveAttendanceOrgCommandHandler deleteConsecutiveAttendanceOrgHandler;

    /**
     * Screen G
     */
    @POST
    @Path("com/register")
    public void registerConsecutiveAttendanceCom(RegisterConsecutiveAttendanceComDto command){
        registerConsecutiveAttendanceComHandler.handle(command);
    }

    @POST
    @Path("com/delete")
    public void deleteConsecutiveAttendanceCom(DeleteConsecutiveAttendanceComDto command){

        deleteConsecutiveAttendanceComHandler.handle(command);
    }

    /**
     * Screen H
     */
    @POST
    @Path("org/getMaxConsDays")
    public void getMaxConsDays (GetMaxConsDaysParam param) {
        consecutiveAttendanceOrgQuery.getMaxConsDays(param.getUnit(), param.getWorkplaceId(), param.getWorkplaceGroupId());
    }

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
