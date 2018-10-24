package nts.uk.ctx.pr.shared.app.command.payrollgeneralpurposeparameters;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class UpdateSalGenDateHistoryCommandHandler extends CommandHandler<SalGenDateHistoryCommand> {
    
    @Inject
    private SalGenParaDateHistoryService salGenParaDateHistoryService;

    @Override
    protected void handle(CommandHandlerContext<SalGenDateHistoryCommand> context) {
        SalGenDateHistoryCommand command = context.getCommand();
        String cId = AppContexts.user().companyId();
        String hisId = command.getHisId();
        if(EditMethod.UPDATE.value == command.getMode()) {
            salGenParaDateHistoryService.updateDateHistory(cId, command.getParaNo(), hisId, command.getStart(), command.getEnd());
        } else {
            salGenParaDateHistoryService.deleteYearMonthHistory(cId, hisId, command.getParaNo());
        }
    }
}
