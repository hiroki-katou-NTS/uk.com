package nts.uk.ctx.at.request.app.command.setting.workplace.requestbyworkplace;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.workplace.requestbyworkplace.RequestByWorkplaceRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class SaveRequestByWorkplaceCommandHandler extends CommandHandler<RequestByWorkplaceCommand> {
    @Inject
    private RequestByWorkplaceRepository requestByWorkplaceRepo;


    @Override
    protected void handle(CommandHandlerContext<RequestByWorkplaceCommand> commandHandlerContext) {
        String companyId = AppContexts.user().companyId();
        RequestByWorkplaceCommand command = commandHandlerContext.getCommand();
        requestByWorkplaceRepo.save(command.toDomain(companyId));
    }
}
