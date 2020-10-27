/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.company;

import java.util.ArrayList;
import java.util.List;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimatedCondition;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTimeSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.MonthlyEstimateTime;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.MonthlyEstimateTimeSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.YearlyEstimateTime;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.YearlyEstimateTimeSetting;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstTimeCom;

/**
 * The Class JpaEstimateTimeSettingCompanyGetMemento.
 */
public class JpaComEstTimeSetGetMemento implements EstimateTimeSettingGetMemento{
	
	/** The est time company. */
	private KscmtEstTimeCom estTimeCompany;
	
	/**
	 * Instantiates a new jpa estimate time setting company get memento.
	 *
	 * @param estTimeCompany the est time company
	 */
	public JpaComEstTimeSetGetMemento(KscmtEstTimeCom estTimeCompany){
		this.estTimeCompany = estTimeCompany;
	}

	/**
	 * Gets the target classification.
	 *
	 * @return the target classification
	 */
	@Override
	public EstimateTargetClassification getTargetClassification() {
		return EnumAdaptor.valueOf(this.estTimeCompany.getKscmtEstTimeComPK().getTargetCls(),
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
		
		
		// check target classification yearly
		if (this.estTimeCompany.getKscmtEstTimeComPK()
				.getTargetCls() == EstimateTargetClassification.YEARLY.value) {
			yearlyEstimateTimeSetting
					.add(new YearlyEstimateTimeSetting(EstimatedCondition.CONDITION_1ST,
							new YearlyEstimateTime(this.estTimeCompany.getEstCondition1stTime())));
			yearlyEstimateTimeSetting
			.add(new YearlyEstimateTimeSetting(EstimatedCondition.CONDITION_2ND,
					new YearlyEstimateTime(this.estTimeCompany.getEstCondition2ndTime())));
			yearlyEstimateTimeSetting
			.add(new YearlyEstimateTimeSetting(EstimatedCondition.CONDITION_3RD,
					new YearlyEstimateTime(this.estTimeCompany.getEstCondition3rdTime())));
			yearlyEstimateTimeSetting
			.add(new YearlyEstimateTimeSetting(EstimatedCondition.CONDITION_4TH,
					new YearlyEstimateTime(this.estTimeCompany.getEstCondition4thTime())));
			yearlyEstimateTimeSetting
			.add(new YearlyEstimateTimeSetting(EstimatedCondition.CONDITION_5TH,
					new YearlyEstimateTime(this.estTimeCompany.getEstCondition5thTime())));
		}
		
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
		
		// check target classification not yearly
		if (this.estTimeCompany.getKscmtEstTimeComPK()
				.getTargetCls() != EstimateTargetClassification.YEARLY.value) {
			monthlyEstimateTimeSetting.add(new MonthlyEstimateTimeSetting(
					new MonthlyEstimateTime(this.estTimeCompany.getEstCondition1stTime()),
					EstimatedCondition.CONDITION_1ST));
			monthlyEstimateTimeSetting.add(new MonthlyEstimateTimeSetting(
					new MonthlyEstimateTime(this.estTimeCompany.getEstCondition2ndTime()),
					EstimatedCondition.CONDITION_2ND));
			monthlyEstimateTimeSetting.add(new MonthlyEstimateTimeSetting(
					new MonthlyEstimateTime(this.estTimeCompany.getEstCondition3rdTime()),
					EstimatedCondition.CONDITION_3RD));
			monthlyEstimateTimeSetting.add(new MonthlyEstimateTimeSetting(
					new MonthlyEstimateTime(this.estTimeCompany.getEstCondition4thTime()),
					EstimatedCondition.CONDITION_4TH));
			monthlyEstimateTimeSetting.add(new MonthlyEstimateTimeSetting(
					new MonthlyEstimateTime(this.estTimeCompany.getEstCondition5thTime()),
					EstimatedCondition.CONDITION_5TH));
		}
		
		return monthlyEstimateTimeSetting;
	}
	

}
