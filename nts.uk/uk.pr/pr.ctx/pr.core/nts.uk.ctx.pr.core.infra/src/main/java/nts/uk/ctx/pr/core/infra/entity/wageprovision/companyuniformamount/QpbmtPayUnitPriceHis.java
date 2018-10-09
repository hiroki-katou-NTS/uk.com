package nts.uk.ctx.pr.core.infra.entity.wageprovision.companyuniformamount;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 給与会社単価履歴
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


    public static QpbmtPayUnitPriceHis toEntity(YearMonthHistoryItem domain, String cId,String code) {
        return new QpbmtPayUnitPriceHis(new QpbmtPayUnitPriceHisPk(cId, code,domain.identifier()), domain.start().v(), domain.end().v());
    }


}
