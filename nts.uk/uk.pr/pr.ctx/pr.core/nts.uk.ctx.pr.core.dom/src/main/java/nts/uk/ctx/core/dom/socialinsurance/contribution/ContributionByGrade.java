package nts.uk.ctx.core.dom.socialinsurance.contribution;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.core.dom.socialinsurance.InsuranceFee;

/**
 * 等級毎拠出金
 */
@AllArgsConstructor
@Getter
public class ContributionByGrade extends DomainObject {

    /**
     * 厚生年金等級
     */
    private int welfarePensionGrade;

    /**
     * 子ども・子育て拠出金
     */
    private InsuranceFee childCareContribution;

    /**
     * 等級毎拠出金
     *
     * @param welfarePensionGrade   厚生年金等級
     * @param childCareContribution 子ども・子育て拠出金
     */
    public ContributionByGrade(int welfarePensionGrade, BigDecimal childCareContribution) {
        super();
        this.welfarePensionGrade = welfarePensionGrade;
        this.childCareContribution = new InsuranceFee(childCareContribution);
    }
}
