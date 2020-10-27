/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.company;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimatedCondition;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.EstimateNumberOfDayGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.MonthlyEstimateDays;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.MonthlyEstimateNumberOfDay;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.YearlyEstimateDays;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.YearlyEstimateNumberOfDay;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstDaysCom;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstDaysComPK;

/**
 * The Class JpaCompanyEstimateNumberOfDayGetMemento.
 */
public class JpaComEstDaysGetMemento implements EstimateNumberOfDayGetMemento {
	
	/** The estimate days company. */
	private KscmtEstDaysCom estimateDaysCompany;
	
	
	/**
	 * Instantiates a new jpa company estimate number of day get memento.
	 *
	 * @param estimateDaysCompany the estimate days company
	 */
	public JpaComEstDaysGetMemento(KscmtEstDaysCom estimateDaysCompany) {
		if(estimateDaysCompany.getKscmtEstDaysComPK() == null){
			estimateDaysCompany.setKscmtEstDaysComPK(new KscmtEstDaysComPK());
		}
		this.estimateDaysCompany =estimateDaysCompany;
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
				.valueOf(this.estimateDaysCompany.getKscmtEstDaysComPK().getTargetCls());
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
		if (this.estimateDaysCompany.getKscmtEstDaysComPK()
				.getTargetCls() == EstimateTargetClassification.YEARLY.value) {
			yearly.add(new YearlyEstimateNumberOfDay(
					new YearlyEstimateDays(this.estimateDaysCompany.getEstCondition1stDays()),
					EstimatedCondition.CONDITION_1ST));
			yearly.add(new YearlyEstimateNumberOfDay(
					new YearlyEstimateDays(this.estimateDaysCompany.getEstCondition2ndDays()),
					EstimatedCondition.CONDITION_2ND));
			yearly.add(new YearlyEstimateNumberOfDay(
					new YearlyEstimateDays(this.estimateDaysCompany.getEstCondition3rdDays()),
					EstimatedCondition.CONDITION_3RD));
			yearly.add(new YearlyEstimateNumberOfDay(
					new YearlyEstimateDays(this.estimateDaysCompany.getEstCondition4thDays()),
					EstimatedCondition.CONDITION_4TH));
			yearly.add(new YearlyEstimateNumberOfDay(
					new YearlyEstimateDays(this.estimateDaysCompany.getEstCondition5thDays()),
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
		if (this.estimateDaysCompany.getKscmtEstDaysComPK()
				.getTargetCls() != EstimateTargetClassification.YEARLY.value) {
			monthly.add(new MonthlyEstimateNumberOfDay(
					new MonthlyEstimateDays(this.estimateDaysCompany.getEstCondition1stDays()),
					EstimatedCondition.CONDITION_1ST));
			monthly.add(new MonthlyEstimateNumberOfDay(
					new MonthlyEstimateDays(this.estimateDaysCompany.getEstCondition2ndDays()),
					EstimatedCondition.CONDITION_2ND));
			monthly.add(new MonthlyEstimateNumberOfDay(
					new MonthlyEstimateDays(this.estimateDaysCompany.getEstCondition3rdDays()),
					EstimatedCondition.CONDITION_3RD));
			monthly.add(new MonthlyEstimateNumberOfDay(
					new MonthlyEstimateDays(this.estimateDaysCompany.getEstCondition4thDays()),
					EstimatedCondition.CONDITION_4TH));
			monthly.add(new MonthlyEstimateNumberOfDay(
					new MonthlyEstimateDays(this.estimateDaysCompany.getEstCondition5thDays()),
					EstimatedCondition.CONDITION_5TH));
		}
		return monthly;
	}

}
