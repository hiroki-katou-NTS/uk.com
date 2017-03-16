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
import nts.uk.ctx.pr.core.app.wagetable.command.dto.WageTableDemensionDetailDto;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.element.WageTableElement;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistorySetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItem;

/**
 * The Class WageTableHistoryModel.
 */
@Getter
@Setter
public class WageTableHistoryDto implements WageTableHistorySetMemento {

	/** The head. */
	private WageTableHeadDto head;

	/** The history id. */
	private String historyId;

	/** The start month. */
	private Integer startMonth;

	/** The end month. */
	private Integer endMonth;

	/** The demension details. */
	private List<WageTableDemensionDetailDto> demensionDetails;

	/** The value items. */
	private List<WageTableItemDto> valueItems;

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
	public void setWageTableCode(WageTableCode code) {
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
	 * setDemensionDetail(java.util.List)
	 */
	@Override
	public void setDemensionDetail(List<WageTableElement> demensionDetails) {
		this.demensionDetails = demensionDetails.stream().map(item -> {
			WageTableDemensionDetailDto dto = new WageTableDemensionDetailDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistorySetMemento#
	 * setValueItems(java.util.List)
	 */
	@Override
	public void setValueItems(List<WageTableItem> valueItems) {
		this.valueItems = valueItems.stream().map(item -> {
			WageTableItemDto dto = new WageTableItemDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

}
