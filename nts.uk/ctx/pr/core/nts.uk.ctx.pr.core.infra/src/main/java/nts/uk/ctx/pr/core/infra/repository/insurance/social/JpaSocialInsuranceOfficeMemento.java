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
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeMemento;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class JpaAggrSchemaMemento.
 */
public class JpaSocialInsuranceOfficeMemento implements SocialInsuranceOfficeMemento {

	// TODO: Object -> entity class.
	protected Object typeValue;

	/**
	 * Instantiates a new jpa aggr schema memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaSocialInsuranceOfficeMemento(Object typeValue) {
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
	public String getHealthInsuOfficeRefCode1st() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHealthInsuOfficeRefCode2nd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPensionOfficeRefCode1st() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPensionOfficeRefCode2nd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWelfarePensionFundCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOfficePensionFundCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHealthInsuCityCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHealthInsuOfficeSign() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPensionCityCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPensionOfficeSign() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHealthInsuOfficeCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHealthInsuAssoCode() {
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
	public void setHealthInsuOfficeRefCode1st(String healthInsuOfficeRefCode1st) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHealthInsuOfficeRefCode2nd(String healthInsuOfficeRefCode2nd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPensionOfficeRefCode1st(String pensionOfficeRefCode1st) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPensionOfficeRefCode2nd(String pensionOfficeRefCode2nd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWelfarePensionFundCode(String welfarePensionFundCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOfficePensionFundCode(String officePensionFundCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHealthInsuCityCode(String healthInsuCityCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHealthInsuOfficeSign(String healthInsuOfficeSign) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPensionCityCode(String pensionCityCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPensionOfficeSign(String pensionOfficeSign) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHealthInsuOfficeCode(String healthInsuOfficeCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHealthInsuAssoCode(String healthInsuAssoCode) {
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
