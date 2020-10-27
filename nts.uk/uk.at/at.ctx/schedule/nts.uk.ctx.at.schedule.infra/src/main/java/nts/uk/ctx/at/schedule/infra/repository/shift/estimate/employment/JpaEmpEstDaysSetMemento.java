/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.employment;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.EstimateNumberOfDaySetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.MonthlyEstimateNumberOfDay;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.YearlyEstimateNumberOfDay;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstDaysEmp;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstDaysEmpPK;

/**
 * The Class JpaEmploymentEstimateNumberOfDaySetMemento.
 */
public class JpaEmpEstDaysSetMemento implements EstimateNumberOfDaySetMemento {
	
	/** The estimate days employment. */
	private KscmtEstDaysEmp estimateDaysEmployment;
	
	/**
	 * Instantiates a new jpa Employment estimate number of day set memento.
	 *
	 * @param estimateDaysEmployment the estimate days Employment
	 */
	public JpaEmpEstDaysSetMemento(KscmtEstDaysEmp estimateDaysEmployment) {
		if(estimateDaysEmployment.getKscmtEstDaysEmpPK() == null){
			estimateDaysEmployment.setKscmtEstDaysEmpPK(new KscmtEstDaysEmpPK());
		}
		this.estimateDaysEmployment = estimateDaysEmployment;
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
		this.estimateDaysEmployment.getKscmtEstDaysEmpPK().setTargetCls(targetClassification.value);
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
		
		if (this.estimateDaysEmployment.getKscmtEstDaysEmpPK()
				.getTargetCls() == EstimateTargetClassification.YEARLY.value) {
			yearlyEstimateNumberOfDaySetting.forEach(yearly -> {
				switch (yearly.getEstimatedCondition()) {
				case CONDITION_1ST:
					this.estimateDaysEmployment.setEstCondition1stDays(yearly.getDays().v());
					break;
				case CONDITION_2ND:
					this.estimateDaysEmployment.setEstCondition2ndDays(yearly.getDays().v());
					break;
				case CONDITION_3RD:
					this.estimateDaysEmployment.setEstCondition3rdDays(yearly.getDays().v());
					break;
				case CONDITION_4TH:
					this.estimateDaysEmployment.setEstCondition4thDays(yearly.getDays().v());
					break;
				case CONDITION_5TH:
					this.estimateDaysEmployment.setEstCondition5thDays(yearly.getDays().v());
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

		if (this.estimateDaysEmployment.getKscmtEstDaysEmpPK()
				.getTargetCls() != EstimateTargetClassification.YEARLY.value) {
			monthlyEstimateNumberOfDaySetting.forEach(monthly -> {
				switch (monthly.getEstimatedCondition()) {
				case CONDITION_1ST:
					this.estimateDaysEmployment.setEstCondition1stDays(monthly.getDays().v());
					break;
				case CONDITION_2ND:
					this.estimateDaysEmployment.setEstCondition2ndDays(monthly.getDays().v());
					break;
				case CONDITION_3RD:
					this.estimateDaysEmployment.setEstCondition3rdDays(monthly.getDays().v());
					break;
				case CONDITION_4TH:
					this.estimateDaysEmployment.setEstCondition4thDays(monthly.getDays().v());
					break;
				case CONDITION_5TH:
					this.estimateDaysEmployment.setEstCondition5thDays(monthly.getDays().v());
					break;

				default:
					break;
				}

			});
		}
		
	}

}
