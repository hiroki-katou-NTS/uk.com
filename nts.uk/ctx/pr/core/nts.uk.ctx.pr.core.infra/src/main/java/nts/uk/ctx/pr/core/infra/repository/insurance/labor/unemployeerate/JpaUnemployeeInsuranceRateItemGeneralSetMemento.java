/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.labor.unemployeerate;

import java.math.BigDecimal;

import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.CareerGroup;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateItemSetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateItemSetting;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.unemployeerate.QismtEmpInsuRate;

public class JpaUnemployeeInsuranceRateItemGeneralSetMemento implements UnemployeeInsuranceRateItemSetMemento {
	/** The type value. */
	protected QismtEmpInsuRate typeValue;

	/**
	 * Instantiates a new jpa unemployee insurance rate item general set
	 * memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaUnemployeeInsuranceRateItemGeneralSetMemento(QismtEmpInsuRate typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public void setCareerGroup(CareerGroup careerGroup) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setCompanySetting(UnemployeeInsuranceRateItemSetting companySetting) {
		this.typeValue.setCEmpRateGeneral(new BigDecimal(String.valueOf(companySetting.getRate())));
		this.typeValue.setCEmpRoundGeneral(companySetting.getRoundAtr().value);
	}

	@Override
	public void setPersonalSetting(UnemployeeInsuranceRateItemSetting personalSetting) {
		this.typeValue.setPEmpRateGeneral(new BigDecimal(String.valueOf(personalSetting.getRate())));
		this.typeValue.setPEmpRoundGeneral(personalSetting.getRoundAtr().value);
	}

}
