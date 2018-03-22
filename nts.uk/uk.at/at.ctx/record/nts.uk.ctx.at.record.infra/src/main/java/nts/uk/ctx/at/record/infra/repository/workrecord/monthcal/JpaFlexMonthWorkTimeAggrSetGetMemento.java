/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggregateTimeSetting;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.FlexAggregateMethod;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.ShortageFlexSetting;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSetGetMemento;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.KrcstFlexMCalSet;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class JpaWorkfixedRepository.
 */
@Stateless
public class JpaFlexMonthWorkTimeAggrSetGetMemento<T extends KrcstFlexMCalSet>
		implements FlexMonthWorkTimeAggrSetGetMemento {

	@Override
	public FlexAggregateMethod getAggrMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShortageFlexSetting getInsufficSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AggregateTimeSetting getLegalAggrSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NotUseAtr getIncludeOverTime() {
		// TODO Auto-generated method stub
		return null;
	}

}
