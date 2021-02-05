package nts.uk.ctx.at.shared.app.command.workrule.weekmanage;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagement;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagementRepo;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class WeekRuleManagementRegisterCommandHandler extends CommandHandler<WeekRuleManagementRegisterCommand> {
    @Inject
    private WeekRuleManagementRepo repo;

    @Override
    protected void handle(CommandHandlerContext<WeekRuleManagementRegisterCommand> commandHandlerContext) {
        String companyId = AppContexts.user().companyId();
        Optional<WeekRuleManagement> domain = repo.find(companyId);
        if (domain.isPresent()) {
            repo.update(WeekRuleManagement.of(companyId, commandHandlerContext.getCommand().getDayOfWeek()));
        } else {
            repo.add(WeekRuleManagement.of(companyId, commandHandlerContext.getCommand().getDayOfWeek()));
        }
    }
}
