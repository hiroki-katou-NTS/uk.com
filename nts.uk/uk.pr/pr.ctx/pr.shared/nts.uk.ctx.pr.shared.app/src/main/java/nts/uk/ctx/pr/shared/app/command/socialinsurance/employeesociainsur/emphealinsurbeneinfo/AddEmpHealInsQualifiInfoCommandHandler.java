package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
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
        String companyId = AppContexts.user().companyId();
        String hisId = IdentifierUtil.randomUniqueId();

        EmpHealthInsurBenefits dateItem = new EmpHealthInsurBenefits(hisId, new DateHistoryItem(hisId, new DatePeriod(
                command.getStartDate() != null ? command.getStartDate() : GeneralDate.min(),
                command.getEndDate() != null ? command.getEndDate() : GeneralDate.max()
        )));

        Optional<EmplHealInsurQualifiInfor> exitHist = emplHealInsurQualifiInforRepository.getEmpHealInsQualifiinfoById(companyId, command.getEmployeeId());

        EmplHealInsurQualifiInfor qualifiInfor = new EmplHealInsurQualifiInfor(command.getEmployeeId(), new ArrayList<>());
        if (exitHist.isPresent()) {
            qualifiInfor = exitHist.get();
        }
        qualifiInfor.add(dateItem);

        HealInsurNumberInfor numberInfor = HealInsurNumberInfor.createFromJavaType(hisId, command.getHealInsNumber(), command.getNurCaseInsNumber());

        emplHealInsurQualifiInforRepository.add(qualifiInfor, numberInfor);
        return new PeregAddCommandResult(hisId);
    }
}
