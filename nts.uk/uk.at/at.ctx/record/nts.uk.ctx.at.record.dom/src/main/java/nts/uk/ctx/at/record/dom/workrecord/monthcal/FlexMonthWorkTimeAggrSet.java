/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggregateTimeSetting;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.ShortageFlexSetting;

/**
 * The Class AggrSettingMonthlyOfFlxNew.
 */
@Getter
// フレックス時間勤務の月の集計設定
public class FlexMonthWorkTimeAggrSet extends DomainObject {

	/** The aggregate method. */
	// 集計方法
	private FlexAggregateMethod aggrMethod;

	/** The shortage set. */
	// 不足設定
	private ShortageFlexSetting insufficSet;

	/** The legal aggregate set. */
	// 法定内集計設定
	private AggregateTimeSetting legalAggrSet;

	/** The include over time. */
	// 残業時間をフレックス時間に含める
	private Boolean includeOverTime;
	
	/** The include illegal holiday work. */
	// 法定外休出時間をフレックス時間に含める
	private Boolean includeIllegalHdwk;

	/**
	 * Instantiates a new aggr setting monthly of flx new.
	 *
	 * @param memento
	 *            the memento
	 */
	public FlexMonthWorkTimeAggrSet(FlexMonthWorkTimeAggrSetGetMemento memento) {
		this.aggrMethod = memento.getAggrMethod();
		this.insufficSet = memento.getInsufficSet();
		this.legalAggrSet = memento.getLegalAggrSet();
		this.includeOverTime = memento.getIncludeOverTime();
		this.includeIllegalHdwk = memento.getIncludeIllegalHdwk();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(FlexMonthWorkTimeAggrSetSetMemento memento) {
		memento.setAggrMethod(this.aggrMethod);
		memento.setIncludeOverTime(this.includeOverTime);
		memento.setInsufficSet(this.insufficSet);
		memento.setLegalAggrSet(this.legalAggrSet);
		memento.setIncludeIllegalHdwk(this.includeIllegalHdwk);
	}

}
