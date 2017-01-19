/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.Address;
import nts.uk.ctx.pr.core.dom.insurance.KanaAddress;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.OfficeName;
import nts.uk.ctx.pr.core.dom.insurance.PicName;
import nts.uk.ctx.pr.core.dom.insurance.PicPosition;
import nts.uk.ctx.pr.core.dom.insurance.PotalCode;
import nts.uk.ctx.pr.core.dom.insurance.ShortName;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Interface SocialInsuranceOfficeMemento.
 */
public interface SocialInsuranceOfficeMemento {

	/**
	 * Gets the company code.
	 *
	 * @return the company code
	 */
	CompanyCode getCompanyCode();

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	OfficeCode getCode();

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	OfficeName getName();

	/**
	 * Gets the short name.
	 *
	 * @return the short name
	 */
	ShortName getShortName();

	/**
	 * Gets the pic name.
	 *
	 * @return the pic name
	 */
	PicName getPicName();

	/**
	 * Gets the pic position.
	 *
	 * @return the pic position
	 */
	PicPosition getPicPosition();

	/**
	 * Gets the potal code.
	 *
	 * @return the potal code
	 */
	PotalCode getPotalCode();

	/**
	 * Gets the prefecture.
	 *
	 * @return the prefecture
	 */
	String getPrefecture();

	/**
	 * Gets the address 1 st.
	 *
	 * @return the address 1 st
	 */
	Address getAddress1st();

	/**
	 * Gets the address 2 nd.
	 *
	 * @return the address 2 nd
	 */
	Address getAddress2nd();

	/**
	 * Gets the kana address 1 st.
	 *
	 * @return the kana address 1 st
	 */
	KanaAddress getKanaAddress1st();

	/**
	 * Gets the kana address 2 nd.
	 *
	 * @return the kana address 2 nd
	 */
	KanaAddress getKanaAddress2nd();

	/**
	 * Gets the phone number.
	 *
	 * @return the phone number
	 */
	// TODO: TelephoneNo
	String getPhoneNumber();

	/**
	 * Gets the health insu office ref code 1 st.
	 *
	 * @return the health insu office ref code 1 st
	 */
	String getHealthInsuOfficeRefCode1st();

	/**
	 * Gets the health insu office ref code 2 nd.
	 *
	 * @return the health insu office ref code 2 nd
	 */
	String getHealthInsuOfficeRefCode2nd();

	/**
	 * Gets the pension office ref code 1 st.
	 *
	 * @return the pension office ref code 1 st
	 */
	String getPensionOfficeRefCode1st();

	/**
	 * Gte pension office ref code 2 nd.
	 *
	 * @return the string
	 */
	String getPensionOfficeRefCode2nd();

	/**
	 * Gets the welfare pension fund code.
	 *
	 * @return the welfare pension fund code
	 */
	String getWelfarePensionFundCode();

	/**
	 * Gets the office pension fund code.
	 *
	 * @return the office pension fund code
	 */
	String getOfficePensionFundCode();

	/**
	 * Gets the health insu city code.
	 *
	 * @return the health insu city code
	 */
	String getHealthInsuCityCode();

	/**
	 * Gets the health insu office sign.
	 *
	 * @return the health insu office sign
	 */
	String getHealthInsuOfficeSign();

	/**
	 * Gets the pension city code.
	 *
	 * @return the pension city code
	 */
	String getPensionCityCode();

	/**
	 * Gets the pension office sign.
	 *
	 * @return the pension office sign
	 */
	String getPensionOfficeSign();

	/**
	 * Gets the health insu office code.
	 *
	 * @return the health insu office code
	 */
	String getHealthInsuOfficeCode();

	/**
	 * Gets the health insu asso code.
	 *
	 * @return the health insu asso code
	 */
	String getHealthInsuAssoCode();

	/**
	 * Gets the memo.
	 *
	 * @return the memo
	 */
	Memo getMemo();

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	Long getVersion();

	/**
	 * Sets the company code.
	 *
	 * @param companyCode the new company code
	 */
	void setCompanyCode(CompanyCode companyCode);

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	void setCode(OfficeCode code);

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	void setName(OfficeName name);

	/**
	 * Sets the short name.
	 *
	 * @param shortName the new short name
	 */
	void setShortName(ShortName shortName);

