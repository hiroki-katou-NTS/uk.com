package nts.uk.ctx.pr.core.app.finder.socialoffice.dto;

import lombok.Builder;
import lombok.Data;
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

@Data
@Builder
public class SocialInsuranceOfficeDto {

	/** The company code. */
	private CompanyCode companyCode;

	/** The code. */
	private OfficeCode code;

	/** The name. */
	private OfficeName name;

	/** The short name. */
	private ShortName shortName;

	/** The pic name. */
	private PicName picName;

	/** The pic position. */
	private PicPosition picPosition;

	/** The potal code. */
	private PotalCode potalCode;

	/** The prefecture. */
	private String prefecture;

	/** The address 1 st. */
	private Address address1st;

	/** The address 2 nd. */
	private Address address2nd;

	/** The kana address 1 st. */
	private KanaAddress kanaAddress1st;

	/** The kana address 2 nd. */
	private KanaAddress kanaAddress2nd;

	/** The phone number. */
	// TODO: TelephoneNo
	private String phoneNumber;

	/** The health insu office ref code 1 st. */
	private String healthInsuOfficeRefCode1st;

	/** The health insu office ref code 2 nd. */
	private String healthInsuOfficeRefCode2nd;

	/** The pension office ref code 1 st. */
	private String pensionOfficeRefCode1st;

	/** The pension office ref code 2 nd. */
	private String pensionOfficeRefCode2nd;

	/** The welfare pension fund code. */
	private String welfarePensionFundCode;

	/** The office pension fund code. */
	private String officePensionFundCode;

	/** The health insu city code. */
	private String healthInsuCityCode;

	/** The health insu office sign. */
	private String healthInsuOfficeSign;

	/** The pension city code. */
	private String pensionCityCode;

	/** The pension office sign. */
	private String pensionOfficeSign;

	/** The health insu office code. */
	private String healthInsuOfficeCode;

	/** The health insu asso code. */
	private String healthInsuAssoCode;

	/** The memo. */
	private Memo memo;
}
