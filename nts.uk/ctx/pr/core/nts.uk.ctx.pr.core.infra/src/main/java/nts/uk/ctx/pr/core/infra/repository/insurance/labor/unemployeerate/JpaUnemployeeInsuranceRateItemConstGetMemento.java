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
public class JpaUnemployeeInsuranceRateItemConstGetMemento implements UnemployeeInsuranceRateItemGetMemento {

	/** The type value. */
	protected QismtEmpInsuRate typeValue;

	/**
	 * Instantiates a new jpa unemployee insurance rate get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaUnemployeeInsuranceRateItemConstGetMemento(QismtEmpInsuRate typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public CareerGroup getCareerGroup() {
		return CareerGroup.Contruction;
	}

	@Override
	public UnemployeeInsuranceRateItemSetting getCompanySetting() {
		UnemployeeInsuranceRateItemSetting unemployeeInsuranceRateItemSettingGeneral = new UnemployeeInsuranceRateItemSetting();
		unemployeeInsuranceRateItemSettingGeneral
				.setRate(Double.valueOf(String.valueOf(this.typeValue.getCEmpRateConst())));
		unemployeeInsuranceRateItemSettingGeneral
				.setRoundAtr(RoundingMethod.valueOf(this.typeValue.getCEmpRoundConst()));
		return unemployeeInsuranceRateItemSettingGeneral;
	}

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
