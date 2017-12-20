/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.ws.sys.auth.roleset;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.screen.com.app.command.sys.auth.roleset.DeleteRoleSetCommandBase;
import nts.uk.screen.com.app.command.sys.auth.roleset.DeleteRoleSetCommandBaseHandler;
import nts.uk.screen.com.app.command.sys.auth.roleset.RegisterRoleSetCommandBaseHandler;
import nts.uk.screen.com.app.command.sys.auth.roleset.RoleSetCommandBase;
import nts.uk.screen.com.app.command.sys.auth.roleset.UpdateRoleSetCommandBaseHandler;

/**
* The Class CAS011Webservice.
* @author HieuNV
*/
@Path("screen/sys/auth/cas011")
@Produces("application/json")
public class CAS011Webservice extends WebService {

    @Inject
    private RegisterRoleSetCommandBaseHandler registerRoleSetCommandBaseHandler;

    @Inject
    private UpdateRoleSetCommandBaseHandler updateRoleSetCommandBaseHandler;

    @Inject
    private DeleteRoleSetCommandBaseHandler deleteRoleSetCommandBaseHandler;

    /**
     * Execute register a new role set. Include register RoleSet and RoleSetLinkWebMenu.
     * @param command
     * @return
     */
    @POST
    @Path("addroleset")
    public JavaTypeResult<String> addRoleSet(RoleSetCommandBase command) {
        return new JavaTypeResult<String>(this.registerRoleSetCommandBaseHandler.handle(command));
    }

    /**
     * Execute update the role set. Include update RoleSet and RoleSetLinkWebMenu.
     * @param command
     */
    @POST
    @Path("updateroleset")
    public void updateRoleSet(RoleSetCommandBase command) {
        this.updateRoleSetCommandBaseHandler.handle(command);
    }

    /**
     * Execute delete the role set. Include delete RoleSet and RoleSetLinkWebMenu.
     * @param command
     */
    @POST
    @Path("deleteroleset")
    public void removeSelectionItem(DeleteRoleSetCommandBase command) {
        this.deleteRoleSetCommandBaseHandler.handle(command);
    }
}
