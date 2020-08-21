package nts.uk.ctx.at.record.ws.reservation;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.reservation.reseritemset.DeleteBentoCommand;
import nts.uk.ctx.at.record.app.command.reservation.reseritemset.DeleteBentoCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("bento/bentomenu")
@Produces("application/json")
public class BentomenuService extends WebService {

    @Inject
    private DeleteBentoCommandHandler delete;

    @POST
    @Path("delete")
    public void update(DeleteBentoCommand command) {
        this.delete.handle(command);
    }
}
