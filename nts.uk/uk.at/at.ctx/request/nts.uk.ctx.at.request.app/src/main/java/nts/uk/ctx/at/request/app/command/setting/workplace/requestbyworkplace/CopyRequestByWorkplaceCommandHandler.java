package nts.uk.ctx.at.request.app.command.setting.workplace.requestbyworkplace;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.workplace.requestbyworkplace.RequestByWorkplace;
import nts.uk.ctx.at.request.dom.setting.workplace.requestbyworkplace.RequestByWorkplaceRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class CopyRequestByWorkplaceCommandHandler extends CommandHandler<CopyRequestByWorkplaceCommand> {
    @Inject
    private RequestByWorkplaceRepository requestByWorkplaceRepo;


    @Override
    protected void handle(CommandHandlerContext<CopyRequestByWorkplaceCommand> commandHandlerContext) {
        String companyId = AppContexts.user().companyId();
        CopyRequestByWorkplaceCommand command = commandHandlerContext.getCommand();
        requestByWorkplaceRepo.findByCompanyAndWorkplace(companyId, command.getWorkplaceId()).ifPresent(source -> {
            command.getTargetWorkplaceIds().forEach(targetId -> {
                requestByWorkplaceRepo.findByCompanyAndWorkplace(companyId, targetId).ifPresent(target -> {
                    requestByWorkplaceRepo.delete(companyId, targetId);
                });
                RequestByWorkplace target = new RequestByWorkplace(companyId, targetId, source.getApprovalFunctionSet());
                requestByWorkplaceRepo.save(target);
            });
        });
    }
}
