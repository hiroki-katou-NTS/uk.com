/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.DeforLaborCalSetting;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforLaborSettlementPeriod;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSetGetMemento;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.ExcessOutsideTimeSetReg;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.KrcstDeforMCalSet;

/**
 * The Class JpaWorkfixedRepository.
 */
@Stateless
public class JpaDeforWorkTimeAggrSetGetMemento<T extends KrcstDeforMCalSet>
		implements DeforWorkTimeAggrSetGetMemento {

	/** The type value. */
	private T typeValue;

	/**
	 * Instantiates a new jpa defor work time aggr set get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaDeforWorkTimeAggrSetGetMemento(T typeValue) {
		super();
		this.typeValue = typeValue;
	}

	@Override
	public ExcessOutsideTimeSetReg getAggregateTimeSet() {
		return null;
	}

	@Override
	public ExcessOutsideTimeSetReg getExcessOutsideTimeSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeforLaborCalSetting getDeforLaborCalSetting() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeforLaborSettlementPeriod getSettlementPeriod() {
		// TODO Auto-generated method stub
		return null;
	}

}
