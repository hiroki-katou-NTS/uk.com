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
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class JpaAggrSchemaMemento.
 */
public class JpaLaborInsuranceOfficeGetMemento implements LaborInsuranceOfficeGetMemento {

	// TODO: Object -> entity class.
	protected Object typeValue;

	/**
	 * Instantiates a new jpa aggr schema memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaLaborInsuranceOfficeGetMemento(Object typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public CompanyCode getCompanyCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OfficeCode getCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OfficeName getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShortName getShortName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PicName getPicName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PicPosition getPicPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PotalCode getPotalCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPrefecture() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address getAddress1st() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address getAddress2nd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KanaAddress getKanaAddress1st() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KanaAddress getKanaAddress2nd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPhoneNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCitySign() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOfficeMark() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOfficeNoA() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOfficeNoB() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOfficeNoC() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Memo getMemo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

}
