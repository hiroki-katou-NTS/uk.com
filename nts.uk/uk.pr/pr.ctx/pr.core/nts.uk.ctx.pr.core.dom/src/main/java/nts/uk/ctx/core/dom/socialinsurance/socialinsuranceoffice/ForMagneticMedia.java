package nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice;

import java.util.Objects;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 磁気媒体用
 */
@Getter
public class ForMagneticMedia extends DomainObject {

    /**
     * 健康保険都市区符号
     */
    private Optional<CityCode> healthInsuranceCityCode;

    /**
     * 健康保険事業所記号
     */
    private Optional<OfficeCode> healthInsuranceOfficeCode;

    /**
     * 厚生年金都市区符号
     */
    private Optional<CityCode> welfarePensionCityCode;

    /**
     * 厚生年金事業所記号
     */
    private Optional<OfficeCode> welfarePensionOfficeCode;

    /**
     * 健康保険都道府県No
     */
    private Optional<Integer> healthInsurancePrefectureNo;

    /**
     * 厚生年金保険都道府県No
     */
    private Optional<Integer> welfarePensionPrefectureNo;

    /**
     * 磁気媒体用
     *
     * @param healthInsuranceCityCode     健康保険都市区符号
     * @param healthInsuranceOfficeCode   健康保険事業所記号
     * @param welfarePensionCityCode      厚生年金都市区符号
     * @param welfarePensionOfficeCode    厚生年金事業所記号
     * @param healthInsurancePrefectureNo 健康保険都道府県No
     * @param welfarePensionPrefectureNo  厚生年金保険都道府県No
     */
    public ForMagneticMedia(String healthInsuranceCityCode, String healthInsuranceOfficeCode, String welfarePensionCityCode, String welfarePensionOfficeCode, Integer healthInsurancePrefectureNo, Integer welfarePensionPrefectureNo) {
        this.healthInsuranceCityCode = Objects.isNull(healthInsuranceCityCode) ? Optional.empty() : Optional.of(new CityCode(healthInsuranceCityCode));
        this.healthInsuranceOfficeCode = Objects.isNull(healthInsuranceOfficeCode) ? Optional.empty() : Optional.of(new OfficeCode(healthInsuranceOfficeCode));
        this.welfarePensionCityCode = Objects.isNull(welfarePensionCityCode) ? Optional.empty() : Optional.of(new CityCode(welfarePensionCityCode));
        this.welfarePensionOfficeCode = Objects.isNull(welfarePensionOfficeCode) ? Optional.empty() : Optional.of(new OfficeCode(welfarePensionOfficeCode));
        this.healthInsurancePrefectureNo = Optional.ofNullable(healthInsurancePrefectureNo);
        this.welfarePensionPrefectureNo = Optional.ofNullable(welfarePensionPrefectureNo);
    }
}
