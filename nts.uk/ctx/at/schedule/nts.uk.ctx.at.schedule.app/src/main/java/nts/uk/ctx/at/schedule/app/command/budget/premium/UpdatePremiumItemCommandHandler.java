package nts.uk.ctx.at.schedule.app.command.budget.premium;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumItem;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumItemRepository;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumName;
import nts.uk.ctx.at.schedule.dom.budget.premium.UseAttribute;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdatePremiumItemCommandHandler extends CommandHandler<List<UpdatePremiumItemCommand>>{
	
	@Inject
	private PremiumItemRepository premiumItemRepository;

	@Override
	protected void handle(CommandHandlerContext<List<UpdatePremiumItemCommand>> context) {
		String companyID = AppContexts.user().companyId();
		List<UpdatePremiumItemCommand> commands = context.getCommand();
		for(UpdatePremiumItemCommand command : commands) {
				this.premiumItemRepository.update(
						new PremiumItem(
								companyID, 
								command.getID(), 
								command.getAttendanceID(),
								new PremiumName(command.getName()), 
								command.getDisplayNumber(), 
								EnumAdaptor.valueOf(command.getUseAtr(), UseAttribute.class) 
						)
				);					
		}
	}

}
