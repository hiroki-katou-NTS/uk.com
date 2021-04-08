package nts.uk.screen.at.ws.ksm.ksm011.e;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.ksm011.e.command.RegisterSettingCommandHandler;
import nts.uk.screen.at.app.ksm011.e.command.RegisterPermissionSettingCommand;
import nts.uk.screen.at.app.ksm011.e.query.PermissionFuncCtrlSettingDto;
import nts.uk.screen.at.app.ksm011.e.query.PermissionFuncCtrlSettingProcessor;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * @author viet.tx
 */
@Path("screen/at/ksm/ksm011/e")
@Produces("application/json")
public class PermissionFuncCtrlSettingWS extends WebService {
    @Inject
    private PermissionFuncCtrlSettingProcessor processor;

    @Inject
    private RegisterSettingCommandHandler command;

    @POST
    @Path("getpermission/{roleId}")
    public PermissionFuncCtrlSettingDto getPermissionFuncControl(@PathParam("roleId") String roleId) {
        return this.processor.getPermissionFunctionSetting(roleId);
    }

    @POST
    @Path("register")
    public void registerPermissionSetting(RegisterPermissionSettingCommand command) {
        this.command.handle(command);
    }
}
