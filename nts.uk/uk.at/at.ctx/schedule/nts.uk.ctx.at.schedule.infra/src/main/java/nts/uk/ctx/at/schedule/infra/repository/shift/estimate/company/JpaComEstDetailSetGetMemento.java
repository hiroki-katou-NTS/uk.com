/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.company;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.EstimateNumberOfDay;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPriceSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTimeSetting;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstDaysCom;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstPriceCom;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstTimeCom;

/**
 * The Class JpaEstimateDetailSettingCompanyGetMemento.
 */
public class JpaComEstDetailSetGetMemento implements EstimateDetailSettingGetMemento{
	
	/** The estimate time companys. */
	private List<KscmtEstTimeCom> estimateTimeCompanys;
	
	/** The estimate price companys. */
	private List<KscmtEstPriceCom> estimatePriceCompanys;
	
	/** The estimate days companys. */
	private List<KscmtEstDaysCom> estimateDaysCompanys;

	
	/**
	 * Instantiates a new jpa estimate detail setting company get memento.
	 *
	 * @param estimateTimeCompanys the estimate time companys
	 */
	public JpaComEstDetailSetGetMemento(List<KscmtEstTimeCom> estimateTimeCompanys,
			List<KscmtEstPriceCom> estimatePriceCompanys,
			List<KscmtEstDaysCom> estimateDaysCompanys) {
		this.estimateTimeCompanys = estimateTimeCompanys;
		this.estimatePriceCompanys = estimatePriceCompanys;
		this.estimateDaysCompanys = estimateDaysCompanys;
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
		return this.estimateTimeCompanys.stream()
				.map(entity -> new EstimateTimeSetting(
						new JpaComEstTimeSetGetMemento(entity)))
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
		return this.estimatePriceCompanys.stream()
				.map(entity -> new EstimatedPriceSetting(
						new JpaComEstPriceGetMemento(entity)))
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
		return this.estimateDaysCompanys.stream()
				.map(entity -> new EstimateNumberOfDay(
						new JpaComEstDaysGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
