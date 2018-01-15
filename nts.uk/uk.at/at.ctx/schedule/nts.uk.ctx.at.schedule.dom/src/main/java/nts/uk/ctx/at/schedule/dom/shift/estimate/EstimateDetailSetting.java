/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.EstimateNumberOfDay;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPriceSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTimeSetting;

/**
 * The Class EstimateDetailSetting.
 */
// 目安詳細設定
@Getter
public class EstimateDetailSetting extends DomainObject{

	
	/** The estimate time. */
	// 目安時間条件
	private List<EstimateTimeSetting> estimateTime;
	
	/** The estimate price. */
	// 目安金額条件
	private List<EstimatedPriceSetting> estimatePrice;
	
	
	/** The estimate number of day. */
	// 目安日数条件
	private List<EstimateNumberOfDay> estimateNumberOfDay;
	
	
	/**
	 * Instantiates a new estimate detail setting.
	 *
	 * @param memento the memento
	 */
	public EstimateDetailSetting(EstimateDetailSettingGetMemento memento){
		this.estimateTime = memento.getEstimateTime();
		this.estimatePrice = memento.getEstimatePrice();
		this.estimateNumberOfDay = memento.getEstimateNumberOfDay();
	}
	
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(EstimateDetailSettingSetMemento memento){
		memento.setEstimateTime(this.estimateTime);
		memento.setEstimatePrice(this.estimatePrice);
		memento.setEstimateNumberOfDay(this.estimateNumberOfDay);
	}
	
}
