package nts.uk.ctx.pr.core.app.insurance.social.pensionrate.find;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.Ins2Rate;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.FundRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionPremiumRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRounding;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateSetMemento;

@Builder
public class PensionRateDto implements PensionRateSetMemento {
	/** The history id. */
	public String historyId;

	/** The company code. */
	public String companyCode;

	/** The office code. */
	public String officeCode;

	/** The start month. */
	public String startMonth;

	/** The end month. */
	public String endMonth;

	/** The max amount. */
	public BigDecimal maxAmount;

	/** The fund rate items. */
	public List<FundRateItemDto> fundRateItems;

	/** The premium rate items. */
	public List<PensionPremiumRateItemDto> premiumRateItems;

	/** The child contribution rate. */
	public BigDecimal childContributionRate;

	/** The rounding methods. */
	public List<PensionRateRounding> roundingMethods;

	/** The fund input apply. */
	public Boolean fundInputApply;

	/** The auto calcuate. */
	public Boolean autoCalcuate;

	/** The version. */
	public Long version;

	@Override
	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}

	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		this.companyCode = companyCode.v();

	}

	@Override
	public void setOfficeCode(OfficeCode officeCode) {
		this.officeCode = officeCode.v();
	}

	@Override
	public void setApplyRange(MonthRange applyRange) {
		this.startMonth = applyRange.getStartMonth().toString();
		this.endMonth = applyRange.getEndMonth().toString();
	}

	@Override
	public void setMaxAmount(CommonAmount maxAmount) {
		this.maxAmount = maxAmount.v();
	}

	@Override
	public void setFundRateItems(List<FundRateItem> fundRateItems) {
		this.fundRateItems = fundRateItems.stream().map(item -> FundRateItemDto.fromDomain(item))
				.collect(Collectors.toList());
	}

	@Override
	public void setPremiumRateItems(List<PensionPremiumRateItem> premiumRateItems) {
		this.premiumRateItems = premiumRateItems.stream().map(item -> PensionPremiumRateItemDto.fromDomain(item))
				.collect(Collectors.toList());
	}

	@Override
	public void setChildContributionRate(Ins2Rate childContributionRate) {
		this.childContributionRate = childContributionRate.v();
	}

	@Override
	public void setRoundingMethods(List<PensionRateRounding> roundingMethods) {
		this.roundingMethods = roundingMethods;
	}

	@Override
	public void setFundInputApply(Boolean fundInputApply) {
		this.fundInputApply = fundInputApply;
	}

	@Override
	public void setAutoCalculate(Boolean autoCalculate) {
		this.autoCalcuate = autoCalculate;
	}

	@Override
	public void setVersion(Long version) {
		this.version = version;
	}
}
