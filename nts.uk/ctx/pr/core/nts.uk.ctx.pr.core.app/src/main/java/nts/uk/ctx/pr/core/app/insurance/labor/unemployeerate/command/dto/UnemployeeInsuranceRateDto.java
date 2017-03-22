/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.command.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;
import nts.arc.time.YearMonth;
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
	private UnemployeeInsuranceHistoryDto historyInsurance;

	/** The rate items. */
	private List<UnemployeeInsuranceRateItemDto> rateItems;

	/**
	 * To domain.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the unemployee insurance rate
	 */
	public UnemployeeInsuranceRate toDomain(String companyCode) {
		return new UnemployeeInsuranceRate(new UnemployeeInsuranceRateGetMementoImpl(companyCode, this));
	}

	/**
	 * The Class UnemployeeInsuranceRateGetMementoImpl.
	 */
	public class UnemployeeInsuranceRateGetMementoImpl implements UnemployeeInsuranceRateGetMemento {

		/** The company code. */
		private String companyCode;

		/** The dto. */
		private UnemployeeInsuranceRateDto dto;

		/**
		 * Instantiates a new unemployee insurance rate get memento impl.
		 *
		 * @param companyCode
		 *            the company code
		 * @param dto
		 *            the dto
		 */
		public UnemployeeInsuranceRateGetMementoImpl(String companyCode, UnemployeeInsuranceRateDto dto) {
			super();
			this.companyCode = companyCode;
			this.dto = dto;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
		 * UnemployeeInsuranceRateGetMemento#getRateItems()
		 */
		@Override
		public Set<UnemployeeInsuranceRateItem> getRateItems() {
			Set<UnemployeeInsuranceRateItem> setUnemployeeInsuranceRateItem = new HashSet<>();
			dto.rateItems.forEach(rateItem -> {
				setUnemployeeInsuranceRateItem.add(rateItem.toDomain(companyCode));
			});
			return setUnemployeeInsuranceRateItem;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
		 * UnemployeeInsuranceRateGetMemento#getHistoryId()
		 */
		@Override
		public String getHistoryId() {
			return historyInsurance.getHistoryId();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
		 * UnemployeeInsuranceRateGetMemento#getCompanyCode()
		 */
		@Override
		public CompanyCode getCompanyCode() {
			return new CompanyCode(companyCode);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
		 * UnemployeeInsuranceRateGetMemento#getApplyRange()
		 */
		@Override
		public MonthRange getApplyRange() {
			return MonthRange.range(YearMonth.of(dto.getHistoryInsurance().getStartMonth()),
				YearMonth.of(dto.getHistoryInsurance().getEndMonth()));
		}

	}
}
