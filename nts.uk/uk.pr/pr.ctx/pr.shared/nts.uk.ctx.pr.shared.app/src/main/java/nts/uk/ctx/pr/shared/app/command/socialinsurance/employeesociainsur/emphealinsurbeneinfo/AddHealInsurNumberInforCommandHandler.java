package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealInsurNumberInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealInsurNumberInforRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

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
