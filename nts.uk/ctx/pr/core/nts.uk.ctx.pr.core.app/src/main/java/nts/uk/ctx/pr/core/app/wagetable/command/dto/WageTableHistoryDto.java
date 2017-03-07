/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.core.dom.util.PrimitiveUtil;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableDemensionDetail;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItem;

/**
 * The Class WageTableHistoryDto.
 */

/**
 * Instantiates a new wage table history dto.
 */
@Data
public class WageTableHistoryDto implements WageTableHistoryGetMemento {

	/** The code. */
	private String code;

	/** The history id. */
	private String historyId;

	/** The start month. */
	private String startMonth;

	/** The end month. */
	private String endMonth;

	/** The demension details. */
	private List<WageTableDemensionDetailDto> demensionDetails;

	/** The value items. */
	private List<WageTableItemDto> valueItems;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryGetMemento#
	 * getCompanyCode()
	 */
	@Override
	public CompanyCode getCompanyCode() {
		// TODO
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryGetMemento#
	 * getCode()
	 */
	@Override
	public WageTableCode getCode() {
		return new WageTableCode(this.code);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryGetMemento#
	 * getApplyRange()
	 */
	@Override
	public MonthRange getApplyRange() {
		return MonthRange.range(startMonth, endMonth, PrimitiveUtil.DEFAULT_YM_SEPARATOR_CHAR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryGetMemento#
	 * getDemensionDetail()
	 */
	@Override
	public List<WageTableDemensionDetail> getDemensionDetail() {
		return demensionDetails.stream().map(item -> new WageTableDemensionDetail(item))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryGetMemento#
	 * getValueItems()
	 */
	@Override
	public List<WageTableItem> getValueItems() {
		return valueItems.stream().map(item -> new WageTableItem(item))
				.collect(Collectors.toList());
	}

}
