/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.base.simplehistory.History;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.element.WageTableElement;

/**
 * The Class WageTableHistory.
 */
@Getter
public class WageTableHistory extends DomainObject implements History<WageTableHistory> {

	/** The company code. */
	private CompanyCode companyCode;

	/** The wage table code. */
	private WageTableCode wageTableCode;

	/** The history id. */
	private String historyId;

	/** The apply range. */
	private MonthRange applyRange;

	/** The demension items. */
	private List<WageTableElement> demensionItems;

	/** The value items. */
	private List<WageTableItem> valueItems;

	/**
	 * Instantiates a new unit price history.
	 */
	private WageTableHistory() {
		this.historyId = IdentifierUtil.randomUniqueId();
	};

	
	
	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new wage table history.
	 *
	 * @param memento
	 *            the memento
	 */
	public WageTableHistory(WageTableHistoryGetMemento memento) {
		this.companyCode = memento.getCompanyCode();
		this.wageTableCode = memento.getWageTableCode();
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
		memento.setWageTableCode(this.wageTableCode);
		memento.setHistoryId(this.historyId);
		memento.setApplyRange(this.applyRange);
		memento.setDemensionDetail(this.demensionItems);
		memento.setValueItems(this.valueItems);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.History#getUuid()
	 */
	@Override
	public String getUuid() {
		return this.historyId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.History#getMasterCode()
	 */
	@Override
	public PrimitiveValue<String> getMasterCode() {
		return this.wageTableCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.History#getStart()
	 */
	@Override
	public YearMonth getStart() {
		return this.applyRange.getStartMonth();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.base.simplehistory.History#getEnd()
	 */
	@Override
	public YearMonth getEnd() {
		return this.applyRange.getEndMonth();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.base.simplehistory.History#setStart(nts.arc.time.
	 * YearMonth)
	 */
	@Override
	public void setStart(YearMonth yearMonth) {
		this.applyRange = MonthRange.range(yearMonth, this.applyRange.getEndMonth());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.base.simplehistory.History#setEnd(nts.arc.time.
	 * YearMonth)
	 */
	@Override
	public void setEnd(YearMonth yearMonth) {
		this.applyRange = MonthRange.range(this.applyRange.getStartMonth(), yearMonth);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.base.simplehistory.History#copyWithDate(nts.arc.
	 * time.YearMonth)
	 */
	@Override
	public WageTableHistory copyWithDate(YearMonth start) {
		WageTableHistory history = new WageTableHistory();
		history.companyCode = this.companyCode;
		history.wageTableCode = this.wageTableCode;
		history.applyRange = MonthRange.toMaxDate(start);
		// TODO NEED A DEEP CLONE HERE
		history.demensionItems = Collections.emptyList();
		history.valueItems = Collections.emptyList();
		return history;
	}

	/**
	 * Creates the with intial.
	 *
	 * @param companyCode
	 *            the company code
	 * @param wageTableCode
	 *            the wage table code
	 * @param startYearMonth
	 *            the start year month
	 * @return the wage table history
	 */
	public static final WageTableHistory createWithIntial(CompanyCode companyCode,
			WageTableCode wageTableCode, YearMonth startYearMonth) {
		WageTableHistory history = new WageTableHistory();
		history.companyCode = companyCode;
		history.wageTableCode = wageTableCode;
		history.applyRange = MonthRange.toMaxDate(startYearMonth);
		history.demensionItems = Collections.emptyList();
		history.valueItems = Collections.emptyList();
		return history;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((historyId == null) ? 0 : historyId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WageTableHistory other = (WageTableHistory) obj;
		if (historyId == null) {
			if (other.historyId != null)
				return false;
		} else if (!historyId.equals(other.historyId))
			return false;
		return true;
	}
}
