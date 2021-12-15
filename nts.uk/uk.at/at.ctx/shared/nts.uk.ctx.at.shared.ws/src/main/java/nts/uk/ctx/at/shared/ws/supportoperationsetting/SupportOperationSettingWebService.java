package nts.uk.ctx.at.shared.ws.supportoperationsetting;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.supportoperationsetting.RegisterSupportOperationSettingCommand;
import nts.uk.ctx.at.shared.app.command.supportoperationsetting.RegisterSupportOperationSettingCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("at/share/supportOperationSetting")
@Produces("application/json")
public class SupportOperationSettingWebService extends WebService {

    @Inject
    private RegisterSupportOperationSettingCommandHandler registerSupportOperationSettingCommandHandler;

    @POST
    @Path("updateSupportOperationSetting")
    public void updateSupportOperationSetting(RegisterSupportOperationSettingCommand command){
        registerSupportOperationSettingCommandHandler.handle(command);
    }
}
