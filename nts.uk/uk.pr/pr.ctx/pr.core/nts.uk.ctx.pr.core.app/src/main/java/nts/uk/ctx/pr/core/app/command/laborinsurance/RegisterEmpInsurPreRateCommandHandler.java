package nts.uk.ctx.pr.core.app.command.laborinsurance;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurBusBurRatio;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurBusBurRatioService;

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
