package nts.uk.ctx.at.record.ws.reservation;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.reservation.reseritemset.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("bento/updateitemsetting")
@Produces("application/json")
public class ReseltemSettingService extends WebService {

    @Inject
    private UpdateReseItemSettingCommandHandler update;

    @Inject
    private CreateReseItemSettingCommandHandler add;

    @Inject
    private DeleteBentoCommandHandler delete;

    @POST
    @Path("add")
    public void add(CreateReseItemSettingCommand command) {
        this.add.handle(command);
    }

    @POST
    @Path("update")
    public void save(UpdateReseItemSettingCommand command) {
        this.update.handle(command);
    }

    @POST
    @Path("delete")
    public void update(DeleteBentoCommand command) {
        this.delete.handle(command);
    }

}
