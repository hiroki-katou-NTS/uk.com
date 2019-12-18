package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empsocialinsgradehis;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeHis;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 社員社会保険等級履歴
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_EMP_SOC_INS_GRA_HIS")
public class QqsmtEmpSocialInsGradeHis extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QqsmtEmpSocialInsGradeHisPk qqsmtEmpSocialInsGradeHisPk;

    /**
     * 会社ID
     */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cId;

    /**
     * 社員ID
     */
    @Basic(optional = false)
    @Column(name = "SID")
    public String sId;

    /**
     * 期間.開始年月
     */
    @Basic(optional = false)
    @Column(name = "START_YM")
    public Integer startYM;

    /**
     * 期間.終了年月
     */
    @Basic(optional = false)
    @Column(name = "END_YM")
    public Integer endYM;

    @Override
    protected Object getKey() {
        return qqsmtEmpSocialInsGradeHisPk;
    }

    /*public static List<EmpSocialInsGradeHis> toDoMain(List<QqsmtEmpSocialInsGradeHis> entities) {

    }*/

    public static EmpSocialInsGradeHis toDomain(List<QqsmtEmpSocialInsGradeHis> entities) {
        if(entities.size() < 1){
            return null;
        }

        return new EmpSocialInsGradeHis(
                entities.get(0).cId,
                entities.get(0).sId,
                entities.stream().map(x -> new YearMonthHistoryItem(x.qqsmtEmpSocialInsGradeHisPk.historyId, new YearMonthPeriod(new YearMonth(x.startYM), new YearMonth(x.endYM)))).collect(Collectors.toList()));
    }

    public EmpSocialInsGradeHis toDomain() {
        List<YearMonthHistoryItem> date = new ArrayList<>();
        date.add(new YearMonthHistoryItem(this.qqsmtEmpSocialInsGradeHisPk.historyId, new YearMonthPeriod(new YearMonth(startYM), new YearMonth(endYM))));
        return new EmpSocialInsGradeHis(this.cId, this.sId, date);
    }

    public static QqsmtEmpSocialInsGradeHis toEntity(EmpSocialInsGradeHis domain) {
        return null;
    }
}
