package nts.uk.ctx.pr.core.app.command.insurance.social.health;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.app.finder.healthinsurance.dto.HealthInsuranceRateDto;

public class RegisterHealthInsuranceCommandHandler extends CommandHandler<RegisterHealthInsuranceCommand> {

	@Override
	protected void handle(CommandHandlerContext<RegisterHealthInsuranceCommand> command) {
		// TODO 
		
		HealthInsuranceRateDto HIRDto = command.getCommand().getHIRDto();
		//convert Dto to Domain
		
//		HealthInsuranceRate healthInsuranceRateDomain = new HealthInsuranceRate();
	}
	
}
