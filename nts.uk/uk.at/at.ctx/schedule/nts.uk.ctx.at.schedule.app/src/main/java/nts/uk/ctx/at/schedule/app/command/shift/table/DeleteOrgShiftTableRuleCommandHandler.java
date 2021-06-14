package nts.uk.ctx.at.schedule.app.command.shift.table;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForOrganizationRepo;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class DeleteOrgShiftTableRuleCommandHandler extends CommandHandler<RegisterOrganizationShiftTableRuleCommand> {
    @Inject
    private ShiftTableRuleForOrganizationRepo repo;

    @Override
    protected void handle(CommandHandlerContext<RegisterOrganizationShiftTableRuleCommand> commandHandlerContext) {
        RegisterOrganizationShiftTableRuleCommand command = commandHandlerContext.getCommand();
        if (command == null) {
            return;
        }
        String companyId = AppContexts.user().companyId();
        TargetOrganizationUnit targetOrgUnit = EnumAdaptor.valueOf(command.getOrganizationSelection().getUnitSelected(), TargetOrganizationUnit.class);
        String targetId = command.getOrganizationSelection().getOrganizationIdSelected();
        repo.delete(companyId, TargetOrgIdenInfor.createFromTargetUnit(targetOrgUnit, targetId));
    }
}
