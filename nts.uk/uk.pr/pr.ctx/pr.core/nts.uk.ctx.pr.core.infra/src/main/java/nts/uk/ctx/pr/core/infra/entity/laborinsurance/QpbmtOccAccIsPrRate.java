package nts.uk.ctx.pr.core.infra.entity.laborinsurance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccInsurBusiBurdenRatio;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 労災保険料率
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_OCC_ACC_IS_PR_RATE")
public class QpbmtOccAccIsPrRate extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtOccAccIsPrRatePk occAccIsPrRatePk;
    
    /**
    * 開始年月
    */
    @Basic(optional = false)
    @Column(name = "START_YEAR_MONTH")
    public Integer startYearMonth;
    
    /**
    * 終了年月
    */
    @Basic(optional = false)
    @Column(name = "END_YEAR_MONTH")
    public Integer endYearMonth;
    
    /**
    * 事業主負担率
    */
    @Basic(optional = false)
    @Column(name = "EMP_CON_RATIO")
    public BigDecimal empConRatio;
    
    /**
    * 端数区分
    */
    @Basic(optional = false)
    @Column(name = "FRAC_CLASS")
    public int fracClass;
    
    @Override
    protected Object getKey() {
        return occAccIsPrRatePk;
    }
    
    public static List<QpbmtOccAccIsPrRate> toEntity(List<OccAccInsurBusiBurdenRatio> domain, String cId, YearMonthHistoryItem yearMonthHistory){
        List<QpbmtOccAccIsPrRate> listEmpInsurBusBurRatio = domain.stream().map(item -> {return new QpbmtOccAccIsPrRate(
                new QpbmtOccAccIsPrRatePk(cId, yearMonthHistory.identifier(), item.getOccAccInsurBusNo()),
                yearMonthHistory.start().v(),
                yearMonthHistory.end().v(),
                item.getEmpConRatio().v(),
                item.getFracClass().value);}).collect(Collectors.toList());
        return listEmpInsurBusBurRatio;
    }
    
    public static List<YearMonthHistoryItem> toDomainHis(List<QpbmtOccAccIsPrRate> entity){
        return entity.stream().map(item -> { return new YearMonthHistoryItem(
                item.occAccIsPrRatePk.historyId,
        		new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth)));}).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());

    }
}
