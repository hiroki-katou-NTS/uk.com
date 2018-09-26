package nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

import java.util.Objects;
import java.util.Optional;

/**
 * 保険料マスタ情報
 */
@Getter
public class InsuranceMasterInformation extends DomainObject {

    /**
     * 厚生年金基金番号
     */
    private Optional<WelfarePensionFundNumber> welfarePensionFundNumber;

    /**
     * 厚生年金事業所番号
     */
    private Optional<WelfarePensionOfficeNumber> welfarePensionOfficeNumber;

    /**
     * 健康保険事業所番号
     */
    private Optional<HealthInsuranceOfficeNumber> healthInsuranceOfficeNumber;

    /**
     * 健保組合事務所番号
     */
    private Optional<HealthInsuranceUnionOfficeNumber> healthInsuranceUnionOfficeNumber;

    /**
     * 事業所整理番号
     */
    private OfficeOrganizationNumber officeOrganizeNumber;

    /**
     * 磁気媒体用
     */
    private ForMagneticMedia forMagneticMedia;

    /**
     * 保険料マスタ情報
     *
     * @param welfarePensionFundNumber         厚生年金基金番号
     * @param welfarePensionOfficeNumber       厚生年金事業所番号
     * @param healthInsuranceOfficeNumber      健康保険事業所番号
     * @param healthInsuranceUnionOfficeNumber 健保組合事務所番号
     * @param officeOrganizeNumber             事業所整理番号
     * @param forMagneticMedia                 磁気媒体用
     */
    public InsuranceMasterInformation(Integer welfarePensionFundNumber, String welfarePensionOfficeNumber, Integer healthInsuranceOfficeNumber, String healthInsuranceUnionOfficeNumber, OfficeOrganizationNumber officeOrganizeNumber, ForMagneticMedia forMagneticMedia) {
        this.welfarePensionFundNumber = Objects.isNull(welfarePensionFundNumber) ? Optional.empty() : Optional.of(new WelfarePensionFundNumber(welfarePensionFundNumber.toString()));
        this.welfarePensionOfficeNumber = Objects.isNull(welfarePensionOfficeNumber) ? Optional.empty() : Optional.of(new WelfarePensionOfficeNumber(welfarePensionOfficeNumber));
        this.healthInsuranceOfficeNumber = Objects.isNull(healthInsuranceOfficeNumber) ? Optional.empty() : Optional.of(new HealthInsuranceOfficeNumber(String.valueOf(healthInsuranceOfficeNumber)));
        this.healthInsuranceUnionOfficeNumber = Objects.isNull(healthInsuranceUnionOfficeNumber) ? Optional.empty() : Optional.of(new HealthInsuranceUnionOfficeNumber(healthInsuranceUnionOfficeNumber));
        this.officeOrganizeNumber = officeOrganizeNumber;
        this.forMagneticMedia = forMagneticMedia;
    }
}