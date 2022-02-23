package nts.uk.ctx.at.function.app.command.modifyanyperiod;


import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.BusinessTypeSFormatDailyRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AddModifyAnyPeriodCommandHandler extends CommandHandler<AddModifyAnyPeriodCommand> {

    @Inject
    private BusinessTypeSFormatDailyRepository businessTypeFormatMDailyRepository;
    @Override
    protected void handle(CommandHandlerContext<AddModifyAnyPeriodCommand> commandHandlerContext) {
        AddModifyAnyPeriodCommand command = commandHandlerContext.getCommand();
        String cid = AppContexts.user().companyId();
        String businessCode = command.getBusinessCode();
        boolean exit = businessTypeFormatMDailyRepository.checkExistData(cid,businessCode);
        if(exit){
            throw  new BusinessException("Msg_3");
        }
    }
}
