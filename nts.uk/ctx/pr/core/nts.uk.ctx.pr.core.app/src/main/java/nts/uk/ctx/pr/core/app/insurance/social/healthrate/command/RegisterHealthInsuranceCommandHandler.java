package nts.uk.ctx.pr.core.app.insurance.social.healthrate.command;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.social.healthrate.find.HealthInsuranceRateDto;
import nts.uk.ctx.pr.core.app.service.healthinsurance.HealthInsuranceService;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
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

//		HealthInsuranceRateDto healthInsuranceRateDto = command.getCommand().getHealthInsuranceRateDto();
//		// convert Dto to Domain
//
//		// convert rateItems
//		List<InsuranceRateItem> rateItems = healthInsuranceRateDto.getRateItems().stream()
//				.map(item -> new InsuranceRateItem(item.getPayType(), item.getInsuranceType(), item.getChargeRate()))
//				.collect(Collectors.toList());
//
//		// convert roundingMethods
//		List<HealthInsuranceRounding> roundingMethods = healthInsuranceRateDto.getRoundingMethods().stream()
//				.map(item -> new HealthInsuranceRounding(item.getPayType(), item.getRoundAtrs()))
//				.collect(Collectors.toList());
//
//		HealthInsuranceRate healthInsuranceRateDomain = new HealthInsuranceRate(healthInsuranceRateDto.getHistoryId(),
//				new CompanyCode(healthInsuranceRateDto.getCompanyCode()),
//				new OfficeCode(healthInsuranceRateDto.getOfficeCode()),
//				MonthRange.range(new YearMonth(Integer.parseInt(healthInsuranceRateDto.getStartMonth())),
//						new YearMonth(Integer.parseInt(healthInsuranceRateDto.getEndMonth()))),
//				healthInsuranceRateDto.getAutoCalculate(), new CommonAmount(healthInsuranceRateDto.getMaxAmount()), rateItems,
//				roundingMethods);
//
//		healthInsuranceService.add(healthInsuranceRateDomain);
	}

}
