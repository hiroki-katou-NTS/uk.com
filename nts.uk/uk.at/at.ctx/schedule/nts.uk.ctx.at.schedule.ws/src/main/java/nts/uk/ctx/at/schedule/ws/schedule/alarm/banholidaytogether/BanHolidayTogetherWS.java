package nts.uk.ctx.at.schedule.ws.schedule.alarm.banholidaytogether;

import nts.uk.ctx.at.schedule.app.command.schedule.alarm.banholidaytogether.*;
import nts.uk.ctx.at.schedule.app.query.schedule.alarm.banholidaytogether.BanHolidayTogetherCodeNameDto;
import nts.uk.ctx.at.schedule.app.query.schedule.alarm.banholidaytogether.BanHolidayTogetherQuery;
import nts.uk.ctx.at.schedule.app.query.schedule.alarm.banholidaytogether.BanHolidayTogetherQueryDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("banholidaytogether")
@Produces(MediaType.APPLICATION_JSON)
public class BanHolidayTogetherWS {
    @Inject
    private BanHolidayTogetherQuery banHolidayTogetherQuery;

    @Inject
    private InsertBanHolidayTogetherCommandHandler insertBanHolidayTogetherHandler;

    @Inject
    private UpdateBanHolidayTogetherCommandHandler updateBanHolidayTogetherHandler;

    @Inject
    private DeleteBanHolidayTogetherCommandHandler deleteBanHolidayTogetherHandler;

    @POST
    @Path("getAllBanHolidayTogether")
    public List<BanHolidayTogetherCodeNameDto> getAllBanHolidayTogether(GetAllBanHolidayTogether param) {
        return banHolidayTogetherQuery.getAllBanHolidayTogether(param.getUnit(), param.getWorkplaceId(), param.getWorkplaceGroupId());
    }

    @POST
    @Path("getBanHolidayByCode")
    public BanHolidayTogetherQueryDto getBanHolidayByCode(GetBanHolidayByCodeParam param) {
        return banHolidayTogetherQuery.getBanHolidayByCode(param.getUnit(), param.getWorkplaceId(), param.getWorkplaceGroupId(), param.getBanHolidayTogetherCode());
    }

    @POST
    @Path("insert")
    public void insertBanHolidayTogether(InsertBanHolidayTogetherDto command) {
        insertBanHolidayTogetherHandler.handle(command);
    }

    @POST
    @Path("update")
    public void updateBanHolidayTogether(UpdateBanHolidayTogetherDto command) {
        updateBanHolidayTogetherHandler.handle(command);
    }

    @POST
    @Path("delete")
    public void deleteBanHolidayTogether(DeleteBanHolidayTogetherDto command) {
        deleteBanHolidayTogetherHandler.handle(command);
    }
}
