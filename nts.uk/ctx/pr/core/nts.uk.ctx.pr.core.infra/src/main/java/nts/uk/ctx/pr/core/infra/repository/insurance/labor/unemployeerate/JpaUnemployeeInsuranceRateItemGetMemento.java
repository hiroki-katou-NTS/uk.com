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
public class JpaUnemployeeInsuranceRateItemGetMemento implements UnemployeeInsuranceRateItemGetMemento {

	/** The type value. */
	private QismtEmpInsuRate typeValue;

	/** The career group. */
	private CareerGroup careerGroup;

	/**
	 * Instantiates a new jpa unemployee insurance rate item general get
	 * memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaUnemployeeInsuranceRateItemGetMemento(QismtEmpInsuRate typeValue, CareerGroup careerGroup) {
		this.typeValue = typeValue;
		this.careerGroup = careerGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateItemGetMemento#getCareerGroup()
	 */
	@Override
	public CareerGroup getCareerGroup() {
		return this.careerGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateItemGetMemento#getCompanySetting()
	 */
	@Override
	public UnemployeeInsuranceRateItemSetting getCompanySetting() {
		UnemployeeInsuranceRateItemSetting unemployeeInsuranceRateItemSetting;
		
		unemployeeInsuranceRateItemSetting = new UnemployeeInsuranceRateItemSetting();
		switch (this.careerGroup) {
		
		case Agroforestry:
			unemployeeInsuranceRateItemSetting
				.setRate(Double.valueOf(String.valueOf(this.typeValue.getCEmpRateGeneral())));
			unemployeeInsuranceRateItemSetting
				.setRoundAtr(RoundingMethod.valueOf(this.typeValue.getCEmpRoundGeneral()));
			break;

		case Contruction:
			unemployeeInsuranceRateItemSetting
				.setRate(Double.valueOf(String.valueOf(this.typeValue.getCEmpRateConst())));
			unemployeeInsuranceRateItemSetting
				.setRoundAtr(RoundingMethod.valueOf(this.typeValue.getCEmpRoundConst()));
			break;

		case Other:
			unemployeeInsuranceRateItemSetting
				.setRate(Double.valueOf(String.valueOf(this.typeValue.getCEmpRateOther())));
			unemployeeInsuranceRateItemSetting
				.setRoundAtr(RoundingMethod.valueOf(this.typeValue.getCEmpRoundOther()));
			break;
		}
		return unemployeeInsuranceRateItemSetting;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.
	 * UnemployeeInsuranceRateItemGetMemento#getPersonalSetting()
	 */
	@Override
	public UnemployeeInsuranceRateItemSetting getPersonalSetting() {
		UnemployeeInsuranceRateItemSetting unemployeeInsuranceRateItemSetting;
		
		unemployeeInsuranceRateItemSetting = new UnemployeeInsuranceRateItemSetting();
		switch (this.careerGroup) {
		case Agroforestry:
			unemployeeInsuranceRateItemSetting
				.setRate(Double.valueOf(String.valueOf(this.typeValue.getPEmpRateGeneral())));
			unemployeeInsuranceRateItemSetting
				.setRoundAtr(RoundingMethod.valueOf(this.typeValue.getPEmpRoundGeneral()));
			break;

		case Contruction:
			unemployeeInsuranceRateItemSetting
				.setRate(Double.valueOf(String.valueOf(this.typeValue.getPEmpRateConst())));
			unemployeeInsuranceRateItemSetting
				.setRoundAtr(RoundingMethod.valueOf(this.typeValue.getPEmpRoundConst()));
			break;

		case Other:
			unemployeeInsuranceRateItemSetting
				.setRate(Double.valueOf(String.valueOf(this.typeValue.getPEmpRateOther())));
			unemployeeInsuranceRateItemSetting
				.setRoundAtr(RoundingMethod.valueOf(this.typeValue.getPEmpRoundOther()));
			break;
		}
		return unemployeeInsuranceRateItemSetting;
	}

}
