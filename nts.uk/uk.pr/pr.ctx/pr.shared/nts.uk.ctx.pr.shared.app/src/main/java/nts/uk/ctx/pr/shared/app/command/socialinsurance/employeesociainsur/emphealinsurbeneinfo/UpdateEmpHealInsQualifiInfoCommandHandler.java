package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpHealInsQualifiInfoService;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpHealthInsurBenefits;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealInsurNumberInfor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class UpdateEmpHealInsQualifiInfoCommandHandler
        extends CommandHandler<UpdateEmpHealInsQualifiInfoCommand>
        implements PeregUpdateCommandHandler<UpdateEmpHealInsQualifiInfoCommand> {
    @Inject
    private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

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

        if (command.getStartDate() != null) {
            Optional<EmplHealInsurQualifiInfor> existHist = emplHealInsurQualifiInforRepository.getEmpHealInsQualifiinfoById(command.getEmployeeId());

            if (!existHist.isPresent()) {
                throw new RuntimeException("Invalid EmpHealInsQualifiInfo");
            }
            Optional<EmpHealthInsurBenefits> itemBenefits = existHist.get().getMourPeriod().stream()
                    .filter(e -> e.identifier().equals(command.getHistoryId()))
                    .findFirst();
            if (!itemBenefits.isPresent()) {
                throw new RuntimeException("Invalid EmpHealInsQualifiInfo");
            }
            existHist.get().changeSpan(itemBenefits.get(), new DatePeriod(command.getStartDate(), command.getEndDate() != null ? command.getEndDate() : GeneralDate.max()));
            HealInsurNumberInfor numberInfor = HealInsurNumberInfor.createFromJavaType(
                    command.getHistoryId(),
                    command.getNurCaseInsNumber(),
                    command.getHealInsNumber()
                    );
            empHealInsQualifiInfoService.update(existHist.get(), itemBenefits.get(), numberInfor);
        }
    }
}
