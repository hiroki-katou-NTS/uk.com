package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empsocialinsgradehis;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Optional;

@Stateless
public class UpdateEmpSocialInsGradeInforCommandHandler
        extends CommandHandler<UpdateEmpSocialInsGradeInforCommand>
        implements PeregUpdateCommandHandler<UpdateEmpSocialInsGradeInforCommand> {

    @Inject
    private EmpSocialInsGradeRepository repository;

    @Inject
    private EmpSocialInsGradeService service;

    @Override
    public String targetCategoryCd() {
        return "CS00092";
    }

    @Override
    public Class<?> commandClass() {
        return UpdateEmpSocialInsGradeInforCommand.class;
    }

    @Override
    protected void handle(CommandHandlerContext<UpdateEmpSocialInsGradeInforCommand> context) {
        val command = context.getCommand();
        String companyId = AppContexts.user().companyId();
        // Update history table
        // In case of date yearMonthHistoryItems are exist in the screen
        if (command.getStartYM() != null) {
            EmpSocialInsGrade empSocialInsGrade = repository.getByEmpId(companyId, command.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("invalid EmpSocialInsGradeHis"));

            YearMonthHistoryItem itemToBeUpdated = empSocialInsGrade.getHistory().items()
                    .stream()
                    .filter(h -> h.identifier().equals(command.getHistoryId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("invalid EmpSocialInsGradeHis"));

            empSocialInsGrade.getHistory().changeSpan(itemToBeUpdated, new YearMonthPeriod(command.getStartYM().yearMonth(),
                    command.getEndYM() != null ? command.getEndYM().yearMonth() : GeneralDate.max().yearMonth()));

            EmpSocialInsGradeInfo info = new EmpSocialInsGradeInfo(
                    command.getHistoryId(),
                    command.getSocInsMonthlyRemune().intValue(),
                    command.getCalculationAtr().intValue(),
                    command.getHealInsStandMonthlyRemune() != null ? command.getHealInsStandMonthlyRemune().intValue() : null,
                    command.getHealInsGrade() != null ? command.getHealInsGrade().intValue() : null,
                    command.getPensionInsStandCompenMonthly() != null ? command.getPensionInsStandCompenMonthly().intValue() : null,
                    command.getPensionInsGrade() != null ? command.getPensionInsGrade().intValue() : null);

            service.update(empSocialInsGrade.getHistory(), info, itemToBeUpdated);
        }
    }

}
