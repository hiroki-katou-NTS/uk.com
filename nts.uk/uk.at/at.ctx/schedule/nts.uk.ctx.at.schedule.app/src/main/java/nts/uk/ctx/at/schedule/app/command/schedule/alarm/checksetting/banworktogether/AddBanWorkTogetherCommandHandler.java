package nts.uk.ctx.at.schedule.app.command.schedule.alarm.checksetting.banworktogether;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.*;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class AddBanWorkTogetherCommandHandler extends CommandHandler<AddBanWorkTogetherCommand> {

    @Inject
    private BanWorkTogetherRepository banWorkTogetherRepo;

    @Override
    protected void handle(CommandHandlerContext<AddBanWorkTogetherCommand> context) {
        AddBanWorkTogetherCommand command = context.getCommand();

        TargetOrgIdenInfor targetOrgIdenInfor = new TargetOrgIdenInfor(
                EnumAdaptor.valueOf(command.getTargetOrgIdenInfor().getUnit(), TargetOrganizationUnit.class),
                Optional.of(command.getTargetOrgIdenInfor().getWorkplaceId()),
                Optional.of(command.getTargetOrgIdenInfor().getWorkplaceGroupId())
        );

        BanWorkTogether banWorkTogether = new BanWorkTogether(
                targetOrgIdenInfor,
                new BanWorkTogetherCode(command.getCode()),
                new BanWorkTogetherName(command.getName()),
                EnumAdaptor.valueOf(command.getApplicableTimeZoneCls(), ApplicableTimeZoneCls.class),
                command.getTargetList(),
                new MaxOfNumberEmployeeTogether(command.getUpperLimit() - 1)
        );

        banWorkTogetherRepo.insert(AppContexts.user().companyId(), banWorkTogether);
    }
}
