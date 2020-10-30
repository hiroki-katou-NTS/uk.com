package nts.uk.ctx.at.schedule.app.command.schedule.alarm.checksetting.banworktogether;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.*;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class UpdateBanWorkTogetherCommandHandler extends CommandHandler<UpdateBanWorkTogetherCommand> {

    @Inject
    private BanWorkTogetherRepository banWorkTogetherRepo;

    @Override
    protected void handle(CommandHandlerContext<UpdateBanWorkTogetherCommand> context) {
        UpdateBanWorkTogetherCommand command = context.getCommand();

        TargetOrgIdenInfor targetOrgIdenInfor = command.toDomainTargetOrgIdenInfor();

        BanWorkTogether banWorkTogether = this.toDomainBanWork(
                command.getApplicableTimeZoneCls(),
                command.getCode(),
                command.getName(),
                command.getTargetList(),
                command.getUpperLimit(),
                targetOrgIdenInfor
        );

        banWorkTogetherRepo.update(AppContexts.user().companyId(), banWorkTogether);
    }

    private BanWorkTogether toDomainBanWork(int applicableTimeZoneCls, String code, String name, List<String> targetList, int upperLimit, TargetOrgIdenInfor targetOrgIdenInfor) {
        return new BanWorkTogether(
                targetOrgIdenInfor,
                new BanWorkTogetherCode(code),
                new BanWorkTogetherName(name),
                EnumAdaptor.valueOf(applicableTimeZoneCls, ApplicableTimeZoneCls.class),
                targetList,
                (upperLimit - 1)
        );
    }
}
