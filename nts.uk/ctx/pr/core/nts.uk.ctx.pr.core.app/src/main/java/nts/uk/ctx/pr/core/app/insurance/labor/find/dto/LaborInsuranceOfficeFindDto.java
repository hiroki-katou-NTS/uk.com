/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.find.dto;

import lombok.Data;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.Address;
import nts.uk.ctx.pr.core.dom.insurance.CitySign;
import nts.uk.ctx.pr.core.dom.insurance.KanaAddress;
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
 * The Class LaborInsuranceOfficeDto.
 */
@Data
public class LaborInsuranceOfficeFindDto implements LaborInsuranceOfficeSetMemento {

	/** The code. officeCode */
	private String code;

	/** The name. officeName */
	private String name;

	/** The short name. */
	private String shortName;

	/** The pic name. */
	private String picName;

	/** The pic position. */
	private String picPosition;

	/** The potal code. */
	private String potalCode;

	/** The prefecture. */
	private String prefecture;

	/** The address 1 st. */
	private String address1st;

	/** The address 2 nd. */
	private String address2nd;

	/** The kana address 1 st. */
	private String kanaAddress1st;

	/** The kana address 2 nd. */
	private String kanaAddress2nd;

	/** The phone number. */
	private String phoneNumber;

	/** The city sign. */
	private String citySign;

	/** The office mark. */
	private String officeMark;

	/** The office no A. */
	private String officeNoA;

	/** The office no B. */
	private String officeNoB;

	/** The office no C. */
	private String officeNoC;

	/** The memo. */
	private String memo;

	/** The version. */
	private long version;

	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// Do nothing.
	}

	@Override
	public void setCode(OfficeCode code) {
		this.code = code.v();
	}

	@Override
	public void setName(OfficeName name) {
		this.name = name.v();
	}

	@Override
	public void setShortName(ShortName shortName) {
		this.shortName = shortName.v();
	}

	@Override
	public void setPicName(PicName picName) {
		this.picName = picName.v();

	}

	@Override
	public void setPicPosition(PicPosition picPosition) {
		this.picPosition = picPosition.v();
	}

	@Override
	public void setPotalCode(PotalCode potalCode) {
		this.potalCode = potalCode.v();
	}

	@Override
	public void setAddress1st(Address address1st) {
		this.address1st = address1st.v();
	}

	@Override
	public void setAddress2nd(Address address2nd) {
		this.address2nd = address2nd.v();
	}

	@Override
	public void setKanaAddress1st(KanaAddress kanaAddress1st) {
		this.kanaAddress1st = kanaAddress1st.v();
	}

	@Override
	public void setKanaAddress2nd(KanaAddress kanaAddress2nd) {
		this.kanaAddress2nd = kanaAddress2nd.v();
	}

	@Override
	public void setMemo(Memo memo) {
		this.memo = memo.v();
	}

	@Override
	public void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber.v();

	}

	@Override
	public void setCitySign(CitySign citySign) {
		this.citySign = citySign.v();
	}

	@Override
	public void setOfficeMark(OfficeMark officeMark) {
		this.officeMark = officeMark.v();
	}

	@Override
	public void setOfficeNoA(OfficeNoA officeNoA) {
		this.officeNoA = officeNoA.v();
	}

	@Override
	public void setOfficeNoB(OfficeNoB officeNoB) {
		this.officeNoB = officeNoB.v();

	}

	@Override
	public void setOfficeNoC(OfficeNoC officeNoC) {
		this.officeNoC = officeNoC.v();

	}

}
