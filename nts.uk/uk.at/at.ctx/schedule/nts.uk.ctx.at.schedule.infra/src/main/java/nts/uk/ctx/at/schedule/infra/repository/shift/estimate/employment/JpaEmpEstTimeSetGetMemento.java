/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.employment;

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
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstTimeEmp;

/**
 * The Class JpaEmploymentEstimateTimeSettingGetMemento.
 */
public class JpaEmpEstTimeSetGetMemento implements EstimateTimeSettingGetMemento{
	
	/** The est time employment. */
	private KscmtEstTimeEmp estTimeEmployment;
	
	/**
	 * Instantiates a new jpa estimate time setting Employment get memento.
	 *
	 * @param estTimeEmployment the est time Employment
	 */
	public JpaEmpEstTimeSetGetMemento(KscmtEstTimeEmp estTimeEmployment){
		this.estTimeEmployment = estTimeEmployment;
	}

	/**
	 * Gets the target classification.
	 *
	 * @return the target classification
	 */
	@Override
	public EstimateTargetClassification getTargetClassification() {
		return EnumAdaptor.valueOf(this.estTimeEmployment.getKscmtEstTimeEmpPK().getTargetCls(),
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
		if (this.estTimeEmployment.getKscmtEstTimeEmpPK()
				.getTargetCls() == EstimateTargetClassification.YEARLY.value) {
			yearlyEstimateTimeSetting
					.add(new YearlyEstimateTimeSetting(EstimatedCondition.CONDITION_1ST,
							new YearlyEstimateTime(this.estTimeEmployment.getEstCondition1stTime())));
			yearlyEstimateTimeSetting
			.add(new YearlyEstimateTimeSetting(EstimatedCondition.CONDITION_2ND,
					new YearlyEstimateTime(this.estTimeEmployment.getEstCondition2ndTime())));
			yearlyEstimateTimeSetting
			.add(new YearlyEstimateTimeSetting(EstimatedCondition.CONDITION_3RD,
					new YearlyEstimateTime(this.estTimeEmployment.getEstCondition3rdTime())));
			yearlyEstimateTimeSetting
			.add(new YearlyEstimateTimeSetting(EstimatedCondition.CONDITION_4TH,
					new YearlyEstimateTime(this.estTimeEmployment.getEstCondition4thTime())));
			yearlyEstimateTimeSetting
			.add(new YearlyEstimateTimeSetting(EstimatedCondition.CONDITION_5TH,
					new YearlyEstimateTime(this.estTimeEmployment.getEstCondition5thTime())));
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
		if (this.estTimeEmployment.getKscmtEstTimeEmpPK()
				.getTargetCls() != EstimateTargetClassification.YEARLY.value) {
			monthlyEstimateTimeSetting.add(new MonthlyEstimateTimeSetting(
					new MonthlyEstimateTime(this.estTimeEmployment.getEstCondition1stTime()),
					EstimatedCondition.CONDITION_1ST));
			monthlyEstimateTimeSetting.add(new MonthlyEstimateTimeSetting(
					new MonthlyEstimateTime(this.estTimeEmployment.getEstCondition2ndTime()),
					EstimatedCondition.CONDITION_2ND));
			monthlyEstimateTimeSetting.add(new MonthlyEstimateTimeSetting(
					new MonthlyEstimateTime(this.estTimeEmployment.getEstCondition3rdTime()),
					EstimatedCondition.CONDITION_3RD));
			monthlyEstimateTimeSetting.add(new MonthlyEstimateTimeSetting(
					new MonthlyEstimateTime(this.estTimeEmployment.getEstCondition4thTime()),
					EstimatedCondition.CONDITION_4TH));
			monthlyEstimateTimeSetting.add(new MonthlyEstimateTimeSetting(
					new MonthlyEstimateTime(this.estTimeEmployment.getEstCondition5thTime()),
					EstimatedCondition.CONDITION_5TH));
		}
		
		return monthlyEstimateTimeSetting;
	}
	

}
