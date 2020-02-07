package nts.uk.ctx.pr.core.infra.entity.socialinsurance.socialinsuranceoffice;

import java.io.Serializable;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 社会保険用都道府県履歴
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_SOCIAL_INS_PRE_HIST")
public class QpbmtSocialInsurancePrefectureHistory extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 履歴ID
     */
    @Basic(optional = false)
    @Column(name = "HISTORY_ID")
    @Id
    public String historyId;

    /**
     * 年月期間
     */
    @Basic(optional = false)
    @Column(name = "START_YM")
    public int startYm;

    /**
     * 年月期間
     */
    @Basic(optional = false)
    @Column(name = "END_YM")
    public int endYm;

    @Override
    protected Object getKey() {
        return historyId;
    }

}
