package nts.uk.ctx.pr.core.infra.entity.socialinsurance.contribution;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 拠出金率
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtContributionRatePk implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会社ID
     */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;

    /**
     * 社会保険事業所コード
     */
    @Basic(optional = false)
    @Column(name = "SOCIAL_INSURANCE_OFFICE_CD")
    public String socialInsuranceOfficeCd;

    /**
     * 履歴ID
     */
    @Basic(optional = false)
    @Column(name = "HISTORY_ID")
    public String historyId;

}
