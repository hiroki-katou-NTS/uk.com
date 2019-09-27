package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.inforunionfundnoti.emphealinsurassinfor;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emphealinsurassinfor.EmpHealthInsurUnionRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class RemoveEmpHealthInsurUnionCommandHandler extends CommandHandler<EmpHealthInsurUnionCommand>
{
    
    @Inject
    private EmpHealthInsurUnionRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<EmpHealthInsurUnionCommand> context) {
        String employeeId = context.getCommand().getEmployeeId();
        repository.remove(employeeId);
    }
}
