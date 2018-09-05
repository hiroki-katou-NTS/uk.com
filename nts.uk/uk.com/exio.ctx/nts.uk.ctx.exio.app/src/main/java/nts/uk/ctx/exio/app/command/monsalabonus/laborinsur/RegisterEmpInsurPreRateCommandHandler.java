package nts.uk.ctx.exio.app.command.monsalabonus.laborinsur;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.EmpInsurBusBurRatio;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.EmpInsurBusBurRatioService;

@Stateless
@Transactional
public class RegisterEmpInsurPreRateCommandHandler extends CommandHandler<RegisterEmpInsurBusBurRatioCommand> {

    @Inject
    private EmpInsurBusBurRatioService empInsurBusBurRatioService;

	@Override
	protected void handle(CommandHandlerContext<RegisterEmpInsurBusBurRatioCommand> context) {
	    
	    RegisterEmpInsurBusBurRatioCommand command = context.getCommand();
	    List<EmpInsurBusBurRatio> listEmpInsurBusBurRatio  = command.getListEmpInsurPreRate().stream().map(item -> { 
	        return new EmpInsurBusBurRatio(item.getHisId(), item.getEmpPreRateId(), item.getIndBdRatio(), item.getEmpContrRatio(), item.getPerFracClass(), item.getBusiOwFracClass());
	        }).collect(Collectors.toList());
	    empInsurBusBurRatioService.registerEmpInsurBusBurRatio(listEmpInsurBusBurRatio);
	    
	}
}
