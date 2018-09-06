package nts.uk.ctx.core.dom.socialinsurance.contribution;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.socialinsurance.AutoCalculationExecutionCls;
import nts.uk.ctx.core.dom.socialinsurance.InsuranceRate;

import java.math.BigDecimal;
import java.util.List;

/**
 * 拠出金率
 */
@Getter
public class ContributionRate extends AggregateRoot {

    /**
     * 履歴ID
     */
    private String historyId;

    /**
     * 子ども・子育て拠出金事業主負担率
     */
    private InsuranceRate childContributionRatio;

    /**
     * 自動計算区分
     */
    private AutoCalculationExecutionCls automaticCalculationCls;

    /**
     * 等級毎拠出金
     */
    private List<ContributionByGrade> contributionByGrade;

    /**
     * 拠出金率
     *
     * @param historyId               履歴ID
     * @param childContributionRatio  子ども・子育て拠出金事業主負担率
     * @param automaticCalculationCls 自動計算区分
     * @param contributionByGrade     等級毎拠出金
     */
    public ContributionRate(String historyId, BigDecimal childContributionRatio, int automaticCalculationCls, List<ContributionByGrade> contributionByGrade) {
        super();
        this.historyId = historyId;
        this.childContributionRatio = new InsuranceRate(childContributionRatio);
        this.automaticCalculationCls = EnumAdaptor.valueOf(automaticCalculationCls, AutoCalculationExecutionCls.class);
        this.contributionByGrade = contributionByGrade;
    }
}
