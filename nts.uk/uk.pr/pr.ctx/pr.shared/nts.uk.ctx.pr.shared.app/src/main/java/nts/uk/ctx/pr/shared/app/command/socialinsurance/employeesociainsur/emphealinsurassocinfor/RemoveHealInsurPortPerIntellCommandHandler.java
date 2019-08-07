package .app.command.ドメインモデル.nittsusystem.universalk.salary.shared.socialinsurance.employeesociainsur.emphealinsurassocinfor;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class RemoveHealInsurPortPerIntellCommandHandler extends CommandHandler<HealInsurPortPerIntellCommand>
{
    
    @Inject
    private HealInsurPortPerIntellRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<HealInsurPortPerIntellCommand> context) {
        String employeeId = context.getCommand().getEmployeeId();
        String hisId = context.getCommand().get();
        repository.remove(employeeId, hisId);
    }
}
