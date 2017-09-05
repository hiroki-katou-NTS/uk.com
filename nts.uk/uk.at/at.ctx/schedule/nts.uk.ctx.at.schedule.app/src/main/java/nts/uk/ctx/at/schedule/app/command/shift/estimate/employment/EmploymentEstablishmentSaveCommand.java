/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.estimate.employment;

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
import nts.uk.ctx.at.schedule.dom.shift.estimate.employment.EmploymentEstablishment;
import nts.uk.ctx.at.schedule.dom.shift.estimate.employment.EmploymentEstablishmentGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.EstimateNumberOfDay;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPriceSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTimeSetting;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class EmploymentEstablishmentSaveCommand.
 */
@Getter
@Setter
public class EmploymentEstablishmentSaveCommand {
	
	/** The employment code. */
	private String employmentCode;

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
	 * @param EmploymentId the Employment id
	 * @return the Employment establishment
	 */
	public EmploymentEstablishment toDomain(String EmploymentId){
		return new EmploymentEstablishment(new EmploymentEstablishmentGetMementoImpl(EmploymentId, this));
	}

	/**
	 * The Class EmploymentEstablishmentGetMementoImpl.
	 */
	class EmploymentEstablishmentGetMementoImpl implements EmploymentEstablishmentGetMemento {
		
		/** The company id. */
		private String companyId;

		/** The command. */
		private EmploymentEstablishmentSaveCommand command;

		/**
		 * Instantiates a new Employment establishment get memento impl.
		 *
		 * @param EmploymentId the Employment id
		 * @param command the command
		 */
		public EmploymentEstablishmentGetMementoImpl(String EmploymentId,
				EmploymentEstablishmentSaveCommand command) {
			this.command = command;
			this.companyId = EmploymentId;
		}


		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.Employment.
		 * EmploymentEstablishmentGetMemento#getTargetYear()
		 */
		@Override
		public Year getTargetYear() {
			return new Year(command.getTargetYear());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.Employment.
		 * EmploymentEstablishmentGetMemento#getAdvancedSetting()
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.employment.
		 * EmploymentEstablishmentGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(companyId);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.employment.
		 * EmploymentEstablishmentGetMemento#getEmploymentCode()
		 */
		@Override
		public EmploymentCode getEmploymentCode() {
			return new EmploymentCode(command.getEmploymentCode());
		}
	}
}
