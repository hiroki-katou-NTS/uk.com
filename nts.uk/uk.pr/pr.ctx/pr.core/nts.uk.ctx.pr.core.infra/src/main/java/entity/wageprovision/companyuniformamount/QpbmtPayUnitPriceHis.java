package entity.wageprovision.companyuniformamount;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceHistory;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 給与会社単価
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_PAY_UNIT_PRICE_HIS")
public class QpbmtPayUnitPriceHis extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtPayUnitPriceHisPk payUnitPriceHisPk;
    
    /**
    * コード
    */
    @Basic(optional = false)
    @Column(name = "CODE")
    public String code;
    
    /**
    * 開始年月
    */
    @Basic(optional = false)
    @Column(name = "START_YEAR_MONTH")
    public int startYearMonth;
    
    /**
    * 終了年月
    */
    @Basic(optional = false)
    @Column(name = "END_YEAR_MONTH")
    public int endYearMonth;
    
    @Override
    protected Object getKey()
    {
        return payUnitPriceHisPk;
    }

    public PayrollUnitPriceHistory toDomain() {
        return new PayrollUnitPriceHistory(this.code,this.payUnitPriceHisPk.cid, new YearMonthHistoryItem(this.payUnitPriceHisPk.hisId , new YearMonthPeriod(new YearMonth(this.startYearMonth), new YearMonth(this.endYearMonth))));
    }
    public static QpbmtPayUnitPriceHis toEntity(PayrollUnitPriceHistory domain) {
        return new QpbmtPayUnitPriceHis(new QpbmtPayUnitPriceHisPk(domain.getCId(), domain.getHistory().identifier()),domain.getCode().v(), domain.getHistory().start().v(), domain.getHistory().end().v());
    }

}
