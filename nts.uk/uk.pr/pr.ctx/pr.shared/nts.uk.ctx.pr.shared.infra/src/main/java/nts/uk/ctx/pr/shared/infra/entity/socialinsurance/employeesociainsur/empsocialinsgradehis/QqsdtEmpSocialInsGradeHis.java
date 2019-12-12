package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empsocialinsgradehis;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.GenericHistYMPeriod;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
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
public class QqsdtEmpSocialInsGradeHis extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QqsdtEmpSocialInsGradeHisPk qqsdtEmpSocialInsGradeHisPk;

    /**
     * 社員ID
     */
    @Basic(optional = false)
    @Column(name = "SID")
    private String sId;

    /**
     * 期間.開始年月
     */
    @Basic(optional = false)
    @Column(name = "START_YEAR_MON")
    public GeneralDate startDate;

    /**
     * 期間.終了年月
     */
    @Basic(optional = false)
    @Column(name = "END_YEAR_MON")
    public GeneralDate endDate;

    @Override
    protected Object getKey() {
        return qqsdtEmpSocialInsGradeHisPk;
    }

    public static EmpSocialInsGradeHis toDomain(List<QqsdtEmpSocialInsGradeHis> entities) {
        if(entities.size() < 1){
            return null;
        }

        return new EmpSocialInsGradeHis(
                entities.get(0).sId,
                entities.stream().map(x -> new GenericHistYMPeriod(x.qqsdtEmpSocialInsGradeHisPk.historyId, new DateHistoryItem(x.qqsdtEmpSocialInsGradeHisPk.historyId, new DatePeriod(x.startDate, x.endDate)))).collect(Collectors.toList()));
    }

    public EmpSocialInsGradeHis toDomain() {
        List<GenericHistYMPeriod> date = new ArrayList<>();
        date.add(new GenericHistYMPeriod(this.qqsdtEmpSocialInsGradeHisPk.historyId, new DateHistoryItem(this.qqsdtEmpSocialInsGradeHisPk.historyId, new DatePeriod(this.startDate, endDate))));
        return new EmpSocialInsGradeHis(this.sId, date);
    }

    public static QqsdtEmpSocialInsGradeHis toEntity(EmpSocialInsGradeHis domain) {
        return null;
    }
}
