package nts.uk.ctx.pr.core.app.find.insurance.social.dto;

import lombok.Data;
import nts.uk.ctx.pr.core.dom.insurance.KanaAddress;
import nts.uk.shr.com.primitive.Memo;

@Data
public class SocialInsuranceOfficeDto {

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
	public Memo memo;
}
