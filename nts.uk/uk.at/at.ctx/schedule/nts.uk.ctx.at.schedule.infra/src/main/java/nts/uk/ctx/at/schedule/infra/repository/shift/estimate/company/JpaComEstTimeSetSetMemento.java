/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.company;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTimeSettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.MonthlyEstimateTimeSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.YearlyEstimateTimeSetting;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstTimeComSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstTimeComSetPK;

/**
 * The Class JpaEstimateTimeSettingCompanyGetMemento.
 */
public class JpaComEstTimeSetSetMemento implements EstimateTimeSettingSetMemento{
	
	/** The est time company. */
	private KscmtEstTimeComSet estTimeCompany;
	
	/**
	 * Instantiates a new jpa estimate time setting company get memento.
	 *
	 * @param estTimeCompany the est time company
	 */
	public JpaComEstTimeSetSetMemento(KscmtEstTimeComSet estTimeCompany){
		if(estTimeCompany.getKscmtEstTimeComSetPK() == null){
			estTimeCompany.setKscmtEstTimeComSetPK(new KscmtEstTimeComSetPK());
		}
		this.estTimeCompany = estTimeCompany;
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
		this.estTimeCompany.getKscmtEstTimeComSetPK().setTargetCls(targetClassification.value);

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
		if (this.estTimeCompany.getKscmtEstTimeComSetPK()
				.getTargetCls() == EstimateTargetClassification.YEARLY.value) {

			
			yearlyEstimateTimeSetting.forEach(yearly->{
				switch (yearly.getEstimatedCondition()) {
				case CONDITION_1ST:
					this.estTimeCompany.setEstCondition1stTime(yearly.getTime().valueAsMinutes());
					break;
				case CONDITION_2ND:
					this.estTimeCompany.setEstCondition2ndTime(yearly.getTime().valueAsMinutes());
					break;
				case CONDITION_3RD:
					this.estTimeCompany.setEstCondition3rdTime(yearly.getTime().valueAsMinutes());
					break;
				case CONDITION_4TH:
					this.estTimeCompany.setEstCondition4thTime(yearly.getTime().valueAsMinutes());
					break;
				case CONDITION_5TH:
					this.estTimeCompany.setEstCondition5thTime(yearly.getTime().valueAsMinutes());
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
		if (this.estTimeCompany.getKscmtEstTimeComSetPK()
				.getTargetCls() != EstimateTargetClassification.YEARLY.value) {

			
			monthlyEstimateTimeSetting.forEach(monthly->{
				switch (monthly.getEstimatedCondition()) {
				case CONDITION_1ST:
					this.estTimeCompany.setEstCondition1stTime(monthly.getTime().valueAsMinutes());
					break;
				case CONDITION_2ND:
					this.estTimeCompany.setEstCondition2ndTime(monthly.getTime().valueAsMinutes());
					break;
				case CONDITION_3RD:
					this.estTimeCompany.setEstCondition3rdTime(monthly.getTime().valueAsMinutes());
					break;
				case CONDITION_4TH:
					this.estTimeCompany.setEstCondition4thTime(monthly.getTime().valueAsMinutes());
					break;
				case CONDITION_5TH:
					this.estTimeCompany.setEstCondition5thTime(monthly.getTime().valueAsMinutes());
					break;

				default:
					break;
				}
			});
		}
		
	}
	

}
