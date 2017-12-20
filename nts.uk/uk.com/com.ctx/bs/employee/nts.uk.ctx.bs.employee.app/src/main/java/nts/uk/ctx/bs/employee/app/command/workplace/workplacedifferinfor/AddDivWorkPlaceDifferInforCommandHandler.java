package nts.uk.ctx.bs.employee.app.command.workplace.workplacedifferinfor;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.workplace.differinfor.DivWorkDifferInfor;
import nts.uk.ctx.bs.employee.dom.workplace.differinfor.DivWorkDifferInforRepository;
/**
 * add DivWorkPlaceDifferInfor Command Handler  
 * @author yennth
 */
@Stateless
public class AddDivWorkPlaceDifferInforCommandHandler extends CommandHandler<AddDivWorkPlaceDifferInforCommand>{
	@Inject
	private DivWorkDifferInforRepository divRep;
	// add a item
	@Override
	protected void handle(CommandHandlerContext<AddDivWorkPlaceDifferInforCommand> context) {
		AddDivWorkPlaceDifferInforCommand data = context.getCommand();
		Optional<DivWorkDifferInfor> div = divRep.findDivWork(data.getCompanyId());
		// if existed in Data base
		if(div.isPresent()){
			throw new BusinessException("Msg_3");
		}
		DivWorkDifferInfor divNew = DivWorkDifferInfor.createFromJavaType(data.getCompanyId(), data.getRegWorkDiv());
		divNew.validate();
		divRep.insertDivWork(divNew);
	}
}
