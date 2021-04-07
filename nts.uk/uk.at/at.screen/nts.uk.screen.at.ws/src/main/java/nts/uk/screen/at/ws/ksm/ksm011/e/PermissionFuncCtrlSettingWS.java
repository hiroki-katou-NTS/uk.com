package nts.uk.screen.at.ws.ksm.ksm011.e;

import nts.uk.screen.at.app.ksm011.e.command.RegisterConfigurationSettingCommand;
import nts.uk.screen.at.app.ksm011.e.command.RegisterConfigurationSettingDto;
import nts.uk.screen.at.app.ksm011.e.query.PermissionFuncCtrlSettingDto;
import nts.uk.screen.at.app.ksm011.e.query.PermissionFuncCtrlSettingProcessor;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author viet.tx
 */
@Path("screen/at/ksm/ksm011/e")
@Produces("application/json")
public class PermissionFuncCtrlSettingWS {
    @Inject
    private PermissionFuncCtrlSettingProcessor processor;

    @Inject
    private RegisterConfigurationSettingCommand command;

    @POST
    @Path("getpermission")
    public PermissionFuncCtrlSettingDto getPermissionFuncControl(String roleId) {
        return processor.getPermissionFunctionSetting(roleId);
    }

    @POST
    @Path("register")
    public void registerSetting(RegisterConfigurationSettingDto request) {
        command.registerConfigurationSetting(request);
    }
}
