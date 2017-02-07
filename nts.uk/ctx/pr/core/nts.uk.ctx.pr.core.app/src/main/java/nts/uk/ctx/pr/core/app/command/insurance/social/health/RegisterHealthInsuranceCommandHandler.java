package nts.uk.ctx.pr.core.app.command.insurance.social.health;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.app.find.insurance.social.healthrate.HealthInsuranceRateDto;
import nts.uk.ctx.pr.core.app.service.healthinsurance.HealthInsuranceService;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRounding;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.InsuranceRateItem;
@Stateless
public class RegisterHealthInsuranceCommandHandler extends CommandHandler<RegisterHealthInsuranceCommand> {

	@Inject
	HealthInsuranceService healthInsuranceService;

	@Override
	@Transactional
	protected void handle(CommandHandlerContext<RegisterHealthInsuranceCommand> command) {
		
		HealthInsuranceRateDto HIRDto = command.getCommand().getHIRDto();
		//convert Dto to Domain
		
		// convert rateItems
		List<InsuranceRateItem> rateItems = HIRDto.getRateItems().stream()
				.map(item -> new InsuranceRateItem(item.getChargeRate(), item.getPayType(), item.getInsuranceType()))
				.collect(Collectors.toList());
		
		// convert roundingMethods
		List<HealthInsuranceRounding> roundingMethods = HIRDto.getRoundingMethods().stream()
				.map(item -> new HealthInsuranceRounding(item.getPayType(), item.getRoundAtrs()))
				.collect(Collectors.toList());
		
		HealthInsuranceRate healthInsuranceRateDomain = new HealthInsuranceRate(
				HIRDto.getHistoryId(), HIRDto.getCompanyCode(), HIRDto.getOfficeCode(), HIRDto.getApplyRange(),
				HIRDto.getAutoCalculate(), HIRDto.getMaxAmount(),rateItems,roundingMethods);
		
		healthInsuranceService.add(healthInsuranceRateDomain);
	}
	
}
