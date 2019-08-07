package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.inforunionfundnoti.emphealinsurassinfor;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emphealinsurassinfor.EmpHealthInsurUnion;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emphealinsurassinfor.EmpHealthInsurUnionRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class AddEmpHealthInsurUnionCommandHandler extends CommandHandler<EmpHealthInsurUnionCommand>
{
    
    @Inject
    private EmpHealthInsurUnionRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<EmpHealthInsurUnionCommand> context) {
        EmpHealthInsurUnionCommand command = context.getCommand();
        repository.add(new EmpHealthInsurUnion(command.getEmployeeId(), command.getHealthInsurInherentProject()));
    
    }
}
