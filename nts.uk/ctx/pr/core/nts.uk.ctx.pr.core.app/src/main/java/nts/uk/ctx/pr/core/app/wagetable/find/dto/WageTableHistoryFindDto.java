/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.find.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableDemensionDetail;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistorySetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItem;

/**
 * Instantiates a new certification find dto.
 */
@Data
public class WageTableHistoryFindDto implements WageTableHistorySetMemento {

	/** The code. */
	private String code;

	/** The history id. */
	private String historyId;

	private String startMonth;

	private String endMonth;

	@Override
	public void setCompanyCode(CompanyCode companyCode) {

	}

	@Override
	public void setCode(WageTableCode code) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHistoryId(String historyId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setApplyRange(MonthRange applyRange) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDemensionDetail(List<WageTableDemensionDetail> demensionDetail) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setValueItems(List<WageTableItem> elements) {
		// TODO Auto-generated method stub

	}

}
