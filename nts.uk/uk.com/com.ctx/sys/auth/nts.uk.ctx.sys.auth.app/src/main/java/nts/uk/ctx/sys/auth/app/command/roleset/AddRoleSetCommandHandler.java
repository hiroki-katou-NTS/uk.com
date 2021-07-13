/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.app.command.roleset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.auth.dom.roleset.service.RoleSetService;

/**
* The Class AddRoleSetCommandHandler.
* @author HieuNV
*/
@Stateless
public class AddRoleSetCommandHandler extends CommandHandlerWithResult<RoleSetCommand, String> {

    @Inject
    private RoleSetService roleSetService;

    @Override
    protected String handle(CommandHandlerContext<RoleSetCommand> context) {
        RoleSetCommand command = context.getCommand();
//TODO Class RoleSet have @AllContructor since there is, I deleted it this time, please correct
/*        RoleSet roleSetDom = new RoleSet(command.getRoleSetCd()
                , AppContexts.user().companyId()
                , command.getRoleSetName()
                , command.isApprovalAuthority() ? ApprovalAuthority.HasRight : ApprovalAuthority.HasntRight
                , command.getOfficeHelperRoleId()
                , command.getMyNumberRoleId()
                , command.getHumanResourceRoleId()
                , command.getPersonInfRoleId()
                , command.getEmploymentRoleId()
                , command.getSalaryRoleId());

        //アルゴリズム「新規登録」を実行する - Execute the algorithm "new registration"
   			this.roleSetService.registerRoleSet(roleSetDom);*/

        return command.getRoleSetCd();
    }
}
