/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.labor;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.Address;
import nts.uk.ctx.pr.core.dom.insurance.KanaAddress;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.OfficeName;
import nts.uk.ctx.pr.core.dom.insurance.PicName;
import nts.uk.ctx.pr.core.dom.insurance.PicPosition;
import nts.uk.ctx.pr.core.dom.insurance.PotalCode;
import nts.uk.ctx.pr.core.dom.insurance.ShortName;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeSetMemento;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class JpaAggrSchemaMemento.
 */
public class JpaLaborInsuranceOfficeSetMemento implements LaborInsuranceOfficeSetMemento {

	// TODO: Object -> entity class.
	protected Object typeValue;

	/**
	 * Instantiates a new jpa aggr schema memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaLaborInsuranceOfficeSetMemento(Object typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCode(OfficeCode code) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setName(OfficeName name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setShortName(ShortName shortName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPicName(PicName picName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPicPosition(PicPosition picPosition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPotalCode(PotalCode potalCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPrefecture(String prefecture) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAddress1st(Address address1st) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAddress2nd(Address address2nd) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setKanaAddress1st(KanaAddress kanaAddress1st) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setKanaAddress2nd(KanaAddress kanaAddress2nd) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPhoneNumber(String phoneNumber) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCitySign(String citySign) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOfficeMark(String officeMark) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOfficeNoA(String officeNoA) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOfficeNoB(String officeNoB) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOfficeNoC(String officeNoC) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMemo(Memo memo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVersion(Long version) {
		// TODO Auto-generated method stub

	}

}
