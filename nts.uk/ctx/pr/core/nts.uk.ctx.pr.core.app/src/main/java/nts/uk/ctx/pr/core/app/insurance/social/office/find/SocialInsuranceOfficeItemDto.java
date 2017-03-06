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

	}

	@Override
	public void setPicName(PicName picName) {

	}

	@Override
	public void setPicPosition(PicPosition picPosition) {

	}

	@Override
	public void setPotalCode(PotalCode potalCode) {

	}

	@Override
	public void setPrefecture(String prefecture) {

	}

	@Override
	public void setAddress1st(Address address1st) {

	}

	@Override
	public void setAddress2nd(Address address2nd) {

	}

	@Override
	public void setKanaAddress1st(KanaAddress kanaAddress1st) {

	}

	@Override
	public void setKanaAddress2nd(KanaAddress kanaAddress2nd) {

	}

	@Override
	public void setPhoneNumber(String phoneNumber) {

	}

	@Override
	public void setHealthInsuOfficeRefCode1st(String healthInsuOfficeRefCode1st) {

	}

	@Override
	public void setHealthInsuOfficeRefCode2nd(String healthInsuOfficeRefCode2nd) {

	}

	@Override
	public void setPensionOfficeRefCode1st(String pensionOfficeRefCode1st) {

	}

	@Override
	public void setPensionOfficeRefCode2nd(String pensionOfficeRefCode2nd) {

	}

	@Override
	public void setWelfarePensionFundCode(String welfarePensionFundCode) {

	}

	@Override
	public void setOfficePensionFundCode(String officePensionFundCode) {

	}

	@Override
	public void setHealthInsuCityCode(String healthInsuCityCode) {

	}

	@Override
	public void setHealthInsuOfficeSign(String healthInsuOfficeSign) {

	}

	@Override
	public void setPensionCityCode(String pensionCityCode) {

	}

	@Override
	public void setPensionOfficeSign(String pensionOfficeSign) {

	}

	@Override
	public void setHealthInsuOfficeCode(String healthInsuOfficeCode) {

	}

	@Override
	public void setHealthInsuAssoCode(String healthInsuAssoCode) {

	}

	@Override
	public void setMemo(Memo memo) {

	}
}
