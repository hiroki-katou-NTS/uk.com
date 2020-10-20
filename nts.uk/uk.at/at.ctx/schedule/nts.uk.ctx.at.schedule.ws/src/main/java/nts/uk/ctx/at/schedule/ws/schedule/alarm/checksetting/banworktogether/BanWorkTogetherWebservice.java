package nts.uk.ctx.at.schedule.ws.schedule.alarm.checksetting.banworktogether;

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

    @POST
    @Path("getByCodeAndWorkplace")
    public BanWorkTogetherDto getBanWorkTogetherByCode(GetBanWorkDto param) {
        return this.getBanWorkTogetherByCodeQuery.get(param);
    }

}
