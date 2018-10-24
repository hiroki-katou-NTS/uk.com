package nts.uk.ctx.pr.core.app.command.wageprovision.averagewagecalculationset;

import lombok.experimental.var;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.app.find.wageprovision.averagewagecalculationset.AverageWageCalculationSetDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.averagewagecalculationset.DataDisplayAverageDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.averagewagecalculationset.StatementDto;
import nts.uk.ctx.pr.core.dom.wageprovision.averagewagecalculationset.AverageWageCalculationSet;
import nts.uk.ctx.pr.core.dom.wageprovision.averagewagecalculationset.AverageWageCalculationSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.PaymentItemSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset.TimeItemSetRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Transactional
public class UpdateAverageWageCalculationSetCommand extends CommandHandler<DataDisplayAverageCommand> {
    @Inject
    private PaymentItemSetRepository paymentItemSetRepository;
    @Inject
    private TimeItemSetRepository timeItemSetRepository;
    @Inject
    private AverageWageCalculationSetRepository averageWageCalculationSetRepository;

    @Override
    protected void handle(CommandHandlerContext<DataDisplayAverageCommand> commandHandlerContext) {
        String cid = AppContexts.user().companyId();
        DataDisplayAverageCommand command = commandHandlerContext.getCommand();

        List<String> lstPaymentItemSalaryItemId = command.getLstStatemetPaymentItem().stream().map(item -> item.getSalaryItemId()).collect(Collectors.toList());
        paymentItemSetRepository.updateAll(lstPaymentItemSalaryItemId);
        int selectWorkDays = command.getAverageWageCalculationSet().getObtainAttendanceDays();
        if (selectWorkDays == 0) {
            List<String> lstStatemetAttendanceItemSalaryItemId = command.getLstStatemetAttendanceItem().stream().map(item -> item.getSalaryItemId()).collect(Collectors.toList());
            timeItemSetRepository.updateAll(lstStatemetAttendanceItemSalaryItemId);
        }
        AverageWageCalculationSetCommand average = command.getAverageWageCalculationSet();
        AverageWageCalculationSet averageWageCalculationSet = new AverageWageCalculationSet(cid, average.getExceptionFormula(),
        average.getObtainAttendanceDays(), average.getDaysFractionProcessing(), average.getDecimalPointCutoffSegment());
        averageWageCalculationSetRepository.update(averageWageCalculationSet);
    }
}
