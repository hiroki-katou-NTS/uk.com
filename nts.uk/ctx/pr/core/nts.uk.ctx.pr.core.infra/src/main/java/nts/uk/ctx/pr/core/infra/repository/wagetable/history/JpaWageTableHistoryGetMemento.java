/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.history;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import nts.arc.time.YearMonth;
import nts.gul.collection.ListUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableDemensionDetail;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItem;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableEleHist;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableHist;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableMny;

/**
 * The Class JpaWageTableHistoryGetMemento.
 */
public class JpaWageTableHistoryGetMemento implements WageTableHistoryGetMemento {

	/** The type value. */
	protected QwtmtWagetableHist typeValue;

	/**
	 * Instantiates a new jpa wage table history get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWageTableHistoryGetMemento(QwtmtWagetableHist typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryGetMemento#
	 * getCompanyCode()
	 */
	@Override
	public CompanyCode getCompanyCode() {
		return new CompanyCode(this.typeValue.getQwtmtWagetableHistPK().getCcd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryGetMemento#
	 * getCode()
	 */
	@Override
	public WageTableCode getCode() {
		return new WageTableCode(this.typeValue.getQwtmtWagetableHistPK().getWageTableCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryGetMemento#
	 * getHistoryId()
	 */
	@Override
	public String getHistoryId() {
		return this.typeValue.getQwtmtWagetableHistPK().getHistId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryGetMemento#
	 * getApplyRange()
	 */
	@Override
	public MonthRange getApplyRange() {
		return MonthRange.range(YearMonth.of(this.typeValue.getStrYm()),
				YearMonth.of(this.typeValue.getEndYm()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryGetMemento#
	 * getValueItems()
	 */
	@Override
	public List<WageTableItem> getValueItems() {
		List<QwtmtWagetableMny> qwtmtWagetableMnyList = this.typeValue.getQwtmtWagetableMnyList();

		// Check empty
		if (ListUtil.isEmpty(qwtmtWagetableMnyList)) {
			return Collections.emptyList();
		}

		return qwtmtWagetableMnyList.stream()
				.map(item -> new WageTableItem(new JpaWageTableItemGetMemento(item)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryGetMemento#
	 * getDemensionDetail()
	 */
	@Override
	public List<WageTableDemensionDetail> getDemensionDetail() {
		List<QwtmtWagetableEleHist> qwtmtWagetableEleHistList = this.typeValue
				.getQwtmtWagetableEleHistList();

		// Check empty
		if (ListUtil.isEmpty(qwtmtWagetableEleHistList)) {
			return Collections.emptyList();
		}

		return qwtmtWagetableEleHistList.stream()
				.map(item -> new WageTableDemensionDetail(new JpaWageTableDetailGetMemento(item)))
				.collect(Collectors.toList());
	}

}
