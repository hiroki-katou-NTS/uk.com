/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.find.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.insurance.Address1;
import nts.uk.ctx.pr.core.dom.insurance.Address2;
import nts.uk.ctx.pr.core.dom.insurance.AddressKana1;
import nts.uk.ctx.pr.core.dom.insurance.AddressKana2;
import nts.uk.ctx.pr.core.dom.insurance.CitySign;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.OfficeMark;
import nts.uk.ctx.pr.core.dom.insurance.OfficeName;
import nts.uk.ctx.pr.core.dom.insurance.OfficeNoA;
import nts.uk.ctx.pr.core.dom.insurance.OfficeNoB;
import nts.uk.ctx.pr.core.dom.insurance.OfficeNoC;
import nts.uk.ctx.pr.core.dom.insurance.PhoneNumber;
import nts.uk.ctx.pr.core.dom.insurance.PicName;
import nts.uk.ctx.pr.core.dom.insurance.PicPosition;
import nts.uk.ctx.pr.core.dom.insurance.PotalCode;
import nts.uk.ctx.pr.core.dom.insurance.ShortName;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeSetMemento;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class LaborInsuranceOfficeFindOutDto.
 */
@Getter
@Setter
public class LaborInsuranceOfficeFindOutDto implements LaborInsuranceOfficeSetMemento {

	/** The code. */
	private String code;

	/** The name. */
	private String name;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeSetMemento#
	 * setCompanyCode(nts.uk.ctx.core.dom.company.CompanyCode)
	 */
	@Override
	public void setCompanyCode(String companyCode) {
		// Nothing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeSetMemento#
	 * setCode(nts.uk.ctx.pr.core.dom.insurance.OfficeCode)
	 */
	@Override
	public void setCode(OfficeCode code) {
		this.code = code.v();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeSetMemento#
	 * setName(nts.uk.ctx.pr.core.dom.insurance.OfficeName)
	 */
	@Override
	public void setName(OfficeName name) {
		this.name = name.v();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeSetMemento#
	 * setShortName(nts.uk.ctx.pr.core.dom.insurance.ShortName)
	 */
	@Override
	public void setShortName(ShortName shortName) {
		// Nothing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeSetMemento#
	 * setPicName(nts.uk.ctx.pr.core.dom.insurance.PicName)
	 */
	@Override
	public void setPicName(PicName picName) {
		// Nothing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeSetMemento#
	 * setPicPosition(nts.uk.ctx.pr.core.dom.insurance.PicPosition)
	 */
	@Override
	public void setPicPosition(PicPosition picPosition) {
		// Nothing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeSetMemento#
	 * setPotalCode(nts.uk.ctx.pr.core.dom.insurance.PotalCode)
	 */
	@Override
	public void setPotalCode(PotalCode potalCode) {
		// Nothing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeSetMemento#
	 * setAddress1st(nts.uk.ctx.pr.core.dom.insurance.Address)
	 */
	@Override
	public void setAddress1st(Address1 address1st) {
		// Nothing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeSetMemento#
	 * setAddress2nd(nts.uk.ctx.pr.core.dom.insurance.Address)
	 */
	@Override
	public void setAddress2nd(Address2 address2nd) {
		// Nothing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeSetMemento#
	 * setKanaAddress1st(nts.uk.ctx.pr.core.dom.insurance.KanaAddress)
	 */
	@Override
	public void setKanaAddress1st(AddressKana1 kanaAddress1st) {
		// Nothing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeSetMemento#
	 * setKanaAddress2nd(nts.uk.ctx.pr.core.dom.insurance.KanaAddress)
	 */
	@Override
	public void setKanaAddress2nd(AddressKana2 kanaAddress2nd) {
		// Nothing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeSetMemento#
	 * setPhoneNumber(nts.uk.ctx.pr.core.dom.insurance.PhoneNumber)
	 */
	@Override
	public void setPhoneNumber(PhoneNumber phoneNumber) {
		// Nothing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeSetMemento#
	 * setCitySign(nts.uk.ctx.pr.core.dom.insurance.CitySign)
	 */
	@Override
	public void setCitySign(CitySign citySign) {
		// Nothing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeSetMemento#
	 * setOfficeMark(nts.uk.ctx.pr.core.dom.insurance.OfficeMark)
	 */
	@Override
	public void setOfficeMark(OfficeMark officeMark) {
		// Nothing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeSetMemento#
	 * setOfficeNoA(nts.uk.ctx.pr.core.dom.insurance.OfficeNoA)
	 */
	@Override
	public void setOfficeNoA(OfficeNoA officeNoA) {
		// Nothing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeSetMemento#
	 * setOfficeNoB(nts.uk.ctx.pr.core.dom.insurance.OfficeNoB)
	 */
	@Override
	public void setOfficeNoB(OfficeNoB officeNoB) {
		// Nothing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeSetMemento#
	 * setOfficeNoC(nts.uk.ctx.pr.core.dom.insurance.OfficeNoC)
	 */
	@Override
	public void setOfficeNoC(OfficeNoC officeNoC) {
		// Nothing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeSetMemento#
	 * setMemo(nts.uk.shr.com.primitive.Memo)
	 */
	@Override
	public void setMemo(Memo memo) {
		// Nothing code

	}
}
