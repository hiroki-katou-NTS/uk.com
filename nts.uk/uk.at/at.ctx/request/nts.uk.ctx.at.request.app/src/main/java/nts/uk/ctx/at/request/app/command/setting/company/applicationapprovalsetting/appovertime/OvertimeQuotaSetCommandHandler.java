package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSetRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
@Transactional
public class OvertimeQuotaSetCommandHandler extends CommandHandler<List<OvertimeQuotaSetUseCommand>> {
    @Inject
    private OvertimeAppSetRepository overtimeAppSetRepo;

    @Override
    protected void handle(CommandHandlerContext<List<OvertimeQuotaSetUseCommand>> commandHandlerContext) {
        String companyId = AppContexts.user().companyId();
        overtimeAppSetRepo.saveOvertimeQuotaSet(companyId, OvertimeQuotaSetUseCommand.toDomains(commandHandlerContext.getCommand()));
    }
}
