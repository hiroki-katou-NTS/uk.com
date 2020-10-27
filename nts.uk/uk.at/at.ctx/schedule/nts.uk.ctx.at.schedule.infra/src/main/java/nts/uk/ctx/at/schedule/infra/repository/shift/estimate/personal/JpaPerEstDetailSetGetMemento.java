/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.personal;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.EstimateNumberOfDay;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPriceSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTimeSetting;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstDaysSya;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstPriceSya;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstTimeSya;

/**
 * The Class JpaEstimateDetailSettingPersonalGetMemento.
 */
public class JpaPerEstDetailSetGetMemento implements EstimateDetailSettingGetMemento{
	
	/** The estimate time Personals. */
	private List<KscmtEstTimeSya> estimateTimePersonals;
	
	/** The estimate price Personals. */
	private List<KscmtEstPriceSya> estimatePricePersonals;
	
	/** The estimate days Personals. */
	private List<KscmtEstDaysSya> estimateDaysPersonals;

	
	/**
	 * Instantiates a new jpa estimate detail setting Personal get memento.
	 *
	 * @param estimateTimePersonals the estimate time Personals
	 */
	public JpaPerEstDetailSetGetMemento(List<KscmtEstTimeSya> estimateTimePersonals,
			List<KscmtEstPriceSya> estimatePricePersonals,
			List<KscmtEstDaysSya> estimateDaysPersonals) {
		this.estimateTimePersonals = estimateTimePersonals;
		this.estimatePricePersonals = estimatePricePersonals;
		this.estimateDaysPersonals = estimateDaysPersonals;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSettingGetMemento
	 * #getEstimateTime()
	 */
	@Override
	public List<EstimateTimeSetting> getEstimateTime() {
		return this.estimateTimePersonals.stream()
				.map(entity -> new EstimateTimeSetting(
						new JpaPerEstTimeSetGetMemento(entity)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSettingGetMemento
	 * #getEstimatePrice()
	 */
	@Override
	public List<EstimatedPriceSetting> getEstimatePrice() {
		return this.estimatePricePersonals.stream()
				.map(entity -> new EstimatedPriceSetting(
						new JpaPerEstPriceGetMemento(entity)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSettingGetMemento
	 * #getEstimateNumberOfDay()
	 */
	@Override
	public List<EstimateNumberOfDay> getEstimateNumberOfDay() {
		return this.estimateDaysPersonals.stream()
				.map(entity -> new EstimateNumberOfDay(
						new JpaPerEstDaysGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
