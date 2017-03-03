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
import nts.uk.ctx.core.dom.util.PrimitiveUtil;
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
			return MonthRange.range(dto.historyInsurance.getStartMonthRage(), dto.historyInsurance.getEndMonthRage(),
					PrimitiveUtil.DEFAULT_YM_SEPARATOR_CHAR);
		}

	}
}
