package nts.uk.ctx.bs.employee.app.command.groupcommonmaster;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.groupcommonmaster.GroupCommonMaster;
import nts.uk.ctx.bs.employee.dom.groupcommonmaster.GroupCommonMasterDomainService;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author yennth
 *
 */
@Stateless
public class UpdateCommonMasterCmdHandler extends CommandHandler<UpdateCommonMasterCommand> {
	
	@Inject
	private GroupCommonMasterDomainService services;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateCommonMasterCommand> context) {
		
		UpdateCommonMasterCommand command = context.getCommand();
		String contractCode = AppContexts.user().contractCode();
		
		// グループ会社共通マスタの更新
		services.updateGroupCommonMaster(contractCode, command.getListCommonMaster().stream()
																.map(x -> GroupCommonMaster.creatFromJavaType(contractCode, x.getCommonMasterId(),
																												x.getCommonMasterCode(),
																												x.getCommonMasterName(), 
																												x.getCommonMasterMemo()))
																.collect(Collectors.toList()));
		
	}

}
