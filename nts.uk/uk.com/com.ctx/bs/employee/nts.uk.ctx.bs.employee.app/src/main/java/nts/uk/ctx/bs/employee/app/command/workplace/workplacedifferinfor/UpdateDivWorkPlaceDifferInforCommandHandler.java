package nts.uk.ctx.bs.employee.app.command.workplace.workplacedifferinfor;

import javax.ejb.Stateless;
/**
 * update a item
 * @author yennth
 *
 */
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.workplace.differinfor.DivWorkDifferInfor;
import nts.uk.ctx.bs.employee.dom.workplace.differinfor.DivWorkDifferInforRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class UpdateDivWorkPlaceDifferInforCommandHandler extends CommandHandler<UpdateDivWorkPlaceDifferInforCommand>{
	@Inject
	private DivWorkDifferInforRepository divRep;

	@Override
	protected void handle(CommandHandlerContext<UpdateDivWorkPlaceDifferInforCommand> context) {
		UpdateDivWorkPlaceDifferInforCommand data = context.getCommand();
		String contractCd = AppContexts.user().contractCode();
		DivWorkDifferInfor div = DivWorkDifferInfor.createFromJavaType(data.getCompanyId(), 
																		data.getCompanyCode(), 
																		contractCd, 
																		data.getRegWorkDiv());
		div.createCompanyId(div.getCompanyCode().v(), div.getContractCd().v());
		divRep.updateDivWork(div);
	}
	
}
