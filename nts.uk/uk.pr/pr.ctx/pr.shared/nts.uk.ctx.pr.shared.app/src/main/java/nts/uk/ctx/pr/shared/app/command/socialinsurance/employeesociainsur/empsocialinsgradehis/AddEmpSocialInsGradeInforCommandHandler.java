package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empsocialinsgradehis;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;
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
    private EmpSocialInsGradeHisRepository empSocialInsGradeHisRepository;

    @Inject
    private EmpSocialInsGradeInfoRepository empSocialInsGradeInfoRepository;

    @Inject
    private EmpSocialInsGradeHisService empSocialInsGradeHisService;

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
                new YearMonthPeriod(command.getStartYM() != null ? command.getStartYM().yearMonth() : YearMonth.of(1900, 1),
                        command.getEndYM() != null ? command.getEndYM().yearMonth() : YearMonth.of(9999, 12)));

        Optional<EmpSocialInsGradeHis> existHist = empSocialInsGradeHisRepository.getEmpSocialInsGradeHisBySId(companyId,
                command.getEmployeeId());

        EmpSocialInsGradeHis itemtoBeAdded = new EmpSocialInsGradeHis(companyId, command.getEmployeeId(),
                new ArrayList<>());
        // In case of exist history of this employee
        if (existHist.isPresent()) {
            itemtoBeAdded = existHist.get();
        }
        itemtoBeAdded.add(dateItem);

        empSocialInsGradeHisService.add(itemtoBeAdded);

        empSocialInsGradeInfoRepository.add(new EmpSocialInsGradeInfo(
                newHistID,
                command.getSocInsMonthlyRemune().intValue(),
                command.getCalculationAtr().intValue(),
                command.getHealInsStandMonthlyRemune() != null ? command.getHealInsStandMonthlyRemune().intValue() : null,
                command.getHealInsGrade() != null ? command.getHealInsGrade().intValue() : null,
                command.getPensionInsStandCompenMonthly() != null ? command.getPensionInsStandCompenMonthly().intValue() : null,
                command.getPensionInsGrade() != null ? command.getPensionInsGrade().intValue() : null));

        return new PeregAddCommandResult(newHistID);
    }
}
