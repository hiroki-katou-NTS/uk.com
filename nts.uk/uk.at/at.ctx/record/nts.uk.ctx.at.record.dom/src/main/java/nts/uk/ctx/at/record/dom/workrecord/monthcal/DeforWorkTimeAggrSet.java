/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal;

import java.io.Serializable;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.DeforLaborCalSetting;

/**
 * The Class LegalAggrSetOfIrgNew.
 */
@Getter
// 変形労働時間勤務の法定内集計設定
public class DeforWorkTimeAggrSet extends DomainObject implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** The aggregate time set. */
	// 集計時間設定
	private ExcessOutsideTimeSetReg aggregateTimeSet;

	/** The excess outside time set. */
	// 時間外超過設定
	private ExcessOutsideTimeSetReg excessOutsideTimeSet;

	/** The calc set of irregular. */
	// 変形労働計算の設定
	private DeforLaborCalSetting deforLaborCalSetting;

	/** The settlement period. */
	// 精算期間
	private DeforLaborSettlementPeriod settlementPeriod;

	/**
	 * Gets the acqui of defor labor acc period.
	 *
	 * @return the acqui of defor labor acc period
	 */
	public DeforLaborSettlementPeriod getDeforLaborAccPeriod() {
		return this.settlementPeriod;
	}

	/**
	 * Instantiates a new aggr setting monthly of flx new.
	 *
	 * @param memento
	 *            the memento
	 */
	public DeforWorkTimeAggrSet(DeforWorkTimeAggrSetGetMemento memento) {
		this.aggregateTimeSet = memento.getAggregateTimeSet();
		this.excessOutsideTimeSet = memento.getExcessOutsideTimeSet();
		this.deforLaborCalSetting = memento.getDeforLaborCalSetting();
		this.settlementPeriod = memento.getSettlementPeriod();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(DeforWorkTimeAggrSetSetMemento memento) {
		memento.setAggregateTimeSet(this.aggregateTimeSet);
		memento.setExcessOutsideTimeSet(this.excessOutsideTimeSet);
		memento.setDeforLaborCalSetting(this.deforLaborCalSetting);
		memento.setSettlementPeriod(this.settlementPeriod);
	}

}
