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
 * The Class JpaUnemployeeInsuranceRateItemGeneralGetMemento.
 */
public class JpaUnemployeeInsuranceRateItemGeneralGetMemento
	implements UnemployeeInsuranceRateItemGetMemento {

	/** The type value. */
	protected QismtEmpInsuRate typeValue;

	/**
	 * Instantiates a new jpa unemployee insurance rate item general get
	 * memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaUnemployeeInsuranceRateItemGeneralGetMemento(QismtEmpInsuRate typeValue) {
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
		return CareerGroup.Agroforestry;
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
			.setRate(Double.valueOf(String.valueOf(this.typeValue.getCEmpRateGeneral())));
		unemployeeInsuranceRateItemSettingGeneral
			.setRoundAtr(RoundingMethod.valueOf(this.typeValue.getCEmpRoundGeneral()));
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
			.setRate(Double.valueOf(String.valueOf(this.typeValue.getPEmpRateGeneral())));
		unemployeeInsuranceRateItemSettingGeneral
			.setRoundAtr(RoundingMethod.valueOf(this.typeValue.getPEmpRoundGeneral()));
		return unemployeeInsuranceRateItemSettingGeneral;
	}

}
