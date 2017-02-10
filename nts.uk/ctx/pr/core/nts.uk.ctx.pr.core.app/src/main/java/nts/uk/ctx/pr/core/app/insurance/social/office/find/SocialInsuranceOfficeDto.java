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
public class SocialInsuranceOfficeDto implements SocialInsuranceOfficeSetMemento {

	/** The company code. */
	public String companyCode;

	/** The code. */
	public String code;

	/** The name. */
	public String name;

	/** The short name. */
	public String shortName;

	/** The pic name. */
	public String picName;

	/** The pic position. */
	public String picPosition;

	/** The potal code. */
	public String potalCode;

	/** The prefecture. */
	public String prefecture;

	/** The address 1 st. */
	public String address1st;

	/** The address 2 nd. */
	public String address2nd;

	/** The kana address 1 st. */
	public String kanaAddress1st;

	/** The kana address 2 nd. */
	public String kanaAddress2nd;

	/** The phone number. */
	public String phoneNumber;

	/** The health insu office ref code 1 st. */
	public String healthInsuOfficeRefCode1st;

	/** The health insu office ref code 2 nd. */
	public String healthInsuOfficeRefCode2nd;

	/** The pension office ref code 1 st. */
	public String pensionOfficeRefCode1st;

	/** The pension office ref code 2 nd. */
	public String pensionOfficeRefCode2nd;

	/** The welfare pension fund code. */
	public String welfarePensionFundCode;

	/** The office pension fund code. */
	public String officePensionFundCode;

	/** The health insu city code. */
	public String healthInsuCityCode;

	/** The health insu office sign. */
	public String healthInsuOfficeSign;

	/** The pension city code. */
	public String pensionCityCode;

	/** The pension office sign. */
	public String pensionOfficeSign;

	/** The health insu office code. */
	public String healthInsuOfficeCode;

	/** The health insu asso code. */
	public String healthInsuAssoCode;

	/** The memo. */
	public String memo;

//	public static SocialInsuranceOfficeDto fromDomain(SocialInsuranceOffice domain) {
//		return new SocialInsuranceOfficeDto(domain.getCompanyCode().v(), domain.getCode().v(), domain.getName().v(),
//				domain.getShortName().v(), domain.getPicName().v(), domain.getPicPosition().v(),
//				domain.getPotalCode().v(), domain.getPrefecture(), domain.getAddress1st().v(),
//				domain.getAddress2nd().v(), domain.getKanaAddress1st().v(), domain.getKanaAddress2nd().v(),
//				domain.getPhoneNumber(), domain.getHealthInsuOfficeRefCode1st(), domain.getHealthInsuOfficeRefCode2nd(),
//				domain.getPensionOfficeRefCode1st(), domain.getPensionOfficeRefCode2nd(),
//				domain.getWelfarePensionFundCode(), domain.getOfficePensionFundCode(), domain.getHealthInsuCityCode(),
//				domain.getHealthInsuOfficeSign(), domain.getPensionCityCode(), domain.getPensionOfficeSign(),
//				domain.getHealthInsuOfficeCode(), domain.getHealthInsuAssoCode(), domain.getMemo().v());
//	}

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
	public void setPrefecture(String prefecture) {
		this.prefecture = prefecture;
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
	public void setPhoneNumber(String phoneNumber) {

		this.phoneNumber = phoneNumber;
	}

	@Override
	public void setHealthInsuOfficeRefCode1st(String healthInsuOfficeRefCode1st) {

		this.healthInsuOfficeRefCode1st = healthInsuOfficeRefCode1st;
	}

	@Override
	public void setHealthInsuOfficeRefCode2nd(String healthInsuOfficeRefCode2nd) {

		this.healthInsuOfficeRefCode2nd = healthInsuOfficeRefCode2nd;
	}

	@Override
	public void setPensionOfficeRefCode1st(String pensionOfficeRefCode1st) {

		this.pensionOfficeRefCode1st = pensionOfficeRefCode1st;
	}

	@Override
	public void setPensionOfficeRefCode2nd(String pensionOfficeRefCode2nd) {

		this.pensionOfficeRefCode2nd = pensionOfficeRefCode2nd;
	}

	@Override
	public void setWelfarePensionFundCode(String welfarePensionFundCode) {

		this.welfarePensionFundCode = welfarePensionFundCode;
	}

	@Override
	public void setOfficePensionFundCode(String officePensionFundCode) {

		this.officePensionFundCode = officePensionFundCode;
	}

	@Override
	public void setHealthInsuCityCode(String healthInsuCityCode) {

		this.healthInsuCityCode = healthInsuCityCode;
	}

	@Override
	public void setHealthInsuOfficeSign(String healthInsuOfficeSign) {

		this.healthInsuOfficeSign = healthInsuOfficeSign;
	}

	@Override
	public void setPensionCityCode(String pensionCityCode) {

		this.pensionCityCode = pensionCityCode;
	}

	@Override
	public void setPensionOfficeSign(String pensionOfficeSign) {

		this.pensionOfficeSign = pensionOfficeSign;
	}

	@Override
	public void setHealthInsuOfficeCode(String healthInsuOfficeCode) {

		this.healthInsuOfficeCode = healthInsuOfficeCode;
	}

	@Override
	public void setHealthInsuAssoCode(String healthInsuAssoCode) {

		this.healthInsuAssoCode = healthInsuAssoCode;
	}

	@Override
	public void setMemo(Memo memo) {

		this.memo = memo.v();
	}

	@Override
	public void setVersion(Long version) {
	}
}
