package nts.uk.ctx.at.function.ws.alarmworkplace.checkcondition;

import nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition.*;
import nts.uk.ctx.at.function.app.find.alarmworkplace.checkcondition.AlarmCheckCdtWkpFinder;
import nts.uk.ctx.at.function.app.find.alarmworkplace.checkcondition.ExtractionCondtionsDto;
import nts.uk.ctx.at.function.app.find.alarmworkplace.checkcondition.InitScreenDto;
import nts.uk.ctx.at.function.app.find.alarmworkplace.checkcondition.ScreenContentDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("alarmworkplace/checkCondition")
@Produces(MediaType.APPLICATION_JSON)
public class AlarmWkpCheckConditionWs {

    @Inject
    private AlarmCheckCdtWkpFinder alarmCheckCdtWkpFinder;

    @Inject
    private AddAlarmCheckCdtWkpCommandHandler addAlarmCheckCdtWkpCommandHandler;

    @Inject
    private DeleteAlarmCheckCdtWkpCommandHandler deleteAlarmCheckCdtWkpCommandHandler;

    @Inject
    private UpdateAlarmCheckCdtWkpCommandHandler updateAlarmCheckCdtWkpCommandHandler;

    @POST
    @Path("getByCategory/{category}")
    public ScreenContentDto getByCategory(@PathParam("category") int category) {
        return this.alarmCheckCdtWkpFinder.getExtractItemsByCategory(category);
    }

    @POST
    @Path("getByCategoryItemCD")
    public ExtractionCondtionsDto getListPatternSettings(InitScreenDto param) {
        return this.alarmCheckCdtWkpFinder.getCategoryItemInfo(param);
    }

    @POST
    @Path("register")
    public void register(AddAlarmCheckCdtWkpCommand param) {
        this.addAlarmCheckCdtWkpCommandHandler.handle(param);
    }

    @POST
    @Path("delete")
    public void delete(DeleteAlarmCheckCdtWkpCommand param) {
        this.deleteAlarmCheckCdtWkpCommandHandler.handle(param);
    }

    @POST
    @Path("update")
    public void update(UpdateAlarmCheckCdtWkpCommand param) {
        this.updateAlarmCheckCdtWkpCommandHandler.handle(param);
    }

}
