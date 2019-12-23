package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class UpdateEmpHealInsQualifiInfoCommandHandler
        extends CommandHandler<UpdateEmpHealInsQualifiInfoCommand>
        implements PeregUpdateCommandHandler<UpdateEmpHealInsQualifiInfoCommand> {
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
        return UpdateEmpHealInsQualifiInfoCommand.class;
    }

    @Override
    protected void handle(CommandHandlerContext<UpdateEmpHealInsQualifiInfoCommand> context) {
        val command = context.getCommand();
        String companyId = AppContexts.user().companyId();
        if (command.getStartDate() != null) {
            Optional<EmplHealInsurQualifiInfor> existHist = emplHealInsurQualifiInforRepository.getEmpHealInsQualifiinfoById(companyId, command.getEmployeeId());

            if (!existHist.isPresent()) {
                throw new RuntimeException("Invalid EmpHealInsQualifiInfo");
            }
            Optional<EmpHealthInsurBenefits> itemBenefits = existHist.get().getMourPeriod().stream()
                    .filter(e -> e.identifier().equals(command.getHistoryId()))
                    .findFirst();
            if (!itemBenefits.isPresent()) {
                throw new RuntimeException("Invalid EmpHealInsQualifiInfo");
            }
            existHist.get().changeSpan(itemBenefits.get(), new DatePeriod(command.getStartDate(), command.getEndDate()));
            empHealInsQualifiInfoService.update(existHist.get(), itemBenefits.get());
        }
        HealInsurNumberInfor numberInfor = HealInsurNumberInfor.createFromJavaType(command.getHistoryId(), command.getHealInsNumber(), command.getNurCaseInsNumber());
        healInsurNumberInforRepository.update(numberInfor);
    }
}
