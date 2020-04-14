package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.*;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Optional;

@Stateless
public class AddEmpHealInsQualifiInfoCommandHandler
        extends CommandHandlerWithResult<AddEmpHealInsQualifiInfoCommand, PeregAddCommandResult>
        implements PeregAddCommandHandler<AddEmpHealInsQualifiInfoCommand> {
    @Inject
    private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

    @Inject
    private HealInsurNumberInforRepository healInsurNumberInforRepository;

    @Inject
    private EmpHealInsQualifiInfoService empHealInsQualifiInfoService;

    @Override
    public String targetCategoryCd() {
        return "CS00082";
    }

    @Override
    public Class<?> commandClass() {
        return AddEmpHealInsQualifiInfoCommand.class;
    }

    @Override
    protected PeregAddCommandResult handle(CommandHandlerContext<AddEmpHealInsQualifiInfoCommand> context) {
        val command = context.getCommand();
        String hisId = IdentifierUtil.randomUniqueId();

        Optional<EmplHealInsurQualifiInfor> exitHist = emplHealInsurQualifiInforRepository.getEmpHealInsQualifiinfoById(command.getEmployeeId());

        EmpHealthInsurBenefits dateItem = new EmpHealthInsurBenefits(hisId,
                new DateHistoryItem(hisId,
                        new DatePeriod(command.getStartDate(), command.getEndDate() != null ? command.getEndDate() : GeneralDate.max()
        )));

        EmplHealInsurQualifiInfor qualifiInfor = new EmplHealInsurQualifiInfor(command.getEmployeeId(), new ArrayList<>());

        HealInsurNumberInfor numberInfor = HealInsurNumberInfor.createFromJavaType(
                dateItem.identifier(),
                command.getNurCaseInsNumber(),
                command.getHealInsNumber()
        );
        if (exitHist.isPresent()){
            qualifiInfor = exitHist.get();
        }
        qualifiInfor.add(dateItem);
        empHealInsQualifiInfoService.add(qualifiInfor, dateItem, numberInfor);
        return new PeregAddCommandResult(hisId);
    }
}
