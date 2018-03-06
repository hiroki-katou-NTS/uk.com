/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult;

import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.CalcSettingOfIrregular;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.SettlementPeriodOfIrg;

/**
 * The Interface LegalAggrSetOfIrgNewGetMemento.
 */
public interface LegalAggrSetOfIrgNewSetMemento {

	/**
     * Sets the aggregate time set.
     *
     * @param aggregateTimeSet the new aggregate time set
     */
    void setAggregateTimeSet(ExcessOutsideTimeSetReg aggregateTimeSet);

	/**
     * Sets the excess outside time set.
     *
     * @param excessOutsideTimeSet the new excess outside time set
     */
    void setExcessOutsideTimeSet(ExcessOutsideTimeSetReg excessOutsideTimeSet);

	/**
     * Sets the calc setting of irregular.
     *
     * @param calcSettingOfIrregular the new calc setting of irregular
     */
    void setCalcSettingOfIrregular(CalcSettingOfIrregular calcSettingOfIrregular);

	/**
     * Sets the settlement period.
     *
     * @param settlementPeriod the new settlement period
     */
    void setSettlementPeriod(SettlementPeriodOfIrg settlementPeriod);
}
