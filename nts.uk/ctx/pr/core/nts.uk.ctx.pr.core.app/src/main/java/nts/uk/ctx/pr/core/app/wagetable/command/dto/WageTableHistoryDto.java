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
import nts.uk.ctx.pr.core.dom.wagetable.WtCode;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtHistory;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtHistoryGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtItem;
import nts.uk.ctx.pr.core.dom.wagetable.history.element.ElementSetting;

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
	public WtHistory toDomain(String companyCode, String wageTableCode) {
		WageTableHistoryDto dto = this;

		// Transfer data
		WtHistory wageTableHistory = new WtHistory(new WageTableHistoryDtoMemento(
				new CompanyCode(companyCode), new WtCode(wageTableCode), dto));

		return wageTableHistory;
	}

	/**
	 * The Class WageTableHistoryAddCommandMemento.
	 */
	private class WageTableHistoryDtoMemento implements WtHistoryGetMemento {

		/** The company code. */
		protected CompanyCode companyCode;

		/** The wage table code. */
		protected WtCode wageTableCode;

		/** The type value. */
		protected WageTableHistoryDto dto;

		/**
		 * Instantiates a new jpa accident insurance rate get memento.
		 *
		 * @param typeValue
		 *            the type value
		 */
		public WageTableHistoryDtoMemento(CompanyCode companyCode, WtCode wageTableCode,
				WageTableHistoryDto dto) {
			this.companyCode = companyCode;
			this.wageTableCode = wageTableCode;
			this.dto = dto;
		}

		@Override
		public List<WtItem> getValueItems() {
			return dto.getValueItems().stream().map(item -> new WtItem(item))
					.collect(Collectors.toList());
		}

		@Override
		public String getHistoryId() {
			return dto.getHistoryId();
		}

		@Override
		public CompanyCode getCompanyCode() {
			return this.companyCode;
		}

		@Override
		public WtCode getWageTableCode() {
			return this.wageTableCode;
		}

		@Override
		public MonthRange getApplyRange() {
			return MonthRange.range(dto.getStartMonth(), dto.getEndMonth(),
					PrimitiveUtil.DEFAULT_YM_SEPARATOR_CHAR);
		}

		@Override
		public List<ElementSetting> getElementSettings() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
