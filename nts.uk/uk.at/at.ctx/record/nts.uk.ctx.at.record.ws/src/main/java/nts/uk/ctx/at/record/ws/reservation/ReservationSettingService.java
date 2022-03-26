package nts.uk.ctx.at.record.ws.reservation;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.reservation.bento.BentoReserveSettingCommand;
import nts.uk.ctx.at.record.app.command.reservation.bento.BentoReserveSettingCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("bento/bentomenusetting")
@Produces("application/json")
public class ReservationSettingService extends WebService{

    @Inject
    private BentoReserveSettingCommandHandler register;

    @POST
    @Path("save")
    public void save(BentoReserveSettingCommand command) {
        this.register.handle(command);
    }
}
