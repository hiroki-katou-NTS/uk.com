package nts.uk.ctx.at.request.app.command.setting.workplace.requestbycompany;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.app.command.setting.workplace.appuseset.ApplicationUseSetCommand;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApprovalFunctionSet;
import nts.uk.ctx.at.request.dom.setting.workplace.requestbycompany.RequestByCompany;
import nts.uk.ctx.at.request.dom.setting.workplace.requestbycompany.RequestByCompanyRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class SaveRequestByCompanyCommandHandler extends CommandHandler<List<ApplicationUseSetCommand>> {
    @Inject
    private RequestByCompanyRepository requestByCompanyRepo;

    /**
     * 登録処理
     * @param commandHandlerContext
     */
    @Override
    protected void handle(CommandHandlerContext<List<ApplicationUseSetCommand>> commandHandlerContext) {
        String companyId = AppContexts.user().companyId();
        List<ApplicationUseSetCommand> commands = commandHandlerContext.getCommand();
        requestByCompanyRepo.save(
                new RequestByCompany(
                        companyId,
                        new ApprovalFunctionSet(commands.stream().map(ApplicationUseSetCommand::toDomain).collect(Collectors.toList()))
                )
        );
    }
}
