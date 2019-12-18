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
import java.math.BigDecimal;
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
                new YearMonthPeriod(command.getStartYM() != null ? new YearMonth(command.getStartYM()) : YearMonth.of(1900, 01),
                        command.getEndYM() != null ? new YearMonth(command.getEndYM()) : YearMonth.of(9999, 12)));

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

        // TODO SoInsPayCategory set to null

        empSocialInsGradeInfoRepository.add(new EmpSocialInsGradeInfo(
                newHistID,
                command.getSocInsMonthlyRemune(),
                command.getCalculationAtr(),
                command.getHealInsStandMonthlyRemune(),
                command.getHealInsGrade(),
                command.getPensionInsStandCompenMonthly(),
                command.getPensionInsGrade()));

        return new PeregAddCommandResult(newHistID);
    }
}
