package nts.uk.ctx.pr.core.infra.entity.socialinsurance.welfarepensioninsurance;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 等級毎報酬月額範囲
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_PEN_REWARD_RANGE")
public class QpbmtWelfarePensionGradePerRewardMonthlyRange extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtWelfarePensionGradePerRewardMonthlyRangePk penRewardRangePk;

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
        return penRewardRangePk;
    }

    public static QpbmtWelfarePensionGradePerRewardMonthlyRange toEntity(int targetStartYm, int targetEndYm, int welfarePensionGrade, long rewardMonthlyLowerLimit, long rewardMonthlyUpperLimit) {
        return new QpbmtWelfarePensionGradePerRewardMonthlyRange(new QpbmtWelfarePensionGradePerRewardMonthlyRangePk(targetStartYm, targetEndYm, welfarePensionGrade), rewardMonthlyLowerLimit, rewardMonthlyUpperLimit);
    }
}
