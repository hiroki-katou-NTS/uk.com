package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurassocinfor;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealthCarePortInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealthCarePortInforRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class AddHealthCarePortInforCommandHandler extends CommandHandler<HealthCarePortInforCommand>
{
    
    @Inject
    private HealthCarePortInforRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<HealthCarePortInforCommand> context) {

    }
}
