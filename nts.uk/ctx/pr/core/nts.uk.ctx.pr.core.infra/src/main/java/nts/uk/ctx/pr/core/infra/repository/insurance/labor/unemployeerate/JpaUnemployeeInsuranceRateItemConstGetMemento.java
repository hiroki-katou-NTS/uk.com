/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.labor.unemployeerate;

import nts.uk.ctx.pr.core.dom.insurance.RoundingMethod;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.CareerGroup;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateItemGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateItemSetting;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.unemployeerate.QismtEmpInsuRate;

/**
 * The Class JpaUnemployeeInsuranceRateItemConstGetMemento.
 */
public class JpaUnemployeeInsuranceRateItemConstGetMemento implements UnemployeeInsuranceRateItemGetMemento {

	/** The type value. */
	protected QismtEmpInsuRate typeValue;

	/**
	 * Instantiates a new jpa unemployee insurance rate item const get memento.
	 *
	 * @param typeValue the type value
	 */
	public JpaUnemployeeInsuranceRateItemConstGetMemento(QismtEmpInsuRate typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateItemGetMemento#getCareerGroup()
	 */
	@Override
	public CareerGroup getCareerGroup() {
		return CareerGroup.Contruction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateItemGetMemento#getCompanySetting()
	 */
	@Override
	public UnemployeeInsuranceRateItemSetting getCompanySetting() {
		UnemployeeInsuranceRateItemSetting unemployeeInsuranceRateItemSettingGeneral = new UnemployeeInsuranceRateItemSetting();
		unemployeeInsuranceRateItemSettingGeneral
			.setRate(Double.valueOf(String.valueOf(this.typeValue.getCEmpRateConst())));
		unemployeeInsuranceRateItemSettingGeneral
			.setRoundAtr(RoundingMethod.valueOf(this.typeValue.getCEmpRoundConst()));
		return unemployeeInsuranceRateItemSettingGeneral;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateItemGetMemento#getPersonalSetting()
	 */
	@Override
	public UnemployeeInsuranceRateItemSetting getPersonalSetting() {
		UnemployeeInsuranceRateItemSetting unemployeeInsuranceRateItemSettingGeneral = new UnemployeeInsuranceRateItemSetting();
		unemployeeInsuranceRateItemSettingGeneral
			.setRate(Double.valueOf(String.valueOf(this.typeValue.getPEmpRateConst())));
		unemployeeInsuranceRateItemSettingGeneral
			.setRoundAtr(RoundingMethod.valueOf(this.typeValue.getPEmpRoundConst()));
		return unemployeeInsuranceRateItemSettingGeneral;
	}

}
