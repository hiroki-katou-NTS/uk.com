/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor;

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
 * The Class LaborInsuranceOfficeMemento.
 *
 * @param <T>
 *            the generic type
 */
public interface LaborInsuranceOfficeSetMemento {

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
	 * Sets the city sign.
	 *
	 * @param citySign the new city sign
	 */
	void setCitySign(String citySign);

	/**
	 * Sets the office mark.
	 *
	 * @param officeMark the new office mark
	 */
	void setOfficeMark(String officeMark);

	/**
	 * Sets the office no A.
	 *
	 * @param officeNoA the new office no A
	 */
	void setOfficeNoA(String officeNoA);

	/**
	 * Sets the office no B.
	 *
	 * @param officeNoB the new office no B
	 */
	void setOfficeNoB(String officeNoB);

	/**
	 * Sets the office no C.
	 *
	 * @param officeNoC the new office no C
	 */
	void setOfficeNoC(String officeNoC);

	/**
	 * Sets the memo.
	 *
	 * @param memo the new memo
	 */
	void setMemo(Memo memo);

}
