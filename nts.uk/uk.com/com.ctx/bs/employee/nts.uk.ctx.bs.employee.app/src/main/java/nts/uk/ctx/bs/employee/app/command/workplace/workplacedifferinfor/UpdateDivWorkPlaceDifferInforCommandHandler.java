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
/**
 * update DivWorkPlaceDifferInfor Command Handler
 * @author yennth
 */
@Stateless
public class UpdateDivWorkPlaceDifferInforCommandHandler extends CommandHandler<UpdateDivWorkPlaceDifferInforCommand>{
	@Inject
	private DivWorkDifferInforRepository divRep;

	// update a item
	@Override
	protected void handle(CommandHandlerContext<UpdateDivWorkPlaceDifferInforCommand> context) {
		UpdateDivWorkPlaceDifferInforCommand data = context.getCommand();
		DivWorkDifferInfor div = DivWorkDifferInfor.createFromJavaType( data.getCompanyId(),
																		data.getRegWorkDiv());
		divRep.updateDivWork(div);
	}
	
}
