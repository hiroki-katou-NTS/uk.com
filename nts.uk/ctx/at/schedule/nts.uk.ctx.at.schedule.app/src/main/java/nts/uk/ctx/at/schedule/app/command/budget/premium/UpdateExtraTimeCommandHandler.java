package nts.uk.ctx.at.schedule.app.command.budget.premium;

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

@Stateless
@Transactional
public class UpdateExtraTimeCommandHandler extends CommandHandler<UpdateExtraTimeCommand>{
	
	@Inject
	private ExtraTimeRepository extraTimeRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateExtraTimeCommand> context) {
		UpdateExtraTimeCommand command = context.getCommand();
		this.extraTimeRepository.update(
				new ExtraTime(
						command.companyID, 
						command.extraItemID, 
						new PremiumName(command.premiumName), 
						command.timeItemID, 
						EnumAdaptor.valueOf(command.useClassification, UseClassification.class) 
				)
		);
		
	}

}
