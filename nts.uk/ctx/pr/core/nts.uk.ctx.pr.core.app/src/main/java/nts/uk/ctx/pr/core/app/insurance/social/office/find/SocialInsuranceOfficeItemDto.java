package nts.uk.ctx.pr.core.app.insurance.social.office.find;

import lombok.Builder;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.Address;
import nts.uk.ctx.pr.core.dom.insurance.KanaAddress;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.OfficeName;
import nts.uk.ctx.pr.core.dom.insurance.PicName;
import nts.uk.ctx.pr.core.dom.insurance.PicPosition;
import nts.uk.ctx.pr.core.dom.insurance.PotalCode;
import nts.uk.ctx.pr.core.dom.insurance.ShortName;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeSetMemento;
import nts.uk.shr.com.primitive.Memo;

@Builder
public class SocialInsuranceOfficeItemDto implements SocialInsuranceOfficeSetMemento {
	/** The company code. */
	public String companyCode;

	/** The code. */
	public String code;

	/** The name. */
	public String name;

	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		this.companyCode = companyCode.v();
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
}
