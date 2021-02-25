package nts.uk.ctx.at.record.ws.bento;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.reservation.bento.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author Chinh.hm
 */
@Path("bento/bentomenuhist")
@Produces("application/json")
public class BentoMenuHistWebService extends WebService {
    @Inject
    private BentoMenuHistCommandHandler bentoMenuHistCommandHandler;

    @Inject
    private UpdateBentoMenuHistCommandHandler updateBentoMenuHistCommandHandler;
    @Inject
    private DeleteBentoMenuHistCommandhHandler deleteBentoMenuHistCommandhHandler;

    @Path("add")
    @POST
    public void add(BentoMenuHistCommand command) {

        this.bentoMenuHistCommandHandler.handle(command);
    }

    @Path("update")
    @POST
    public void update(UpdateBentoMenuHistCommand command) {

        this.updateBentoMenuHistCommandHandler.handle(command);
    }

    @Path("delete")
    @POST
    public void delete(DeleteBentoMenuHistCommand command) {
        this.deleteBentoMenuHistCommandhHandler.handle(command);
    }
}
