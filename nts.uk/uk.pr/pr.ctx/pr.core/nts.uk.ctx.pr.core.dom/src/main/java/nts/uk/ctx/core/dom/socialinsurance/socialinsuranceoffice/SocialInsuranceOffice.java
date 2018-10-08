package nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

import java.util.Objects;
import java.util.Optional;

/**
 * 社会保険事業所
 */
@Getter
public class SocialInsuranceOffice extends AggregateRoot {

    /**
     * 会社ID
     */
    private String companyID;

    /**
     * コード
     */
    private SocialInsuranceOfficeCode code;

    /**
     * 名称
     */
    private SocialInsuranceOfficeName name;

    /**
     * 基本情報
     */
    private BasicInformation basicInformation;

    /**
     * 保険料マスタ情報
     */
    private InsuranceMasterInformation insuranceMasterInformation;

    public SocialInsuranceOffice(String companyID, String code, String name,
                                 String shortName, String representativeName, String representativePosition, String postalCode, String address1, String addressKana1, String address2, String addressKana2, String phoneNumber, String memo,
                                 Integer welfarePensionFundNumber, String welfarePensionOfficeNumber, Integer healthInsuranceOfficeNumber, String healthInsuranceUnionOfficeNumber,
                                 String healthInsuranceOfficeNumber1, String healthInsuranceOfficeNumber2, String welfarePensionOfficeNumber1, String welfarePensionOfficeNumber2,
                                 String healthInsuranceCityCode, String healthInsuranceOfficeCode, String welfarePensionCityCode, String welfarePensionOfficeCode, Integer healthInsurancePrefectureNo, Integer welfarePensionPrefectureNo) {
        this.companyID = companyID;
        this.code = new SocialInsuranceOfficeCode(code);
        this.name = new SocialInsuranceOfficeName(name);

        Optional<SocialInsuranceBusinessAddress> address = !Objects.isNull(postalCode) || !Objects.isNull(address1) || !Objects.isNull(addressKana1) || !Objects.isNull(address2) || !Objects.isNull(addressKana2) || !Objects.isNull(phoneNumber)
                ? Optional.of(new SocialInsuranceBusinessAddress(postalCode, address1, addressKana1, address2, addressKana2, phoneNumber)) : Optional.empty();
        this.basicInformation = new BasicInformation(shortName, representativeName, representativePosition, address, memo);

        OfficeOrganizationNumber officeOrganizeNumber = new OfficeOrganizationNumber(healthInsuranceOfficeNumber1, healthInsuranceOfficeNumber2, welfarePensionOfficeNumber1, welfarePensionOfficeNumber2);
        ForMagneticMedia forMagneticMedia = new ForMagneticMedia(healthInsuranceCityCode, healthInsuranceOfficeCode, welfarePensionCityCode, welfarePensionOfficeCode, healthInsurancePrefectureNo, welfarePensionPrefectureNo);
        this.insuranceMasterInformation = new InsuranceMasterInformation(welfarePensionFundNumber, welfarePensionOfficeNumber, healthInsuranceOfficeNumber, healthInsuranceUnionOfficeNumber, officeOrganizeNumber, forMagneticMedia);
    }
}