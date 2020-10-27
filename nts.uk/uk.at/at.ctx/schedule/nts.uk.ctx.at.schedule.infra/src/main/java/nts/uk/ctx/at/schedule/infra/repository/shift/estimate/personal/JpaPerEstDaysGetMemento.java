/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.personal;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimatedCondition;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.EstimateNumberOfDayGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.MonthlyEstimateDays;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.MonthlyEstimateNumberOfDay;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.YearlyEstimateDays;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.YearlyEstimateNumberOfDay;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstDaysSya;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstDaysSyaPK;

/**
 * The Class JpaPersonalEstimateNumberOfDayGetMemento.
 */
public class JpaPerEstDaysGetMemento implements EstimateNumberOfDayGetMemento {
	
	/** The estimate days Personal. */
	private KscmtEstDaysSya estimateDaysPersonal;
	
	
	/**
	 * Instantiates a new jpa Personal estimate number of day get memento.
	 *
	 * @param estimateDaysPersonal the estimate days Personal
	 */
	public JpaPerEstDaysGetMemento(KscmtEstDaysSya estimateDaysPersonal) {
		if(estimateDaysPersonal.getKscmtEstDaysSyaPK() == null){
			estimateDaysPersonal.setKscmtEstDaysSyaPK(new KscmtEstDaysSyaPK());
		}
		this.estimateDaysPersonal =estimateDaysPersonal;
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
				.valueOf(this.estimateDaysPersonal.getKscmtEstDaysSyaPK().getTargetCls());
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
		if (this.estimateDaysPersonal.getKscmtEstDaysSyaPK()
				.getTargetCls() == EstimateTargetClassification.YEARLY.value) {
			yearly.add(new YearlyEstimateNumberOfDay(
					new YearlyEstimateDays(this.estimateDaysPersonal.getEstCondition1stDays()),
					EstimatedCondition.CONDITION_1ST));
			yearly.add(new YearlyEstimateNumberOfDay(
					new YearlyEstimateDays(this.estimateDaysPersonal.getEstCondition2ndDays()),
					EstimatedCondition.CONDITION_2ND));
			yearly.add(new YearlyEstimateNumberOfDay(
					new YearlyEstimateDays(this.estimateDaysPersonal.getEstCondition3rdDays()),
					EstimatedCondition.CONDITION_3RD));
			yearly.add(new YearlyEstimateNumberOfDay(
					new YearlyEstimateDays(this.estimateDaysPersonal.getEstCondition4thDays()),
					EstimatedCondition.CONDITION_4TH));
			yearly.add(new YearlyEstimateNumberOfDay(
					new YearlyEstimateDays(this.estimateDaysPersonal.getEstCondition5thDays()),
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
		if (this.estimateDaysPersonal.getKscmtEstDaysSyaPK()
				.getTargetCls() != EstimateTargetClassification.YEARLY.value) {
			monthly.add(new MonthlyEstimateNumberOfDay(
					new MonthlyEstimateDays(this.estimateDaysPersonal.getEstCondition1stDays()),
					EstimatedCondition.CONDITION_1ST));
			monthly.add(new MonthlyEstimateNumberOfDay(
					new MonthlyEstimateDays(this.estimateDaysPersonal.getEstCondition2ndDays()),
					EstimatedCondition.CONDITION_2ND));
			monthly.add(new MonthlyEstimateNumberOfDay(
					new MonthlyEstimateDays(this.estimateDaysPersonal.getEstCondition3rdDays()),
					EstimatedCondition.CONDITION_3RD));
			monthly.add(new MonthlyEstimateNumberOfDay(
					new MonthlyEstimateDays(this.estimateDaysPersonal.getEstCondition4thDays()),
					EstimatedCondition.CONDITION_4TH));
			monthly.add(new MonthlyEstimateNumberOfDay(
					new MonthlyEstimateDays(this.estimateDaysPersonal.getEstCondition5thDays()),
					EstimatedCondition.CONDITION_5TH));
		}
		return monthly;
	}

}
