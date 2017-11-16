/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.estimate.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPrice;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPriceSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPriceSettingGetMemento;

/**
 * The Class CompanyEstimatePriceDto.
 */
@Getter
@Setter
public class EstablishmentPriceDto implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The monthly estimates. */
	private List<EstimatePriceDto> monthlyEstimates;

	/** The yearly estimate. */
	private EstimatePriceDto yearlyEstimate;
	
	
	/**
	 * To price setting.
	 *
	 * @return the list
	 */
	
	public List<EstimatedPriceSetting> toPriceSetting(){
		List<EstimatedPriceSetting> estimatedPriceSettings = new ArrayList<>();
		estimatedPriceSettings.add(new EstimatedPriceSetting(new EstimatedPriceSettingGetMemento() {
			
			/**
			 * Gets the target classification.
			 *
			 * @return the target classification
			 */
			@Override
			public EstimateTargetClassification getTargetClassification() {
				return EstimateTargetClassification.YEARLY;
			}
			
			/**
			 * Gets the price setting.
			 *
			 * @return the price setting
			 */
			@Override
			public List<EstimatedPrice> getPriceSetting() {
				return yearlyEstimate.toPriceSetting();
			}
		}));
		
		monthlyEstimates.forEach(monthly->{
			estimatedPriceSettings.add(new EstimatedPriceSetting(new EstimatedPriceSettingGetMemento() {
				
				/**
				 * Gets the target classification.
				 *
				 * @return the target classification
				 */
				@Override
				public EstimateTargetClassification getTargetClassification() {
					return EstimateTargetClassification.valueOf(monthly.getMonth());
				}
				
				/**
				 * Gets the price setting.
				 *
				 * @return the price setting
				 */
				@Override
				public List<EstimatedPrice> getPriceSetting() {
					return monthly.toPriceSetting();
				}
			}));
		});
		return estimatedPriceSettings;
	}
}
