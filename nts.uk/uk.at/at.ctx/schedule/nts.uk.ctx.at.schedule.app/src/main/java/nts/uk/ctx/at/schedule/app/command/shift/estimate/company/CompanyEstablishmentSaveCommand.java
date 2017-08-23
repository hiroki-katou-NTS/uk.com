/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.estimate.company;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.company.dto.CompanyEstimateTimeDto;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.Year;
import nts.uk.ctx.at.schedule.dom.shift.estimate.company.CompanyEstablishment;
import nts.uk.ctx.at.schedule.dom.shift.estimate.company.CompanyEstablishmentGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.EstimateNumberOfDay;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPriceSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTimeSetting;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

@Getter
@Setter
public class CompanyEstablishmentSaveCommand {

	/** The estimate time. */
	private CompanyEstimateTimeDto estimateTime;
	
	private int targetYear;
	
	
	/**
	 * To domain.
	 *
	 * @param companyId the company id
	 * @return the company establishment
	 */
	public CompanyEstablishment toDomain(String companyId){
		return new CompanyEstablishment(new CompanyEstablishmentGetMementoImpl(companyId, this));
	}

	/**
	 * The Class CompanyEstablishmentGetMementoImpl.
	 */
	class CompanyEstablishmentGetMementoImpl implements CompanyEstablishmentGetMemento {
		private String companyId;

		private CompanyEstablishmentSaveCommand command;

		/**
		 * Instantiates a new company establishment get memento impl.
		 *
		 * @param companyId the company id
		 * @param command the command
		 */
		public CompanyEstablishmentGetMementoImpl(String companyId,
				CompanyEstablishmentSaveCommand command) {
			this.command = command;
			this.companyId = companyId;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.company.
		 * CompanyEstablishmentGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(companyId);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.company.
		 * CompanyEstablishmentGetMemento#getTargetYear()
		 */
		@Override
		public Year getTargetYear() {
			return new Year(command.getTargetYear());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.company.
		 * CompanyEstablishmentGetMemento#getAdvancedSetting()
		 */
		@Override
		public EstimateDetailSetting getAdvancedSetting() {
			return new EstimateDetailSetting(new EstimateDetailSettingGetMemento() {
				
				/**
				 * Gets the estimate time.
				 *
				 * @return the estimate time
				 */
				@Override
				public List<EstimateTimeSetting> getEstimateTime() {
					List<EstimateTimeSetting> estimateTimeSettings = new ArrayList<>();
					estimateTimeSettings.add(command.getEstimateTime().toYearly());
					estimateTimeSettings.addAll(command.getEstimateTime().toListMonthly());
					return estimateTimeSettings;
				}
				
				/**
				 * Gets the estimate price.
				 *
				 * @return the estimate price
				 */
				@Override
				public List<EstimatedPriceSetting> getEstimatePrice() {
					return new ArrayList<>();
				}
				
				/**
				 * Gets the estimate number of day.
				 *
				 * @return the estimate number of day
				 */
				@Override
				public List<EstimateNumberOfDay> getEstimateNumberOfDay() {
					return new ArrayList<>();
				}
			});
		}
	}
}
