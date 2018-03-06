/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.AggrSettingMonthlyOfFlxNew;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.SetCalMonthlyFlex;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Class WkpCalSetMonthlyActualFlex.
 */
@Getter
//* フレックス職場別月別実績集計設定.
public class WkpCalSetMonthlyActualFlex extends AggregateRoot implements SetCalMonthlyFlex {
	
	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;
	
	/** The workplace id. */
	/** 職場ID. */
	private WorkplaceId workplaceId;
	
	/** The aggr setting monthly of flx new. */
	/** フレックス職場別月別実績集計設定. */
	private AggrSettingMonthlyOfFlxNew aggrSettingMonthlyOfFlxNew;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.SetCalMonthlyFlex#getAggrSettingMonthlyOfFlxNew()
	 */
	@Override
	public AggrSettingMonthlyOfFlxNew getAggrSettingMonthlyOfFlxNew() {
		return aggrSettingMonthlyOfFlxNew;
	}
	
	/**
	 * Instantiates a new wkp cal set monthly actual flex.
	 *
	 * @param memento the memento
	 */
	public WkpCalSetMonthlyActualFlex (WkpCalSetMonthlyActualFlex memento) {
		this.companyId  = memento.getCompanyId();
		this.workplaceId = memento.getWorkplaceId();
		this.aggrSettingMonthlyOfFlxNew = memento.getAggrSettingMonthlyOfFlxNew();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento (WkpCalSetMonthlyActualFlexSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkplaceId(this.workplaceId);
		memento.setAggrSettingMonthlyOfFlxNew(this.aggrSettingMonthlyOfFlxNew);	
	}
	
}
