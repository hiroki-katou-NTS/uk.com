package nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceClassification;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 厚生年金保険区分
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_WELFARE_PEN_INS_CLS")
public class QpbmtWelfarePensionInsuranceClassification extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 履歴ID
     */
    @Id
    @Column(name = "HISTORY_ID")
    private String historyId;

    /**
     * 厚生年金基金加入区分
     */
    @Basic(optional = false)
    @Column(name = "FUND_CLASSIFICATION")
    public int fundClassification;

    @Override
    protected Object getKey() {
        return historyId;
    }

    public WelfarePensionInsuranceClassification toDomain() {
        return new WelfarePensionInsuranceClassification(this.historyId, this.fundClassification);
    }

    public static QpbmtWelfarePensionInsuranceClassification toEntity(WelfarePensionInsuranceClassification domain) {
        return new QpbmtWelfarePensionInsuranceClassification(domain.getHistoryId(), domain.getFundClassification().value);
    }

}
