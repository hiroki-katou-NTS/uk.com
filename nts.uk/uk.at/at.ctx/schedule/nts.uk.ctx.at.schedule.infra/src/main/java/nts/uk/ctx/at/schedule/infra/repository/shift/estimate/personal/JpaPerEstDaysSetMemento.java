/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.personal;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.EstimateNumberOfDaySetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.MonthlyEstimateNumberOfDay;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.YearlyEstimateNumberOfDay;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstDaysSya;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstDaysSyaPK;

/**
 * The Class JpaPersonalEstimateNumberOfDaySetMemento.
 */
public class JpaPerEstDaysSetMemento implements EstimateNumberOfDaySetMemento {
	/** The estimate days Personal. */
	private KscmtEstDaysSya estimateDaysPersonal;
	
	/**
	 * Instantiates a new jpa Personal estimate number of day set memento.
	 *
	 * @param estimateDaysPersonal the estimate days Personal
	 */
	public JpaPerEstDaysSetMemento(KscmtEstDaysSya estimateDaysPersonal) {
		if(estimateDaysPersonal.getKscmtEstDaysSyaPK() == null){
			estimateDaysPersonal.setKscmtEstDaysSyaPK(new KscmtEstDaysSyaPK());
		}
		this.estimateDaysPersonal = estimateDaysPersonal;
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
		this.estimateDaysPersonal.getKscmtEstDaysSyaPK().setTargetCls(targetClassification.value);
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
		
		if (this.estimateDaysPersonal.getKscmtEstDaysSyaPK()
				.getTargetCls() == EstimateTargetClassification.YEARLY.value) {
			yearlyEstimateNumberOfDaySetting.forEach(yearly -> {
				switch (yearly.getEstimatedCondition()) {
				case CONDITION_1ST:
					this.estimateDaysPersonal.setEstCondition1stDays(yearly.getDays().v());
					break;
				case CONDITION_2ND:
					this.estimateDaysPersonal.setEstCondition2ndDays(yearly.getDays().v());
					break;
				case CONDITION_3RD:
					this.estimateDaysPersonal.setEstCondition3rdDays(yearly.getDays().v());
					break;
				case CONDITION_4TH:
					this.estimateDaysPersonal.setEstCondition4thDays(yearly.getDays().v());
					break;
				case CONDITION_5TH:
					this.estimateDaysPersonal.setEstCondition5thDays(yearly.getDays().v());
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

		if (this.estimateDaysPersonal.getKscmtEstDaysSyaPK()
				.getTargetCls() != EstimateTargetClassification.YEARLY.value) {
			monthlyEstimateNumberOfDaySetting.forEach(monthly -> {
				switch (monthly.getEstimatedCondition()) {
				case CONDITION_1ST:
					this.estimateDaysPersonal.setEstCondition1stDays(monthly.getDays().v());
					break;
				case CONDITION_2ND:
					this.estimateDaysPersonal.setEstCondition2ndDays(monthly.getDays().v());
					break;
				case CONDITION_3RD:
					this.estimateDaysPersonal.setEstCondition3rdDays(monthly.getDays().v());
					break;
				case CONDITION_4TH:
					this.estimateDaysPersonal.setEstCondition4thDays(monthly.getDays().v());
					break;
				case CONDITION_5TH:
					this.estimateDaysPersonal.setEstCondition5thDays(monthly.getDays().v());
					break;

				default:
					break;
				}

			});
		}
		
	}

}
