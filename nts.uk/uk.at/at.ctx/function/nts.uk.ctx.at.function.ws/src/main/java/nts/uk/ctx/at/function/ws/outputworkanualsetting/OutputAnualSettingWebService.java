package nts.uk.ctx.at.function.ws.outputworkanualsetting;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.outputworkanualsetting.*;
import nts.uk.ctx.at.function.app.command.outputworkstatustable.DuplicateSettingDetailCommand;
import nts.uk.ctx.at.function.app.query.outputworkstatustable.CheckDailyPerformAuthorQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("at/function/kwr004")
@Produces("application/json")
public class OutputAnualSettingWebService extends WebService {

    @Inject
    private CreateAnualWorkledgerSettingCommandHandler create;

    @Inject
    private UpdateAnualWorkledgerSettingCommandHandler update;

    @Inject
    private DuplicateAnualWorkledgerSettingCommandHandler duplicate;

    @Inject
    private DeleteAnualWorkledgerSettingCommandHandler delete;

    @POST
    @Path("create")
    public void create(CreateAnualWorkledgerSettingCommand command) {
        this.create.handle(command);
    }

    @POST
    @Path("update")
    public void update(UpdateAnualWorkledgerSettingCommand command) {
        this.update.handle(command);
    }

    @POST
    @Path("duplicate")
    public void duplicate(DuplicateAnualWorkledgerSettingCommand command) {
        this.duplicate.handle(command);
    }

    @POST
    @Path("delete")
    public void delete(DeleteAnualWorkledgerSettingCommand command) {
        this.delete.handle(command);
    }

}