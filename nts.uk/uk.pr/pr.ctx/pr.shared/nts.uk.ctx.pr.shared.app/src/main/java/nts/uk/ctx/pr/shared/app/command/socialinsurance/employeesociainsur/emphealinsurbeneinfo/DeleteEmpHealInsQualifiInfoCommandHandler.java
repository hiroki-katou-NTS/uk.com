package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.*;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class DeleteEmpHealInsQualifiInfoCommandHandler
        extends CommandHandler<DeleteEmpHealInsQualifiInfoCommand>
        implements PeregDeleteCommandHandler<DeleteEmpHealInsQualifiInfoCommand> {
    @Inject
    private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

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
        Optional<EmplHealInsurQualifiInfor> listHist = emplHealInsurQualifiInforRepository.getEmpHealInsQualifiinfoById(command.getEmployeeId());
        if (!listHist.isPresent()){
            throw new RuntimeException("Invalid EmplHealInsurQualifiInfor");
        }
        Optional<EmpHealthInsurBenefits> itemToBeDeleted = listHist.get().getMourPeriod().stream()
                .filter(e->e.identifier().equals(command.getHistoryId()))
                .findFirst();
        if (!itemToBeDeleted.isPresent()) {
            throw new RuntimeException("Invalid EmplHealInsurQualifiInfor");
        }
        listHist.get().remove(itemToBeDeleted.get());
        empHealInsQualifiInfoService.delete(listHist.get(), itemToBeDeleted.get());
    }
}
