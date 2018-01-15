/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.estimate.company;

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
import nts.uk.ctx.at.schedule.dom.shift.estimate.company.CompanyEstablishment;
import nts.uk.ctx.at.schedule.dom.shift.estimate.company.CompanyEstablishmentGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.EstimateNumberOfDay;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPriceSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTimeSetting;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class CompanyEstablishmentSaveCommand.
 */
@Getter
@Setter
public class CompanyEstablishmentSaveCommand {

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
				
				/*
				 * (non-Javadoc)
				 * 
				 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.
				 * EstimateDetailSettingGetMemento#getEstimateTime()
				 */
				@Override
				public List<EstimateTimeSetting> getEstimateTime() {
					List<EstimateTimeSetting> estimateTimeSettings = new ArrayList<>();
					estimateTimeSettings.add(command.getEstimateTime().toYearly());
					estimateTimeSettings.addAll(command.getEstimateTime().toListMonthly());
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
					return command.getEstimatePrice().toPriceSetting();
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
					estimateDaysSettings.add(command.getEstimateNumberOfDay().toYearly());
					estimateDaysSettings.addAll(command.getEstimateNumberOfDay().toListMonthly());
					return estimateDaysSettings;
				}
			});
		}
	}
}
