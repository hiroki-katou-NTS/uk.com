package nts.uk.ctx.at.request.app.command.setting.company.appreasonstandard;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandardRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class DeleteReasonTypeItemCommandHandler extends CommandHandler<ReasonTypeItemCommand> {
    @Inject
    private AppReasonStandardRepository appReasonStandardRepo;

    @Override
    protected void handle(CommandHandlerContext<ReasonTypeItemCommand> commandHandlerContext) {
        String companyId = AppContexts.user().companyId();
        ReasonTypeItemCommand command = commandHandlerContext.getCommand();
        appReasonStandardRepo.deleteReasonTypeItem(companyId, command.getAppType(), command.getHolidayAppType(), command.getReasonCode());
    }
}
