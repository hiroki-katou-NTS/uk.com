package nts.uk.ctx.at.function.ws.supportworklist.aggregationsetting;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.supportworklist.aggregationsetting.SupportWorkAggregationSettingCommand;
import nts.uk.ctx.at.function.app.command.supportworklist.aggregationsetting.SupportWorkAggregationSettingRegisterCommandHandler;
import nts.uk.ctx.at.function.app.find.supportworklist.aggregationsetting.SupportWorkAggregationSettingDto;
import nts.uk.ctx.at.function.app.find.supportworklist.aggregationsetting.SupportWorkAggregationSettingFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("at/function/supportworklist/aggregationsetting")
@Produces(MediaType.APPLICATION_JSON)
public class SupportWorkAggregationSettingWebService extends WebService {
    @Inject
    private SupportWorkAggregationSettingFinder finder;

    @Inject
    private SupportWorkAggregationSettingRegisterCommandHandler commandHandler;

    @POST
    @Path("get")
    public SupportWorkAggregationSettingDto getSetting() {
        return finder.getSetting();
    }

    @POST
    @Path("register")
    public void register(SupportWorkAggregationSettingCommand command) {
        commandHandler.handle(command);
    }
}
