package nts.uk.ctx.pr.shared.app.command.payrollgeneralpurposeparameters;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.EditMethod;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.SalGenParaValue;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.SalGenParaYearMonthHistoryService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class UpdateSalGenYearMonthHistoryCommandHandler extends CommandHandler<SalGenYearMonthHistoryCommand> {
    
    @Inject
    private SalGenParaYearMonthHistoryService salGenParaYearMonthHistoryService;

    @Override
    protected void handle(CommandHandlerContext<SalGenYearMonthHistoryCommand> context) {
        SalGenYearMonthHistoryCommand command = context.getCommand();
        String cId = AppContexts.user().companyId();
        String hisId = command.getHisId();
        if(EditMethod.UPDATE.value == command.getMode()) {
            YearMonth start = new YearMonth(command.getStart());
            YearMonth end = new YearMonth(command.getEnd());
            salGenParaYearMonthHistoryService.updateYearMonthHistory(cId,command.getParaNo(), hisId, start, end);
        } else {
            salGenParaYearMonthHistoryService.deleteYearMonthHistory(cId, hisId, command.getParaNo());
        }
//        ,new SalGenParaValue(command.getSalGenParaValueCommand().getHistoryId(),command.getSalGenParaValueCommand().getSelection(),command.getSalGenParaValueCommand().getAvailableAtr(),command.getSalGenParaValueCommand().getNumValue(),command.getSalGenParaValueCommand().getCharValue(),command.getSalGenParaValueCommand().getTimeValue(),command.getSalGenParaValueCommand().getTargetAtr()),

    }
}
