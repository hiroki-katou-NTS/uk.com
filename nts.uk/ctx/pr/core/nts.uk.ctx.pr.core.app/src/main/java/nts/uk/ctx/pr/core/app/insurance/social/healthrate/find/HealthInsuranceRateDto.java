package nts.uk.ctx.pr.core.app.insurance.social.healthrate.find;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Builder;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateSetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRounding;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.InsuranceRateItem;

@Builder
public class HealthInsuranceRateDto implements HealthInsuranceRateSetMemento {
	/** The history id. */
	public String historyId;

	/** The company code. */
	public String companyCode;

	/** The office code. */
	public String officeCode;

	/** The office name. */
	public String officeName;

	/** The start month. */
	public String startMonth;

	/** The end month. */
	public String endMonth;

	/** The auto calculate. */
	public Boolean autoCalculate;

	/** The max amount. */
	public BigDecimal maxAmount;

	/** The rate items. */
	public Set<InsuranceRateItemDto> rateItems;

	/** The rounding methods. */
	public Set<HealthInsuranceRounding> roundingMethods;

	/** The version. */
	public Long version;

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
	public void setRateItems(Set<InsuranceRateItem> rateItems) {
		this.rateItems = rateItems.stream()
				.map(insuranceRateItemDomain -> InsuranceRateItemDto.fromDomain(insuranceRateItemDomain))
				.collect(Collectors.toSet());
	}

	@Override
	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}

	@Override
	public void setAutoCalculate(Boolean autoCalculate) {
		this.autoCalculate = autoCalculate;
	}

	@Override
	public void setRoundingMethods(Set<HealthInsuranceRounding> roundingMethods) {
		this.roundingMethods = roundingMethods;
	}

}
