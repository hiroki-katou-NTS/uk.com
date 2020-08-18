package nts.uk.ctx.at.record.ws.reservation;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.reservation.reseritemset.UpdateReseItemSettingCommand;
import nts.uk.ctx.at.record.app.command.reservation.reseritemset.UpdateReseItemSettingCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("bento/updateItemSetting")
@Produces("application/json")
public class UpdateReseltemSettingService extends WebService {

    @Inject
    private UpdateReseItemSettingCommandHandler update;

    @POST
    @Path("update")
    public void save(UpdateReseItemSettingCommand command) {
        this.update.handle(command);
    }
}
