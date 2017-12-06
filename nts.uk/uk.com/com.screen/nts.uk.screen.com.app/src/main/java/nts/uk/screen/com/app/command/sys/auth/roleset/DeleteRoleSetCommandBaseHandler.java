/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.app.command.sys.auth.roleset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.auth.app.command.roleset.DeleteRoleSetCommand;
import nts.uk.ctx.sys.auth.app.command.roleset.DeleteRoleSetCommandHandler;
import nts.uk.ctx.sys.portal.app.command.webmenu.webmenulinking.DeleteRoleSetLinkWebMenuCommand;
import nts.uk.ctx.sys.portal.app.command.webmenu.webmenulinking.DeleteRoleSetLinkWebMenuCommandHandler;

/**
* The Class DeleteRoleSetCommandBaseHandler.
* @author HieuNV
*/
@Stateless
@javax.transaction.Transactional
public class DeleteRoleSetCommandBaseHandler extends CommandHandlerWithResult<DeleteRoleSetCommandBase, String> {

    @Inject
    private DeleteRoleSetCommandHandler deleteRoleSetCommandHandler;

    @Inject
    private DeleteRoleSetLinkWebMenuCommandHandler deleteRoleSetLinkWebMenuCommandHandler;

    /**
     *         //アルゴリズム「削除」を実行する - Execute algorithm "delete"
     */
    @Override
    protected String handle(CommandHandlerContext<DeleteRoleSetCommandBase> context) {
        DeleteRoleSetCommandBase command = context.getCommand();
        // delete Role set
        DeleteRoleSetCommand deleteRoleSetCommand = new DeleteRoleSetCommand(command.getRoleSetCd());
        this.deleteRoleSetCommandHandler.handle(deleteRoleSetCommand);

        // delete Role set link Web menu
        DeleteRoleSetLinkWebMenuCommand deleteRoleSetLinkWebMenuCommand = new DeleteRoleSetLinkWebMenuCommand(command.getRoleSetCd());
        this.deleteRoleSetLinkWebMenuCommandHandler.handle(deleteRoleSetLinkWebMenuCommand);

        return command.getRoleSetCd();
    }
}