	/**
	 * Sets the pic name.
	 *
	 * @param picName the new pic name
	 */
	void setPicName(PicName picName);

	/**
	 * Sets the pic position.
	 *
	 * @param picPosition the new pic position
	 */
	void setPicPosition(PicPosition picPosition);

	/**
	 * Sets the potal code.
	 *
	 * @param potalCode the new potal code
	 */
	void setPotalCode(PotalCode potalCode);

	/**
	 * Sets the prefecture.
	 *
	 * @param prefecture the new prefecture
	 */
	void setPrefecture(String prefecture);

	/**
	 * Sets the address 1 st.
	 *
	 * @param address1st the new address 1 st
	 */
	void setAddress1st(Address address1st);

	/**
	 * Sets the address 2 nd.
	 *
	 * @param address2nd the new address 2 nd
	 */
	void setAddress2nd(Address address2nd);

	/**
	 * Sets the kana address 1 st.
	 *
	 * @param kanaAddress1st the new kana address 1 st
	 */
	void setKanaAddress1st(KanaAddress kanaAddress1st);

	/**
	 * Sets the kana address 2 nd.
	 *
	 * @param kanaAddress2nd the new kana address 2 nd
	 */
	void setKanaAddress2nd(KanaAddress kanaAddress2nd);

	/**
	 * Sets the phone number.
	 *
	 * @param phoneNumber the new phone number
	 */
	void setPhoneNumber(String phoneNumber);

	/**
	 * Sets the health insu office ref code 1 st.
	 *
	 * @param healthInsuOfficeRefCode1st the new health insu office ref code 1 st
	 */
	void setHealthInsuOfficeRefCode1st(String healthInsuOfficeRefCode1st);

	/**
	 * Sets the health insu office ref code 2 nd.
	 *
	 * @param healthInsuOfficeRefCode2nd the new health insu office ref code 2 nd
	 */
	void setHealthInsuOfficeRefCode2nd(String healthInsuOfficeRefCode2nd);

	/**
	 * Sets the pension office ref code 1 st.
	 *
	 * @param pensionOfficeRefCode1st the new pension office ref code 1 st
	 */
	void setPensionOfficeRefCode1st(String pensionOfficeRefCode1st);

	/**
	 * Sets the pension office ref code 2 nd.
	 *
	 * @param pensionOfficeRefCode2nd the new pension office ref code 2 nd
	 */
	void setPensionOfficeRefCode2nd(String pensionOfficeRefCode2nd);

	/**
	 * Sets the welfare pension fund code.
	 *
	 * @param welfarePensionFundCode the new welfare pension fund code
	 */
	void setWelfarePensionFundCode(String welfarePensionFundCode);

	/**
	 * Sets the office pension fund code.
	 *
	 * @param officePensionFundCode the new office pension fund code
	 */
	void setOfficePensionFundCode(String officePensionFundCode);

	/**
	 * Sets the health insu city code.
	 *
	 * @param healthInsuCityCode the new health insu city code
	 */
	void setHealthInsuCityCode(String healthInsuCityCode);

	/**
	 * Sets the health insu office sign.
	 *
	 * @param healthInsuOfficeSign the new health insu office sign
	 */
	void setHealthInsuOfficeSign(String healthInsuOfficeSign);

	/**
	 * Sets the pension city code.
	 *
	 * @param pensionCityCode the new pension city code
	 */
	void setPensionCityCode(String pensionCityCode);

	/**
	 * Sets the pension office sign.
	 *
	 * @param pensionOfficeSign the new pension office sign
	 */
	void setPensionOfficeSign(String pensionOfficeSign);

	/**
	 * Sets the health insu office code.
	 *
	 * @param healthInsuOfficeCode the new health insu office code
	 */
	void setHealthInsuOfficeCode(String healthInsuOfficeCode);

	/**
	 * Sets the health insu asso code.
	 *
	 * @param healthInsuAssoCode the new health insu asso code
	 */
	void setHealthInsuAssoCode(String healthInsuAssoCode);

	/**
	 * Sets the memo.
	 *
	 * @param memo the new memo
	 */
	void setMemo(Memo memo);

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	void setVersion(Long version);

}
