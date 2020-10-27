/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.company;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.EstimateNumberOfDaySetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.MonthlyEstimateNumberOfDay;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.YearlyEstimateNumberOfDay;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstDaysCom;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstDaysComPK;

/**
 * The Class JpaCompanyEstimateNumberOfDaySetMemento.
 */
public class JpaComEstDaysSetMemento implements EstimateNumberOfDaySetMemento {
	/** The estimate days company. */
	private KscmtEstDaysCom estimateDaysCompany;
	
	/**
	 * Instantiates a new jpa company estimate number of day set memento.
	 *
	 * @param estimateDaysCompany the estimate days company
	 */
	public JpaComEstDaysSetMemento(KscmtEstDaysCom estimateDaysCompany) {
		if(estimateDaysCompany.getKscmtEstDaysComPK() == null){
			estimateDaysCompany.setKscmtEstDaysComPK(new KscmtEstDaysComPK());
		}
		this.estimateDaysCompany = estimateDaysCompany;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.
	 * EstimateNumberOfDaySetMemento#setTargetClassification(nts.uk.ctx.at.
	 * schedule.dom.shift.estimate.EstimateTargetClassification)
	 */
	@Override
	public void setTargetClassification(EstimateTargetClassification targetClassification) {
		this.estimateDaysCompany.getKscmtEstDaysComPK().setTargetCls(targetClassification.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.
	 * EstimateNumberOfDaySetMemento#setYearlyEstimateNumberOfDaySetting(java.
	 * util.List)
	 */
	@Override
	public void setYearlyEstimateNumberOfDaySetting(
			List<YearlyEstimateNumberOfDay> yearlyEstimateNumberOfDaySetting) {
		
		if (this.estimateDaysCompany.getKscmtEstDaysComPK()
				.getTargetCls() == EstimateTargetClassification.YEARLY.value) {
			yearlyEstimateNumberOfDaySetting.forEach(yearly -> {
				switch (yearly.getEstimatedCondition()) {
				case CONDITION_1ST:
					this.estimateDaysCompany.setEstCondition1stDays(yearly.getDays().v());
					break;
				case CONDITION_2ND:
					this.estimateDaysCompany.setEstCondition2ndDays(yearly.getDays().v());
					break;
				case CONDITION_3RD:
					this.estimateDaysCompany.setEstCondition3rdDays(yearly.getDays().v());
					break;
				case CONDITION_4TH:
					this.estimateDaysCompany.setEstCondition4thDays(yearly.getDays().v());
					break;
				case CONDITION_5TH:
					this.estimateDaysCompany.setEstCondition5thDays(yearly.getDays().v());
					break;

				default:
					break;
				}

			});
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.
	 * EstimateNumberOfDaySetMemento#setMonthlyEstimateNumberOfDaySetting(java.
	 * util.List)
	 */
	@Override
	public void setMonthlyEstimateNumberOfDaySetting(
			List<MonthlyEstimateNumberOfDay> monthlyEstimateNumberOfDaySetting) {

		if (this.estimateDaysCompany.getKscmtEstDaysComPK()
				.getTargetCls() != EstimateTargetClassification.YEARLY.value) {
			monthlyEstimateNumberOfDaySetting.forEach(monthly -> {
				switch (monthly.getEstimatedCondition()) {
				case CONDITION_1ST:
					this.estimateDaysCompany.setEstCondition1stDays(monthly.getDays().v());
					break;
				case CONDITION_2ND:
					this.estimateDaysCompany.setEstCondition2ndDays(monthly.getDays().v());
					break;
				case CONDITION_3RD:
					this.estimateDaysCompany.setEstCondition3rdDays(monthly.getDays().v());
					break;
				case CONDITION_4TH:
					this.estimateDaysCompany.setEstCondition4thDays(monthly.getDays().v());
					break;
				case CONDITION_5TH:
					this.estimateDaysCompany.setEstCondition5thDays(monthly.getDays().v());
					break;

				default:
					break;
				}

			});
		}
		
	}

}
