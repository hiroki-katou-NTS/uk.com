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
public class JpaUnemployeeInsuranceRateItemOtherGetMemento implements UnemployeeInsuranceRateItemGetMemento {

	/** The type value. */
	protected QismtEmpInsuRate typeValue;

	/**
	 * Instantiates a new jpa unemployee insurance rate get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaUnemployeeInsuranceRateItemOtherGetMemento(QismtEmpInsuRate typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public CareerGroup getCareerGroup() {
		return CareerGroup.Other;
	}

	@Override
	public UnemployeeInsuranceRateItemSetting getCompanySetting() {
		UnemployeeInsuranceRateItemSetting unemployeeInsuranceRateItemSettingGeneral = new UnemployeeInsuranceRateItemSetting();
		unemployeeInsuranceRateItemSettingGeneral
				.setRate(Double.valueOf(String.valueOf(this.typeValue.getCEmpRateOther())));
		unemployeeInsuranceRateItemSettingGeneral
				.setRoundAtr(RoundingMethod.valueOf(this.typeValue.getCEmpRoundOther()));
		return unemployeeInsuranceRateItemSettingGeneral;
	}

	@Override
	public UnemployeeInsuranceRateItemSetting getPersonalSetting() {
		UnemployeeInsuranceRateItemSetting unemployeeInsuranceRateItemSettingGeneral = new UnemployeeInsuranceRateItemSetting();
		unemployeeInsuranceRateItemSettingGeneral
				.setRate(Double.valueOf(String.valueOf(this.typeValue.getPEmpRateOther())));
		unemployeeInsuranceRateItemSettingGeneral
				.setRoundAtr(RoundingMethod.valueOf(this.typeValue.getPEmpRoundOther()));
		return unemployeeInsuranceRateItemSettingGeneral;
	}

}
