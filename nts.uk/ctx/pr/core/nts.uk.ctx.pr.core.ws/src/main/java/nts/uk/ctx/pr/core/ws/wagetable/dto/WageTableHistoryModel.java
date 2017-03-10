/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.wagetable.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.WageTableDemensionDetailDto;
import nts.uk.ctx.pr.core.app.wagetable.find.dto.WageTableHeadDto;
import nts.uk.ctx.pr.core.app.wagetable.find.dto.WageTableItemDto;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.element.WageTableElement;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistorySetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItem;

/**
 * The Class WageTableHistoryDto.
 */
@Builder
@Getter
@Setter
public class WageTableHistoryModel implements WageTableHistorySetMemento {

	/** The history id. */
	private String historyId;

	/** The start month. */
	private Integer startMonth;

	/** The end month. */
	private Integer endMonth;

	/** The head. */
	private WageTableHeadDto head;

	/** The demension details. */
	private List<WageTableDemensionDetailDto> demensionDetails;

	/** The value items. */
	private List<WageTableItemDto> valueItems;

	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWageTableCode(WageTableCode code) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setApplyRange(MonthRange applyRange) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDemensionDetail(List<WageTableElement> demensionDetails) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setValueItems(List<WageTableItem> elements) {
		// TODO Auto-generated method stub

	}

}
