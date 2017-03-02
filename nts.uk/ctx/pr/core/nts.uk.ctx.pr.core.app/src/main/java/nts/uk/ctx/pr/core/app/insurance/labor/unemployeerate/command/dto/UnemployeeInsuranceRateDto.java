/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.command.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateItem;

/**
 * The Class UnemployeeInsuranceRateDto.
 */
@Data
public class UnemployeeInsuranceRateDto {
	
	/** The history insurance. */
	private HistoryUnemployeeInsuranceDto historyInsurance;

	/** The rate items. */
	private List<UnemployeeInsuranceRateItemDto> rateItems;

	/**
	 * To domain.
	 *
	 * @param companyCode the company code
	 * @return the unemployee insurance rate
	 */
	public UnemployeeInsuranceRate toDomain(String companyCode) {
		UnemployeeInsuranceRateDto dto = this;
		return new UnemployeeInsuranceRate(new UnemployeeInsuranceRateGetMemento() {

			@Override
			public Set<UnemployeeInsuranceRateItem> getRateItems() {
				Set<UnemployeeInsuranceRateItem> setUnemployeeInsuranceRateItem = new HashSet<>();
				for (UnemployeeInsuranceRateItemDto unemployeeInsuranceRateItem : dto.rateItems) {
					setUnemployeeInsuranceRateItem.add(unemployeeInsuranceRateItem.toDomain(companyCode));
				}
				return setUnemployeeInsuranceRateItem;
			}

			@Override
			public String getHistoryId() {
				return historyInsurance.getHistoryId();
			}

			@Override
			public CompanyCode getCompanyCode() {
				return new CompanyCode(companyCode);
			}

			@Override
			public MonthRange getApplyRange() {
				return MonthRange.range(dto.historyInsurance.getStartMonthRage(),
						dto.historyInsurance.getEndMonthRage(), "/");
			}
		});
	}
}
