package nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * 厚生年金端数区分
 */
@Getter
public class EmployeesPensionClassification extends DomainObject {

    /**
     * 個人端数区分
     */
    private InsurancePremiumFractionClassification personalFraction;

    /**
     * 事業主端数区分
     */
    private InsurancePremiumFractionClassification businessOwnerFraction;

    /**
     * 厚生年金端数区分
     *
     * @param personalFraction      個人端数区分
     * @param businessOwnerFraction 事業主端数区分
     */
    public EmployeesPensionClassification(int personalFraction, int businessOwnerFraction) {
        this.personalFraction      = EnumAdaptor.valueOf(personalFraction, InsurancePremiumFractionClassification.class);
        this.businessOwnerFraction = EnumAdaptor.valueOf(businessOwnerFraction, InsurancePremiumFractionClassification.class);
    }
}
