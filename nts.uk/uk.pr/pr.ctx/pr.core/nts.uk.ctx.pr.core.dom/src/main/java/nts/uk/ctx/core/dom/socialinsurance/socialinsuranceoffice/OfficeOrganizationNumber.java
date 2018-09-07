package nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

import java.util.Objects;
import java.util.Optional;

/**
 * 事業所整理番号
 */
@AllArgsConstructor
@Getter
public class OfficeOrganizationNumber extends DomainObject {

    /**
     * 健康保険事業所整理番号1
     */
    private Optional<HealthInsuranceOfficeNumber1> healthInsuranceOfficeNumber1;

    /**
     * 健康保険事業所整理番号2
     */
    private Optional<HealthInsuranceOfficeNumber2> healthInsuranceOfficeNumber2;

    /**
     * 厚生年金事業所整理番号1
     */
    private Optional<WelfarePensionOfficeNumber1> welfarePensionOfficeNumber1;

    /**
     * 厚生年金事業所整理番号2
     */
    private Optional<WelfarePensionOfficeNumber2> welfarePensionOfficeNumber2;

    /**
     * 事業所整理番号
     *
     * @param healthInsuranceOfficeNumber1 健康保険事業所整理番号1
     * @param healthInsuranceOfficeNumber2 健康保険事業所整理番号2
     * @param welfarePensionOfficeNumber1  厚生年金事業所整理番号1
     * @param welfarePensionOfficeNumber2  厚生年金事業所整理番号2
     */
    public OfficeOrganizationNumber(String healthInsuranceOfficeNumber1, String healthInsuranceOfficeNumber2, String welfarePensionOfficeNumber1, String welfarePensionOfficeNumber2) {
        this.healthInsuranceOfficeNumber1 = Objects.isNull(healthInsuranceOfficeNumber1) ? Optional.empty() : Optional.of(new HealthInsuranceOfficeNumber1(healthInsuranceOfficeNumber1));
        this.healthInsuranceOfficeNumber2 = Objects.isNull(healthInsuranceOfficeNumber2) ? Optional.empty() : Optional.of(new HealthInsuranceOfficeNumber2(healthInsuranceOfficeNumber2));
        this.welfarePensionOfficeNumber1 = Objects.isNull(welfarePensionOfficeNumber1) ? Optional.empty() : Optional.of(new WelfarePensionOfficeNumber1(welfarePensionOfficeNumber1));
        this.welfarePensionOfficeNumber2 = Objects.isNull(welfarePensionOfficeNumber2) ? Optional.empty() : Optional.of(new WelfarePensionOfficeNumber2(welfarePensionOfficeNumber2));
    }
}