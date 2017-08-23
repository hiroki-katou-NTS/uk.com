package nts.uk.ctx.at.schedule.infra.repository.shift.totaltimes;

import nts.uk.ctx.at.schedule.dom.shift.totaltimes.ConditionThresholdLimit;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalConditionGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.UseAtr;
import nts.uk.ctx.at.schedule.infra.entity.shift.totaltimes.KshstTotalCondition;

public class JpaTotalConditionGetMemento implements TotalConditionGetMemento {

	/** The entity. */
	private KshstTotalCondition entity;

	/**
	 * Instantiates a new jpa total condition set memento.
	 *
	 * @param totalTimes
	 *            the total times
	 */
	public JpaTotalConditionGetMemento(KshstTotalCondition totalTimes) {
		this.entity = totalTimes;
	}

	@Override
	public UseAtr getUpperLimitSettingAtr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UseAtr getLowerLimitSettingAtr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConditionThresholdLimit getThresoldUpperLimit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConditionThresholdLimit getThresoldLowerLimit() {
		// TODO Auto-generated method stub
		return null;
	}

}
