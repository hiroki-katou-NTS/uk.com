package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.*;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class DeleteEmpHealInsQualifiInfoCommandHandler
        extends CommandHandler<DeleteEmpHealInsQualifiInfoCommand>
        implements PeregDeleteCommandHandler<DeleteEmpHealInsQualifiInfoCommand> {
    @Inject
    private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

    @Inject
    private HealInsurNumberInforRepository healInsurNumberInforRepository;

    @Inject EmpHealInsQualifiInfoService empHealInsQualifiInfoService;

    @Override
    public String targetCategoryCd(){
        return "CS00082";
    }

    @Override
    public Class<?> commandClass(){
        return DeleteEmpHealInsQualifiInfoCommand.class;
    }

    @Override
    protected void handle(CommandHandlerContext<DeleteEmpHealInsQualifiInfoCommand> context){
        val command = context.getCommand();
        EmplHealInsurQualifiInfor qualifiInfor = emplHealInsurQualifiInforRepository.getEmpHealInsQualifiInfoOfEmp(command.getEmployeeId());
        if (qualifiInfor == null){
            throw new RuntimeException("Invalid EmplHealInsurQualifiInfor");
        }

        Optional<EmpHealthInsurBenefits> itemToBeDeleted = qualifiInfor.getMourPeriod().stream()
                .filter(e->e.getHealInsurProfirMourHisId().equals(command.getHistoryId())).findFirst();

        empHealInsQualifiInfoService.delete(qualifiInfor, itemToBeDeleted.get());

        healInsurNumberInforRepository.remove(command.getHistoryId());
    }
}
