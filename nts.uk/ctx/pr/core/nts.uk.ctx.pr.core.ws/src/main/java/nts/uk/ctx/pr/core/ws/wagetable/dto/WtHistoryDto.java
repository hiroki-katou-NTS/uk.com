/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.wagetable.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.ElementItemDto;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.ElementSettingDto;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.wagetable.WtCode;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtHistorySetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtItem;
import nts.uk.ctx.pr.core.dom.wagetable.history.element.ElementSetting;
import nts.uk.ctx.pr.core.dom.wagetable.history.element.item.CodeItem;
import nts.uk.ctx.pr.core.dom.wagetable.history.element.item.RangeItem;

/**
 * The Class WtHistoryDto.
 */
@Getter
@Setter
public class WtHistoryDto implements WtHistorySetMemento {

	/** The history id. */
	private String historyId;

	/** The start month. */
	private Integer startMonth;

	/** The end month. */
	private Integer endMonth;

	/** The elements. */
	private List<ElementSettingDto> elements;

	/** The value items. */
	private List<WtItemDto> valueItems;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistorySetMemento#
	 * setCompanyCode(nts.uk.ctx.core.dom.company.CompanyCode)
	 */
	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// Do nothing.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistorySetMemento#
	 * setWageTableCode(nts.uk.ctx.pr.core.dom.wagetable.WageTableCode)
	 */
	@Override
	public void setWageTableCode(WtCode code) {
		// Do nothing.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistorySetMemento#
	 * setHistoryId(java.lang.String)
	 */
	@Override
	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistorySetMemento#
	 * setApplyRange(nts.uk.ctx.pr.core.dom.insurance.MonthRange)
	 */
	@Override
	public void setApplyRange(MonthRange applyRange) {
		this.startMonth = applyRange.getStartMonth().v();
		this.endMonth = applyRange.getEndMonth().v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistorySetMemento#
	 * setValueItems(java.util.List)
	 */
	@Override
	public void setValueItems(List<WtItem> valueItems) {
		this.valueItems = valueItems.stream().map(item -> {
			WtItemDto dto = new WtItemDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WtHistorySetMemento#setElementSettings(java.util.List)
	 */
	@Override
	public void setElementSettings(List<ElementSetting> elementSettings) {
		this.elements = elementSettings.stream().map(item -> {
			ElementSettingDto elementSettingDto = new ElementSettingDto();
			elementSettingDto.setDemensionNo(item.getDemensionNo().value);
			elementSettingDto.setType(item.getType().value);

			// Code mode
			if (item.getType().isCodeMode) {
				elementSettingDto.setItemList(item.getItemList().stream().map(subItem -> {
					CodeItem codeItem = (CodeItem) subItem;
					return ElementItemDto.builder().uuid(codeItem.getUuid())
							.referenceCode(codeItem.getReferenceCode()).build();
				}).collect(Collectors.toList()));
			}

			// Range mode
			if (item.getType().isRangeMode) {
				elementSettingDto.setItemList(item.getItemList().stream().map(subItem -> {
					RangeItem rangeItem = (RangeItem) subItem;
					return ElementItemDto.builder().uuid(rangeItem.getUuid())
							.orderNumber(rangeItem.getOrderNumber())
							.startVal(rangeItem.getStartVal()).endVal(rangeItem.getEndVal())
							.build();
				}).collect(Collectors.toList()));
			}

			return elementSettingDto;
		}).collect(Collectors.toList());
	}

}
