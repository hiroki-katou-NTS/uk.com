package nts.uk.ctx.at.aggregation.ws.scheduledailytable;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.aggregation.app.command.scheduledailytable.RegisterScheduleDailyTablePrintSettingCommandHandler;
import nts.uk.ctx.at.aggregation.app.command.scheduledailytable.DeleteScheduleDailyTablePrintSettingCommandHandler;
import nts.uk.ctx.at.aggregation.app.command.scheduledailytable.CopyScheduleDailyTablePrintSettingCommandHandler;
import nts.uk.ctx.at.aggregation.app.command.scheduledailytable.ScheduleDailyTablePrintSettingCommand;
import nts.uk.ctx.at.aggregation.app.command.scheduledailytable.ScheduleDailyTablePrintSettingCopyCommand;
import nts.uk.ctx.at.aggregation.app.find.scheduledailytable.ScheduleDailyTablePrintSettingDto;
import nts.uk.ctx.at.aggregation.app.find.scheduledailytable.ScheduleDailyTablePrintSettingFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("ctx/at/aggregation/scheduledailytable/printsetting")
@Produces("application/json")
public class ScheduleDailyTablePrintSettingWebService extends WebService {
    @Inject
    private ScheduleDailyTablePrintSettingFinder finder;

    @Inject
    private RegisterScheduleDailyTablePrintSettingCommandHandler registerHandler;

    @Inject
    private DeleteScheduleDailyTablePrintSettingCommandHandler deleteHandler;

    @Inject
    private CopyScheduleDailyTablePrintSettingCommandHandler copyHandler;

    @Path("get-all")
    @POST
    public List<ScheduleDailyTablePrintSettingDto> getAll() {
        return finder.getAll();
    }

    @Path("get")
    @POST
    public ScheduleDailyTablePrintSettingDto getOne(String code) {
        return finder.get(code);
    }

    @Path("register")
    @POST
    public void register(ScheduleDailyTablePrintSettingCommand command) {
        this.registerHandler.handle(command);
    }

    @Path("delete")
    @POST
    public void delete(String code) {
        this.deleteHandler.handle(code);
    }

    @Path("duplicate")
    @POST
    public void duplicate(ScheduleDailyTablePrintSettingCopyCommand command) {
        this.copyHandler.handle(command);
    }
}
