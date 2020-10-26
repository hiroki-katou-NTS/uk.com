package nts.uk.ctx.at.schedule.ws.schedule.alarm.banholidaytogether;

import nts.uk.ctx.at.schedule.app.command.schedule.alarm.banholidaytogether.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("banholidaytogether")
@Produces(MediaType.APPLICATION_JSON)
public class BanHolidayTogetherWS {
    @Inject
    private InsertBanHolidayTogetherCommandHandler insertBanHolidayTogetherHandler;

    @Inject
    private UpdateBanHolidayTogetherCommandHandler updateBanHolidayTogetherHandler;

    @Inject
    private DeleteBanHolidayTogetherCommandHandler deleteBanHolidayTogetherHandler;

    @POST
    @Path("insert")
    public void registerConsecutiveAttendanceCom(InsertBanHolidayTogetherDto command){
        insertBanHolidayTogetherHandler.handle(command);
    }

    @POST
    @Path("update")
    public void registerConsecutiveAttendanceCom(UpdateBanHolidayTogetherDto command){
        updateBanHolidayTogetherHandler.handle(command);
    }

    @POST
    @Path("delete")
    public void registerConsecutiveAttendanceCom(DeleteBanHolidayTogetherDto command){
        deleteBanHolidayTogetherHandler.handle(command);
    }
}
