package nts.uk.ctx.core.infra.entity.socialinsurance.contribution;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionRate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 拠出金率
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_CONTRIBUTION_RATE")
public class QpbmtContributionRate extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 履歴ID
     */
    @Basic(optional = false)
    @Column(name = "HISTORY_ID")
    @Id
    public String historyId;

    /**
     * 自動計算区分
     */
    @Basic(optional = false)
    @Column(name = "AUTO_CALCULATION_CLS")
    public int autoCalculationCls;

    /**
     * 子ども・子育て拠出金事業主負担率
     */
    @Basic(optional = false)
    @Column(name = "CHILD_CARE_CONTRIBUTION_RATIO")
    public BigDecimal childCareContributionRatio;

    @Override
    protected Object getKey() {
        return historyId;
    }

    public static QpbmtContributionRate toEntity(ContributionRate domain) {
        return new QpbmtContributionRate(domain.getHistoryId(),
                domain.getAutomaticCalculationCls().value,
                domain.getChildContributionRatio().v());
    }
}
