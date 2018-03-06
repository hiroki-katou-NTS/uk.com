/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.ShortageFlexSetting;
import nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime.NotUseAtr;

/**
 * The Class AggrSettingMonthlyOfFlxNew.
 */
@Getter
//* フレックス時間勤務の月の集計設定.
public class AggrSettingMonthlyOfFlxNew extends DomainObject{

	/** The aggregate method. */
	/** フレックス集計方法 */
	private FlexAggregateMethod aggregateMethod;
	
	/** The include over time. */
	/** するしない区分. */
	private NotUseAtr includeOverTime;
	
	/** The shortage set. */
	/** フレックス不足設定 */
	private ShortageFlexSetting shortageSet;
	
	/** The legal aggregate set. */
	/** 法定内フレックス時間集計 */	
	private LegalAggFlexTime legalAggregateSet;
	
	/**
	 * Instantiates a new aggr setting monthly of flx new.
	 *
	 * @param memento the memento
	 */
	public AggrSettingMonthlyOfFlxNew (AggrSettingMonthlyOfFlxNew memento) {
		this.aggregateMethod  = memento.getAggregateMethod();
		this.includeOverTime = memento.getIncludeOverTime();
		this.shortageSet = memento.getShortageSet();
		this.legalAggregateSet = memento.getLegalAggregateSet();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento (AggrSettingMonthlyOfFlxNewSetMemento memento) {
		memento.setFlexAggregateMethod(this.aggregateMethod);
		memento.setIncludeOverTime(this.includeOverTime);
		memento.setShortageFlexSetting(this.shortageSet);
		memento.setLegalAggrSetOfFlx(this.legalAggregateSet);
	}

}
