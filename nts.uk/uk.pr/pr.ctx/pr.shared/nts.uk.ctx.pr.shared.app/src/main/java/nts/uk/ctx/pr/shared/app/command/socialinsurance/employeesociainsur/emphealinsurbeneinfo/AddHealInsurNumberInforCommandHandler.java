package .app.command.ドメインモデル.nittsusystem.universalk.salary.shared.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
@Transactional
public class AddHealInsurNumberInforCommandHandler extends CommandHandler<HealInsurNumberInforCommand>
{
    
    @Inject
    private HealInsurNumberInforRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<HealInsurNumberInforCommand> context) {
        HealInsurNumberInforCommand command = context.getCommand();
        repository.add(new HealInsurNumberInfor(command.getHistoryId(), command.getCareInsurNumber(), command.getHealInsNumber()));
    
    }
}
