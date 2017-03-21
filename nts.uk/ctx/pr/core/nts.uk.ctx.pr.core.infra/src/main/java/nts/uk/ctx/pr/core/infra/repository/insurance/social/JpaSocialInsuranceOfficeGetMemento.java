/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.Address;
import nts.uk.ctx.pr.core.dom.insurance.KanaAddress;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.OfficeName;
import nts.uk.ctx.pr.core.dom.insurance.PicName;
import nts.uk.ctx.pr.core.dom.insurance.PicPosition;
import nts.uk.ctx.pr.core.dom.insurance.PotalCode;
import nts.uk.ctx.pr.core.dom.insurance.ShortName;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.QismtSocialInsuOffice;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class JpaSocialInsuranceOfficeGetMemento.
 */
public class JpaSocialInsuranceOfficeGetMemento implements SocialInsuranceOfficeGetMemento {

	/** The type value. */
	protected QismtSocialInsuOffice typeValue;

	/**
	 * Instantiates a new jpa social insurance office get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaSocialInsuranceOfficeGetMemento(QismtSocialInsuOffice typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getCompanyCode()
	 */
	@Override
	public CompanyCode getCompanyCode() {
		return new CompanyCode(this.typeValue.getQismtSocialInsuOfficePK().getCcd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getCode()
	 */
	@Override
	public OfficeCode getCode() {
		return new OfficeCode(this.typeValue.getQismtSocialInsuOfficePK().getSiOfficeCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getName()
	 */
	@Override
	public OfficeName getName() {
		return new OfficeName(this.typeValue.getSiOfficeName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getShortName()
	 */
	@Override
	public ShortName getShortName() {
		return new ShortName(this.typeValue.getSiOfficeAbName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getPicName()
	 */
	@Override
	public PicName getPicName() {
		return new PicName(this.typeValue.getPresidentName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getPicPosition()
	 */
	@Override
	public PicPosition getPicPosition() {
		return new PicPosition(this.typeValue.getPresidentTitle());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getPotalCode()
	 */
	@Override
	public PotalCode getPotalCode() {
		return new PotalCode(this.typeValue.getPostal());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getPrefecture()
	 */
	@Override
	public String getPrefecture() {
		return this.typeValue.getPrefecture();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getAddress1st()
	 */
	@Override
	public Address getAddress1st() {
		return new Address(this.typeValue.getAddress1());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getAddress2nd()
	 */
	@Override
	public Address getAddress2nd() {
		return new Address(this.typeValue.getAddress2());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getKanaAddress1st()
	 */
	@Override
	public KanaAddress getKanaAddress1st() {
		return new KanaAddress(this.typeValue.getKnAddress1());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getKanaAddress2nd()
	 */
	@Override
	public KanaAddress getKanaAddress2nd() {
		return new KanaAddress(this.typeValue.getKnAddress2());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getPhoneNumber()
	 */
	@Override
	public String getPhoneNumber() {
		return this.typeValue.getTelNo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getHealthInsuOfficeRefCode1st()
	 */
	@Override
	public String getHealthInsuOfficeRefCode1st() {
		return this.typeValue.getHealthInsuOfficeRefno1();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getHealthInsuOfficeRefCode2nd()
	 */
	@Override
	public String getHealthInsuOfficeRefCode2nd() {
		return this.typeValue.getHealthInsuOfficeRefno2();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getPensionOfficeRefCode1st()
	 */
	@Override
	public String getPensionOfficeRefCode1st() {
		return this.typeValue.getPensionOfficeRefno1();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getPensionOfficeRefCode2nd()
	 */
	@Override
	public String getPensionOfficeRefCode2nd() {
		return this.typeValue.getPensionOfficeRefno2();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getWelfarePensionFundCode()
	 */
	@Override
	public String getWelfarePensionFundCode() {
		return this.typeValue.getPensionFundNo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getOfficePensionFundCode()
	 */
	@Override
	public String getOfficePensionFundCode() {
		return this.typeValue.getPensionFundOfficeNo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getHealthInsuCityCode()
	 */
	@Override
	public String getHealthInsuCityCode() {
		return this.typeValue.getHealthInsuCityMark();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getHealthInsuOfficeSign()
	 */
	@Override
	public String getHealthInsuOfficeSign() {
		return this.typeValue.getHealthInsuOfficeMark();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getPensionCityCode()
	 */
	@Override
	public String getPensionCityCode() {
		return this.typeValue.getPensionCityMark();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getPensionOfficeSign()
	 */
	@Override
	public String getPensionOfficeSign() {
		return this.typeValue.getPensionOfficeMark();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getHealthInsuOfficeCode()
	 */
	@Override
	public String getHealthInsuOfficeCode() {
		return this.typeValue.getHealthInsuOfficeNo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getHealthInsuAssoCode()
	 */
	@Override
	public String getHealthInsuAssoCode() {
		return this.typeValue.getHealthInsuAssoNo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento#
	 * getMemo()
	 */
	@Override
	public Memo getMemo() {
		return new Memo(this.typeValue.getMemo());
	}

}
