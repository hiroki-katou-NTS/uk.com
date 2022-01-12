package nts.uk.ctx.at.function.infra.entity.supportworklist.aggregationsetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.StandardWorkplaceHierarchy;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportAggregationUnit;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkAggregationSetting;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.WorkplaceSupportJudgmentMethod;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Optional;

@Entity
@Table(name="KFNMT_SUP_WK_JUDGE")
@NoArgsConstructor
@AllArgsConstructor
public class KfnmtWplSupJudge extends ContractUkJpaEntity {
    @Id
    @Column(name = "CID")
    public String companyId;
    /**
     * 集計単位
     */
    @Column(name = "AGGREGATE_UNIT")
    public int aggregationUnit;

    /**
     * 判断方法
     */
    @Column(name = "SUP_JUDGE_ATR")
    public Integer judgmentMethod;

    /**
     * 基準職場階層
     */
    @Column(name = "WPL_HIREARCHY")
    public Integer standardWorkplaceHierarchy;

    @Override
    protected Object getKey() {
        return companyId;
    }

    public SupportWorkAggregationSetting toDomain() {
        return new SupportWorkAggregationSetting(
                EnumAdaptor.valueOf(aggregationUnit, SupportAggregationUnit.class),
                Optional.ofNullable(judgmentMethod == null ? null : EnumAdaptor.valueOf(judgmentMethod, WorkplaceSupportJudgmentMethod.class)),
                Optional.ofNullable(standardWorkplaceHierarchy == null ? null : new StandardWorkplaceHierarchy(standardWorkplaceHierarchy))
        );
    }
}
