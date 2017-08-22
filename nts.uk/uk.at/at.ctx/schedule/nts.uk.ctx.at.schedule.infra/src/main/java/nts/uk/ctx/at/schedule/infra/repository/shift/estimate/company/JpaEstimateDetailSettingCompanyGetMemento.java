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
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstTimeComSet;

/**
 * The Class JpaEstimateDetailSettingCompanyGetMemento.
 */
public class JpaEstimateDetailSettingCompanyGetMemento implements EstimateDetailSettingGetMemento{
	
	/** The estimate time companys. */
	private List<KscmtEstTimeComSet> estimateTimeCompanys;

	
	/**
	 * Instantiates a new jpa estimate detail setting company get memento.
	 *
	 * @param estimateTimeCompanys the estimate time companys
	 */
	public JpaEstimateDetailSettingCompanyGetMemento(List<KscmtEstTimeComSet> estimateTimeCompanys){
		this.estimateTimeCompanys = estimateTimeCompanys;
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
						new JpaEstimateTimeSettingCompanyGetMemento(entity)))
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

}
