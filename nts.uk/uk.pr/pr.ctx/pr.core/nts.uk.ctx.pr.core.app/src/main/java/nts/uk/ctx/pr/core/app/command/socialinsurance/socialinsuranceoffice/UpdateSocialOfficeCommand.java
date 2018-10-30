package nts.uk.ctx.pr.core.app.command.socialinsurance.socialinsuranceoffice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSocialOfficeCommand {
	private String companyID;
	private String code;
	private String name;
	private String shortName;
	private String representativeName;
	private String representativePosition;
	private String memo;
	private String postalCode;
	private String address1;
	private String addressKana1;
	private String address2;
	private String addressKana2;
	private String phoneNumber;
	private Integer welfarePensionFundNumber;
	private String welfarePensionOfficeNumber;
	private Integer healthInsuranceOfficeNumber;
	private String healthInsuranceUnionOfficeNumber;
	private String healthInsuranceOfficeNumber1;
	private String healthInsuranceOfficeNumber2;
	private String welfarePensionOfficeNumber1;
	private String welfarePensionOfficeNumber2;
	private String healthInsuranceCityCode;
	private String healthInsuranceOfficeCode;
	private String welfarePensionCityCode;
	private String welfarePensionOfficeCode;
	private Integer healthInsurancePrefectureNo;
	private Integer welfarePensionPrefectureNo;
}
