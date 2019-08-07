package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfPenNumInformationRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class UpdateWelfPenNumInformationCommandHandler extends CommandHandler<WelfPenNumInformationCommand>
{
    
    @Inject
    private WelfPenNumInformationRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<WelfPenNumInformationCommand> context) {

    
    }
}
