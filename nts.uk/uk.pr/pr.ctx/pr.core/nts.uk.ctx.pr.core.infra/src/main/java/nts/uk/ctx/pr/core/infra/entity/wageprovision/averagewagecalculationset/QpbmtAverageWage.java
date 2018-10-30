package nts.uk.ctx.pr.core.infra.entity.wageprovision.averagewagecalculationset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.averagewagecalculationset.AverageWageCalculationSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 平均賃金計算設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_AVERAGE_WAGE")
public class QpbmtAverageWage extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtAverageWagePk averageWagePk;
    
    /**
    * 例外計算式割合
    */
    @Basic(optional = false)
    @Column(name = "EXCEPTION_FORMULA")
    public int exceptionFormula;
    
    /**
    * 出勤日数の取得方法
    */
    @Basic(optional = false)
    @Column(name = "OBTAIN_ATTENDANCE_DAYS")
    public int obtainAttendanceDays;
    
    /**
    * 日数端数処理方法
    */
    @Basic(optional = true)
    @Column(name = "DAYS_FRACTION_PROCESSING")
    public Integer daysFractionProcessing;
    
    /**
    * 小数点切捨区分
    */
    @Basic(optional = false)
    @Column(name = "DECIMAL_POINT_CUTOFF_SEGMENT")
    public int decimalPointCutoffSegment;
    
    @Override
    protected Object getKey()
    {
        return averageWagePk;
    }

    public AverageWageCalculationSet toDomain() {
        return new AverageWageCalculationSet(this.averageWagePk.cid, this.exceptionFormula, this.obtainAttendanceDays, this.daysFractionProcessing, this.decimalPointCutoffSegment);
    }
    public static QpbmtAverageWage toEntity(AverageWageCalculationSet domain) {
        return new QpbmtAverageWage(new QpbmtAverageWagePk(domain.getCId()),domain.getExceptionFormula().v(), domain.getDaysAttendance().getObtainAttendanceDays().value, domain.getDaysAttendance().getDaysFractionProcessing().map(i->i.value).orElse(null), domain.getDecimalPointCutoffSegment().value);
    }

}
