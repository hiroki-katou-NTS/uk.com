/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.employment;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.EstimateNumberOfDay;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPriceSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTimeSetting;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstDaysEmp;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstPriceEmp;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstTimeEmp;

/**
 * The Class JpaEstimateDetailSettingEmploymentGetMemento.
 */
public class JpaEmpEstDetailSetGetMemento implements EstimateDetailSettingGetMemento{
	
	/** The estimate time Employments. */
	private List<KscmtEstTimeEmp> estimateTimeEmployments;
	
	/** The estimate price Employments. */
	private List<KscmtEstPriceEmp> estimatePriceEmployments;
	
	/** The estimate days Employments. */
	private List<KscmtEstDaysEmp> estimateDaysEmployments;

	
	/**
	 * Instantiates a new jpa estimate detail setting Employment get memento.
	 *
	 * @param estimateTimeEmployments the estimate time Employments
	 */
	public JpaEmpEstDetailSetGetMemento(List<KscmtEstTimeEmp> estimateTimeEmployments,
			List<KscmtEstPriceEmp> estimatePriceEmployments,
			List<KscmtEstDaysEmp> estimateDaysEmployments) {
		this.estimateTimeEmployments = estimateTimeEmployments;
		this.estimatePriceEmployments = estimatePriceEmployments;
		this.estimateDaysEmployments = estimateDaysEmployments;
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
		return this.estimateTimeEmployments.stream()
				.map(entity -> new EstimateTimeSetting(
						new JpaEmpEstTimeSetGetMemento(entity)))
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
		return this.estimatePriceEmployments.stream()
				.map(entity -> new EstimatedPriceSetting(
						new JpaEmpEstPriceGetMemento(entity)))
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
		return this.estimateDaysEmployments.stream()
				.map(entity -> new EstimateNumberOfDay(
						new JpaEmpEstDaysGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
