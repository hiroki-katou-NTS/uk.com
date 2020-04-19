package nts.uk.ctx.bs.employee.app.command.employee.employeeinfo.workplacegroup;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupRespository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RegisterWorkplaceGroupCommandHandler extends CommandHandler<RegisterWorkplaceGroupCommand>{

	@Inject
	private WorkplaceGroupRespository repo;
	
	@Override
	protected void handle(CommandHandlerContext<RegisterWorkplaceGroupCommand> context) {
		String CID = AppContexts.user().companyId();
		RegisterWorkplaceGroupCommand cmd = context.getCommand();
		
		// 1:get(会社ID, 職場グループコード)
		// return Optional<職場グループ>
		Optional<WorkplaceGroup> wpgrp = repo.getByCode(CID, cmd.getWKPGRPCode());
		
		// 2:職場グループ.isPresent()
		if (wpgrp.isPresent())
			throw new BusinessException("Msg_3");
			
	}
}
