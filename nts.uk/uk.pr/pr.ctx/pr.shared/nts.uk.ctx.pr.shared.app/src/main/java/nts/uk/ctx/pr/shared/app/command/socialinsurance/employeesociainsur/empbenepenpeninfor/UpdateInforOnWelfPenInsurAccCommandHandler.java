package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

import javax.ejb.Stateless;
import javax.transaction.Transactional;


@Stateless
@Transactional
public class UpdateInforOnWelfPenInsurAccCommandHandler extends CommandHandler<InforOnWelfPenInsurAccCommand>
{

    
    @Override
    protected void handle(CommandHandlerContext<InforOnWelfPenInsurAccCommand> context) {

    }
}
