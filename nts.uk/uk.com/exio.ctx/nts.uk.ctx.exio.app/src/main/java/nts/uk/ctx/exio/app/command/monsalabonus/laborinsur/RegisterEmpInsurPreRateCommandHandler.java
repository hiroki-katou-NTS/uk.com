package nts.uk.ctx.exio.app.command.monsalabonus.laborinsur;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.EmpInsurBusBurRatioService;

@Stateless
@Transactional
public class RegisterEmpInsurPreRateCommandHandler extends CommandHandler<EmpInsurBusBurRatioCommand> {

    @Inject
    private EmpInsurBusBurRatioService empInsurBusBurRatioService;

	@Override
	protected void handle(CommandHandlerContext<EmpInsurBusBurRatioCommand> context) {
	    
	    List<EmpInsurBusBurRatio> empInsurBusBurRatio = command.g
	    empInsurBusBurRatioService.registerEmpInsurBusBurRatio();
	    
	}
}
