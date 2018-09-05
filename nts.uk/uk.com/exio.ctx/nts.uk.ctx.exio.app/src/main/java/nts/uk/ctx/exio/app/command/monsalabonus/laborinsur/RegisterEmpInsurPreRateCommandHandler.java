package nts.uk.ctx.exio.app.command.monsalabonus.laborinsur;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
@Transactional
public class RegisterEmpInsurPreRateCommandHandler extends CommandHandler<EmpInsurPreRateCommand> {



	@Override
	protected void handle(CommandHandlerContext<EmpInsurPreRateCommand> context) {

	}
}
