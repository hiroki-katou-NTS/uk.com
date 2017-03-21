/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.wagetable.DemensionNo;
import nts.uk.ctx.pr.core.dom.wagetable.ElementType;
import nts.uk.ctx.pr.core.dom.wagetable.WtCode;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtHistory;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtHistoryGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtItem;
import nts.uk.ctx.pr.core.dom.wagetable.history.element.ElementSetting;
import nts.uk.ctx.pr.core.dom.wagetable.history.element.item.CodeItem;
import nts.uk.ctx.pr.core.dom.wagetable.history.element.item.RangeItem;
import nts.uk.shr.com.context.AppContexts;

/**
 * Instantiates a new certification find dto.
 */
@Setter
@Getter
public class WtHistoryDto {

	/** The history id. */
	private String historyId;

	/** The start month. */
	private Integer startMonth;

	/** The end month. */
	private Integer endMonth;

	/** The element settings. */
	private List<ElementSettingDto> elementSettings;

	/** The value items. */
	private List<WtItemDto> valueItems;

	/**
	 * To domain.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the wage table history
	 */
	public WtHistory toDomain(String wageTableCode) {
		WtHistoryDto dto = this;

		// Transfer data
		WtHistory wageTableHistory = new WtHistory(
				new WageTableHistoryDtoMemento(new WtCode(wageTableCode), dto));

		return wageTableHistory;
	}

	/**
	 * The Class WageTableHistoryAddCommandMemento.
	 */
	private class WageTableHistoryDtoMemento implements WtHistoryGetMemento {

		/** The wage table code. */
		protected WtCode wageTableCode;

		/** The type value. */
		protected WtHistoryDto dto;

		/**
		 * Instantiates a new jpa accident insurance rate get memento.
		 *
		 * @param typeValue
		 *            the type value
		 */
		public WageTableHistoryDtoMemento(WtCode wageTableCode, WtHistoryDto dto) {
			this.wageTableCode = wageTableCode;
			this.dto = dto;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WtHistoryGetMemento#
		 * getValueItems()
		 */
		@Override
		public List<WtItem> getValueItems() {
			return this.dto.getValueItems().stream().map(item -> new WtItem(item))
					.collect(Collectors.toList());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WtHistoryGetMemento#
		 * getHistoryId()
		 */
		@Override
		public String getHistoryId() {
			return this.dto.getHistoryId();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WtHistoryGetMemento#
		 * getCompanyCode()
		 */
		@Override
		public CompanyCode getCompanyCode() {
			return new CompanyCode(AppContexts.user().companyCode());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WtHistoryGetMemento#
		 * getWageTableCode()
		 */
		@Override
		public WtCode getWageTableCode() {
			return this.wageTableCode;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WtHistoryGetMemento#
		 * getApplyRange()
		 */
		@Override
		public MonthRange getApplyRange() {
			return MonthRange.range(new YearMonth(this.dto.getStartMonth()),
					new YearMonth(this.dto.getEndMonth()));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WtHistoryGetMemento#
		 * getElementSettings()
		 */
		@Override
		public List<ElementSetting> getElementSettings() {
			return this.dto.getElementSettings().stream().map(item -> {

				// Code mode
				if (ElementType.valueOf(item.getType()).isCodeMode) {
					return new ElementSetting(DemensionNo.valueOf(item.getDemensionNo()),
							ElementType.valueOf(item.getType()),
							item.getItemList().stream()
									.map(codeItem -> new CodeItem(codeItem.getReferenceCode(),
											codeItem.getUuid()))
									.collect(Collectors.toList()));
				}

				// Range mode
				if (ElementType.valueOf(item.getType()).isRangeMode) {
					return new ElementSetting(DemensionNo.valueOf(item.getDemensionNo()),
							ElementType.valueOf(item.getType()),
							item.getItemList().stream()
									.map(rangeItem -> new RangeItem(rangeItem.getOrderNumber(),
											rangeItem.getStartVal(), rangeItem.getEndVal(),
											rangeItem.getUuid()))
									.collect(Collectors.toList()));
				}

				// Other
				return null;

			}).collect(Collectors.toList());
		}
	}
}
