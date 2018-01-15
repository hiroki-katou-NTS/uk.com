package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVerticalSettingRepository;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.VerticalCnt;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 *
 */
@Transactional
@Stateless
public class AddVerticalCntSettingCommandHandler extends CommandHandler<AddVerticalCntSettingCommand> {
	
	@Inject
	private FixedVerticalSettingRepository repository;

	@Override
	protected void handle(CommandHandlerContext<AddVerticalCntSettingCommand> context) {
		AddVerticalCntSettingCommand command = context.getCommand();
		
		String companyId = AppContexts.user().companyId();
		repository.deleteCount(companyId, command.getFixedItemAtr());
		
			command.getVerticalCountNo().forEach(menuCode-> {
				VerticalCnt verticalCnt = new VerticalCnt(companyId ,command.getFixedItemAtr(),menuCode );
				
				//Validate Vertical Cnt
				verticalCnt.validate();
				
				//Add Vertical Cnt
				repository.addVerticalCnt(verticalCnt);
			});
	}

}
