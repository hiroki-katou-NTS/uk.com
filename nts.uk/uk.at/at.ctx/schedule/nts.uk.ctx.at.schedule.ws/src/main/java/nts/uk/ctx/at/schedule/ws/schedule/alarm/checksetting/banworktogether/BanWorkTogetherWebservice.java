package nts.uk.ctx.at.schedule.ws.schedule.alarm.checksetting.banworktogether;

import nts.uk.ctx.at.schedule.app.command.schedule.alarm.checksetting.banworktogether.*;
import nts.uk.ctx.at.schedule.app.query.schedule.alarm.checksetting.banworktogether.BanWorkTogetherDto;
import nts.uk.ctx.at.schedule.app.query.schedule.alarm.checksetting.banworktogether.GetBanWorkDto;
import nts.uk.ctx.at.schedule.app.query.schedule.alarm.checksetting.banworktogether.GetBanWorkTogetherByCodeQuery;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.BanWorkTogether;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("at/schedule/alarm/banworktogether")
@Produces("application/json")
public class BanWorkTogetherWebservice {

    @Inject
    private GetBanWorkTogetherByCodeQuery getBanWorkTogetherByCodeQuery;

    @Inject
    private AddBanWorkTogetherCommandHandler addBanWorkTogetherCommandHandler;

    @Inject
    private UpdateBanWorkTogetherCommandHandler updateBanWorkTogetherCommandHandler;

    @Inject
    private DeleteBanWorkTogetherCommandHandler deleteBanWorkTogetherCommandHandler;

    @POST
    @Path("getByCodeAndWorkplace")
    public BanWorkTogetherDto getBanWorkTogetherByCode(GetBanWorkDto param) {
        return this.getBanWorkTogetherByCodeQuery.get(param);
    }

    @POST
    @Path("register")
    public void register(AddBanWorkTogetherCommand param) {
        this.addBanWorkTogetherCommandHandler.handle(param);
    }

    @POST
    @Path("update")
    public void register(UpdateBanWorkTogetherCommand param) {
        this.updateBanWorkTogetherCommandHandler.handle(param);
    }

    @POST
    @Path("delete")
    public void delete(DeleteBanWorkTogetherCommand param) {
        this.deleteBanWorkTogetherCommandHandler.handle(param);
    }
}
