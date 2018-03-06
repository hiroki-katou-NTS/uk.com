/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.CalcSettingOfIrregular;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.SettlementPeriodOfIrg;

/**
 * The Class LegalAggrSetOfIrgNew.
 */

/**
 * Gets the trans labor pay period.
 *
 * @return the trans labor pay period
 */
@Getter
//* 変形労働時間勤務の法定内集計設定
public class LegalAggrSetOfIrgNew extends DomainObject{
	
	/** The aggregate time set. */
	/** 割増集計方法. */
	private ExcessOutsideTimeSetReg aggregateTimeSet;
	
	/** The excess outside time set. */
	/** 割増集計方法. */
	private ExcessOutsideTimeSetReg excessOutsideTimeSet;
	
	/** The calc set of irregular. */
	/** 変形労働計算の設定. */
	private CalcSettingOfIrregular calcSetOfIrregular;
	
	/** The settlement period. */
	/** 変形労働の精算期間. */
	private SettlementPeriodOfIrg settlementPeriod;
		
	/** The trans labor pay period. */
	/** 変形労働精算期間. */
	private SettlementPeriodOfIrgNew transLaborPayPeriod;
	
	/**
	 * Gets the acqui of defor labor acc period.
	 *
	 * @return the acqui of defor labor acc period
	 */
	public SettlementPeriodOfIrgNew getAcquiOfDeforLaborAccPeriod(){
		return this.transLaborPayPeriod;	
	}
	
	/**
	 * Instantiates a new legal aggr set of irg new.
	 *
	 * @param memento the memento
	 */
	public LegalAggrSetOfIrgNew (LegalAggrSetOfIrgNew memento) {
		this.aggregateTimeSet  = memento.getAggregateTimeSet();
		this.excessOutsideTimeSet = memento.getExcessOutsideTimeSet();
		this.calcSetOfIrregular = memento.getCalcSetOfIrregular();
		this.settlementPeriod = memento.getSettlementPeriod();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento (LegalAggrSetOfIrgNewSetMemento memento) {
		memento.setAggregateTimeSet(this.aggregateTimeSet);
		memento.setExcessOutsideTimeSet(this.excessOutsideTimeSet);
		memento.setCalcSettingOfIrregular(this.calcSetOfIrregular);
		memento.setSettlementPeriod(this.settlementPeriod);		
	}
}
