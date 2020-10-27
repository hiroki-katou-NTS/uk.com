/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.employment;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTimeSettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.MonthlyEstimateTimeSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.YearlyEstimateTimeSetting;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstTimeEmp;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstTimeEmpPK;

/**
 * The Class JpaEstimateTimeSettingEmploymentGetMemento.
 */
public class JpaEmpEstTimeSetSetMemento implements EstimateTimeSettingSetMemento{
	
	/** The est time employment. */
	private KscmtEstTimeEmp estTimeEmployment;
	
	/**
	 * Instantiates a new jpa employment estimate time setting set memento.
	 *
	 * @param estTimeEmployment the est time employment
	 */
	public JpaEmpEstTimeSetSetMemento(KscmtEstTimeEmp estTimeEmployment){
		if(estTimeEmployment.getKscmtEstTimeEmpPK() == null){
			estTimeEmployment.setKscmtEstTimeEmpPK(new KscmtEstTimeEmpPK());
		}
		this.estTimeEmployment = estTimeEmployment;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.time.
	 * EstimateTimeSettingSetMemento#setTargetClassification(nts.uk.ctx.at.
	 * schedule.dom.shift.estimate.EstimateTargetClassification)
	 */
	@Override
	public void setTargetClassification(EstimateTargetClassification targetClassification) {
		this.estTimeEmployment.getKscmtEstTimeEmpPK().setTargetCls(targetClassification.value);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.time.
	 * EstimateTimeSettingSetMemento#setYearlyEstimateTimeSetting(java.util.
	 * List)
	 */
	@Override
	public void setYearlyEstimateTimeSetting(
			List<YearlyEstimateTimeSetting> yearlyEstimateTimeSetting) {
		if (this.estTimeEmployment.getKscmtEstTimeEmpPK()
				.getTargetCls() == EstimateTargetClassification.YEARLY.value) {

			
			yearlyEstimateTimeSetting.forEach(yearly->{
				switch (yearly.getEstimatedCondition()) {
				case CONDITION_1ST:
					this.estTimeEmployment.setEstCondition1stTime(yearly.getTime().valueAsMinutes());
					break;
				case CONDITION_2ND:
					this.estTimeEmployment.setEstCondition2ndTime(yearly.getTime().valueAsMinutes());
					break;
				case CONDITION_3RD:
					this.estTimeEmployment.setEstCondition3rdTime(yearly.getTime().valueAsMinutes());
					break;
				case CONDITION_4TH:
					this.estTimeEmployment.setEstCondition4thTime(yearly.getTime().valueAsMinutes());
					break;
				case CONDITION_5TH:
					this.estTimeEmployment.setEstCondition5thTime(yearly.getTime().valueAsMinutes());
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
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.time.
	 * EstimateTimeSettingSetMemento#setMonthlyEstimateTimeSetting(java.util.
	 * List)
	 */
	@Override
	public void setMonthlyEstimateTimeSetting(
			List<MonthlyEstimateTimeSetting> monthlyEstimateTimeSetting) {
		
		// check target class not yearly
		if (this.estTimeEmployment.getKscmtEstTimeEmpPK()
				.getTargetCls() != EstimateTargetClassification.YEARLY.value) {

			
			monthlyEstimateTimeSetting.forEach(monthly->{
				switch (monthly.getEstimatedCondition()) {
				case CONDITION_1ST:
					this.estTimeEmployment.setEstCondition1stTime(monthly.getTime().valueAsMinutes());
					break;
				case CONDITION_2ND:
					this.estTimeEmployment.setEstCondition2ndTime(monthly.getTime().valueAsMinutes());
					break;
				case CONDITION_3RD:
					this.estTimeEmployment.setEstCondition3rdTime(monthly.getTime().valueAsMinutes());
					break;
				case CONDITION_4TH:
					this.estTimeEmployment.setEstCondition4thTime(monthly.getTime().valueAsMinutes());
					break;
				case CONDITION_5TH:
					this.estTimeEmployment.setEstCondition5thTime(monthly.getTime().valueAsMinutes());
					break;

				default:
					break;
				}
			});
		}
		
	}
	

}
