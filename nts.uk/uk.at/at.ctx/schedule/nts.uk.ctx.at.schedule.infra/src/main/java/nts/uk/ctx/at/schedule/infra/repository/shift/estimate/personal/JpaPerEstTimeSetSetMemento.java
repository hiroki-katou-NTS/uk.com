/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.personal;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTimeSettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.MonthlyEstimateTimeSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.YearlyEstimateTimeSetting;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstTimeSya;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstTimeSyaPK;

/**
 * The Class JpaEstimateTimeSettingPersonalGetMemento.
 */
public class JpaPerEstTimeSetSetMemento implements EstimateTimeSettingSetMemento{
	
	/** The est time Personal. */
	private KscmtEstTimeSya estTimePersonal;
	
	/**
	 * Instantiates a new jpa estimate time setting Personal get memento.
	 *
	 * @param estTimePersonal the est time Personal
	 */
	public JpaPerEstTimeSetSetMemento(KscmtEstTimeSya estTimePersonal){
		if(estTimePersonal.getKscmtEstTimeSyaPK() == null){
			estTimePersonal.setKscmtEstTimeSyaPK(new KscmtEstTimeSyaPK());
		}
		this.estTimePersonal = estTimePersonal;
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
		this.estTimePersonal.getKscmtEstTimeSyaPK().setTargetCls(targetClassification.value);

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
		if (this.estTimePersonal.getKscmtEstTimeSyaPK()
				.getTargetCls() == EstimateTargetClassification.YEARLY.value) {

			
			yearlyEstimateTimeSetting.forEach(yearly->{
				switch (yearly.getEstimatedCondition()) {
				case CONDITION_1ST:
					this.estTimePersonal.setEstCondition1stTime(yearly.getTime().valueAsMinutes());
					break;
				case CONDITION_2ND:
					this.estTimePersonal.setEstCondition2ndTime(yearly.getTime().valueAsMinutes());
					break;
				case CONDITION_3RD:
					this.estTimePersonal.setEstCondition3rdTime(yearly.getTime().valueAsMinutes());
					break;
				case CONDITION_4TH:
					this.estTimePersonal.setEstCondition4thTime(yearly.getTime().valueAsMinutes());
					break;
				case CONDITION_5TH:
					this.estTimePersonal.setEstCondition5thTime(yearly.getTime().valueAsMinutes());
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
		if (this.estTimePersonal.getKscmtEstTimeSyaPK()
				.getTargetCls() != EstimateTargetClassification.YEARLY.value) {

			
			monthlyEstimateTimeSetting.forEach(monthly->{
				switch (monthly.getEstimatedCondition()) {
				case CONDITION_1ST:
					this.estTimePersonal.setEstCondition1stTime(monthly.getTime().valueAsMinutes());
					break;
				case CONDITION_2ND:
					this.estTimePersonal.setEstCondition2ndTime(monthly.getTime().valueAsMinutes());
					break;
				case CONDITION_3RD:
					this.estTimePersonal.setEstCondition3rdTime(monthly.getTime().valueAsMinutes());
					break;
				case CONDITION_4TH:
					this.estTimePersonal.setEstCondition4thTime(monthly.getTime().valueAsMinutes());
					break;
				case CONDITION_5TH:
					this.estTimePersonal.setEstCondition5thTime(monthly.getTime().valueAsMinutes());
					break;

				default:
					break;
				}
			});
		}
		
	}
	

}
