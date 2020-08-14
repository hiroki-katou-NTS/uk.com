package nts.uk.ctx.at.record.ws.reservation;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.reservation.bento.DeleteBentoMenuCommand;
import nts.uk.ctx.at.record.app.command.reservation.bento.DeleteBentoMenuCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("bento/bentomenu")
@Produces("application/json")
public class BentomenuService extends WebService {

    @Inject
    private DeleteBentoMenuCommandHandler delete;

    @POST
    @Path("delete")
    public void update(DeleteBentoMenuCommand command) {
        this.delete.handle(command);
    }
}
