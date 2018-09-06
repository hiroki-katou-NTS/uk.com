package nts.uk.ctx.core.infra.entity.socialinsurance.contribution;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 等級毎拠出金
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_CONTRIBUTION_BY_GRA")
public class QpbmtContributionByGrade extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtContributionByGradePk contributionByGradePk;

    /**
     * 子ども・子育て拠出金
     */
    @Basic(optional = false)
    @Column(name = "CHILD_CARE_CONTRIBUTION")
    public BigDecimal childCareContribution;

    @Override
    protected Object getKey() {
        return contributionByGradePk;
    }
}
