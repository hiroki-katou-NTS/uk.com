package nts.uk.ctx.at.request.app.command.setting.company.appreasonstandard;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandardRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class SaveReasonTypeItemCommandHandler extends CommandHandler<List<ReasonTypeItemCommand>> {
    @Inject
    private AppReasonStandardRepository appReasonStandardRepo;

    @Override
    protected void handle(CommandHandlerContext<List<ReasonTypeItemCommand>> commandHandlerContext) {
        String companyId = AppContexts.user().companyId();
        List<ReasonTypeItemCommand> command = commandHandlerContext.getCommand();
        command.forEach(c -> {
            appReasonStandardRepo.saveReasonTypeItem(companyId, c.getAppType(), c.getHolidayAppType(), c.toDomain());
        });
    }
}
