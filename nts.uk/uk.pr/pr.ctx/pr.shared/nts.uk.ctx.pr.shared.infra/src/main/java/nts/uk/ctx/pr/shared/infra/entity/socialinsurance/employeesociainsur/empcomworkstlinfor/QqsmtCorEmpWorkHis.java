package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empcomworkstlinfor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomworkstlinfor.CorEmpWorkHis;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
* 社保勤務形態履歴
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSDT_SYAHO_WORKFORM_INFO")
public class QqsmtCorEmpWorkHis extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqsmtCorEmpWorkHisPk corEmpWorkHisPk;
    
    /**
    * 被保険者区分
    */
    @Basic(optional = false)
    @Column(name = "INSURED_ATR")
    public int insPerCls;
    
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
    
    @Override
    protected Object getKey()
    {
        return corEmpWorkHisPk;
    }

    public static CorEmpWorkHis toDomainCorEmpWorkHis(List<QqsmtCorEmpWorkHis> corEmpWorkHis) {
        List<YearMonthHistoryItem> ymHistoryItem = corEmpWorkHis.stream().map(i -> { return
                new YearMonthHistoryItem(i.corEmpWorkHisPk.historyId, new YearMonthPeriod(new YearMonth(i.startYm), new YearMonth(i.endYm)));}).collect(Collectors.toList());
        return  new CorEmpWorkHis(corEmpWorkHis.get(0).corEmpWorkHisPk.empId, ymHistoryItem);
    }

}
