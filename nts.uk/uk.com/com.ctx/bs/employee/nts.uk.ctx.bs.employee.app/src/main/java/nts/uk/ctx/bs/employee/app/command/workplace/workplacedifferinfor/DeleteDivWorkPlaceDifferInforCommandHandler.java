package nts.uk.ctx.bs.employee.app.command.workplace.workplacedifferinfor;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.workplace.differinfor.DivWorkDifferInfor;
import nts.uk.ctx.bs.employee.dom.workplace.differinfor.DivWorkDifferInforRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author yennth
 *
 */
@Stateless
public class DeleteDivWorkPlaceDifferInforCommandHandler extends CommandHandler<DeleteDivWorkPlaceDifferInforCommand>{
	@Inject
	private DivWorkDifferInforRepository divRep;

	@Override
	protected void handle(CommandHandlerContext<DeleteDivWorkPlaceDifferInforCommand> context) {
		DeleteDivWorkPlaceDifferInforCommand data = context.getCommand();
		String contractCd = AppContexts.user().contractCode();
		Optional<DivWorkDifferInfor> div = divRep.findDivWork(data.getCompanyId());
		if(!div.isPresent()){
			throw new BusinessException("対象データがありません。");
		}
		divRep.deleteDivWork(data.getCompanyId(), data.getCompanyCode(), contractCd);
	}
}
