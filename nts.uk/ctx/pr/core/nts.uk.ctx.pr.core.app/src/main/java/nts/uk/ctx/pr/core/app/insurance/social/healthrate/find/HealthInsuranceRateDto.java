package nts.uk.ctx.pr.core.app.insurance.social.healthrate.find;

import java.math.BigDecimal;
import java.util.List;
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
	public List<InsuranceRateItemDto> rateItems;

	/** The rounding methods. */
	public List<HealthInsuranceRounding> roundingMethods;

//	/**
//	 * From domain.
//	 *
//	 * @param domain
//	 *            the domain
//	 * @return the health insurance rate dto
//	 */
//	public static HealthInsuranceRateDto fromDomain(HealthInsuranceRate domain) {
//		return new HealthInsuranceRateDto(domain.getHistoryId(), domain.getCompanyCode().v(),
//				domain.getOfficeCode().v(), "", domain.getApplyRange().getStartMonth().toString(),
//				domain.getApplyRange().getEndMonth().toString(), domain.getAutoCalculate(), domain.getMaxAmount().v(),
//				domain.getRateItems().stream()
//						.map(insuranceRateItemDomain -> InsuranceRateItemDto.fromDomain(insuranceRateItemDomain))
//						.collect(Collectors.toList()),
//				domain.getRoundingMethods());
//
//	}

	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// TODO Auto-generated method stub
		this.companyCode = companyCode.v();
	}

	@Override
	public void setOfficeCode(OfficeCode officeCode) {
		// TODO Auto-generated method stub
		this.officeCode = officeCode.v();
	}

	@Override
	public void setApplyRange(MonthRange applyRange) {
		// TODO Auto-generated method stub
		this.startMonth = applyRange.getStartMonth().toString();
		this.endMonth = applyRange.getEndMonth().toString();
	}

	@Override
	public void setMaxAmount(CommonAmount maxAmount) {
		// TODO Auto-generated method stub
		this.maxAmount = maxAmount.v();
	}

	@Override
	public void setRateItems(List<InsuranceRateItem> rateItems) {
		// TODO Auto-generated method stub
		this.rateItems = rateItems.stream()
				.map(insuranceRateItemDomain -> InsuranceRateItemDto.fromDomain(insuranceRateItemDomain))
				.collect(Collectors.toList());
	}

	@Override
	public void setVersion(Long version) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setHistoryId(String historyId) {
		// TODO Auto-generated method stub
		this.historyId = historyId;
	}

	@Override
	public void setAutoCalculate(Boolean autoCalculate) {
		// TODO Auto-generated method stub
		this.autoCalculate =autoCalculate;
	}

	@Override
	public void setRoundingMethods(List<HealthInsuranceRounding> roundingMethods) {
		// TODO Auto-generated method stub
		this.roundingMethods = roundingMethods;
	}

}
