package nts.uk.screen.at.app.command.kdl.kdl016;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

import javax.ejb.Stateless;

@Stateless
public class RegisterSupportInforCommandHandler extends CommandHandlerWithResult<RegisterSupportInforCommand, RegisterSupportInforResult> {
    @Override
    protected RegisterSupportInforResult handle(CommandHandlerContext<RegisterSupportInforCommand> commandHandlerContext) {
        RegisterSupportInforCommand command = commandHandlerContext.getCommand();

        for (String employee : command.getEmployeeIds()) {
            //1.1 対象組織識別情報
            TargetOrgIdenInfor targetOrgIdenInfor = command.getOrgUnit() == TargetOrganizationUnit.WORKPLACE.value
                    ? TargetOrgIdenInfor.creatIdentifiWorkplace(command.getSupportDestinationId())
                    : TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(command.getSupportDestinationId());

            // TODO

        }
        return null;
    }
}
