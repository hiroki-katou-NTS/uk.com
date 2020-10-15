package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.optionalitemappsetting;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemAppSetRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class DeleteOptinalItemAppSetCommandHandler extends CommandHandler<String> {

    @Inject
    private OptionalItemAppSetRepository repository;

    @Override
    protected void handle(CommandHandlerContext<String> commandHandlerContext) {
        String companyId = AppContexts.user().companyId();
        String code = commandHandlerContext.getCommand();
        repository.delete(companyId, code);
    }
}
