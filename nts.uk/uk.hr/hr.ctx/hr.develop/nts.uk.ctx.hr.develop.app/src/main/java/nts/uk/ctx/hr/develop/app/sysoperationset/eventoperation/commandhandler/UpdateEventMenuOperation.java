package nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.commandhandler;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
/**
 * 
 * @author yennth
 *
 */
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.command.EventOperationCmd;
import nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.command.JMM018Cmd;
import nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.command.MenuOperationCmd;
import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.EventOperation;
import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.MenuOperation;
import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.algorithm.EventMenuOperSer;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author yennth
 *
 */
@Transactional
@Stateless
public class UpdateEventMenuOperation extends CommandHandler<JMM018Cmd>{
	
	@Inject
	private EventMenuOperSer eventMenuOperSer;
	
	@Override
	protected void handle(CommandHandlerContext<JMM018Cmd> context) {
		String companyId = AppContexts.user().companyId();
		BigInteger ccd = new BigInteger(AppContexts.user().companyCode());
		JMM018Cmd command = context.getCommand();
		List<EventOperationCmd> listEvent = command.getListEventOper();
		List<MenuOperationCmd> listMenu = command.getListMenuOper();
		for(EventOperationCmd item: listEvent){
			// ドメインモデル[イベント管理]を更新する - update domain event Operation
			eventMenuOperSer.updateEvent(EventOperation.createFromJavaType(item.getEventId(), item.getUseEvent(), companyId, ccd));
		}
		for(MenuOperationCmd cmd: listMenu){
			// ドメインモデル[メニュー管理]を更新する - update domain menu operation
			eventMenuOperSer.updateMenu(MenuOperation.createFromJavaType(cmd.getProgramId(), cmd.getUseMenu(), 
															companyId, cmd.getUseApproval(), cmd.getUseNotice(), null, ccd));
		}
	}

}
