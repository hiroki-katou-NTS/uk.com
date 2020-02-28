package nts.uk.ctx.pr.core.infra.entity.socialinsurance.welfarepensioninsurance;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceClassification;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
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
    @EmbeddedId
    public QpbmtWelfarePensionInsuranceClassificationPk welfarePenClsPk;

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
     * 厚生年金基金加入区分
     */
    @Basic(optional = false)
    @Column(name = "FUND_CLASSIFICATION")
    public int fundClassification;

    @Override
    protected Object getKey() {
        return welfarePenClsPk;
    }

    public WelfarePensionInsuranceClassification toDomain() {
        return new WelfarePensionInsuranceClassification(this.welfarePenClsPk.historyId, this.fundClassification);
    }

    public static QpbmtWelfarePensionInsuranceClassification toEntity(WelfarePensionInsuranceClassification domain, String officeCode, YearMonthHistoryItem yearMonth) {
        return new QpbmtWelfarePensionInsuranceClassification(new QpbmtWelfarePensionInsuranceClassificationPk(AppContexts.user().companyId(), officeCode, domain.getHistoryId()), yearMonth.start().v(), yearMonth.end().v(), domain.getFundClassification().value);
    }

}
