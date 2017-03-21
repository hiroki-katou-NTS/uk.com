/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.history;

import java.util.Collections;
import java.util.List;

import nts.arc.time.YearMonth;
import nts.gul.collection.LazyList;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.wagetable.DemensionNo;
import nts.uk.ctx.pr.core.dom.wagetable.ElementType;
import nts.uk.ctx.pr.core.dom.wagetable.WtCode;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtHistoryGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtItem;
import nts.uk.ctx.pr.core.dom.wagetable.history.element.ElementSetting;
import nts.uk.ctx.pr.core.dom.wagetable.history.element.StepElementSetting;
import nts.uk.ctx.pr.core.infra.entity.wagetable.element.QwtmtWagetableElement;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableEleHistPK;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableHist;

/**
 * The Class JpaWageTableHistoryGetMemento.
 */
public class JpaWtHistoryGetMemento implements WtHistoryGetMemento {

	/** The type value. */
	protected QwtmtWagetableHist typeValue;

	/**
	 * Instantiates a new jpa wage table history get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWtHistoryGetMemento(QwtmtWagetableHist typeValue) {
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
	public WtCode getWageTableCode() {
		return new WtCode(this.typeValue.getQwtmtWagetableHistPK().getWageTableCd());
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
	public List<WtItem> getValueItems() {
		// Ret.
		return LazyList.withMap(() -> this.typeValue.getQwtmtWagetableMnyList(), (entity) -> {
			return new WtItem(new JpaWtItemGetMemento(entity));
		});
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WtHistoryGetMemento#getElementSettings()
	 */
	@Override
	public List<ElementSetting> getElementSettings() {
		return LazyList.withMap(() -> this.typeValue.getQwtmtWagetableEleHistList(), (entity) -> {
			QwtmtWagetableEleHistPK pk = entity.getQwtmtWagetableEleHistPK();
			QwtmtWagetableElement element = entity.getQwtmtWagetableElement();
			ElementType type = ElementType.valueOf(element.getDemensionType());
			ElementSetting el;

			// TODO: NEED TO LOAD STEP.
			if (type.isRangeMode) {
				el = new StepElementSetting(DemensionNo.valueOf(pk.getDemensionNo()),
						type,
						Collections.emptyList());
			} else {
				el = new ElementSetting(DemensionNo.valueOf(pk.getDemensionNo()),
						type,
						Collections.emptyList());
			}
			return el;
		});
	}

}
