/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.core.dom.util.PrimitiveUtil;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.element.WageTableElement;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistory;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItem;

/**
 * Instantiates a new certification find dto.
 */
@Setter
@Getter
public class WageTableHistoryDto extends WageTableDetailDto {

	/** The history id. */
	private String historyId;

	/** The start month. */
	private String startMonth;

	/** The end month. */
	private String endMonth;

	/** The value items. */
	private List<WageTableItemDto> valueItems;

	/**
	 * To domain.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the wage table history
	 */
	public WageTableHistory toDomain(String companyCode, String wageTableCode) {
		WageTableHistoryDto dto = this;

		// Transfer data
		WageTableHistory wageTableHistory = new WageTableHistory(new WageTableHistoryDtoMemento(
				new CompanyCode(companyCode), new WageTableCode(wageTableCode), dto));

		return wageTableHistory;
	}

	/**
	 * The Class WageTableHistoryAddCommandMemento.
	 */
	private class WageTableHistoryDtoMemento implements WageTableHistoryGetMemento {

		/** The company code. */
		protected CompanyCode companyCode;

		/** The wage table code. */
		protected WageTableCode wageTableCode;

		/** The type value. */
		protected WageTableHistoryDto dto;

		/**
		 * Instantiates a new jpa accident insurance rate get memento.
		 *
		 * @param typeValue
		 *            the type value
		 */
		public WageTableHistoryDtoMemento(CompanyCode companyCode, WageTableCode wageTableCode,
				WageTableHistoryDto dto) {
			this.companyCode = companyCode;
			this.wageTableCode = wageTableCode;
			this.dto = dto;
		}

		@Override
		public List<WageTableItem> getValueItems() {
			return dto.getValueItems().stream().map(item -> new WageTableItem(item))
					.collect(Collectors.toList());
		}

		@Override
		public String getHistoryId() {
			return dto.getHistoryId();
		}

		@Override
		public List<WageTableElement> getDemensionDetail() {
			return dto.getDemensionDetails().stream().map(item -> new WageTableElement(item))
					.collect(Collectors.toList());
		}

		@Override
		public CompanyCode getCompanyCode() {
			return this.companyCode;
		}

		@Override
		public WageTableCode getWageTableCode() {
			return this.wageTableCode;
		}

		@Override
		public MonthRange getApplyRange() {
			return MonthRange.range(dto.getStartMonth(), dto.getEndMonth(),
					PrimitiveUtil.DEFAULT_YM_SEPARATOR_CHAR);
		}
	}
}
