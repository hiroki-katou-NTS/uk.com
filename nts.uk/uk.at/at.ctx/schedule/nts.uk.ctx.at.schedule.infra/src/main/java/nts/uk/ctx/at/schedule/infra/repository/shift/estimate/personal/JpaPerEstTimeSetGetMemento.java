/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.personal;

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
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstTimeSya;

/**
 * The Class JpaEstimateTimeSettingPersonalGetMemento.
 */
public class JpaPerEstTimeSetGetMemento implements EstimateTimeSettingGetMemento{
	
	/** The est time Personal. */
	private KscmtEstTimeSya estTimePersonal;
	
	/**
	 * Instantiates a new jpa estimate time setting Personal get memento.
	 *
	 * @param estTimePersonal the est time Personal
	 */
	public JpaPerEstTimeSetGetMemento(KscmtEstTimeSya estTimePersonal){
		this.estTimePersonal = estTimePersonal;
	}

	/**
	 * Gets the target classification.
	 *
	 * @return the target classification
	 */
	@Override
	public EstimateTargetClassification getTargetClassification() {
		return EnumAdaptor.valueOf(this.estTimePersonal.getKscmtEstTimeSyaPK().getTargetCls(),
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
		if (this.estTimePersonal.getKscmtEstTimeSyaPK()
				.getTargetCls() == EstimateTargetClassification.YEARLY.value) {
			yearlyEstimateTimeSetting
					.add(new YearlyEstimateTimeSetting(EstimatedCondition.CONDITION_1ST,
							new YearlyEstimateTime(this.estTimePersonal.getEstCondition1stTime())));
			yearlyEstimateTimeSetting
			.add(new YearlyEstimateTimeSetting(EstimatedCondition.CONDITION_2ND,
					new YearlyEstimateTime(this.estTimePersonal.getEstCondition2ndTime())));
			yearlyEstimateTimeSetting
			.add(new YearlyEstimateTimeSetting(EstimatedCondition.CONDITION_3RD,
					new YearlyEstimateTime(this.estTimePersonal.getEstCondition3rdTime())));
			yearlyEstimateTimeSetting
			.add(new YearlyEstimateTimeSetting(EstimatedCondition.CONDITION_4TH,
					new YearlyEstimateTime(this.estTimePersonal.getEstCondition4thTime())));
			yearlyEstimateTimeSetting
			.add(new YearlyEstimateTimeSetting(EstimatedCondition.CONDITION_5TH,
					new YearlyEstimateTime(this.estTimePersonal.getEstCondition5thTime())));
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
		if (this.estTimePersonal.getKscmtEstTimeSyaPK()
				.getTargetCls() != EstimateTargetClassification.YEARLY.value) {
			monthlyEstimateTimeSetting.add(new MonthlyEstimateTimeSetting(
					new MonthlyEstimateTime(this.estTimePersonal.getEstCondition1stTime()),
					EstimatedCondition.CONDITION_1ST));
			monthlyEstimateTimeSetting.add(new MonthlyEstimateTimeSetting(
					new MonthlyEstimateTime(this.estTimePersonal.getEstCondition2ndTime()),
					EstimatedCondition.CONDITION_2ND));
			monthlyEstimateTimeSetting.add(new MonthlyEstimateTimeSetting(
					new MonthlyEstimateTime(this.estTimePersonal.getEstCondition3rdTime()),
					EstimatedCondition.CONDITION_3RD));
			monthlyEstimateTimeSetting.add(new MonthlyEstimateTimeSetting(
					new MonthlyEstimateTime(this.estTimePersonal.getEstCondition4thTime()),
					EstimatedCondition.CONDITION_4TH));
			monthlyEstimateTimeSetting.add(new MonthlyEstimateTimeSetting(
					new MonthlyEstimateTime(this.estTimePersonal.getEstCondition5thTime()),
					EstimatedCondition.CONDITION_5TH));
		}
		
		return monthlyEstimateTimeSetting;
	}
	

}
