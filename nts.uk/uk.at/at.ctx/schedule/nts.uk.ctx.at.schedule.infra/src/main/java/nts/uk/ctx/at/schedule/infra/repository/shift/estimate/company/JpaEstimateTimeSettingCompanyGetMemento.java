/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.company;

import java.util.ArrayList;
import java.util.List;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimatedCondition;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTimeSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.MonthlyEstimateTime;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.MonthlyEstimateTimeSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.YearlyEstimateTime;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.YearlyEstimateTimeSetting;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstTimeComSet;

/**
 * The Class JpaEstimateTimeSettingCompanyGetMemento.
 */
public class JpaEstimateTimeSettingCompanyGetMemento implements EstimateTimeSettingGetMemento{
	
	/** The est time company. */
	private KscmtEstTimeComSet estTimeCompany;
	
	/**
	 * Instantiates a new jpa estimate time setting company get memento.
	 *
	 * @param estTimeCompany the est time company
	 */
	public JpaEstimateTimeSettingCompanyGetMemento(KscmtEstTimeComSet estTimeCompany){
		this.estTimeCompany = estTimeCompany;
	}

	/**
	 * Gets the target classification.
	 *
	 * @return the target classification
	 */
	@Override
	public EstimateTargetClassification getTargetClassification() {
		return EnumAdaptor.valueOf(this.estTimeCompany.getKscmtEstTimeComSetPK().getTargetCls(),
				EstimateTargetClassification.class);
	}

	/**
	 * Gets the yearly estimate time setting.
	 *
	 * @return the yearly estimate time setting
	 */
	@Override
	public List<YearlyEstimateTimeSetting> getYearlyEstimateTimeSetting() {
		List<YearlyEstimateTimeSetting> yearlyEstimateTimeSetting = new ArrayList<>();
		yearlyEstimateTimeSetting.add(this.getYearlyEstimateTimeSetting(
				EstimatedCondition.CONDITION_1ST, this.estTimeCompany.getYCondition1stTime()));
		yearlyEstimateTimeSetting.add(this.getYearlyEstimateTimeSetting(
				EstimatedCondition.CONDITION_2ND, this.estTimeCompany.getYCondition2ndTime()));
		yearlyEstimateTimeSetting.add(this.getYearlyEstimateTimeSetting(
				EstimatedCondition.CONDITION_3RD, this.estTimeCompany.getYCondition3rdTime()));
		yearlyEstimateTimeSetting.add(this.getYearlyEstimateTimeSetting(
				EstimatedCondition.CONDITION_4TH, this.estTimeCompany.getYCondition4thTime()));
		yearlyEstimateTimeSetting.add(this.getYearlyEstimateTimeSetting(
				EstimatedCondition.CONDITION_5TH, this.estTimeCompany.getYCondition5thTime()));
		return yearlyEstimateTimeSetting;
	}

	/**
	 * Gets the monthly estimate time setting.
	 *
	 * @return the monthly estimate time setting
	 */
	@Override
	public List<MonthlyEstimateTimeSetting> getMonthlyEstimateTimeSetting() {
		List<MonthlyEstimateTimeSetting> monthlyEstimateTimeSetting = new ArrayList<>();
		monthlyEstimateTimeSetting.add(this.getMonthlyEstimateTimeSetting(
				EstimatedCondition.CONDITION_1ST, this.estTimeCompany.getMCondition1stTime()));
		monthlyEstimateTimeSetting.add(this.getMonthlyEstimateTimeSetting(
				EstimatedCondition.CONDITION_2ND, this.estTimeCompany.getMCondition2ndTime()));
		monthlyEstimateTimeSetting.add(this.getMonthlyEstimateTimeSetting(
				EstimatedCondition.CONDITION_3RD, this.estTimeCompany.getMCondition3rdTime()));
		monthlyEstimateTimeSetting.add(this.getMonthlyEstimateTimeSetting(
				EstimatedCondition.CONDITION_4TH, this.estTimeCompany.getMCondition4thTime()));
		monthlyEstimateTimeSetting.add(this.getMonthlyEstimateTimeSetting(
				EstimatedCondition.CONDITION_5TH, this.estTimeCompany.getMCondition5thTime()));
		return monthlyEstimateTimeSetting;
	}
	
	/**
	 * Gets the yearly estimate time setting.
	 *
	 * @param estimatedCondition the estimated condition
	 * @param time the time
	 * @return the yearly estimate time setting
	 */
	public YearlyEstimateTimeSetting getYearlyEstimateTimeSetting(
			EstimatedCondition estimatedCondition, int time) {
		YearlyEstimateTimeSetting yearly = new YearlyEstimateTimeSetting();
		yearly.setTime(new YearlyEstimateTime(time));
		yearly.setEstimatedCondition(estimatedCondition);
		return yearly;
	}
	
	/**
	 * Gets the monthly estimate time setting.
	 *
	 * @param estimatedCondition the estimated condition
	 * @param time the time
	 * @return the monthly estimate time setting
	 */
	public MonthlyEstimateTimeSetting getMonthlyEstimateTimeSetting(
			EstimatedCondition estimatedCondition, int time) {
		MonthlyEstimateTimeSetting monthly = new MonthlyEstimateTimeSetting();
		monthly.setTime(new MonthlyEstimateTime(time));
		monthly.setEstimatedCondition(estimatedCondition);
		return monthly;
	}

}
