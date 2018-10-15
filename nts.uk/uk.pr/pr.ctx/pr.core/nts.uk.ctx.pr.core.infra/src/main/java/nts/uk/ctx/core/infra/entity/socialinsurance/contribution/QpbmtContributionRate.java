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
     * ID
     */
    @EmbeddedId
    public QpbmtContributionRatePk contributionRatePk;


    /**
     * 年月開始
     */
    @Basic(optional = false)
    @Column(name = "START_YEAR_MONTH")
    public int startYearMonth;

    /**
     * 年月終了
     */
    @Basic(optional = false)
    @Column(name = "END_YEAR_MONTH")
    public int endYearMonth;

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
        return contributionRatePk;
    }

    public static QpbmtContributionRate toEntity(ContributionRate domain) {
        return new QpbmtContributionRate(new QpbmtContributionRatePk(domain.getCid(),domain.getSocialInsuranceOfficeCd(),domain.getHistoryId()),
                domain.getEndYearMonth(),domain.getEndYearMonth(),
                domain.getAutomaticCalculationCls().value,
                domain.getChildContributionRatio().v());
    }
}
