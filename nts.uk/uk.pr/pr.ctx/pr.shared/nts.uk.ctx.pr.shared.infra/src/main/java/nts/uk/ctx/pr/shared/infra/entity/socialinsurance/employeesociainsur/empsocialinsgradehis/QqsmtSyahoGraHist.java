package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empsocialinsgradehis;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeInfo;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

/**
 * 社員社会保険等級履歴
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_SYAHO_GRA_HIST")
public class QqsmtSyahoGraHist extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QqsmtSyahoGraHistPk syahoGraHistPk;

    /**
     * 開始年月
     */
    @Basic(optional = false)
    @Column(name = "START_YM")
    public int startYm;

    /**
     * 終了年月
     */
    @Basic(optional = false)
    @Column(name = "END_YM")
    public int endYm;

    /**
     * 社会保険報酬月額(実質)
     */
    @Basic(optional = false)
    @Column(name = "SYAHO_HOSYU_REAL")
    public int syahoHosyuReal;

    /**
     * 健康保険等級
     */
    @Basic(optional = true)
    @Column(name = "KENHO_TOQ")
    public Integer kenhoToq;

    /**
     * 健康保険標準報酬月額
     */
    @Basic(optional = true)
    @Column(name = "KENHO_HOSYU")
    public Integer kenhoHosyu;

    /**
     * 厚生年金保険等級
     */
    @Basic(optional = true)
    @Column(name = "KOUHO_TOQ")
    public Integer kouhoToq;

    /**
     * 厚生年金保険標準報酬月額
     */
    @Basic(optional = true)
    @Column(name = "KOUHO_HOSYU")
    public Integer kouhoHosyu;

    /**
     * 算定区分
     */
    @Basic(optional = false)
    @Column(name = "CAL_ATR")
    public int calAtr;

    @Override
    protected Object getKey() {
        return syahoGraHistPk;
    }

    public EmpSocialInsGradeHis toHistoryDomain() {
        return new EmpSocialInsGradeHis(
                this.syahoGraHistPk.cid,
                this.syahoGraHistPk.sid,
                Arrays.asList(new YearMonthHistoryItem(this.syahoGraHistPk.histId, new YearMonthPeriod(new YearMonth(this.startYm), new YearMonth(this.endYm)))));
    }

    public EmpSocialInsGradeInfo toInfoDomain() {
        return new EmpSocialInsGradeInfo(
                this.syahoGraHistPk.histId,
                this.syahoHosyuReal,
                this.calAtr,
                this.kenhoHosyu,
                this.kenhoToq,
                this.kouhoHosyu,
                this.kouhoToq);
    }

    public static QqsmtSyahoGraHist toEntity(EmpSocialInsGradeHis history, EmpSocialInsGradeInfo info) {
        return new QqsmtSyahoGraHist(
                new QqsmtSyahoGraHistPk(history.getCompanyId(), history.getEmployeeId(), history.items().get(0).identifier()),
                history.items().get(0).span().start().v(),
                history.items().get(0).span().end().v(),
                info.getSocInsMonthlyRemune().v(),
                info.getHealInsGrade().map(PrimitiveValueBase::v).orElse(null),
                info.getHealInsStandMonthlyRemune().map(PrimitiveValueBase::v).orElse(null),
                info.getPensionInsGrade().map(PrimitiveValueBase::v).orElse(null),
                info.getPensionInsStandCompenMonthly().map(PrimitiveValueBase::v).orElse(null),
                info.getCalculationAtr().value);
    }
}
