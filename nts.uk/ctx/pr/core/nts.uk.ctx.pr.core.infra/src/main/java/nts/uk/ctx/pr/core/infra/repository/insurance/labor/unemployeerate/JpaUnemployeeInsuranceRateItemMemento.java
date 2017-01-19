/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.labor.unemployeerate;

import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.CareerGroup;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateItemMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateItemSetting;

/**
 * The Class JpaAggrSchemaMemento.
 */
public class JpaUnemployeeInsuranceRateItemMemento implements UnemployeeInsuranceRateItemMemento {

	// TODO: Object -> entity class.
	protected Object typeValue;

	/**
	 * Instantiates a new jpa aggr schema memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaUnemployeeInsuranceRateItemMemento(Object typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public CareerGroup getCareerGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnemployeeInsuranceRateItemSetting getCompanySetting() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnemployeeInsuranceRateItemSetting getPersonalSetting() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCareerGroup(CareerGroup careerGroup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCompanySetting(UnemployeeInsuranceRateItemSetting companySetting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPersonalSetting(UnemployeeInsuranceRateItemSetting personalSetting) {
		// TODO Auto-generated method stub
		
	}


}
