/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.employment;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimatedCondition;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.EstimateNumberOfDayGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.MonthlyEstimateDays;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.MonthlyEstimateNumberOfDay;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.YearlyEstimateDays;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.YearlyEstimateNumberOfDay;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstDaysEmp;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstDaysEmpPK;

/**
 * The Class JpaEmploymentEstimateNumberOfDayGetMemento.
 */
public class JpaEmpEstDaysGetMemento implements EstimateNumberOfDayGetMemento {
	
	/** The estimate days employment. */
	private KscmtEstDaysEmp estimateDaysEmployment;
	
	
	/**
	 * Instantiates a new jpa Employment estimate number of day get memento.
	 *
	 * @param estimateDaysEmployment the estimate days Employment
	 */
	public JpaEmpEstDaysGetMemento(KscmtEstDaysEmp estimateDaysEmployment) {
		if(estimateDaysEmployment.getKscmtEstDaysEmpPK() == null){
			estimateDaysEmployment.setKscmtEstDaysEmpPK(new KscmtEstDaysEmpPK());
		}
		this.estimateDaysEmployment =estimateDaysEmployment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.
	 * EstimateNumberOfDayGetMemento#getTargetClassification()
	 */
	@Override
	public EstimateTargetClassification getTargetClassification() {
		return EstimateTargetClassification
				.valueOf(this.estimateDaysEmployment.getKscmtEstDaysEmpPK().getTargetCls());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.
	 * EstimateNumberOfDayGetMemento#getYearlyEstimateNumberOfDaySetting()
	 */
	@Override
	public List<YearlyEstimateNumberOfDay> getYearlyEstimateNumberOfDaySetting() {
		List<YearlyEstimateNumberOfDay> yearly = new ArrayList<>();
		if (this.estimateDaysEmployment.getKscmtEstDaysEmpPK()
				.getTargetCls() == EstimateTargetClassification.YEARLY.value) {
			yearly.add(new YearlyEstimateNumberOfDay(
					new YearlyEstimateDays(this.estimateDaysEmployment.getEstCondition1stDays()),
					EstimatedCondition.CONDITION_1ST));
			yearly.add(new YearlyEstimateNumberOfDay(
					new YearlyEstimateDays(this.estimateDaysEmployment.getEstCondition2ndDays()),
					EstimatedCondition.CONDITION_2ND));
			yearly.add(new YearlyEstimateNumberOfDay(
					new YearlyEstimateDays(this.estimateDaysEmployment.getEstCondition3rdDays()),
					EstimatedCondition.CONDITION_3RD));
			yearly.add(new YearlyEstimateNumberOfDay(
					new YearlyEstimateDays(this.estimateDaysEmployment.getEstCondition4thDays()),
					EstimatedCondition.CONDITION_4TH));
			yearly.add(new YearlyEstimateNumberOfDay(
					new YearlyEstimateDays(this.estimateDaysEmployment.getEstCondition5thDays()),
					EstimatedCondition.CONDITION_5TH));
		}
		return yearly;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.
	 * EstimateNumberOfDayGetMemento#getMonthlyEstimateNumberOfDaySetting()
	 */
	@Override
	public List<MonthlyEstimateNumberOfDay> getMonthlyEstimateNumberOfDaySetting() {
		List<MonthlyEstimateNumberOfDay> monthly = new ArrayList<>();
		if (this.estimateDaysEmployment.getKscmtEstDaysEmpPK()
				.getTargetCls() != EstimateTargetClassification.YEARLY.value) {
			monthly.add(new MonthlyEstimateNumberOfDay(
					new MonthlyEstimateDays(this.estimateDaysEmployment.getEstCondition1stDays()),
					EstimatedCondition.CONDITION_1ST));
			monthly.add(new MonthlyEstimateNumberOfDay(
					new MonthlyEstimateDays(this.estimateDaysEmployment.getEstCondition2ndDays()),
					EstimatedCondition.CONDITION_2ND));
			monthly.add(new MonthlyEstimateNumberOfDay(
					new MonthlyEstimateDays(this.estimateDaysEmployment.getEstCondition3rdDays()),
					EstimatedCondition.CONDITION_3RD));
			monthly.add(new MonthlyEstimateNumberOfDay(
					new MonthlyEstimateDays(this.estimateDaysEmployment.getEstCondition4thDays()),
					EstimatedCondition.CONDITION_4TH));
			monthly.add(new MonthlyEstimateNumberOfDay(
					new MonthlyEstimateDays(this.estimateDaysEmployment.getEstCondition5thDays()),
					EstimatedCondition.CONDITION_5TH));
		}
		return monthly;
	}

}
