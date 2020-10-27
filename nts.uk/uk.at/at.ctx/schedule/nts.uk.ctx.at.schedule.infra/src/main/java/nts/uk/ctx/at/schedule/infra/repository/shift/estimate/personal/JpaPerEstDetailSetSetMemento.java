/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.personal;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.EstimateNumberOfDay;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPriceSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTimeSetting;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstDaysSya;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstPriceSya;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstTimeSya;

/**
 * The Class JpaEstimateDetailSettingPersonalSetMemento.
 */
public class JpaPerEstDetailSetSetMemento implements EstimateDetailSettingSetMemento{
	
	/** The estimate time Personals. */
	private List<KscmtEstTimeSya> estimateTimePersonals;
	
	/** The estimate price Personals. */
	private List<KscmtEstPriceSya> estimatePricePersonals;
	
	/** The estimate days Personals. */
	private List<KscmtEstDaysSya> estimateDaysPersonals;

	
	/**
	 * Instantiates a new jpa estimate detail setting Personal set memento.
	 *
	 * @param estimateTimePersonals the estimate time Personals
	 * @param estimatePricePersonals the estimate price Personals
	 */
	public JpaPerEstDetailSetSetMemento(List<KscmtEstTimeSya> estimateTimePersonals,
			List<KscmtEstPriceSya> estimatePricePersonals,List<KscmtEstDaysSya> estimateDaysPersonals) {
		this.estimateTimePersonals = estimateTimePersonals;
		this.estimatePricePersonals = estimatePricePersonals;
		this.estimateDaysPersonals = estimateDaysPersonals;
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
			this.estimateTimePersonals.forEach(entity -> {
				if (entity.getKscmtEstTimeSyaPK()
						.getTargetCls() == estimateSetting.getTargetClassification().value) {
					estimateSetting
							.saveToMemento(new JpaPerEstTimeSetSetMemento(entity));
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
			this.estimatePricePersonals.forEach(entity -> {
				if (entity.getKscmtEstPriceSyaPK()
						.getTargetCls() == estimateSetting.getTargetClassification().value) {
					estimateSetting.saveToMemento(new JpaPerEstPriceSetMemento(entity));
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
			this.estimateDaysPersonals.forEach(entity -> {
				if (entity.getKscmtEstDaysSyaPK()
						.getTargetCls() == estimateSetting.getTargetClassification().value) {
					estimateSetting
							.saveToMemento(new JpaPerEstDaysSetMemento(entity));
				}
			});
		});

	}
}
