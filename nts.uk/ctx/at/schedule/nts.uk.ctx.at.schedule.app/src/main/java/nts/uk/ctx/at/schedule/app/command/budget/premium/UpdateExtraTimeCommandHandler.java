package nts.uk.ctx.at.schedule.app.command.budget.premium;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.budget.premium.ExtraTime;
import nts.uk.ctx.at.schedule.dom.budget.premium.ExtraTimeRepository;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumName;
import nts.uk.ctx.at.schedule.dom.budget.premium.UseClassification;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateExtraTimeCommandHandler extends CommandHandler<List<UpdateExtraTimeCommand>>{
	
	@Inject
	private ExtraTimeRepository extraTimeRepository;

	@Override
	protected void handle(CommandHandlerContext<List<UpdateExtraTimeCommand>> context) {
		String companyID = AppContexts.user().companyId();
		List<UpdateExtraTimeCommand> command = context.getCommand();
		for(UpdateExtraTimeCommand cm : command) {
				this.extraTimeRepository.update(
						new ExtraTime(
								companyID, 
								cm.extraItemID, 
								new PremiumName(cm.name), 
								cm.timeItemID, 
								EnumAdaptor.valueOf(cm.useAtr, UseClassification.class) 
						)
				);					
		}
	}

}
