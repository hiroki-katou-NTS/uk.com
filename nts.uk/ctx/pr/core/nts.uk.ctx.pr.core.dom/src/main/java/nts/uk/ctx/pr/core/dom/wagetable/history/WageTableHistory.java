/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;

/**
 * The Class WageTableHistory.
 */
@Getter
public class WageTableHistory extends DomainObject {

	/** The company code. */
	private CompanyCode companyCode;

	/** The code. */
	private WageTableCode code;

	/** The history id. */
	private String historyId;

	/** The apply range. */
	private MonthRange applyRange;

	/** The demensions. */
	private List<WageTableDemensionDetail> demensionItems;

	/** The items. */
	private List<WageTableItem> valueItems;

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new wage table history.
	 *
	 * @param memento
	 *            the memento
	 */
	public WageTableHistory(WageTableHistoryGetMemento memento) {
		this.companyCode = memento.getCompanyCode();
		this.code = memento.getCode();
		this.historyId = memento.getHistoryId();
		this.applyRange = memento.getApplyRange();
		this.demensionItems = memento.getDemensionDetail();
		this.valueItems = memento.getValueItems();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WageTableHistorySetMemento memento) {
		memento.setCompanyCode(this.companyCode);
		memento.setCode(this.code);
		memento.setHistoryId(this.historyId);
		memento.setApplyRange(this.applyRange);
		memento.setDemensionDetail(this.demensionItems);
		memento.setValueItems(this.valueItems);
	}
}
