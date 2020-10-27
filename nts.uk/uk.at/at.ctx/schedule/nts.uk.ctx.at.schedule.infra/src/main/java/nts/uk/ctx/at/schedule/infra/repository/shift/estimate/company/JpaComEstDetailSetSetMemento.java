/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.company;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.EstimateNumberOfDay;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPriceSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTimeSetting;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstDaysCom;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstPriceCom;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstTimeCom;

/**
 * The Class JpaEstimateDetailSettingCompanySetMemento.
 */
public class JpaComEstDetailSetSetMemento implements EstimateDetailSettingSetMemento{
	
	/** The estimate time companys. */
	private List<KscmtEstTimeCom> estimateTimeCompanys;
	
	/** The estimate price companys. */
	private List<KscmtEstPriceCom> estimatePriceCompanys;
	
	/** The estimate days companys. */
	private List<KscmtEstDaysCom> estimateDaysCompanys;

	
	/**
	 * Instantiates a new jpa estimate detail setting company set memento.
	 *
	 * @param estimateTimeCompanys the estimate time companys
	 * @param estimatePriceCompanys the estimate price companys
	 */
	public JpaComEstDetailSetSetMemento(List<KscmtEstTimeCom> estimateTimeCompanys,
			List<KscmtEstPriceCom> estimatePriceCompanys,List<KscmtEstDaysCom> estimateDaysCompanys) {
		this.estimateTimeCompanys = estimateTimeCompanys;
		this.estimatePriceCompanys = estimatePriceCompanys;
		this.estimateDaysCompanys = estimateDaysCompanys;
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
			this.estimateTimeCompanys.forEach(entity -> {
				if (entity.getKscmtEstTimeComPK()
						.getTargetCls() == estimateSetting.getTargetClassification().value) {
					estimateSetting
							.saveToMemento(new JpaComEstTimeSetSetMemento(entity));
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
			this.estimatePriceCompanys.forEach(entity -> {
				if (entity.getKscmtEstPriceComPK()
						.getTargetCls() == estimateSetting.getTargetClassification().value) {
					estimateSetting.saveToMemento(new JpaComEstPriceSetMemento(entity));
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
			this.estimateDaysCompanys.forEach(entity -> {
				if (entity.getKscmtEstDaysComPK()
						.getTargetCls() == estimateSetting.getTargetClassification().value) {
					estimateSetting
							.saveToMemento(new JpaComEstDaysSetMemento(entity));
				}
			});
		});

	}
}
