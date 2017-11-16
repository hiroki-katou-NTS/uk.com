/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.estimate.personal;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstablishmentNumberOfDayDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstablishmentPriceDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstablishmentTimeDto;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.Year;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.EstimateNumberOfDay;
import nts.uk.ctx.at.schedule.dom.shift.estimate.personal.PersonalEstablishment;
import nts.uk.ctx.at.schedule.dom.shift.estimate.personal.PersonalEstablishmentGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPriceSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTimeSetting;

@Getter
@Setter
public class PersonalEstablishmentSaveCommand {
	
	/** The employee id. */
	private String employeeId;

	/** The estimate time. */
	private EstablishmentTimeDto estimateTime;
	
	/** The estimate price. */
	private EstablishmentPriceDto estimatePrice;
	
	/** The estimate number of day. */
	private EstablishmentNumberOfDayDto estimateNumberOfDay;
	
	/** The target year. */
	private int targetYear;
	
	
	/**
	 * To domain.
	 *
	 * @return the personal establishment
	 */
	public PersonalEstablishment toDomain(){
		return new PersonalEstablishment(new PersonalEstablishmentGetMementoImpl());
	}

	/**
	 * The Class PersonalEstablishmentGetMementoImpl.
	 */
	class PersonalEstablishmentGetMementoImpl implements PersonalEstablishmentGetMemento {
		
		/**
		 * Instantiates a new personal establishment get memento impl.
		 */
		public PersonalEstablishmentGetMementoImpl() {
			super();
		}
		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.Personal.
		 * PersonalEstablishmentGetMemento#getTargetYear()
		 */
		@Override
		public Year getTargetYear() {
			return new Year(targetYear);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.Personal.
		 * PersonalEstablishmentGetMemento#getAdvancedSetting()
		 */
		@Override
		public EstimateDetailSetting getAdvancedSetting() {
			return new EstimateDetailSetting(new EstimateDetailSettingGetMemento() {
				
				/*
				 * (non-Javadoc)
				 * 
				 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.
				 * EstimateDetailSettingGetMemento#getEstimateTime()
				 */
				@Override
				public List<EstimateTimeSetting> getEstimateTime() {
					List<EstimateTimeSetting> estimateTimeSettings = new ArrayList<>();
					estimateTimeSettings.add(estimateTime.toYearly());
					estimateTimeSettings.addAll(estimateTime.toListMonthly());
					return estimateTimeSettings;
				}

				/*
				 * (non-Javadoc)
				 * 
				 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.
				 * EstimateDetailSettingGetMemento#getEstimatePrice()
				 */
				@Override
				public List<EstimatedPriceSetting> getEstimatePrice() {
					return estimatePrice.toPriceSetting();
				}

				/*
				 * (non-Javadoc)
				 * 
				 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.
				 * EstimateDetailSettingGetMemento#getEstimateNumberOfDay()
				 */
				@Override
				public List<EstimateNumberOfDay> getEstimateNumberOfDay() {
					List<EstimateNumberOfDay> estimateDaysSettings = new ArrayList<>();
					estimateDaysSettings.add(estimateNumberOfDay.toYearly());
					estimateDaysSettings.addAll(estimateNumberOfDay.toListMonthly());
					return estimateDaysSettings;
				}
			});
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.personal.
		 * PersonalEstablishmentGetMemento#getEmployeeId()
		 */
		@Override
		public String getEmployeeId() {
			return employeeId;
		}
	}
}
