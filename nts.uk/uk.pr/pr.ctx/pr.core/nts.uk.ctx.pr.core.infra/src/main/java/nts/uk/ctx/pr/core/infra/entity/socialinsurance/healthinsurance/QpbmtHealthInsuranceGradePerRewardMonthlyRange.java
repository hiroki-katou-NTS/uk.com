package nts.uk.ctx.pr.core.infra.entity.socialinsurance.healthinsurance;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance.MonthlyHealthInsuranceCompensation;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 等級毎報酬月額範囲
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_HEALTH_REWARD_RANGE")
public class QpbmtHealthInsuranceGradePerRewardMonthlyRange extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtHealthInsuranceGradePerRewardMonthlyRangePk healthRewardRangePk;

    /**
     * 報酬月額下限
     */
    @Basic(optional = false)
    @Column(name = "REWARD_MONTHLY_LOWER_LIMIT")
    public long rewardMonthlyLowerLimit;

    /**
     * 報酬月額上限
     */
    @Basic(optional = false)
    @Column(name = "REWARD_MONTHLY_UPPER_LIMIT")
    public long rewardMonthlyUpperLimit;

    @Override
    protected Object getKey() {
        return healthRewardRangePk;
    }

    public static List<QpbmtHealthInsuranceGradePerRewardMonthlyRange> toEntity(MonthlyHealthInsuranceCompensation domain) {
        return domain.getHealthInsuranceGradePerRewardMonthlyRange().stream().map(x -> new QpbmtHealthInsuranceGradePerRewardMonthlyRange(
                new QpbmtHealthInsuranceGradePerRewardMonthlyRangePk(domain.getTargetPeriod().start().v(), domain.getTargetPeriod().end().v(), x.getHealthInsuranceGrade()),
                x.getRewardMonthlyLowerLimit(),
                x.getRewardMonthlyUpperLimit()
        )).collect(Collectors.toList());
    }
}
