package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empsocialinsgradehis;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Optional;

@Stateless
public class AddEmpSocialInsGradeInforCommandHandler
        extends CommandHandlerWithResult<AddEmpSocialInsGradeInforCommand, PeregAddCommandResult>
        implements PeregAddCommandHandler<AddEmpSocialInsGradeInforCommand> {

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
        return AddEmpSocialInsGradeInforCommand.class;
    }

    @Override
    protected PeregAddCommandResult handle(CommandHandlerContext<AddEmpSocialInsGradeInforCommand> context) {
        val command = context.getCommand();
        String companyId = AppContexts.user().companyId();

        String newHistID = IdentifierUtil.randomUniqueId();
        YearMonthHistoryItem dateItem = new YearMonthHistoryItem(newHistID,
                new YearMonthPeriod(command.getStartYM() != null ? command.getStartYM().yearMonth() : GeneralDate.min().yearMonth(),
                        command.getEndYM() != null ? command.getEndYM().yearMonth() : GeneralDate.max().yearMonth()));

        Optional<EmpSocialInsGrade> existHist = repository.getByEmpId(companyId, command.getEmployeeId());
        EmpSocialInsGradeHis itemToBeAdded = new EmpSocialInsGradeHis(companyId, command.getEmployeeId(), new ArrayList<>());
        EmpSocialInsGradeInfo info = new EmpSocialInsGradeInfo(
                newHistID,
                command.getSocInsMonthlyRemune().intValue(),
                command.getCalculationAtr().intValue(),
                command.getHealInsStandMonthlyRemune() != null ? command.getHealInsStandMonthlyRemune().intValue() : null,
                command.getHealInsGrade() != null ? command.getHealInsGrade().intValue() : null,
                command.getPensionInsStandCompenMonthly() != null ? command.getPensionInsStandCompenMonthly().intValue() : null,
                command.getPensionInsGrade() != null ? command.getPensionInsGrade().intValue() : null);

        if (existHist.isPresent()) {
            itemToBeAdded = existHist.get().getHistory();
        }
        itemToBeAdded.add(dateItem);
        service.add(itemToBeAdded, info);
        return new PeregAddCommandResult(newHistID);
    }
}
