/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.employment;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.EstimateNumberOfDay;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPriceSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTimeSetting;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstDaysEmp;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstPriceEmp;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstTimeEmp;

/**
 * The Class JpaEstimateDetailSettingEmploymentSetMemento.
 */
public class JpaEmpEstDetailSetSetMemento implements EstimateDetailSettingSetMemento{
	
	/** The estimate time Employments. */
	private List<KscmtEstTimeEmp> estimateTimeEmployments;
	
	/** The estimate price Employments. */
	private List<KscmtEstPriceEmp> estimatePriceEmployments;
	
	/** The estimate days Employments. */
	private List<KscmtEstDaysEmp> estimateDaysEmployments;

	
	/**
	 * Instantiates a new jpa estimate detail setting Employment set memento.
	 *
	 * @param estimateTimeEmployments the estimate time Employments
	 * @param estimatePriceEmployments the estimate price Employments
	 */
	public JpaEmpEstDetailSetSetMemento(List<KscmtEstTimeEmp> estimateTimeEmployments,
			List<KscmtEstPriceEmp> estimatePriceEmployments,List<KscmtEstDaysEmp> estimateDaysEmployments) {
		this.estimateTimeEmployments = estimateTimeEmployments;
		this.estimatePriceEmployments = estimatePriceEmployments;
		this.estimateDaysEmployments = estimateDaysEmployments;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSettingSetMemento
	 * #setEstimateTime(java.util.List)
	 */
	@Override
	public void setEstimateTime(List<EstimateTimeSetting> estimateTime) {

		estimateTime.forEach(estimateSetting -> {
			this.estimateTimeEmployments.forEach(entity -> {
				if (entity.getKscmtEstTimeEmpPK()
						.getTargetCls() == estimateSetting.getTargetClassification().value) {
					estimateSetting
							.saveToMemento(new JpaEmpEstTimeSetSetMemento(entity));
				}
			});
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSettingSetMemento
	 * #setEstimatePrice(java.util.List)
	 */
	@Override
	public void setEstimatePrice(List<EstimatedPriceSetting> estimatePrice) {
		estimatePrice.forEach(estimateSetting -> {
			this.estimatePriceEmployments.forEach(entity -> {
				if (entity.getKscmtEstPriceEmpPK()
						.getTargetCls() == estimateSetting.getTargetClassification().value) {
					estimateSetting.saveToMemento(new JpaEmpEstPriceSetMemento(entity));
				}
			});
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSettingSetMemento
	 * #setEstimateNumberOfDay(java.util.List)
	 */
	@Override
	public void setEstimateNumberOfDay(List<EstimateNumberOfDay> estimateNumberOfDay) {
		estimateNumberOfDay.forEach(estimateSetting -> {
			this.estimateDaysEmployments.forEach(entity -> {
				if (entity.getKscmtEstDaysEmpPK()
						.getTargetCls() == estimateSetting.getTargetClassification().value) {
					estimateSetting
							.saveToMemento(new JpaEmpEstDaysSetMemento(entity));
				}
			});
		});

	}
}
