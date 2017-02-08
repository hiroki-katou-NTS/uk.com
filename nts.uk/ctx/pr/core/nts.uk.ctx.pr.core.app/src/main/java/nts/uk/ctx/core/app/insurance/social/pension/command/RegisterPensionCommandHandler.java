package nts.uk.ctx.core.app.insurance.social.pension.command;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.app.insurance.social.pensionrate.find.PensionRateDto;
import nts.uk.ctx.pr.core.app.service.pension.PensionService;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.FundRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionPremiumRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRate;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRounding;
@Stateless
public class RegisterPensionCommandHandler extends CommandHandler<RegisterPensionCommand> {

	@Inject
	PensionService pensionService;
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<RegisterPensionCommand> command) {
		PensionRateDto pensionRateDto = command.getCommand().getPensionRate();
	
		// Convert to Dto
		List<FundRateItem> fundRateItems = pensionRateDto.getFundRateItems().stream()
				.map(item -> new FundRateItem(item.getPayType(),item.getGenderType(),item.getBurdenChargeRate(),item.getExemptionChargeRate()))
				.collect(Collectors.toList());
		
		List<PensionPremiumRateItem> premiumRateItems = pensionRateDto.getPremiumRateItems().stream()
				.map(item-> new PensionPremiumRateItem(item.getChargeRates(),item.getPayType(),item.getGenderType()))
				.collect(Collectors.toList());
		List<PensionRateRounding> roundingMethods = pensionRateDto.getRoundingMethods().stream()
				.map(item -> new PensionRateRounding(item.getPayType(),item.getRoundAtrs()))
				.collect(Collectors.toList());
		//
		PensionRate pensionRateDomain = new PensionRate(
				pensionRateDto.getHistoryId(), pensionRateDto.getCompanyCode(), pensionRateDto.getOfficeCode(), pensionRateDto.getApplyRange(),
				pensionRateDto.getMaxAmount(),fundRateItems,premiumRateItems,
				pensionRateDto.getChildContributionRate(),roundingMethods
				);
		pensionService.add(pensionRateDomain);
		return;
	}

}
