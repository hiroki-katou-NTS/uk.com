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
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.QismtLaborInsuOffice;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class JpaLaborInsuranceOfficeGetMemento.
 */
public class JpaLaborInsuranceOfficeGetMemento implements LaborInsuranceOfficeGetMemento {

	/** The type value. */
	protected QismtLaborInsuOffice typeValue;

	/**
	 * Instantiates a new jpa labor insurance office get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaLaborInsuranceOfficeGetMemento(QismtLaborInsuOffice typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento#
	 * getCompanyCode()
	 */
	@Override
	public CompanyCode getCompanyCode() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento#
	 * getCode()
	 */
	@Override
	public OfficeCode getCode() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento#
	 * getName()
	 */
	@Override
	public OfficeName getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento#
	 * getShortName()
	 */
	@Override
	public ShortName getShortName() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento#
	 * getPicName()
	 */
	@Override
	public PicName getPicName() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento#
	 * getPicPosition()
	 */
	@Override
	public PicPosition getPicPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento#
	 * getPotalCode()
	 */
	@Override
	public PotalCode getPotalCode() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento#
	 * getPrefecture()
	 */
	@Override
	public String getPrefecture() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento#
	 * getAddress1st()
	 */
	@Override
	public Address getAddress1st() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento#
	 * getAddress2nd()
	 */
	@Override
	public Address getAddress2nd() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento#
	 * getKanaAddress1st()
	 */
	@Override
	public KanaAddress getKanaAddress1st() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento#
	 * getKanaAddress2nd()
	 */
	@Override
	public KanaAddress getKanaAddress2nd() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento#
	 * getPhoneNumber()
	 */
	@Override
	public String getPhoneNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento#
	 * getCitySign()
	 */
	@Override
	public String getCitySign() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento#
	 * getOfficeMark()
	 */
	@Override
	public String getOfficeMark() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento#
	 * getOfficeNoA()
	 */
	@Override
	public String getOfficeNoA() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento#
	 * getOfficeNoB()
	 */
	@Override
	public String getOfficeNoB() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento#
	 * getOfficeNoC()
	 */
	@Override
	public String getOfficeNoC() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento#
	 * getMemo()
	 */
	@Override
	public Memo getMemo() {
		// TODO Auto-generated method stub
		return null;
	}

}
